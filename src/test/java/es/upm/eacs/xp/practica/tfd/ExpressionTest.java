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

    public static Constant buildConstant(float value) {
        return new Constant(value);
    }

    public static Variable buildVariable(float value, String name) {
        return new Variable(value, name);
    }

    @Test
    public void givenAnEmptyExpression_whenAddAnotherExpression_thenValuesCanBeGot() {
        Expression expr1 = new Expression();
        expr1.add(buildVariable(aFloat(0.023f), varName("X")));
        expr1.add(buildConstant(aFloat(45f)));
        Expression expr2 = new Expression();
        expr2.add(expr1);
        assertThat(45f, equalTo(aFloat(expr2.getValue())));
        assertThat(0.023f, equalTo(aFloat(expr1.getValue(varName("X")))));
    }

    @Test
    public void givenANonEmptyExpressionWithVarAndConstant_whenAskedForAValueNoName_thenReturnValue() {
        Expression expr1 = new Expression();
        expr1.add(buildVariable(aFloat(0.023f), varName("X")));
        expr1.add(buildConstant(aFloat(45f)));
        assertThat(45f, equalTo(aFloat(expr1.getValue())));
    }

    @Test
    public void givenANonEmptyExpressionWithVar_whenAskedForAValueWithNoName_thenReturn0f() {
        Expression expr1 = new Expression();
        expr1.add(buildVariable(aFloat(0.023f), varName("X")));
        assertThat(0.0f, equalTo(aFloat(expr1.getValue())));
    }

    @Test
    public void givenANonEmptyExpressionWithConstant_whenAskedForAValueWithNoName_thenReturn() {
        Expression expr1 = new Expression();
        expr1.add(buildConstant(aFloat(88f)));
        assertThat(88f, equalTo(expr1.getValue()));
    }

    @Test(expected = AssertionError.class)
    public void givenAnEmptyExpression_whenAskedForAValueWithNoName_thenAssertException() {
        Expression expr1 = new Expression();
        expr1.getValue();
    }

    @Test
    public void givenAnExpressionWithOneConstant_whenAskForValueWithNoName_thenReturnValue() {
        Expression expr = new Expression();
        Constant cte = buildConstant(aFloat(33.33f));
        expr.add(cte);
        assertThat(cte.getValue(), equalTo(expr.getValue()));
    }

    @Test
    public void givenAnExpressionWithOneVar_whenAskForValueWithName_thenReturnValue() {
        Expression expr = new Expression();
        Variable var = buildVariable(aFloat(-9.23f), varName("X"));
        expr.add(var);
        assertThat(var.getValue(), equalTo(expr.getValue(varName("X"))));
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

    @Test(expected = AssertionError.class)
    public void givenAnEmptyExpression_whenAskedForAValueWithName_thenAssertException() {
        Expression expr1 = new Expression();
        expr1.getValue(varName("X"));
    }

    @Test
    public void givenAnExpressionWithMultipleVariableAndAConstant_WhenAskedForValueWithName_thenReturnValue() {
        Variable var1 = buildVariable(aFloat(0.023f), varName("X"));
        Variable var2 = buildVariable(aFloat(-67f), varName("Y"));
        Variable var3 = buildVariable(aFloat(1.34f), varName("Z"));
        Expression expr1 = new ExpressionBuilder().term(var1).term(var2).term(var3).build();
        expr1.add(buildConstant(aFloat(-52f)));

        assertThat(var1.getValue(), equalTo(expr1.getValue(varName("X"))));
        assertThat(var2.getValue(), equalTo(expr1.getValue(varName("Y"))));
        assertThat(var3.getValue(), equalTo(expr1.getValue(varName("Z"))));

    }

    @Test
    public void givenAnExpressionWithMultipleVariableAndAConstant_WhenAskedForValueWithNoName_thenReturnValue() {
        Variable var1 = buildVariable(aFloat(0.023f), varName("X"));
        Variable var2 = buildVariable(aFloat(-67f), varName("Y"));
        Variable var3 = buildVariable(aFloat(1.34f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(-52f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        assertThat(cte1.getValue(), equalTo(expr1.getValue()));
    }

    @Test
    public void giveAnExpression_whenMultipleByAConstant_thenAllTermsAreMultiplyByTheNumber() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        float aMultiplier = 5.5f;
        expr1.multiply(aMultiplier);

        assertThat((2f * aMultiplier), equalTo(expr1.getValue(varName("X"))));
        assertThat((-3.5f * aMultiplier), equalTo(expr1.getValue(varName("Y"))));
        assertThat((4.2f * aMultiplier), equalTo(expr1.getValue(varName("Z"))));
        assertThat((5.1f * aMultiplier), equalTo(expr1.getValue()));
    }

    @Test
    public void givenAnExpressionWithConstant_whenCheckedEqualityWithAnotherIdenticalExpression_thenequal() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).build();
        Expression result = new ExpressionBuilder().term(cte1).build();
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithSeveralVariables_whenCheckedEqualityWithAnotherIdenticalExpression_thenequal() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Expression expr1 = new ExpressionBuilder().term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(var1).term(var2).term(var3).build();

        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithSeveralTerms_whenCheckedEqualityWithAnotherIdenticalExpression_thenequal() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        assertTrue(expr1.equal(result));
    }

    @Test
    public void givenAnExpressionWithConstant_whenCheckedEqualityWithAnEmptyExpression_thenNonEqual() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).build();
        Expression result = new ExpressionBuilder().build();
        assertFalse(expr1.equal(result));
    }

    @Test
    public void givenAnExpressionWithConstant_whenCheckedEqualityWithADifferentExpression_thenNonEqual() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Constant cte2 = buildConstant(aFloat(1.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).build();
        Expression result = new ExpressionBuilder().term(cte2).build();
        assertFalse(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithSeveralVariables_whenCheckedEqualityWithADifferentExpression_thenNonEqual() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Expression expr1 = new ExpressionBuilder().term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(var2).term(var3).build();

        assertFalse(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithSeveralTerms_whenCheckedEqualityWithADifferentExpression_thenNonEqual() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(var1).term(var2).term(var3).build();

        assertFalse(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithAConstant_whenSimplify_thenKeepTheSameExpr() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).build();
        Expression result = new ExpressionBuilder().term(cte1).build();

        expr1.simplify();
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithAConstantAndAVariable_whenSimplify_thenKeepTheSameExpr() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).build();
        Expression result = new ExpressionBuilder().term(cte1).term(var1).build();

        expr1.simplify();
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithServeralConstant_whenSimplify_thenConstantsSimplified() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Constant cte2 = buildConstant(aFloat(4.0f));
        Constant cte3 = buildConstant(aFloat(1.0f));
        Constant cte4 = buildConstant(aFloat(0.1f));
        Expression expr1 = new ExpressionBuilder().term(cte2).term(cte3).term(cte4).build();
        Expression result = new ExpressionBuilder().term(cte1).build();

        expr1.simplify();
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithServeralConstantAndVariables_whenSimplify_thenJustOneConstantAndTheSameVar() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Constant cte2 = buildConstant(aFloat(4.0f));
        Constant cte3 = buildConstant(aFloat(1.0f));
        Constant cte4 = buildConstant(aFloat(0.1f));
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Expression expr1 = new ExpressionBuilder().term(cte2).term(cte3).term(cte4).term(var1).term(var2).term(var3)
                .build();
        Expression result = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        expr1.simplify();
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWhitoutRepeatedTerms_whenSimplify_thenKeepTheSameExpr() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        expr1.simplify();
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithVariablesRepeated_whenSimplify_thenVariable() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("X"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        expr1.simplify();
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithConstantNulable_whenSimplify_thenExprWithNoConstant() {
        Constant cte1 = buildConstant(aFloat(-1.1f));
        Constant cte2 = buildConstant(aFloat(1.1f));
        Constant cte3 = buildConstant(aFloat(0.0f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(cte2).build();
        Expression result = new ExpressionBuilder().term(cte3).build();

        expr1.simplify();
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithNonVariableRepeated_whenSimplifyVar_thenKeepTheSameExpr() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Expression expr1 = new ExpressionBuilder().term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(var1).term(var2).term(var3).build();

        expr1.simplify(varName("X"));
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWhitoutRepeatedTerms_whenSimplifyVar_thenKeepTheSameExpr() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        expr1.simplify(varName("X"));
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithSeveralVariableRepeated_whenSimplifyVar_thenVariableSimplified() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("X"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Variable var4 = buildVariable(aFloat(-1.5f), varName("X"));
        Expression expr1 = new ExpressionBuilder().term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(var4).term(var3).build();

        expr1.simplify(varName("X"));
        assertTrue(expr1.equal(result));
    }

    @Test
    public void giveAnExpressionWithVariableNulable_whenSimplify_thenExprWithoutThatVariable() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-2.0f), varName("X"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Expression expr1 = new ExpressionBuilder().term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(var3).build();

        expr1.simplify(varName("X"));
        assertTrue(expr1.equal(result));
    }

    @Test
    public void givenAnExpression_whenClon_thenGotNewExpression() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-2.0f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Expression expr1 = new ExpressionBuilder().term(var1).term(var2).term(var3).build();

        Expression expr2 = expr1.clon();
        assertTrue(expr1.equal(expr2));
    }

    @Test
    public void givenAnExpressionWithOnlyConstant_whenRequestHasName_thenReturnFalse() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).build();

        assertFalse(expr1.hasName(varName("X")));
    }

    @Test
    public void givenAnExpression_whenRequestANameDoesNotExist_thenReturnFalse() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        assertFalse(expr1.hasName(varName("T")));
    }

    @Test
    public void givenAnExpression_whenRequestANameExist_thenReturnTrue() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();

        assertTrue(expr1.hasName(varName("X")));
    }

    @Test
    public void givenAnEmptyExpresssion_whenApplyAValueForAVariable_thenTheExpressionIsStillEmpty() {
        Expression expr1 = new Expression();
        Expression result = new Expression();

        expr1.apply(varName("X"), aFloat(2f));
        assertTrue(expr1.equal(result));

    }

    @Test
    public void givenAnExpresssionWithOnlyConstant_whenApplyAValueForAVariable_thenTheExpressionIsStillEmpty() {
        Constant cte1 = buildConstant(aFloat(5.1f));
        Expression expr1 = new ExpressionBuilder().term(cte1).build();
        Expression result = new ExpressionBuilder().term(cte1).build();

        expr1.apply(varName("X"), aFloat(2f));
        assertTrue(expr1.equal(result));
    }

    @Test
    public void givenAnExpresssion_whenApplyAValueForAVariable_thenTermIsWorkedOutAsConstant() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));
        Constant cte2 = buildConstant(aFloat(5.1f + (2f * 2f)));
        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(cte2).term(var2).term(var3).build();

        expr1.apply(varName("X"), aFloat(2f));
        assertTrue(expr1.equal(result));
    }

    @Test
    public void givenAnExpresssionContainsAVarWithTheSameName_whenApplyAValueForAVariable_thenTermIsWorkedOutAsConstant() {
        Variable var1 = buildVariable(aFloat(2f), varName("Y"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(-3.5f * 2f));
        Constant cte2 = buildConstant(aFloat(2f * 2f));
        Expression expr1 = new ExpressionBuilder().term(var1).term(var2).term(var3).build();
        Expression result = new ExpressionBuilder().term(cte1).term(cte2).term(var3).build();

        expr1.apply(varName("Y"), aFloat(2f));
        assertFalse(expr1.equal(result));
    }

    @Test
    public void givenAnEmptyExpression_whenTranslateToSTring_thenReturnEmptyString() {
        Expression expr1 = new Expression();
        String result = "";

        assertThat(result, equalTo(expr1.toString()));
    }

    @Test
    public void givenAnExpressionWithConstantAndVariables_whenTranslateToSTring_thenReturnExpressionAsString() {
        Variable var1 = buildVariable(aFloat(2f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.5f), varName("Y"));
        Variable var3 = buildVariable(aFloat(4.2f), varName("Z"));
        Constant cte1 = buildConstant(aFloat(5.1f));

        Expression expr1 = new ExpressionBuilder().term(cte1).term(var1).term(var2).term(var3).build();
        String result = "+5.1+2.0X-3.5Y+4.2Z";

        assertThat(result, equalTo(expr1.toString()));
    }

}
