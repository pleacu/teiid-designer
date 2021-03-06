/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.query.resolver.v88;

import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion.Version;
import org.teiid.query.resolver.v87.Test87FunctionResolving;

@SuppressWarnings( {"javadoc"} )
public class Test88FunctionResolving extends Test87FunctionResolving {

    protected Test88FunctionResolving(Version teiidVersion) {
        super(teiidVersion);
    }

    public Test88FunctionResolving() {
        this(Version.TEIID_8_8);
    }
}
