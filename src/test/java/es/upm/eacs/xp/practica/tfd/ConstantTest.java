package es.upm.eacs.xp.practica.tfd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConstantTest {

    public static float aFloat(float number) {
        return number;
    }

    public static String varName(String name) {
        return name;
    }

    @Test
    public void givenTwoPositiveContanst_whenCte1IsFewerThanCt2_thenNotEquals() {
        Constant constant1 = new Constant(aFloat(2.5f));
        Constant constant2 = new Constant(aFloat(4f));
        assertThat(constant1.getValue(), lessThan(constant2.getValue()));
        assertFalse(constant1.equal(constant2));

    }

    @Test
    public void givenTwoPositiveContanst_whenCte1IsGreaterThanCt2_thenNotEquals() {
        Constant constant1 = new Constant(aFloat(700.5f));
        Constant constant2 = new Constant(aFloat(0.35f));
        assertThat(constant1.getValue(), greaterThan(constant2.getValue()));
        assertFalse(constant1.equal(constant2));

    }

    @Test
    public void givenTwoPositiveContanst_whenCte1IsEqualThanCt2_thenEquals() {
        Constant constant1 = new Constant(aFloat(29.987987f));
        Constant constant2 = new Constant(aFloat(29.987987f));
        assertThat(constant1.getValue(), equalTo(constant2.getValue()));
        assertTrue(constant1.equal(constant2));

    }

    @Test
    public void givenTwoNegativeContanst_whenCte1IsFewerThanCt2_thenNotEquals() {
        Constant constant1 = new Constant(aFloat(-9.5f));
        Constant constant2 = new Constant(aFloat(-0.000001f));
        assertThat(constant1.getValue(), lessThan(constant2.getValue()));
        assertFalse(constant1.equal(constant2));

    }

    @Test
    public void givenTwoNegativeContanst_whenCte1IsGreaterThanCt2_thenNotEquals() {
        Constant constant1 = new Constant(aFloat(-0.0001f));
        Constant constant2 = new Constant(aFloat(-0.0002f));
        assertThat(constant1.getValue(), greaterThan(constant2.getValue()));
        assertFalse(constant1.equal(constant2));

    }

    @Test
    public void givenTwoNegativeContanst_whenCte1IsEqualThanCt2_thenEquals() {
        Constant constant1 = new Constant(aFloat(-29.987987f));
        Constant constant2 = new Constant(aFloat(-29.987987f));
        assertThat(constant1.getValue(), equalTo(constant2.getValue()));
        assertTrue(constant1.equal(constant2));

    }

    @Test
    public void givenTwoContanstCloseToZero_whenCte1IsGreaterThanCt2_thenNonEquals() {
        Constant constant1 = new Constant(aFloat(0.00001f));
        Constant constant2 = new Constant(aFloat(-0.00001f));
        assertThat(constant1.getValue(), greaterThan(constant2.getValue()));
        assertFalse(constant1.equal(constant2));

    }

    @Test
    public void givenTwoNegativeContanstCloseToZero_whenCte1IsEqualThanCt2_thenEquals() {
        Constant constant1 = new Constant(aFloat(-0.00001f));
        Constant constant2 = new Constant(aFloat(-0.00001f));
        assertThat(constant1.getValue(), equalTo(constant2.getValue()));
        assertTrue(constant1.equal(constant2));

    }

    @Test
    public void givenTwoContanst_whenEqualToZeroButDiffenteSign_thenEquals() {
        Constant constant1 = new Constant(aFloat(-0.00f));
        Constant constant2 = new Constant(aFloat(0.00f));
        assertThat(constant1.getValue(), lessThan(constant2.getValue()));
        assertTrue(constant1.equal(constant2));
    }

    @Test
    public void givenAConstantAndAVariable_whenCompared_AreNotEquals() {
        Constant constant1 = new Constant(aFloat(-29.987987f));
        Variable variable1 = new Variable(aFloat(-29.987987f), varName("X"));
        assertFalse(constant1.equal(variable1));
    }

    @Test
    public void givenAPositiveConstant_whenClonned_thenReturnAConstantEqualToIt() {
        Constant constant1 = new Constant(aFloat(10000.00001f));
        Constant constant2 = constant1.clon();
        assertThat(constant1.getValue(), equalTo(constant2.getValue()));
        assertTrue(constant1.equal(constant2));

    }

    @Test
    public void givenANegativeConstant_whenClonned_thenReturnAConstantEqualToIt() {
        Constant constant1 = new Constant(aFloat(-0.35f));
        Constant constant2 = constant1.clon();
        assertThat(constant1.getValue(), equalTo(constant2.getValue()));
        assertTrue(constant1.equal(constant2));
    }

    @Test
    public void givenAZeroConstant_whenClonned_thenReturnAConstantEqualToIt() {
        Constant constant1 = new Constant(aFloat(-0.00000f));
        Constant constant2 = constant1.clon();
        assertThat(constant1.getValue(), equalTo(constant2.getValue()));
        assertTrue(constant1.equal(constant2));
    }

}
