package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.TIntHashSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class IndexedHornImpContainer implements Collection<HornImplication> {
	Set<HornImplication> imps;

	TIntObjectHashMap<Collection<HornImplication>> headIndex;

	TIntObjectHashMap<Collection<HornImplication>> bodyIndex;

	public IndexedHornImpContainer() {
		this.imps = new HashSet<HornImplication>();
		this.headIndex = new TIntObjectHashMap<Collection<HornImplication>>();
		this.bodyIndex = new TIntObjectHashMap<Collection<HornImplication>>();
	}
	public Set<HornImplication> getImps() {
		return imps;
	}
	@Override
	public int size() {
		return imps.size();
	}

	@Override
	public boolean isEmpty() {
		return imps.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<HornImplication> iterator() {
		return imps.iterator();
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(HornImplication imp) {

		if (!this.subsubmedImpl(imp)) {

			imps.add(imp);
			int head = imp.getHead();
			ensureContainsKey(headIndex, head);
			headIndex.get(head).add(imp);

			TIntHashSet body = imp.getBody();

			TIntIterator bI = body.iterator();

			while (bI.hasNext()) {
				int b = bI.next();
				ensureContainsKey(bodyIndex, b);
				bodyIndex.get(b).add(imp);
			}

			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean remove(Object o) {
		HornImplication imp = (HornImplication) o;
		imps.remove(imp);

		int head = imp.getHead();
		// ensureContainsKey(headIndex, head);
		headIndex.get(head).remove(imp);

		TIntHashSet body = imp.getBody();

		TIntIterator bI = body.iterator();

		while (bI.hasNext()) {
			int b = bI.next();
			ensureContainsKey(bodyIndex, b);
			bodyIndex.get(b).remove(imp);
		}
		//
		// for (int b = imp.getBody().nextSetBit(0); b >= 0; b =
		// imp.getBody().nextSetBit(b + 1)) {
		// // ensureContainsKey(bodyIndex, b);
		// bodyIndex.get(b).remove(imp);
		// }

		return true;
	}

	private void ensureContainsKey(
			TIntObjectHashMap<Collection<HornImplication>> map, int key) {
		if (!map.containsKey(key)) {
			map.put(key, new HashSet<HornImplication>());
		}

	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends HornImplication> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	// private void ensureContainsKey(Map<Integer,
	// Collection<HornImplicationRelation>> map, Integer key) {
	// if (!map.containsKey(key)) {
	// map.put(key, new HashSet<HornImplicationRelation>());
	// }
	// }

	public Collection<HornImplication> matchBody(TIntHashSet candidateBody) {

		TIntIterator bI = candidateBody.iterator();

		int first = bI.next();

		if (bodyIndex.get(first) == null) {
			return Collections.emptyList();
		}

		Collection<HornImplication> result = new ArrayList<HornImplication>(
				bodyIndex.get(first));

		if (result.size() == 0) {
			return Collections.emptyList();
		}

		while (bI.hasNext()) {
			int key = bI.next();

			if (result.isEmpty())
				return result;

			Collection<HornImplication> rest = bodyIndex.get(key);
			if (rest == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest);
		}

		return result;

	}

	public Collection<HornImplication> matchHead(int head) {
		if (this.headIndex.containsKey(head)) {
			return headIndex.get(head);
		}

		return Collections.emptyList();

	}

	private boolean subsubmedImpl(HornImplication impl) {

		return this.imps.contains(impl);
		/*old code:
		Collection<HornImplication> list = this.matchHead(impl.getHead());
		for (HornImplication elem : list) {
			if (impl.getBody().containsAll(elem.getBody())) {
				return true;
			}
		}

		return false;
		*/
	}

	

	
	public void fillWithImplicants(Collection<TIntHashSet> set) {

		TIntHashSet new_body;
		Set<TIntHashSet> addition = new HashSet<TIntHashSet>();
		boolean modified = true;

		while (modified) {
			modified = false;

			for (TIntHashSet body : set) {
				for (int concept : body.toArray()) {
					Collection<HornImplication> list = this.matchHead(concept);
					for (HornImplication imp : list) {
						new_body = new TIntHashSet(body);
						new_body.addAll(imp.getBody());
						new_body.remove(imp.getHead());
						
						boolean subsumed = false;
						for (TIntHashSet existing_body : set)
						  if (new_body.containsAll(existing_body)) subsumed = true;
						
						if (!subsumed) {
							addition.add(new_body);
							modified = true;
						}

					}

				}
				
			}
			set.addAll(addition);
			addition.clear();
			System.out.println("Inside size:"+set.size());
		}
	}
}
