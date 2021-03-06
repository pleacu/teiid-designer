/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */

package org.teiid.designer.metadata.runtime.impl;

import java.util.ArrayList;
import java.util.List;

import org.teiid.designer.core.index.IndexConstants;
import org.teiid.designer.metadata.runtime.ColumnSetRecord;
import org.teiid.designer.metadata.runtime.ListEntryRecord;
import org.teiid.designer.metadata.runtime.MetadataConstants;

/**
 * ColumnSetRecordImpl
 *
 * @since 8.0
 */
public class ColumnSetRecordImpl extends AbstractMetadataRecord implements ColumnSetRecord {
    
    /**
     */
    private static final long serialVersionUID = 1L;
    private List columnIDs;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public ColumnSetRecordImpl() {
    	this(new MetadataRecordDelegate());
    }
    
    protected ColumnSetRecordImpl(MetadataRecordDelegate delegate) {
    	this.delegate = delegate;
    }

    //==================================================================================
    //                     I N T E R F A C E   M E T H O D S
    //==================================================================================    

    /** 
     * @see org.teiid.designer.metadata.runtime.ColumnSetRecord#getColumnIDs()
     */
    @Override
	public List getColumnIDs() {
        return columnIDs;
    }

    /** 
     * @see org.teiid.designer.metadata.runtime.ColumnSetRecord#getColumnIdEntries()
     */
    @Override
	public ListEntryRecord[] getColumnIdEntries() {
        final List entryRecords = new ArrayList(columnIDs.size());
        for (int i = 0, n = columnIDs.size(); i < n; i++) {
            final String uuid  = (String)columnIDs.get(i);
            final int position = i+1;
            entryRecords.add( new ListEntryRecordImpl(uuid,position) );
        }
        return (ListEntryRecord[])entryRecords.toArray(new ListEntryRecord[entryRecords.size()]);
    }

    /** 
     * @see org.teiid.designer.metadata.runtime.ColumnSetRecord#isAccessPattern()
     */
    @Override
	public boolean isAccessPattern() {
        if (super.getRecordType() == IndexConstants.RECORD_TYPE.ACCESS_PATTERN) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.teiid.designer.metadata.runtime.ColumnSetRecord#isIndex()
     */
    @Override
	public boolean isIndex() {
        if (super.getRecordType() == IndexConstants.RECORD_TYPE.INDEX) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.teiid.designer.metadata.runtime.ColumnSetRecord#isPrimaryKey()
     */
    @Override
	public boolean isPrimaryKey() {
        if (super.getRecordType() == IndexConstants.RECORD_TYPE.PRIMARY_KEY) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.teiid.designer.metadata.runtime.ColumnSetRecord#isUniqueKey()
     */
    @Override
	public boolean isUniqueKey() {
        if (super.getRecordType() == IndexConstants.RECORD_TYPE.UNIQUE_KEY) {
            return true;
        }
        return false;
    }

    /**
     * @see org.teiid.designer.metadata.runtime.ColumnSetRecord#isResultSet()
     */
    @Override
	public boolean isResultSet() {
        if (super.getRecordType() == IndexConstants.RECORD_TYPE.RESULT_SET) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.teiid.designer.metadata.runtime.ColumnSetRecord#getType()
     */
    @Override
	public short getType() {
        return this.getKeyTypeForRecordType(this.getRecordType());
    }

    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    /**
     * @param list
     */
    public void setColumnIDs(List list) {
        columnIDs = list;
    }
    
    protected short getKeyTypeForRecordType(final char recordType) {
        switch (recordType) {
            case IndexConstants.RECORD_TYPE.UNIQUE_KEY: return MetadataConstants.KEY_TYPES.UNIQUE_KEY;
            case IndexConstants.RECORD_TYPE.INDEX: return MetadataConstants.KEY_TYPES.INDEX;
            case IndexConstants.RECORD_TYPE.ACCESS_PATTERN: return MetadataConstants.KEY_TYPES.ACCESS_PATTERN;
            case IndexConstants.RECORD_TYPE.PRIMARY_KEY: return MetadataConstants.KEY_TYPES.PRIMARY_KEY;
            case IndexConstants.RECORD_TYPE.FOREIGN_KEY: return MetadataConstants.KEY_TYPES.FOREIGN_KEY;
            default:
                throw new IllegalArgumentException("Invalid record type, for key" + recordType); //$NON-NLS-1$
        }
    }

}