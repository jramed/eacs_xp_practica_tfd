package es.upm.eacs.xp.practica.tfd;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class EquationSystemTest {

    @Test
    public void givenAnEmptyEquationSytem_whenCheckingEguality_thenEgual() {
        EquationSystem equationSystem1 = new EquationSystem();
        EquationSystem equationSystem2 = new EquationSystem();

        assertTrue(equationSystem1.equal(equationSystem2));

    }

    @Test
    public void givenAnEmptyEquationSytem_whenAddAnEquation_thenEquationAdded() {
        EquationSystem equationSystem1 = new EquationSystem();
        EquationSystem equationSystem2 = new EquationSystem();
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .term(2f, "X").build();
        Equation equation2 = equation1.clon();
        equationSystem1.add(equation1);
        equationSystem2.add(equation2);

        assertTrue(equationSystem1.equal(equationSystem2));
    }

    @Test
    public void givenAnEquationSystemWithSeveralEquation_whenCheckingEqualityWithAn_thenEqual() {
        EquationSystem equationSystem1 = new EquationSystem();
        EquationSystem equationSystem2 = new EquationSystem();
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .term(2f, "X").build();
        Equation equation2 = equation1.clon();
        Equation equation3 = new EquationBuilder().term(3f, "X").term(-1f, "Z").term(-4f).assign().term(5f, "Y")
                .term(2f, "Y").build();
        Equation equation4 = equation3.clon();
        equationSystem1.add(equation3);
        equationSystem1.add(equation1);
        equationSystem2.add(equation2);
        equationSystem2.add(equation4);

        assertTrue(equationSystem1.equal(equationSystem2));
    }

    @Test
    public void givenAnEquationSystem_whenGetNameSet_thenObtainGetNameSet() {
        EquationSystem equationSystem1 = new EquationSystem();
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .term(2f, "X").build();
        Equation equation2 = new EquationBuilder().term(3f, "X").term(-1f, "Z").term(-4f).assign().term(5f, "Y")
                .term(2f, "Y").build();
        equationSystem1.add(equation1);
        equationSystem1.add(equation2);
        Set<String> myNameSet = new HashSet<String>();
        myNameSet.add("X");
        myNameSet.add("Y");
        myNameSet.add("Z");

        assertThat(myNameSet, equalTo(equationSystem1.getNameSet()));
    }

    @Test
    public void givenAnEquationSystem_WhenGetIndex_thenReturnEquationFromThatIndex() {
        EquationSystem equationSystem1 = new EquationSystem();
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .term(2f, "X").build();
        Equation equation2 = new EquationBuilder().term(3f, "X").term(-1f, "Z").term(-4f).assign().term(5f, "Y")
                .term(2f, "Y").build();
        equationSystem1.add(equation1);
        equationSystem1.add(equation2);

        assertTrue(equation1.equal(equationSystem1.get(0)));
        assertTrue(equation2.equal(equationSystem1.get(1)));
    }
}
