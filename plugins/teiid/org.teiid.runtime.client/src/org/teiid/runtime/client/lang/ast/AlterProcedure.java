/* Generated By:JJTree: Do not edit this line. Alter.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.runtime.client.lang.ast;

import org.teiid.designer.query.sql.lang.IAlterProcedure;
import org.teiid.runtime.client.lang.parser.LanguageVisitor;
import org.teiid.runtime.client.lang.parser.TeiidParser;


/**
 *
 * @param <T>
 */
public abstract class AlterProcedure<T extends Command> extends Alter<T> implements IAlterProcedure<Expression, LanguageVisitor> {

    /**
     * @param p
     * @param id
     */
    public AlterProcedure(TeiidParser p, int id) {
        super(p, id);
    }

    @Override
    public abstract AlterProcedure clone();
}
/* JavaCC - OriginalChecksum=4c2a7e700d4af2b1569d4947a1d82223 (do not edit this line) */
