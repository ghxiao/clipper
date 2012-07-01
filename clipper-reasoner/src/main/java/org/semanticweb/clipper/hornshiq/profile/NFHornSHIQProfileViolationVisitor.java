package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.owlapi.profiles.UseOfAnonymousIndividual;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
import org.semanticweb.owlapi.profiles.*;
/**
 * Author: Matthew Horridge<br> Kien Modified
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public interface NFHornSHIQProfileViolationVisitor {

    void visit(UseOfAnonymousIndividual individual);

    void visit(UseOfDataOneOfWithMultipleLiterals individual);

    void visit(UseOfIllegalAxiom individual);

    void visit(UseOfIllegalDataRange individual);

    void visit(UseOfNonAtomicClassExpression individual);

    void visit(UseOfNonSubClassExpression individual);
    
    void visit(UseOfNonSuperClassExpression individual);
    void visit(UseOfNonSimplePropertyInCardinalityRestriction individual);
    
    void visit(UseOfObjectOneOf individual);
    void visit(UseOfObjectUnionOf individual);
    void visit (UseOfNonSimplePropertyInSubPropertyAxiom axiom);
}
