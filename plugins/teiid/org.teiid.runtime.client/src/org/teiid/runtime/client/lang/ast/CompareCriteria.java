/* Generated By:JJTree: Do not edit this line. CompareCriteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.designer.query.sql.lang.ICompareCriteria;
import org.teiid.runtime.client.lang.parser.LanguageVisitor;
import org.teiid.runtime.client.lang.parser.TeiidParser;

/**
 *
 */
public class CompareCriteria extends AbstractCompareCriteria implements ICompareCriteria<Expression, LanguageVisitor> {

    /** The right-hand expression. */
    private Expression rightExpression;

    private Boolean isOptional = Boolean.FALSE;

    /**
     * @param p
     * @param id
     */
    public CompareCriteria(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Set right expression.
     * @param expression Right expression
     */
    @Override
    public void setRightExpression(Expression expression) { 
        this.rightExpression = expression;
    }
    
    /**
     * Get right expression.
     * @return right expression
     */
    @Override
    public Expression getRightExpression() {
        return this.rightExpression;
    }

    /**
     * Set during planning to indicate that this criteria is no longer needed
     * to correctly process a join
     * @param isOptional
     */
    public void setOptional(Boolean isOptional) {
        if (isOptional == null && Boolean.TRUE.equals(this.isOptional)) {
            return;
        }
        this.isOptional = isOptional;
    }
    
    /**
     * @return true if the compare criteria is used as join criteria, but not needed
     * during processing.
     */
    public boolean isOptional() {
        return isOptional == null || isOptional;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.isOptional == null) ? 0 : this.isOptional.hashCode());
        result = prime * result + ((this.rightExpression == null) ? 0 : this.rightExpression.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        CompareCriteria other = (CompareCriteria)obj;
        if (this.isOptional == null) {
            if (other.isOptional != null) return false;
        } else if (!this.isOptional.equals(other.isOptional)) return false;
        if (this.rightExpression == null) {
            if (other.rightExpression != null) return false;
        } else if (!this.rightExpression.equals(other.rightExpression)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public CompareCriteria clone() {
        CompareCriteria clone = new CompareCriteria(this.parser, this.id);

        if(getRightExpression() != null)
            clone.setRightExpression(getRightExpression().clone());
        clone.setOptional(isOptional());
        if(operator != null)
            clone.setOperator(operator);
        if(getLeftExpression() != null)
            clone.setLeftExpression(getLeftExpression().clone());

        return clone;
    }

    /**
     * TODO Refactor {@link CriteriaOperator} to spi
     * and replace int with {@link CriteriaOperator.Operator}
     */
    @Deprecated
    @Override
    public void setOperator(int operator) {
        this.operator = Operator.values()[operator];
    }


}
/* JavaCC - OriginalChecksum=b3e2979ada751b1aae0903db53c85d1c (do not edit this line) */
