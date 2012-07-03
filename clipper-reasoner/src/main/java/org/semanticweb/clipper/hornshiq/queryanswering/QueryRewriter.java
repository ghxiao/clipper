package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.clipper.hornshiq.rule.CQ;

public interface QueryRewriter {

	public abstract Collection<CQ> rewrite(CQ query);

}