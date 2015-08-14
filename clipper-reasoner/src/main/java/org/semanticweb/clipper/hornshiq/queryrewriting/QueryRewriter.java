package org.semanticweb.clipper.hornshiq.queryrewriting;

import org.semanticweb.clipper.hornshiq.rule.CQ;

import java.util.Collection;

public interface QueryRewriter {

	Collection<CQ> rewrite(CQ query);

}