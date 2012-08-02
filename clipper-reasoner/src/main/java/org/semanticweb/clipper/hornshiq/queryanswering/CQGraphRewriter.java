package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.semanticweb.clipper.hornshiq.ontology.NormalHornALCHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.SubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.semanticweb.clipper.util.BitSetUtilOpt;

import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;

@Slf4j
public class CQGraphRewriter implements QueryRewriter {

	// final Logger log = LoggerFactory.getLogger(CQGraphRewriter.class);

	CQGraphHomomorphismChecker checker;


	NormalHornALCHIQOntology ontology;
	IndexedEnfContainer enfs;

	Multimap<Integer, Integer> transSuperRole2SubRolesMmap;

	List<CQGraph> resultGraphs;
	List<CQ> resultCQs;

	private SelfLoopComponentCluster slcc;

	public CQGraphRewriter(NormalHornALCHIQOntology ontology, IndexedEnfContainer enfs) {
		this.ontology = ontology;
		this.enfs = enfs;
		checker = new CQGraphHomomorphismChecker();
		List<SubPropertyAxiom> subPropertyAxioms = ontology.computeNonSimpleSubPropertyClosure();
		transSuperRole2SubRolesMmap = HashMultimap.create();
		for (SubPropertyAxiom subPropertyAxiom : subPropertyAxioms) {
			int subRole = subPropertyAxiom.getRole1();
			int superRole = subPropertyAxiom.getRole2();
			transSuperRole2SubRolesMmap.put(superRole, subRole);
		}
	}

	/**
	 * rewrite a CQ graph into a collection of CQ graphs
	 * 
	 * @param g
	 *            input CQ graph
	 * @return
	 */
	public List<CQGraph> rewrite(CQGraph g) {
		log.debug("rewrite(CQGraph g)");
		log.debug("g = {}", g);

		resultGraphs = Lists.newArrayList();
		resultCQs = Lists.newArrayList();

		slcc = new SelfLoopComponentCluster(enfs);

		rewrite_recursive(g);
		return resultGraphs;
	}

	/**
	 * rewrite a CQ graph: recursive entry
	 * 
	 * @param g
	 */
	public void rewrite_recursive(CQGraph g) {

		log.debug("rewrite_recursive(CQGraph g)");
		log.debug("g = {}", g);
		log.debug("cq(g) = {}", g.toCQ());

		resultGraphs.add(g);
		resultCQs.add(g.toCQ());

		Set<Set<Variable>> selfLoopComponents = slcc.transform(g);

		for (Set<Variable> component : selfLoopComponents) {
			for (Set<Variable> leaves : Sets.powerSet(component)) {
				if (!leaves.isEmpty()) {
					rewrite(g, leaves);
				}
			}
		}
	}

	public void rewrite(CQGraph g, Collection<Variable> leaves) {
		log.debug("leaves = {}", leaves);

		g.focus(leaves);

		List<CQGraphEdge> edges = g.getInEdges(leaves);
		// Edge with non-simple roles to sub roles map
		Multimap<CQGraphEdge, Integer> edge2SubRolesMmap = HashMultimap.create();
		for (CQGraphEdge edge : edges) {
			edge2SubRolesMmap.putAll(edge, transSuperRole2SubRolesMmap.get(edge.getRole()));
		}

		Set<CQGraphEdge> allKeys = edge2SubRolesMmap.keySet();

		for (Set<CQGraphEdge> keys : Sets.powerSet(allKeys)) {
			Multimap<CQGraphEdge, Integer> filteredMap = Multimaps.filterKeys(edge2SubRolesMmap, Predicates.in(keys));
			List<CQGraphEdge> subKeys = Lists.newArrayList();
			List<Set<Integer>> candidates = Lists.newArrayList();
			for (CQGraphEdge key : filteredMap.keySet()) {
				subKeys.add(key);
				candidates.add(Sets.newHashSet(filteredMap.get(key)));
			}
			Set<List<Integer>> cartesianProduct = Sets.cartesianProduct(candidates);

			for (List<Integer> replacement : cartesianProduct) {

				Map<CQGraphEdge, Integer> map = Maps.newHashMap();

				int size = subKeys.size();
				for (int i = 0; i < size; i++) {
					map.put(subKeys.get(i), replacement.get(i));
				}

				rewrite(g, leaves, edges, map);
			}
		}
	}

	/**
	 * 
	 * @param g
	 * @param leaves
	 * @param edges
	 * @param map
	 */
	public void rewrite(CQGraph g, Collection<Variable> leaves, List<CQGraphEdge> edges, Map<CQGraphEdge, Integer> map) {

		TIntHashSet roles = new TIntHashSet();

		for (CQGraphEdge edge : edges) {
			if (map.containsKey(edge)) {
				roles.add(map.get(edge));
			} else {
				roles.add(edge.getRole());
			}
		}

		TIntHashSet type2 = new TIntHashSet();

		for (Variable leaf : leaves) {
			type2.addAll(g.getConcepts(leaf));
		}

		Collection<EnforcedRelation> matchedEnfs = enfs.matchRolesAndType2(roles, type2);

		for (EnforcedRelation enf : matchedEnfs) {

			boolean mergeable = mergeable(g, enf, leaves);

			log.debug("mergable = {}", mergeable);

			if (mergeable) {
				CQGraph g1 = g.deepCopy();

				List<Integer> type = toList(enf.getType1());

				log.debug("cq(g) = {}", g.toCQ());
				log.debug("edges = {}; map = {}", edges, map);
				log.debug("type = {}", type);
				g1.clip(leaves, edges, map, type);

				CQ cq = g1.toCQ();
				// if (!redundant(g1)) {
				if (!resultCQs.contains(cq)) {
					log.debug("-- new cq = {}", cq);
					rewrite_recursive(g1);
				}
			}
		}

	}

	public boolean redundant(CQGraph g1) {
		for (CQGraph g : resultGraphs) {
			if (checker.isHomomorphism(g, g1)) {
				Map<Term, Term> map = checker.getMap();
				System.out.println(Strings.repeat("-", 80));
				System.out.println(g.toCQ());
				System.out.println("--> " + map);
				System.out.println(g1.toCQ());
				System.out.println(checker.check(map, g, g1));
				System.out.println(Strings.repeat("-", 80));
				return true;
			}
		}

		return false;
	}

	/**
	 * @param tmp
	 * @return
	 */
	private List<Integer> toList(TIntHashSet tmp) {
		List<Integer> type = Lists.newArrayList();
		for (Integer t : tmp.toArray()) {
			type.add(t);
		}
		return type;
	}

	/**
	 * check whether the leaves can be merged into one single node
	 * 
	 * @param g
	 * @param enf
	 * @param leaves
	 * @return
	 */
	private boolean mergeable(CQGraph g, EnforcedRelation enf, Collection<Variable> leaves) {
		Collection<CQGraphEdge> leafInterEdges = g.getInterEdges(leaves);
		for (CQGraphEdge edge : leafInterEdges) {
			Integer role = edge.getRole();
			int inverseRole = BitSetUtilOpt.inverseRole(role);
			if ((enf.getRoles().contains(role) && enf.getRoles().contains(inverseRole))) {
				TIntHashSet type1 = enf.getType2();
				TIntHashSet type2 = enf.getType2();
				TIntHashSet roles = new TIntHashSet();
				roles.add(role);
				roles.add(inverseRole);
				if (enfs.matchRolesAndType2(roles, type1).size() == 0
						&& enfs.matchRolesAndType2(roles, type2).size() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public List<CQGraph> getResult() {
		return resultGraphs;
	}

	@Override
	public Collection<CQ> rewrite(CQ query) {
		rewrite(new CQGraph(query));
		return resultCQs;
	}
}
