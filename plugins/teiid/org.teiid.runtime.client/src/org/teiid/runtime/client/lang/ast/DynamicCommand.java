/* Generated By:JJTree: Do not edit this line. DynamicCommand.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.teiid.designer.query.sql.lang.IDynamicCommand;
import org.teiid.runtime.client.lang.parser.LanguageVisitor;
import org.teiid.runtime.client.lang.parser.TeiidParser;

/**
 *
 */
public class DynamicCommand extends Command implements IDynamicCommand<Expression, LanguageVisitor> {

    private Expression sql;
    
    private List<ElementSymbol> asColumns;
    
    private GroupSymbol intoGroup;
    
    private int updatingModelCount;
    
    private SetClauseList using;
    
    private boolean asClauseSet;

    /**
     * @param p
     * @param id
     */
    public DynamicCommand(TeiidParser p, int id) {
        super(p, id);
    }

    @Override
    public int getType() {
        return TYPE_DYNAMIC;
    }

    /** 
     * @return Returns the columns.
     */
    public List getAsColumns() {
        if (this.asColumns == null) {
            return Collections.EMPTY_LIST;
        }
        return this.asColumns;
    }

    /** 
     * @param columns The columns to set.
     */
    public void setAsColumns(List columns) {
        this.asColumns = columns;
    }
    
    /** 
     * @return Returns the intoGroup.
     */
    public GroupSymbol getIntoGroup() {
        return this.intoGroup;
    }
    
    /** 
     * @param intoGroup The intoGroup to set.
     */
    public void setIntoGroup(GroupSymbol intoGroup) {
        this.intoGroup = intoGroup;
    }
    
    /** 
     * @return Returns the sql.
     */
    public Expression getSql() {
        return this.sql;
    }
    
    /** 
     * @param sql The sql to set.
     */
    public void setSql(Expression sql) {
        this.sql = sql;
    }
            
    /** 
     * @return Returns the using.
     */
    public SetClauseList getUsing() {
        return this.using;
    }
    
    /** 
     * @param using The using to set.
     */
    public void setUsing(SetClauseList using) {
        this.using = using;
    }

    /** 
     * @return Returns the asClauseSet.
     */
    public boolean isAsClauseSet() {
        return this.asClauseSet;
    }

    /** 
     * @param asClauseSet The asClauseSet to set.
     */
    public void setAsClauseSet(boolean asClauseSet) {
        this.asClauseSet = asClauseSet;
    }

    /**
     * @return updating model count
     */
    public int getUpdatingModelCount() {
        return this.updatingModelCount;
    }

    /**
     * @param count
     */
    public void setUpdatingModelCount(int count) {
        if (count < 0) {
            count = 0;
        } else if (count > 2) {
            count = 2;
        }
        this.updatingModelCount = count;
    }

    @Override
    public boolean returnsResultSet() {
        return intoGroup == null;
    }

    /** 
     * Once past resolving, an EMPTY set of project columns indicates that the
     * project columns of the actual command do not need to be checked during
     * processing.
     */
    @Override
    public List<Expression> getProjectedSymbols() {
        if (intoGroup != null) {
            return getUpdateCommandSymbol();
        }
        
        if (asColumns != null) {
            List<Expression> ps = new ArrayList<Expression>();
            for (ElementSymbol es : asColumns) {
                ps.add(es);
            }
            return ps;
        }
        
        return Collections.EMPTY_LIST;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.asClauseSet ? 1231 : 1237);
        result = prime * result + ((this.asColumns == null) ? 0 : this.asColumns.hashCode());
        result = prime * result + ((this.intoGroup == null) ? 0 : this.intoGroup.hashCode());
        result = prime * result + ((this.sql == null) ? 0 : this.sql.hashCode());
        result = prime * result + this.updatingModelCount;
        result = prime * result + ((this.using == null) ? 0 : this.using.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        DynamicCommand other = (DynamicCommand)obj;
        if (this.asClauseSet != other.asClauseSet) return false;
        if (this.asColumns == null) {
            if (other.asColumns != null) return false;
        } else if (!this.asColumns.equals(other.asColumns)) return false;
        if (this.intoGroup == null) {
            if (other.intoGroup != null) return false;
        } else if (!this.intoGroup.equals(other.intoGroup)) return false;
        if (this.sql == null) {
            if (other.sql != null) return false;
        } else if (!this.sql.equals(other.sql)) return false;
        if (this.updatingModelCount != other.updatingModelCount) return false;
        if (this.using == null) {
            if (other.using != null) return false;
        } else if (!this.using.equals(other.using)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public DynamicCommand clone() {
        DynamicCommand clone = new DynamicCommand(this.parser, this.id);

        if(getAsColumns() != null && ! getAsColumns().isEmpty())
            clone.setAsColumns(cloneList(getAsColumns()));
        if(getIntoGroup() != null)
            clone.setIntoGroup(getIntoGroup().clone());
        if(getSql() != null)
            clone.setSql(getSql().clone());
        if(getUsing() != null)
            clone.setUsing(getUsing().clone());
        clone.setAsClauseSet(isAsClauseSet());
        clone.setUpdatingModelCount(getUpdatingModelCount());
        if(getSourceHint() != null)
            clone.setSourceHint(getSourceHint());
        if(getOption() != null)
            clone.setOption(getOption().clone());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=077000c775a7694361ac6d951f20ebb4 (do not edit this line) */
