/* Generated By:JJTree: Do not edit this line. OrderByItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.designer.query.sql.lang.IOrderByItem;
import org.teiid.runtime.client.lang.SortSpecification;
import org.teiid.runtime.client.lang.parser.LanguageVisitor;
import org.teiid.runtime.client.lang.parser.TeiidParser;

/**
 *
 */
public class OrderByItem extends SimpleNode
    implements SortSpecification, IOrderByItem<Expression, LanguageVisitor> {

    private boolean ascending = true;

    private Expression symbol;

    private NullOrdering nullOrdering;

    /**
     * @param p
     * @param id
     */
    public OrderByItem(TeiidParser p, int id) {
        super(p, id);
    }

    @Override
    public boolean isAscending() {
        return ascending;
    }

    /**
     * @param ascending
     */
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public Expression getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(Expression symbol) {
        this.symbol = symbol;
    }
    
    /**
     * @return ordering value
     */
    public NullOrdering getNullOrdering() {
        return nullOrdering;
    }
    
    /**
     * @param nullOrdering
     */
    public void setNullOrdering(NullOrdering nullOrdering) {
        this.nullOrdering = nullOrdering;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.ascending ? 1231 : 1237);
        result = prime * result + ((this.nullOrdering == null) ? 0 : this.nullOrdering.hashCode());
        result = prime * result + ((this.symbol == null) ? 0 : this.symbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        OrderByItem other = (OrderByItem)obj;
        if (this.ascending != other.ascending) return false;
        if (this.nullOrdering != other.nullOrdering) return false;
        if (this.symbol == null) {
            if (other.symbol != null) return false;
        } else if (!this.symbol.equals(other.symbol)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public OrderByItem clone() {
        OrderByItem clone = new OrderByItem(this.parser, this.id);

        if(getSymbol() != null)
            clone.setSymbol(getSymbol().clone());
        if(getNullOrdering() != null)
            clone.setNullOrdering(getNullOrdering());
        clone.setAscending(isAscending());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=8206369bce4a105220b34cfd64063716 (do not edit this line) */
