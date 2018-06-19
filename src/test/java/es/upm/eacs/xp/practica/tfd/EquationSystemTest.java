package es.upm.eacs.xp.practica.tfd;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EquationSystemTest {

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

}
