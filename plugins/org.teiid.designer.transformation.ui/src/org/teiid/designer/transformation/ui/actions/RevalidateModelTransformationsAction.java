/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.transformation.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mapping.MappingHelper;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.teiid.designer.core.ModelerCore;
import org.teiid.designer.core.metamodel.aspect.sql.SqlAspect;
import org.teiid.designer.core.metamodel.aspect.sql.SqlTableAspect;
import org.teiid.designer.core.query.QueryValidationResult;
import org.teiid.designer.core.query.QueryValidator;
import org.teiid.designer.core.workspace.ModelResource;
import org.teiid.designer.core.workspace.ModelUtil;
import org.teiid.designer.core.workspace.ModelWorkspaceException;
import org.teiid.designer.metamodels.transformation.SqlTransformation;
import org.teiid.designer.metamodels.transformation.SqlTransformationMappingRoot;
import org.teiid.designer.transformation.ui.PluginConstants;
import org.teiid.designer.transformation.ui.UiConstants;
import org.teiid.designer.transformation.ui.UiPlugin;
import org.teiid.designer.transformation.ui.editors.TransformationObjectEditorPage;
import org.teiid.designer.transformation.util.SqlMappingRootCache;
import org.teiid.designer.transformation.util.TransformationHelper;
import org.teiid.designer.transformation.util.TransformationMappingHelper;
import org.teiid.designer.transformation.validation.TransformationValidator;
import org.teiid.designer.ui.actions.ISelectionAction;
import org.teiid.designer.ui.common.eventsupport.SelectionUtilities;
import org.teiid.designer.ui.common.widget.ListMessageDialog;
import org.teiid.designer.ui.editors.ModelEditorManager;
import org.teiid.designer.ui.editors.ModelObjectEditorPage;
import org.teiid.designer.ui.editors.MultiPageModelEditor;
import org.teiid.designer.ui.viewsupport.ModelObjectUtilities;
import org.teiid.designer.ui.viewsupport.ModelUtilities;


/**
 * @since 8.0
 */
public class RevalidateModelTransformationsAction extends Action implements ISelectionListener, Comparable, ISelectionAction {

    private ModelResource modelResource;
    List brokenTables = Collections.EMPTY_LIST;
    private static final String REVALIDATE_TRANS = "Revalidate Transformations"; //$NON-NLS-1$

    public RevalidateModelTransformationsAction() {
        super();
        setImageDescriptor(UiPlugin.getDefault().getImageDescriptor(PluginConstants.Images.REVALIDATE_TRANSFORMATION_ICON));
    }

    @Override
	public void selectionChanged( IWorkbenchPart part,
                                  ISelection selection ) {
        Object selectedObject = SelectionUtilities.getSelectedObject(selection);
        boolean enable = false;
        if (selectedObject instanceof IResource && ModelUtilities.isModelFile((IResource)selectedObject)) {
            try {
                this.modelResource = ModelUtil.getModelResource(((IFile)selectedObject), false);
                if (ModelUtilities.isVirtual(this.modelResource)) {
                    enable = true;
                }
            } catch (ModelWorkspaceException e) {
                UiConstants.Util.log(e);
            }
        }
        setEnabled(enable);
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run() {
        //
        // Get the currently active ModelObjectEditorPage, ensure its TransformationObjEditorPage
        //
        IEditorPart editor = UiPlugin.getDefault().getCurrentWorkbenchWindow().getActivePage().getActiveEditor();
        if (editor != null && editor instanceof MultiPageModelEditor) {
            ModelObjectEditorPage moep = ((MultiPageModelEditor)editor).getActiveObjectEditor();
            if (moep != null && moep instanceof TransformationObjectEditorPage) {
                ((MultiPageModelEditor)editor).closeObjectEditor();
            }
        }
        // Force the modelEditor open, so that changes mark the resource dirty
        IResource resource = modelResource.getResource();
        ModelEditorManager.getModelEditorForFile((IFile)resource, true);

        final WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
            @Override
            public void execute( IProgressMonitor theMonitor ) {
                brokenTables = revalidate();
                theMonitor.done();
            }
        };
        try {
            new ProgressMonitorDialog(Display.getCurrent().getActiveShell()).run(true, true, op);
        } catch (InterruptedException e) {
        } catch (InvocationTargetException e) {
            UiConstants.Util.log(e.getTargetException());
        }
        warnUserAboutInvalidTransformations(brokenTables);
        brokenTables = Collections.EMPTY_LIST;
    }

    List revalidate() {
        // Keep list of problems
        List brokenSqlTables = new ArrayList();

        if (modelResource != null) {
            // shut off transformation notifications
            UiPlugin.getDefault().setIgnoreTransformationNotifications(true);

            // start txn
            boolean requiredStart = ModelerCore.startTxn(true, false, REVALIDATE_TRANS, this);
            boolean succeeded = false;
            try {

                Object nextObj = null;
                List transformations = modelResource.getModelTransformations().getTransformations();

                // Clean out the SqlMappingRootCache for these transformations
                invalidateSqlRootCache(transformations);

                // Iterate all of the transformation mapping roots
                for (Iterator iter = transformations.iterator(); iter.hasNext();) {
                    nextObj = iter.next();
                    if (TransformationHelper.isSqlTransformationMappingRoot(nextObj)) {
                        // Get current mapping root and query validator
                        SqlTransformationMappingRoot mappingRoot = (SqlTransformationMappingRoot)nextObj;
                        QueryValidator qv = new TransformationValidator(mappingRoot, false);

                        // Check if INSERT/UPDATE/DELETE should be validated
                        boolean supportsUpdates = false;
                        EObject mRootTarget = mappingRoot.getTarget();
                        SqlAspect sqlAspect = org.teiid.designer.core.metamodel.aspect.sql.SqlAspectHelper.getSqlAspect(mRootTarget);
                        if (sqlAspect != null && (sqlAspect instanceof SqlTableAspect)) {
                            supportsUpdates = ((SqlTableAspect)sqlAspect).supportsUpdate(mRootTarget);
                        }

                        // Init query valid flags - will be set to false if problems.
                        boolean selectValid = true;
                        boolean insertValid = true;
                        boolean updateValid = true;
                        boolean deleteValid = true;

                        // Reset Select
                        selectValid = resetTransformation(mappingRoot, QueryValidator.SELECT_TRNS, qv);

                        // Reset Insert, Update, Delete
                        if (supportsUpdates) {
                            // RESET INSERT
                            insertValid = resetTransformation(mappingRoot, QueryValidator.INSERT_TRNS, qv);
                            // RESET UPDATE
                            updateValid = resetTransformation(mappingRoot, QueryValidator.UPDATE_TRNS, qv);
                            // RESET DELETE
                            deleteValid = resetTransformation(mappingRoot, QueryValidator.DELETE_TRNS, qv);
                        } else {
                            clearUUIDSqlString(mappingRoot, QueryValidator.INSERT_TRNS);
                            clearUUIDSqlString(mappingRoot, QueryValidator.UPDATE_TRNS);
                            clearUUIDSqlString(mappingRoot, QueryValidator.DELETE_TRNS);
                        }

                        // If any of the components have a problem, add to broken table list.
                        if (!selectValid || !insertValid || !updateValid || !deleteValid) {
                            brokenSqlTables.add(mRootTarget);
                        }
                    }
                }
                succeeded = true;
            } catch (ModelWorkspaceException e) {
                UiConstants.Util.log(e);
            } finally {
                if (requiredStart) {
                    if (succeeded) {
                        ModelerCore.commitTxn();
                    } else {
                        ModelerCore.rollbackTxn();
                    }
                }
                // re-enable transformation notifications
                UiPlugin.getDefault().setIgnoreTransformationNotifications(false);
            }
        }

        return brokenSqlTables;
    }

    private boolean resetTransformation( final SqlTransformationMappingRoot mappingRoot,
                                         final int cmdType,
                                         final QueryValidator qv ) {
    	
        String userSqlString = TransformationHelper.getSqlString(mappingRoot, cmdType);
        setUserString(mappingRoot, userSqlString, cmdType);
        TransformationMappingHelper.reconcileMappingsOnSqlChange(mappingRoot, null);
        QueryValidationResult result = qv.validateSql(userSqlString, cmdType, false);

        return result.isParsable() && result.isResolvable() && result.isValidatable();
    }

    private void clearUUIDSqlString( final SqlTransformationMappingRoot mappingRoot,
                                     final int cmdType ) {
        if (mappingRoot != null) {
            MappingHelper helper = mappingRoot.getHelper();
            if (helper != null && helper instanceof SqlTransformation) {
                SqlTransformation sqlHelper = (SqlTransformation)helper;
                switch (cmdType) {
                    case QueryValidator.SELECT_TRNS:
                        sqlHelper.setSelectSql(null);
                        break;
                    case QueryValidator.INSERT_TRNS:
                        if (sqlHelper.isInsertAllowed() && !sqlHelper.isInsertSqlDefault()) {
                            sqlHelper.setInsertSql(null);
                        }
                        break;
                    case QueryValidator.UPDATE_TRNS:
                        if (sqlHelper.isUpdateAllowed() && !sqlHelper.isUpdateSqlDefault()) {
                            sqlHelper.setUpdateSql(null);
                        }
                        break;
                    case QueryValidator.DELETE_TRNS:
                        if (sqlHelper.isDeleteAllowed() && !sqlHelper.isDeleteSqlDefault()) {
                            sqlHelper.setDeleteSql(null);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void setUserString( final SqlTransformationMappingRoot mappingRoot,
                                final String userSqlStr,
                                final int cmdType ) {
        if (cmdType == QueryValidator.SELECT_TRNS) {
            TransformationHelper.setSelectSqlUserString(mappingRoot, userSqlStr, false, false, this);
        } else if (cmdType == QueryValidator.INSERT_TRNS) {
            TransformationHelper.setInsertSqlUserString(mappingRoot, userSqlStr, false, false, this);
        } else if (cmdType == QueryValidator.UPDATE_TRNS) {
            TransformationHelper.setUpdateSqlUserString(mappingRoot, userSqlStr, false, false, this);
        } else if (cmdType == QueryValidator.DELETE_TRNS) {
            TransformationHelper.setDeleteSqlUserString(mappingRoot, userSqlStr, false, false, this);
        }
    }

    public void warnUserAboutInvalidTransformations( List invalidSourceList ) {
        if (!invalidSourceList.isEmpty()) {
            String title = UiConstants.Util.getString("RevalidateModelTransformationsAction.invalidTransformationsTitle"); //$NON-NLS-1$
            String msg = UiConstants.Util.getString("RevalidateModelTransformationsAction.invalidTransformationsMessage"); //$NON-NLS-1$
            List copyOfList = new ArrayList(invalidSourceList.size());
            for (Iterator iter = invalidSourceList.iterator(); iter.hasNext();) {
                Object nextObj = iter.next();
                String path = ModelObjectUtilities.getTrimmedFullPath((EObject)nextObj);
                String name = ModelerCore.getModelEditor().getName((EObject)nextObj);
                String row = path + "/" + name; //$NON-NLS-1$
                copyOfList.add(row);
            }

            ListMessageDialog.openWarning(Display.getCurrent().getActiveShell(), title, null, msg, copyOfList, null);
        }
    }

    @Override
	public int compareTo( Object o ) {
        if (o instanceof String) {
            return getText().compareTo((String)o);
        }

        if (o instanceof Action) {
            return getText().compareTo(((Action)o).getText());
        }
        return 0;
    }

    @Override
	public boolean isApplicable( ISelection selection ) {
        boolean result = false;
        Object selectedObject = SelectionUtilities.getSelectedObject(selection);
        if (selectedObject instanceof IResource && ModelUtilities.isModelFile((IResource)selectedObject)) {
            try {
                this.modelResource = ModelUtil.getModelResource(((IFile)selectedObject), false);
                if (ModelUtilities.isVirtual(this.modelResource)) {
                    result = true;
                }
            } catch (ModelWorkspaceException e) {
                UiConstants.Util.log(e);
            }
        }
        return result;
    }

    private void invalidateSqlRootCache( List mappingRoots ) {
        Object nextObj = null;
        for (Iterator iter = mappingRoots.iterator(); iter.hasNext();) {
            nextObj = iter.next();
            if (nextObj instanceof SqlTransformationMappingRoot) {
                SqlTransformationMappingRoot mappingRoot = (SqlTransformationMappingRoot)nextObj;
                SqlMappingRootCache.invalidateSelectStatus(mappingRoot, false, this);
            }
        }
    }
}
