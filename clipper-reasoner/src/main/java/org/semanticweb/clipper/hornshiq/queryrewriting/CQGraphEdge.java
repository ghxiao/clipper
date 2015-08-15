package org.semanticweb.clipper.hornshiq.queryrewriting;

import org.semanticweb.clipper.hornshiq.rule.Term;

/**
 * Immutable
 */
public class CQGraphEdge {

    private Term source, dest;

    private Integer role;

    public Term getSource() {
        return this.source;
    }

    public Term getDest() {
        return this.dest;
    }

    public Integer getRole() {
        return this.role;
    }

    public CQGraphEdge(Term source, Term dest, Integer role) {
        this.source = source;
        this.dest = dest;
        this.role = role;
    }

    @Override
    public String toString() {
        return "<" + source + ", " + dest + ">[" + role + "]";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CQGraphEdge)) return false;
        final CQGraphEdge other = (CQGraphEdge) o;
        if (!other.canEqual(this)) return false;
        final Object this$source = this.source;
        final Object other$source = other.source;
        if (this$source == null ? other$source != null : !this$source.equals(other$source)) return false;
        final Object this$dest = this.dest;
        final Object other$dest = other.dest;
        if (this$dest == null ? other$dest != null : !this$dest.equals(other$dest)) return false;
        final Object this$role = this.role;
        final Object other$role = other.role;
        return !(this$role == null ? other$role != null : !this$role.equals(other$role));
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $source = this.source;
        result = result * PRIME + ($source == null ? 0 : $source.hashCode());
        final Object $dest = this.dest;
        result = result * PRIME + ($dest == null ? 0 : $dest.hashCode());
        final Object $role = this.role;
        result = result * PRIME + ($role == null ? 0 : $role.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof CQGraphEdge;
    }
}