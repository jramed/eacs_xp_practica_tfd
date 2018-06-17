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

    @Test
    public void givenANonEmptyEquation_whenMulplityByANumber_thenAllTermOfTheEquationAreMultiplyByThatNumber() {
        Equation equation1 = new EquationBuilder().term(3f).term(-1f, "X").term(-4f, "Y").assign().term(5f, "Z")
                .build();
        equation1.multiply(3f);

        Equation equationResult = new EquationBuilder().term(9f).term(-3f, "X").term(-12f, "Y").assign().term(15f, "Z")
                .build();

        assertTrue(equation1.equal(equationResult));
    }
}
