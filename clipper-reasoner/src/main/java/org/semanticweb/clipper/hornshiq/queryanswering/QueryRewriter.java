package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.Collection;
import org.semanticweb.clipper.hornshiq.rule.CQ;

public interface QueryRewriter {

	Collection<CQ> rewrite(CQ query);

}