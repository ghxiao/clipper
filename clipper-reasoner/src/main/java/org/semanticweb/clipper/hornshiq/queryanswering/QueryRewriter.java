package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.Collection;
import org.semanticweb.clipper.hornshiq.rule.CQ;

public interface QueryRewriter {

	public abstract Collection<CQ> rewrite(CQ query);

}