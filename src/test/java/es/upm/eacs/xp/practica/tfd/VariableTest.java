package es.upm.eacs.xp.practica.tfd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class VariableTest {

    public static float aFloat(float number) {
        return number;
    }

    public static String varName(String name) {
        return name;
    }

    @Test
    public void givenAVariable_whenAskedForItsName_thenReturnTrue() {
        Variable variable1 = new Variable(aFloat(0.0f), varName("xyz"));
        assertTrue(variable1.hasName(varName("xyz")));
    }

    @Test
    public void givenAVariable_whenAskedForItsNameInDifferentCase_thenReturnTrue() {
        Variable variable1 = new Variable(aFloat(2.5f), varName("xyz"));
        assertTrue(variable1.hasName(varName("XyZ")));
    }

    @Test
    public void givenAVariable_whenAskedForAnotherName_thenReturnFalse() {
        Variable variable1 = new Variable(aFloat(-72.89f), varName("yxz"));
        assertFalse(variable1.hasName(varName("xyz")));
    }

    @Test
    public void givenAVariable_whenAskedForSetOfNamesThanContainItsName_thenReturnTrue() {
        Variable variable1 = new Variable(aFloat(2.5f), varName("variable"));
        Set<String> setNames = new HashSet<String>();
        setNames.add("variable");
        setNames.add("x");
        setNames.add("Y");
        setNames.add("Z");
        assertTrue(variable1.hasName(setNames));
    }

    @Test
    public void givenAVariable_whenAskedForSetOfNamesThanContainItsNameInDifferentCase_thenReturnTrue() {
        Variable variable1 = new Variable(aFloat(2.5f), varName("VARiable"));
        Set<String> setNames = new HashSet<String>();
        setNames.add("variable");
        setNames.add("x");
        setNames.add("Y");
        setNames.add("Z");
        assertTrue(variable1.hasName(setNames));
    }

    @Test
    public void givenAVariable_whenAskedForSetOfNameThanNonContainItsName_thenReturnFalse() {
        Variable variable1 = new Variable(aFloat(2.5f), varName("pepe"));
        Set<String> setNames = new HashSet<String>();
        setNames.add("variable");
        setNames.add("x");
        setNames.add("Y");
        setNames.add("Z");
        assertFalse(variable1.hasName(setNames));
    }

    @Test
    public void givenTwoPositiveValues_whenValue1IsFewerThanValue2AndSameName_thenNotEquals() {
        Variable variable1 = new Variable(aFloat(2.5f), varName("x"));
        Variable variable2 = new Variable(aFloat(4f), varName("x"));
        assertThat(variable1.getValue(), lessThan(variable2.getValue()));
        assertFalse(variable1.equal(variable2));

    }

    @Test
    public void givenTwoPositiveValues_whenValue1IsGreaterThanValue2AndSameName_thenNotEquals() {
        Variable variable1 = new Variable(aFloat(700.5f), varName("x"));
        Variable variable2 = new Variable(aFloat(0.35f), varName("x"));
        assertThat(variable1.getValue(), greaterThan(variable2.getValue()));
        assertFalse(variable1.equal(variable2));

    }

    @Test
    public void givenTwoPositiveValues_whenValue1IsEqualThanValue2AndSameName_thenEquals() {
        Variable variable1 = new Variable(aFloat(29.987987f), varName("x"));
        Variable variable2 = new Variable(aFloat(29.987987f), varName("x"));
        assertThat(variable1.getValue(), equalTo(variable2.getValue()));
        assertTrue(variable1.equal(variable2));

    }

    @Test
    public void givenTwoPositiveValues_whenValue1IsEqualThanValue2AndDifferentName_thenNonEquals() {
        Variable variable1 = new Variable(aFloat(29.987987f), varName("x"));
        Variable variable2 = new Variable(aFloat(29.987987f), varName("Y"));
        assertThat(variable1.getValue(), equalTo(variable2.getValue()));
        assertFalse(variable1.equal(variable2));

    }

    @Test
    public void givenTwoNegativeValues_whenValue1IsFewerThanValue2AndSameName_thenNotEquals() {
        Variable variable1 = new Variable(aFloat(-9.5f), varName("x"));
        Variable variable2 = new Variable(aFloat(-0.000001f), varName("x"));
        assertThat(variable1.getValue(), lessThan(variable2.getValue()));
        assertFalse(variable1.equal(variable2));

    }

    @Test
    public void givenTwoNegativeValues_whenValue1IsGreaterThanValue2AndSameName_thenNotEquals() {
        Variable variable1 = new Variable(aFloat(-0.0001f), varName("x"));
        Variable variable2 = new Variable(aFloat(-0.0002f), varName("x"));
        assertThat(variable1.getValue(), greaterThan(variable2.getValue()));
        assertFalse(variable1.equal(variable2));

    }

    @Test
    public void givenTwoNegativeValues_whenValue1IsEqualThanValue2AndSameName_thenEquals() {
        Variable variable1 = new Variable(aFloat(-29.987987f), varName("x"));
        Variable variable2 = new Variable(aFloat(-29.987987f), varName("x"));
        assertThat(variable1.getValue(), equalTo(variable2.getValue()));
        assertTrue(variable1.equal(variable2));

    }

    @Test
    public void givenTwoValuesCloseToZero_whenValue1IsGreaterThanValue2AndSameName_thenNonEquals() {
        Variable variable1 = new Variable(aFloat(0.0000001f), varName("x"));
        Variable variable2 = new Variable(aFloat(-0.0000001f), varName("x"));
        assertThat(variable1.getValue(), greaterThan(variable2.getValue()));
        assertFalse(variable1.equal(variable2));

    }

    @Test
    public void givenTwoNegativeValuesCloseToZero_whenValue1IsEqualThanValue2AndDifferentName_thenNonEquals() {
        Variable variable1 = new Variable(aFloat(-0.0000001f), varName("x"));
        Variable variable2 = new Variable(aFloat(-0.0000001f), varName("Z"));
        assertThat(variable1.getValue(), equalTo(variable2.getValue()));
        assertFalse(variable1.equal(variable2));

    }

    @Test
    public void givenTwoValuesEqualToZeroButDifferentSign_whenValue1IsEqualThanValue2_thenNonEquals() {
        Variable variable1 = new Variable(aFloat(-0.00f), varName("x"));
        Variable variable2 = new Variable(aFloat(0.00f), varName("x"));
        assertThat(variable1.getValue(), lessThan(variable2.getValue()));
        assertFalse(variable1.equal(variable2));
    }

    @Test
    public void givenTwoValues_whenEqualToZeroAndDifferentName_thenNonEquals() {
        Variable variable1 = new Variable(aFloat(0.00f), varName("AnotherVariable"));
        Variable variable2 = new Variable(aFloat(0.00f), varName("variable1"));
        assertThat(variable1.getValue(), equalTo(variable2.getValue()));
        assertFalse(variable1.equal(variable2));
    }
}