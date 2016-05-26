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

public class IndexedEnfContainer implements Collection<EnforcedRelation> {

	@Override
	public String toString() {
		return "IndexedEnfContainer [enfs=" + enfs + "]";
	}

	Set<EnforcedRelation> enfs;

	TIntObjectHashMap<Collection<EnforcedRelation>> type1Index;
	TIntObjectHashMap<Collection<EnforcedRelation>> type2Index;
	TIntObjectHashMap<Collection<EnforcedRelation>> rolesIndex;

	public IndexedEnfContainer() {
		this.enfs = new HashSet<EnforcedRelation>();
		this.type1Index = new TIntObjectHashMap<Collection<EnforcedRelation>>();
		this.type2Index = new TIntObjectHashMap<Collection<EnforcedRelation>>();
		this.rolesIndex = new TIntObjectHashMap<Collection<EnforcedRelation>>();
	}

	public Set<EnforcedRelation> getEnfs() {
		return enfs;
	}

	@Override
	public int size() {
		return enfs.size();
	}

	@Override
	public boolean isEmpty() {
		return enfs.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<EnforcedRelation> iterator() {
		return this.enfs.iterator();
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
	public boolean add(EnforcedRelation enf) {
		if (!this.subsubmedEnf(enf)) {

			enfs.add(enf);

			TIntHashSet type1 = enf.getType1();
			TIntIterator type1Iterator = type1.iterator();
			while (type1Iterator.hasNext()) {
				int key = type1Iterator.next();
				ensureContainsKey(type1Index, key);
				type1Index.get(key).add(enf);
			}

			TIntHashSet type2 = enf.getType2();
			TIntIterator type2Iterator = type2.iterator();
			while (type2Iterator.hasNext()) {
				int key = type2Iterator.next();
				ensureContainsKey(type2Index, key);
				type2Index.get(key).add(enf);
			}

			TIntHashSet roles = enf.getRoles();
			TIntIterator rolesIterator = roles.iterator();
			while (rolesIterator.hasNext()) {
				int key = rolesIterator.next();
				ensureContainsKey(rolesIndex, key);
				rolesIndex.get(key).add(enf);
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Object o) {
		EnforcedRelation enf = (EnforcedRelation) o;
		enfs.remove(enf);

		TIntHashSet type1 = enf.getType1();
		TIntIterator type1Iterator = type1.iterator();
		while (type1Iterator.hasNext()) {
			int key = type1Iterator.next();
			ensureContainsKey(type1Index, key);
			type1Index.get(key).remove(enf);
		}

		TIntHashSet type2 = enf.getType2();
		TIntIterator type2Iterator = type2.iterator();
		while (type2Iterator.hasNext()) {
			int key = type2Iterator.next();
			ensureContainsKey(type2Index, key);
			type2Index.get(key).remove(enf);
		}

		TIntHashSet roles = enf.getRoles();
		TIntIterator rolesIterator = roles.iterator();
		while (rolesIterator.hasNext()) {
			int key = rolesIterator.next();
			ensureContainsKey(rolesIndex, key);
			rolesIndex.get(key).remove(enf);
		}

		return true;
	}

	private void ensureContainsKey(
			TIntObjectHashMap<Collection<EnforcedRelation>> map, int key) {
		if (!map.containsKey(key)) {
			map.put(key, new HashSet<EnforcedRelation>());
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends EnforcedRelation> c) {
		boolean modified = false;
		for (EnforcedRelation enf : c) {
			if (this.add(enf))
				modified = true;
		}
		return modified;
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

	public Collection<EnforcedRelation> matchType1(TIntHashSet candidateType1) {
		return match(candidateType1, type1Index);
	}

	public Collection<EnforcedRelation> matchType2(TIntHashSet candidateType2) {
		return match(candidateType2, type2Index);
	}

	public Collection<EnforcedRelation> matchRoles(TIntHashSet candidateRoles) {
		return match(candidateRoles, rolesIndex);
	}

	protected Collection<EnforcedRelation> match(TIntHashSet subset,
			TIntObjectHashMap<Collection<EnforcedRelation>> index) {
		TIntIterator keyI = subset.iterator();

		int firstKey = keyI.next();

		if (index.get(firstKey) == null) {
			return Collections.emptyList();
		}
		Collection<EnforcedRelation> result = new ArrayList<EnforcedRelation>(
				index.get(firstKey));

		if (result.isEmpty()) {
			return Collections.emptyList();
		}

		while (keyI.hasNext()) {
			int key = keyI.next();

			if (result.isEmpty())
				return result;

			Collection<EnforcedRelation> rest = index.get(key);
			if (rest == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest);
		}

		return result;
	}

	public Collection<EnforcedRelation> matchRolesAndType2(
			TIntHashSet candidateRoles, TIntHashSet candidateType2) {
		TIntIterator roleKeyI = candidateRoles.iterator();

		if (!roleKeyI.hasNext()) {
			return new ArrayList<EnforcedRelation>(1);
		}

		int firstRoleKey = roleKeyI.next();

		if (rolesIndex.get(firstRoleKey) == null) {
			return Collections.emptyList();
		}
		Collection<EnforcedRelation> result = new ArrayList<EnforcedRelation>(
				rolesIndex.get(firstRoleKey));

		if (result.isEmpty()) {
			return Collections.emptyList();
		}

		while (roleKeyI.hasNext()) {
			int key = roleKeyI.next();

			if (result.isEmpty())
				return result;

			Collection<EnforcedRelation> rest = rolesIndex.get(key);
			if (rest == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest);
		}

		TIntIterator type2KeyI = candidateType2.iterator();
		// Old code: while (roleKeyI.hasNext()) {
		// Kien changed roleKeyI to type2KeyI
		while (type2KeyI.hasNext()) {
			int key = type2KeyI.next();

			if (result.isEmpty())
				return result;

			Collection<EnforcedRelation> rest = type2Index.get(key);
			if (rest == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest);
		}

		return result;
	}

	// Kien added this mathRolesType1
	public Collection<EnforcedRelation> matchRolesAndType1(
			TIntHashSet candidateRoles, TIntHashSet candidateType1) {
		TIntIterator roleKeyI = candidateRoles.iterator();

		int firstRoleKey = roleKeyI.next();

		if (rolesIndex.get(firstRoleKey) == null) {
			return Collections.emptyList();
		}
		Collection<EnforcedRelation> result = new ArrayList<EnforcedRelation>(
				rolesIndex.get(firstRoleKey));

		if (result.isEmpty()) {
			return Collections.emptyList();
		}

		while (roleKeyI.hasNext()) {
			int key = roleKeyI.next();

			if (result.isEmpty())
				return result;

			Collection<EnforcedRelation> rest = rolesIndex.get(key);
			if (rest == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest);
		}

		TIntIterator type1KeyI = candidateType1.iterator();
		while (type1KeyI.hasNext()) {
			int key = type1KeyI.next();

			if (result.isEmpty())
				return result;

			Collection<EnforcedRelation> rest = type1Index.get(key);
			if (rest == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest);
		}

		return result;
	}

	// Kien added this mathRolesAnhType1AndType2
	public Collection<EnforcedRelation> matchRolesAndType1AndType2(
			TIntHashSet candidateRoles, TIntHashSet candidateType1,
			TIntHashSet candidateType2) {
		TIntIterator roleKeyI = candidateRoles.iterator();

		int firstRoleKey = roleKeyI.next();

		if (rolesIndex.get(firstRoleKey) == null) {
			return Collections.emptyList();
		}
		Collection<EnforcedRelation> result = new ArrayList<EnforcedRelation>(
				rolesIndex.get(firstRoleKey));

		if (result.isEmpty()) {
			return Collections.emptyList();
		}

		while (roleKeyI.hasNext()) {
			int key = roleKeyI.next();

			if (result.isEmpty())
				return result;

			Collection<EnforcedRelation> rest = rolesIndex.get(key);
			if (rest == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest);
		}

		TIntIterator type1KeyI = candidateType1.iterator();
		while (type1KeyI.hasNext()) {
			int key = type1KeyI.next();

			if (result.isEmpty())
				return result;

			Collection<EnforcedRelation> rest = type1Index.get(key);
			if (rest == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest);
		}
		TIntIterator type2KeyI = candidateType2.iterator();
		// Old code: while (roleKeyI.hasNext()) {
		// Kien changed roleKeyI to type2KeyI
		while (type2KeyI.hasNext()) {
			int key = type2KeyI.next();

			if (result.isEmpty())
				return result;

			Collection<EnforcedRelation> rest2 = type2Index.get(key);
			if (rest2 == null) {
				return Collections.emptyList();
			}
			result.retainAll(rest2);
		}
		return result;
	}

	public boolean updateNewImpl(HornImplication impl) {

		Collection<EnforcedRelation> list = this.matchType2(impl.getBody());

		for (EnforcedRelation enf : list) {
			remove(enf);
			if (enf.getType2().add(impl.getHead())) {
				enf.computeImplicationClosure();
			}
			add(enf);
		}

		return !list.isEmpty();
	}

	private boolean subsubmedEnf(EnforcedRelation enf) {
		/*
		 * TODO:
		 * For now, we store every new enf
		 * Unless we find a cheap way to check the redundant enf
		 */
		return this.enfs.contains(enf);
		/*
		 * This is very expensive check; bottleneck of the system
		 * handling large ontologies
		 * for (EnforcedRelation i : this.enfs) {
		 * if (i.getRoles().containsAll(enf.getRoles())
		 * && i.getType2().containsAll(enf.getType2())
		 * && enf.getType1().containsAll(i.getType1())
		 * && i.getType1().containsAll(enf.getType1()))
		 * return true;
		 * }
		 * 
		 * return false;
		 */
	}

}
