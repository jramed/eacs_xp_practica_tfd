package es.upm.eacs.xp.practica.tfd;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

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
    public void givenTwoEmptyEquations_whenCheckedForEqualness_thenEquals() {
        Equation equation1 = new Equation();
        Equation equation2 = new Equation();

        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenTwoNonEmptyButEqualEquation_whenCheckdForEqualness_thenEquals() {
        Variable var1 = buildVariable(aFloat(4f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.4f), varName("Y"));
        Constant cte1 = buildConstant(aFloat(3.5f));
        Constant cte2 = buildConstant(aFloat(5f));

        Expression[] expressions1 = new Expression[] { new ExpressionBuilder().term(cte1).term(var1).term(var2).build(),
                new ExpressionBuilder().term(cte2).build() };
        Expression[] expressions2 = new Expression[] { new ExpressionBuilder().term(cte1).term(var1).term(var2).build(),
                new ExpressionBuilder().term(cte2).build() };
        Equation equation1 = new Equation(expressions1);
        Equation equation2 = new Equation(expressions2);

        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenTwoNonEmptyAndNonEqualEquation_whenCheckdForEqualness_thenNonEquals() {
        Variable var1 = buildVariable(aFloat(4f), varName("X"));
        Variable var2 = buildVariable(aFloat(-3.4f), varName("Y"));
        Variable var3 = buildVariable(aFloat(45f), varName("Y"));
        Constant cte1 = buildConstant(aFloat(3.5f));
        Constant cte2 = buildConstant(aFloat(5f));

        Expression[] expressions1 = new Expression[] { new ExpressionBuilder().term(cte1).term(var1).term(var2).build(),
                new ExpressionBuilder().term(cte2).build() };
        Expression[] expressions2 = new Expression[] { new ExpressionBuilder().term(cte2).term(var1).term(var3).build(),
                new ExpressionBuilder().term(cte1).build() };
        Equation equation1 = new Equation(expressions1);
        Equation equation2 = new Equation(expressions2);

        assertFalse(equation1.equal(equation2));
    }

    @Test
    public void givenAnEquation_whenAddATermInRigthSide_thenEqualToTheSameEquation() {
        Equation equation1 = new EquationBuilder().term(3.4f).term(1f, "X").term(-4.5f, "Y").assign().term(-8.1f, "Z")
                .build();
        equation1.add(Side.RIGHT, buildConstant(-9f));

        Equation equation2 = new EquationBuilder().term(3.4f).term(1f, "X").term(-4.5f, "Y").assign().term(-8.1f, "Z")
                .term(-9f).build();
        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenAnEquation_whenAddATermInLeftSide_thenEqualToTheSameEquation() {
        Equation equation1 = new EquationBuilder().term(3.4f).term(1f, "X").term(-4.5f, "Y").assign().term(-8.1f, "Z")
                .build();
        equation1.add(Side.LEFT, buildConstant(-9f));

        Equation equation2 = new EquationBuilder().term(3.4f).term(-9f).term(1f, "X").term(-4.5f, "Y").assign()
                .term(-8.1f, "Z").build();
        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenAnEmptyEquation_whenAddAnEquation_thenEqualToTheSameEquation() {
        Equation equation1 = new Equation();
        Equation equation2 = new EquationBuilder().term(3.4f).term(-9f).term(1f, "X").term(-4.5f, "Y").assign()
                .term(-8.1f, "Z").build();

        equation1.add(equation2);
        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenANonEmptyEquation_whenAddAnEquation_thenEqualToTheSameEquation() {
        Equation equation1 = new EquationBuilder().term(3.4f).term(1f, "X").term(-4.5f, "Y").assign().term(-8.1f, "Z")
                .build();
        Equation equation2 = new EquationBuilder().term(3.4f).term(1f, "X").term(-4.5f, "Y").assign().term(-8.1f, "Z")
                .build();

        equation1.add(equation2);

        Equation equationResult = new EquationBuilder().term(3.4f).term(3.4f).term(1f, "X").term(1f, "X")
                .term(-4.5f, "Y").term(-4.5f, "Y").assign().term(-8.1f, "Z").term(-8.1f, "Z").build();
        assertTrue(equation1.equal(equationResult));
    }

    @Test(expected = AssertionError.class)
    public void givenAnEmptyEquation_whenMulplityByANumber_thenAssert() {
        Equation equation1 = new Equation();
        equation1.multiply(3f);
    }

    @Test
    public void givenANonEmptyEquation_whenMulplityByANumber_thenAllTermOfTheEquationAreMultiplyByThatNumber() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();
        equation1.multiply(3f);

        Equation equationResult = new EquationBuilder().term(9f).term(-3f, "X").term(-12f, "Y").assign().term(15f, "Z")
                .build();

        assertTrue(equation1.equal(equationResult));
    }

    @Test
    public void givenANonEmptyEquation_whenGetValueNameInRightSide_thenTheValuaAssociatedWithThatVarNameAreReturned() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();

        assertThat(-1f, equalTo(equation1.getValue("X")));
    }

    @Test
    public void givenANonEmptyEquation_whenGetValueNameInLefttSide_thenTheValuaAssociatedWithThatVarNameAreReturned() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();

        assertThat(5f, equalTo(equation1.getValue("Z")));
    }

    @Test
    public void givenANonEmptyEquation_whenGetValueNameButDoesNotExit_thenRerturned0f() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();

        assertThat(0f, equalTo(equation1.getValue("T")));
    }

    @Test
    public void givenAnEmptyEquation_whenGetValueNameButDoesNotExit_thenRerturned0f() {
        Equation equation1 = new Equation();

        assertThat(0f, equalTo(equation1.getValue("T")));
    }

    @Test
    public void givenANonEmptyEquation_whenGetValueFromLefttSide_thenTheConstantValueIsReturnedIfAny() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();

        assertThat(3f, equalTo(equation1.getValue(Side.LEFT)));
    }

    @Test
    public void givenANonEmptyEquation_whenGetValueFromLefttSide_then0fValueIsReturnedIfAny() {
        Equation equation1 = new EquationBuilder().term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z").term(3f)
                .build();

        assertThat(0f, equalTo(equation1.getValue(Side.LEFT)));
    }

    @Test
    public void givenANonEmptyEquation_whenGetValueFromRighttSide_then0FValueIsReturnedIfAny() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();

        assertThat(0f, equalTo(equation1.getValue(Side.RIGHT)));
    }

    @Test
    public void givenANonEmptyEquation_whenGetValueFromRighttSide_thenTheConstantValueIsReturnedIfAny() {
        Equation equation1 = new EquationBuilder().term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z").term(3f)
                .build();

        assertThat(3f, equalTo(equation1.getValue(Side.RIGHT)));
    }

    @Test(expected = AssertionError.class)
    public void givenAnEmptyEquation_whenGetValueFromRight_thenAssert() {
        Equation equation1 = new Equation();
        equation1.getValue(Side.RIGHT);
    }

    @Test(expected = AssertionError.class)
    public void givenAnEmptyEquation_whenGetValueFromLeft_thenAssert() {
        Equation equation1 = new Equation();
        equation1.getValue(Side.LEFT);
    }

    @Test
    public void givenANonEmptyEquation_whenSimplifyOnLefttSideWithNoVarName_thenConstantsAreSimplified() {
        Equation equation1 = new EquationBuilder().term(-4f).term(67f).term(3f).term(-1f, "X").term(-4f, "Y").assign()
                .term(5f, "Z").term(4f).term(-3f).build();
        Equation equation2 = new EquationBuilder().term(66f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .term(4f).term(-3f).build();

        equation1.simplify(Side.LEFT);

        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenANonEmptyEquation_whenSimplifyOnLeftSideWithNoVarName_thenConstantsAreSimplified() {
        Equation equation1 = new EquationBuilder().term(-4f).term(67f).term(3f).term(-1f, "X").term(-4f, "Y").assign()
                .term(5f, "Z").term(4f).term(-3f).build();
        Equation equation2 = new EquationBuilder().term(-4f).term(67f).term(3f).term(-1f, "X").term(-4f, "Y").assign()
                .term(5f, "Z").term(1f).build();

        equation1.simplify(Side.RIGHT);

        assertTrue(equation1.equal(equation2));
    }

    @Test(expected = AssertionError.class)
    public void givenAnExpressionWithLeftSideEmptyAndRightSideNonEmpty_whenSimplifyLeftSide_thenAssert() {
        Constant cte2 = buildConstant(aFloat(5f));
        Expression[] expressions1 = new Expression[] { new Expression(), new ExpressionBuilder().term(cte2).build() };

        Equation equation1 = new Equation(expressions1);
        equation1.simplify(Side.LEFT);
    }

    @Test(expected = AssertionError.class)
    public void givenAnExpressionWithLEfSideNonEmptyAndRightSideEmpty_whenSimplifyRightSide_thenAssert() {
        Constant cte2 = buildConstant(aFloat(5f));

        Expression[] expressions1 = new Expression[] { new ExpressionBuilder().term(cte2).build(), new Expression() };
        Equation equation1 = new Equation(expressions1);
        equation1.simplify(Side.RIGHT);
    }

    @Test
    public void givenANonEmptyEquation_whenSimplifyOnLefttSideWithVarName_thenVariableIsSimplifiedIsLeftSide() {
        Equation equation1 = new EquationBuilder().term(-4f).term(67f).term(3f).term(-1f, "X").term(-4f, "Y")
                .term(82f, "Y").assign().term(5f, "Z").term(4f).term(-3f).build();
        Equation equation2 = new EquationBuilder().term(-4f).term(67f).term(3f).term(-1f, "X").term(78f, "Y").assign()
                .term(5f, "Z").term(4f).term(-3f).build();

        equation1.simplify(Side.LEFT, "Y");

        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenANonEmptyEquation_whenSimplifyOnLeftSideWithVarName_thenVariableISSimplifiedInRight() {
        Equation equation1 = new EquationBuilder().term(-4f).term(67f).term(3f).term(-1f, "X").term(4f, "X")
                .term(-4f, "Y").term(1f, "Y").assign().term(5f, "Z").term(6.6f, "Z").term(4f).term(-3f).build();
        Equation equation2 = new EquationBuilder().term(-4f).term(67f).term(3f).term(-1f, "X").term(4f, "X")
                .term(-4f, "Y").term(1f, "Y").assign().term(11.6f, "Z").term(4f).term(-3f).build();

        equation1.simplify(Side.RIGHT, "Z");

        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenANonEmptyEquation_whenGetNameSet_thenReturnASetWithAllVariableNames() {
        Equation equation1 = new EquationBuilder().term(-4f).term(67f).term(3f).term(-1f, "X").term(4f, "X")
                .term(-4f, "Y").term(1f, "Y").assign().term(5f, "Z").term(6.6f, "Z").term(4f).term(-3f).build();
        Set<String> myNameSet = new HashSet<String>();
        myNameSet.add("X");
        myNameSet.add("Y");
        myNameSet.add("Z");

        Set<String> nameSet = equation1.getNameSet();
        assertThat(myNameSet, equalTo(nameSet));
    }

    @Test
    public void givenANonEmptyEquationWithOnlyConstants_whenGetNameSet_thenReturnAnEmptySetOfNames() {
        Equation equation1 = new EquationBuilder().term(-4f).term(67f).term(3f).assign().term(4f).term(-3f).build();
        Set<String> myNameSet = new HashSet<String>();

        Set<String> nameSet = equation1.getNameSet();
        assertThat(myNameSet, equalTo(nameSet));
    }

    @Test(expected = AssertionError.class)
    public void givenAnEmptyEquation_whenGetNameSet_thenAssert() {
        Equation equation1 = new Equation();

        equation1.getNameSet();
    }

    @Test
    public void givenANonEmptyEquation_whenRequestAClon_thenClonnedEquationEqualToOriginal() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();
        Equation equation2 = equation1.clon();

        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenAnEmptyEquation_whenRequestedToString_thenReturnEmptyString() {
        Equation equation1 = new Equation();
        String result = "";

        assertThat(result, equalTo(equation1.toString()));
    }

    @Test
    public void givenANonEmptyEquation_whenRequestedToString_thenReturnString() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();
        String result = "+3.0-1.0X-4.0Y = +5.0Z";

        assertThat(result, equalTo(equation1.toString()));
    }

    @Test
    public void givenAnEmptyEquation_whenApplyAValueForAVariable_thenEquationNotModified() {
        Equation equation1 = new Equation();
        Equation equation2 = new Equation();

        equation1.apply(3f, "X");
        assertTrue(equation1.equal(equation2));
    }

    @Test
    public void givenAnEquation_whenApplyAValueForAVariable_thenVariableSusbtituted() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .term(2f, "X").build();
        equation1.apply(5.3f, "X");

        Equation equation2 = new EquationBuilder().term(-2.3f).term(-4f, "Y").assign().term(5f, "Z").term(10.6f)
                .build();

        assertTrue(equation1.equal(equation2));

    }

    @Test
    public void givenAnEquation_whenApplyAValueForAVariableDoesNotExit_thenNotModified() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .term(2f, "X").build();
        equation1.apply(5.3f, "T");

        Equation equation2 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .term(2f, "X").build();

        assertTrue(equation1.equal(equation2));

    }
}
