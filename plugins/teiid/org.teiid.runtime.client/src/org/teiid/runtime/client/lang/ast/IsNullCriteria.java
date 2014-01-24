/* Generated By:JJTree: Do not edit this line. IsNullCriteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.designer.query.sql.lang.IIsNullCriteria;
import org.teiid.runtime.client.lang.parser.LanguageVisitor;
import org.teiid.runtime.client.lang.parser.TeiidParser;

/**
 *
 */
public class IsNullCriteria extends Criteria implements PredicateCriteria, IIsNullCriteria<Expression, LanguageVisitor> {

    private Expression expression;

    /** Negation flag. Indicates whether the criteria expression contains a NOT. */
    private boolean negated;

    /**
     * @param p
     * @param id
     */
    public IsNullCriteria(TeiidParser p, int id) {
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
    @Override
    public void setExpression(Expression expression) {
        this.expression = expression;
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
        result = prime * result + (this.negated ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        IsNullCriteria other = (IsNullCriteria)obj;
        if (this.expression == null) {
            if (other.expression != null) return false;
        } else if (!this.expression.equals(other.expression)) return false;
        if (this.negated != other.negated) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public IsNullCriteria clone() {
        IsNullCriteria clone = new IsNullCriteria(this.parser, this.id);

        if(getExpression() != null)
            clone.setExpression(getExpression().clone());
        clone.setNegated(isNegated());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=c564df426ba245d1c4e483affd0083cb (do not edit this line) */
