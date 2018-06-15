package es.upm.eacs.xp.practica.tfd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EquationTest {

    public static float aFloat(float number) {
        return number;
    }

    public static String varName(String name) {
        return name;
    }

    public static Constant buildConstant(float value) {
        return new Constant(value);
    }

    public static Variable buildVariable(float value, String name) {
        return new Variable(value, name);
    }

    @Test
    public void givenAnEmptyEquation_whenCheckedForEmptiness_thenItisEmpty() {
        Equation equation = new Equation();

        assertTrue(equation.empty());
    }

    @Test
    public void givenANonEmptyEquation_whenCheckdForEmptiness_thenNonEmpty() {
        Variable var1 = buildVariable(aFloat(4f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.4f), varName("Y"));
        Constant cte = buildConstant(aFloat(3.5f));

        Expression expression = new ExpressionBuilder().term(cte).term(var1).term(var2).build();
        Equation equation = new Equation(expression);

        assertFalse(equation.empty());
    }

    @Test
    public void givenAnEmptyEquation_whenAddATerm_thenEquationIsNotEmpty() {
        Equation equation = new Equation();
        Constant cte = buildConstant(aFloat(3.9f));

        equation.add(cte);
        assertFalse(equation.empty());
    }
}
