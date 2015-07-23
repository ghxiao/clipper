package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.rule.CQ;

import java.util.Collection;

public interface QueryRewriter {

	Collection<CQ> rewrite(CQ query);

}