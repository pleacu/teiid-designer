/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.transformation.ui.builder;

import org.teiid.designer.query.sql.lang.ILanguageObject;
import org.teiid.designer.transformation.ui.builder.util.LanguageObjectContentProvider;

/**
 * The <code>ILanguageObjectInputProvider</code> interface is used by the {@link LanguageObjectContentProvider}
 * to set it's input. In order for the content provider's getChildren() method to be called correctly, the
 * input to the <code>TreeViewer</code> cannot be the root of the tree. If the input is the root, the entire
 * tree is refreshed. This interface was created in order to allow the input to the viewer to not be the 
 * root of the tree (which is the LanguageObject).
 *
 * @since 8.0
 */
public interface ILanguageObjectInputProvider {

    /**
     * Gets the <code>LanguageObject</code> being provided.
     * @return the LanguageObject
     */
    ILanguageObject getLanguageObject();
}
