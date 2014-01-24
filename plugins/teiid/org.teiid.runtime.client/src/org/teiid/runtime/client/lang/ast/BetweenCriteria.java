/* Generated By:JJTree: Do not edit this line. BetweenCriteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.designer.query.sql.lang.IBetweenCriteria;
import org.teiid.runtime.client.lang.parser.LanguageVisitor;
import org.teiid.runtime.client.lang.parser.TeiidParser;

/**
 *
 */
public class BetweenCriteria extends Criteria implements PredicateCriteria, IBetweenCriteria<LanguageVisitor> {

    private Expression expression;

    private Expression lowerExpression;

    private Expression upperExpression;
    
    /** Negation flag. Indicates whether the criteria expression contains a NOT. */
    private boolean negated = false;

    /**
     * @param p
     * @param id
     */
    public BetweenCriteria(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * @return the expression
     */
    @Override
    public Expression getExpression() {
        return this.expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * @return the lowerExpression
     */
    @Override
    public Expression getLowerExpression() {
        return this.lowerExpression;
    }

    /**
     * @param lowerExpression the lowerExpression to set
     */
    public void setLowerExpression(Expression lowerExpression) {
        this.lowerExpression = lowerExpression;
    }

    /**
     * @return the upperExpression
     */
    @Override
    public Expression getUpperExpression() {
        return this.upperExpression;
    }

    /**
     * @param upperExpression the upperExpression to set
     */
    public void setUpperExpression(Expression upperExpression) {
        this.upperExpression = upperExpression;
    }

    /**
     * @return the negated
     */
    @Override
    public boolean isNegated() {
        return this.negated;
    }

    /**
     * @param negated the negated to set
     */
    @Override
    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.expression == null) ? 0 : this.expression.hashCode());
        result = prime * result + ((this.lowerExpression == null) ? 0 : this.lowerExpression.hashCode());
        result = prime * result + (this.negated ? 1231 : 1237);
        result = prime * result + ((this.upperExpression == null) ? 0 : this.upperExpression.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        BetweenCriteria other = (BetweenCriteria)obj;
        if (this.expression == null) {
            if (other.expression != null) return false;
        } else if (!this.expression.equals(other.expression)) return false;
        if (this.lowerExpression == null) {
            if (other.lowerExpression != null) return false;
        } else if (!this.lowerExpression.equals(other.lowerExpression)) return false;
        if (this.negated != other.negated) return false;
        if (this.upperExpression == null) {
            if (other.upperExpression != null) return false;
        } else if (!this.upperExpression.equals(other.upperExpression)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public BetweenCriteria clone() {
        BetweenCriteria clone = new BetweenCriteria(this.parser, this.id);

        if(getExpression() != null)
            clone.setExpression(getExpression().clone());
        if(getLowerExpression() != null)
            clone.setLowerExpression(getLowerExpression().clone());
        if(getUpperExpression() != null)
            clone.setUpperExpression(getUpperExpression().clone());
        clone.setNegated(isNegated());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=c3fc97791e1610f2c6a0633d0ef3c39c (do not edit this line) */
