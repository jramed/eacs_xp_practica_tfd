package es.upm.eacs.xp.practica.tfd;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void givenAnEmptyExpression_whenAddAnotherExpression_thenNoEmpty() {
        Expression expr1 = new Expression();
        expr1.add(buildVariable(aFloat(0.023f), varName("X")));
        expr1.add(buildConstant(aFloat(45f)));
        Expression expr2 = new Expression();
        expr2.add(expr1);
        assertFalse(expr2.empty());
    }

    // \TODO add more test to test add(Expression) method, when getValue and
    // GetNameSet are implemented

    @Test
    public void givenANonEmptyExpression_whenAskedForAValue_thenReturnValue() {
        Expression expr1 = new Expression();
        expr1.add(buildVariable(aFloat(0.023f), varName("X")));
        expr1.add(buildConstant(aFloat(45f)));
        assertThat(0.023f, equalTo(expr1.getValue()));
    }

    @Test
    public void givenANonEmptyExpression_whenAskedForSetOfName_ThenReturnSetOfName() {
        Expression expr1 = new Expression();
        expr1.add(buildVariable(aFloat(0.023f), varName("X")));
        expr1.add(buildConstant(aFloat(45f)));

        Set<String> myNameSet = new HashSet<String>();
        myNameSet.add(varName("X"));

        Set<String> nameSet = expr1.getNameSet();
        assertThat(myNameSet, equalTo(nameSet));
    }

}
