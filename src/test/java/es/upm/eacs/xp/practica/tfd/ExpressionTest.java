package es.upm.eacs.xp.practica.tfd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExpressionTest {

    public static float aFloat(float number) {
        return number;
    }

    public static String varName(String name) {
        return name;
    }

    public static Term buildConstant(float value) {
        return new Constant(value);
    }

    public static Term buildVariable(float value, String name) {
        return new Variable(value, name);
    }

    @Test
    public void givenAnExpression_whenHasNoTerms_thenEmpty() {
        Expression expr = new Expression();
        assertTrue(expr.empty());
    }

    @Test
    public void givenAnExpression_whenHasOneConstant_thenNoEmpty() {
        Expression expr = new Expression();
        expr.add(buildConstant(aFloat(33.33f)));
        assertFalse(expr.empty());
    }

    @Test
    public void givenAnExpression_whenHasOneVariable_thenNoEmpty() {
        Expression expr = new Expression();
        expr.add(buildVariable(aFloat(-9.23f), varName("X")));
        assertFalse(expr.empty());
    }

    @Test
    public void givenAnExpression_whenHasOneVariableAndOneConstant_thenNoEmpty() {
        Expression expr = new Expression();
        expr.add(buildVariable(aFloat(0.023f), varName("X")));
        expr.add(buildConstant(aFloat(45f)));
        assertFalse(expr.empty());
    }

    // \TODO This test must be refactored. PRobably getting the nameSet or values to
    // check
    // the new expression has been added
    @Test
    public void givenAnExpression_whenAddAnotherExpression_thenNoEmpty() {
        Expression expr1 = new Expression();
        expr1.add(buildVariable(aFloat(0.023f), varName("X")));
        expr1.add(buildConstant(aFloat(45f)));
        Expression expr2 = new Expression();
        expr2.add(expr1);
        assertFalse(expr2.empty());
    }

}
