/* Generated By:JJTree: Do not edit this line. ElementSymbol.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.designer.query.sql.symbol.IElementSymbol;
import org.teiid.runtime.client.lang.TeiidNodeFactory.ASTNodes;
import org.teiid.runtime.client.lang.parser.LanguageVisitor;
import org.teiid.runtime.client.lang.parser.TeiidParser;

/**
 *
 */
@SuppressWarnings( "unused" )
public class ElementSymbol extends Symbol implements SingleElementSymbol, Expression, IElementSymbol<GroupSymbol, LanguageVisitor> {

    private Class type;
    
    private Object metadataID;

    private GroupSymbol groupSymbol;

    private DisplayMode displayMode = DisplayMode.OUTPUT_NAME;

    private boolean isExternalReference;

    /**
     * @param p
     * @param id
     */
    public ElementSymbol(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Get the type of the symbol
     * @return Type of the symbol, may be null before resolution
     */
    @Override
    public Class getType() {
        return this.type;
    }   
    
    /**
     * Set the type of the symbol
     * @param type New type
     */
    @Override
    public void setType(Class type) {
        this.type = type;
    }

    /**
     * Get the metadata ID reference
     * @return Metadata ID reference, may be null before resolution
     */
    @Override
    public Object getMetadataID() {
        return this.metadataID;
    }

    /**
     * Set the metadata ID reference for this element
     * @param metadataID Metadata ID reference
     */
    @Override
    public void setMetadataID(Object metadataID) {
        this.metadataID = metadataID;
    }

    /**
     * Get the group symbol referred to by this element symbol, may be null before resolution
     * @return Group symbol referred to by this element, may be null
     */
    @Override
    public GroupSymbol getGroupSymbol() {
        return this.groupSymbol;
    }

    /**
     * Set the group symbol referred to by this element symbol
     * @param symbol the group symbol to set
     */
    @Override
    public void setGroupSymbol(GroupSymbol symbol) {
        this.groupSymbol = symbol;
    }

    @Override
    public String getName() {
        if (this.groupSymbol != null) {
            return this.groupSymbol.getName() + Symbol.SEPARATOR + this.getShortName();
        }
        return super.getName();
    }

    @Override
    public void setName(String name) {
        int index = name.lastIndexOf('.');
        if (index > 0) {
            if (this.groupSymbol != null) {
                throw new AssertionError("Attempt to set an invalid name"); //$NON-NLS-1$
            }
            GroupSymbol gs = parser.createASTNode(ASTNodes.GROUP_SYMBOL);
            gs.setName(new String(name.substring(0, index)));
            this.setGroupSymbol(gs);
            name = new String(name.substring(index + 1));
        } else {
            this.groupSymbol = null;
        }
        super.setShortName(name);
    }

    @Override
    public void setDisplayMode(DisplayMode displayMode) {
        if (displayMode == null) {
            this.displayMode = DisplayMode.OUTPUT_NAME;
        }
        this.displayMode = displayMode;
    }

    @Override
    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    /**
     * Set whether this element will be displayed as fully qualified
     * @param displayFullyQualified True if should display fully qualified
     */
    @Override
    public void setDisplayFullyQualified(boolean displayFullyQualified) { 
        this.displayMode = displayFullyQualified?DisplayMode.FULLY_QUALIFIED:DisplayMode.SHORT_OUTPUT_NAME; 
    }
    
    /**
     * Get whether this element will be displayed as fully qualified
     * @return True if should display fully qualified
     */
    public boolean getDisplayFullyQualified() { 
        return this.displayMode.equals(DisplayMode.FULLY_QUALIFIED);    
    }

    /**
     * Set whether this element is an external reference.  An external 
     * reference is an element that comes from a group outside the current
     * command context.  Typical uses would be variables and correlated 
     * references in subqueries.
     * @param isExternalReference True if element is an external reference
     */
    public void setIsExternalReference(boolean isExternalReference) { 
        this.isExternalReference = isExternalReference; 
    }

    /**
     * Get whether this element is an external reference to a group
     * outside the command context.
     * @return True if element is an external reference
     */
    @Override
    public boolean isExternalReference() { 
        return this.isExternalReference;  
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.metadataID == null) ? 0 : this.metadataID.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ElementSymbol other = (ElementSymbol)obj;
        if (this.metadataID == null) {
            if (other.metadataID != null) return false;
        } else if (!this.metadataID.equals(other.metadataID)) return false;
        if (this.type == null) {
            if (other.type != null) return false;
        } else if (!this.type.equals(other.type)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ElementSymbol clone() {
        ElementSymbol clone = new ElementSymbol(this.parser, this.id);

        if (getGroupSymbol() != null)
            clone.setGroupSymbol(getGroupSymbol().clone());
        if(getType() != null)
            clone.setType(getType());
        if(getMetadataID() != null)
            clone.setMetadataID(getMetadataID());
        if(getDisplayMode() != null)
            clone.setDisplayMode(getDisplayMode());
        if(getCanonicalShortName() != null)
            clone.setCanonicalShortName(getCanonicalShortName());
        if(getOutputName() != null)
            clone.setOutputName(getOutputName());
        if(getShortName() != null)
            clone.setShortName(getShortName());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=77f5180826dcbc69682360fac6b1d467 (do not edit this line) */
