package org.semanticweb.clipper.hornshiq.queryanswering;

import org.apache.commons.collections15.Transformer;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import java.util.Set;

/**
 * Created by xiao on 20/07/15.
 */
public interface SelfLoopComponentCluster extends Transformer<CQGraph, Set<Set<Variable>>> {
}
