/* Generated By:JJTree: Do not edit this line. WindowFunction.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast.v8;

import org.teiid.runtime.client.lang.ast.AggregateSymbol;
import org.teiid.runtime.client.lang.ast.SimpleNode;
import org.teiid.runtime.client.lang.ast.WindowFunction;
import org.teiid.runtime.client.lang.ast.WindowSpecification;
import org.teiid.runtime.client.lang.parser.LanguageVisitor;
import org.teiid.runtime.client.lang.parser.v8.Teiid8Parser;

/**
 * From Teiid version 8 onwards the WindowFunction no longer extends Symbol
 */
public class Window8Function extends SimpleNode implements WindowFunction {

    private AggregateSymbol function;

    private WindowSpecification windowSpecification;

    /**
     * @param p
     * @param id
     */
    public Window8Function(Teiid8Parser p, int id) {
        super(p, id);
    }

    /**
     * @return the function
     */
    @Override
    public AggregateSymbol getFunction() {
        return this.function;
    }

    /**
     * @param function the function to set
     */
    @Override
    public void setFunction(AggregateSymbol function) {
        this.function = function;
    }

    /**
     * @return the windowSpecification
     */
    @Override
    public WindowSpecification getWindowSpecification() {
        return this.windowSpecification;
    }

    /**
     * @param windowSpecification the windowSpecification to set
     */
    @Override
    public void setWindowSpecification(WindowSpecification windowSpecification) {
        this.windowSpecification = windowSpecification;
    }

    @Override
    public Class<?> getType() {
        return function.getType();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.function == null) ? 0 : this.function.hashCode());
        result = prime * result + ((this.windowSpecification == null) ? 0 : this.windowSpecification.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Window8Function other = (Window8Function)obj;
        if (this.function == null) {
            if (other.function != null) return false;
        } else if (!this.function.equals(other.function)) return false;
        if (this.windowSpecification == null) {
            if (other.windowSpecification != null) return false;
        } else if (!this.windowSpecification.equals(other.windowSpecification)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Window8Function clone() {
        Window8Function clone = new Window8Function((Teiid8Parser) this.parser, this.id);

        if(getFunction() != null)
            clone.setFunction(getFunction().clone());
        if(getWindowSpecification() != null)
            clone.setWindowSpecification(getWindowSpecification().clone());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=da43cffa8d1927cfd8b4f378cdcfeae0 (do not edit this line) */
