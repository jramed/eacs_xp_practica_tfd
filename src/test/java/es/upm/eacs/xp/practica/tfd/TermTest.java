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

public class TermTest {

    public static float aFloat(float number) {
        return number;
    }

    public static String varName(String name) {
        return name;
    }

    @Test
    public void givenTwoPositiveValues_whenValue1IsFewerThanValue2AndSameName_thenNotEquals() {
        Term term1 = new Term(aFloat(2.5f));
        Term term2 = new Term(aFloat(4f));
        assertThat(term1.getValue(), lessThan(term2.getValue()));
        assertFalse(term1.equal(term2));
    }

    @Test
    public void givenTwoPositiveValues_whenValue1IsGreaterThanValue2_thenNotEquals() {
        Term term1 = new Term(aFloat(700.5f));
        Term term2 = new Term(aFloat(0.35f));
        assertThat(term1.getValue(), greaterThan(term2.getValue()));
        assertFalse(term1.equal(term2));

    }

    @Test
    public void givenTwoPositiveValues_whenValue1IsEqualThanValue2_thenEquals() {
        Term term1 = new Term(aFloat(29.987987f));
        Term term2 = new Term(aFloat(29.987987f));
        assertThat(term1.getValue(), equalTo(term2.getValue()));
        assertTrue(term1.equal(term2));

    }

    @Test
    public void givenTwoNegativeValues_whenValue1IsFewerThanValue2_thenNotEquals() {
        Term term1 = new Term(aFloat(-9.5f));
        Term term2 = new Term(aFloat(-0.000001f));
        assertThat(term1.getValue(), lessThan(term2.getValue()));
        assertFalse(term1.equal(term2));

    }

    @Test
    public void givenTwoNegativeValues_whenValue1IsGreaterThanValue2_thenNotEquals() {
        Term term1 = new Term(aFloat(-0.0001f));
        Term term2 = new Term(aFloat(-0.0002f));
        assertThat(term1.getValue(), greaterThan(term2.getValue()));
        assertFalse(term1.equal(term2));

    }

    @Test
    public void givenTwoNegativeValues_whenValue1IsEqualThanValue2_thenEquals() {
        Term term1 = new Term(aFloat(-29.987987f));
        Term term2 = new Term(aFloat(-29.987987f));
        assertThat(term1.getValue(), equalTo(term2.getValue()));
        assertTrue(term1.equal(term2));

    }

    @Test
    public void givenTwoValuesCloseToZero_whenValue1IsGreaterThanValue2_thenNonEquals() {
        Term term1 = new Term(aFloat(0.0000001f));
        Term term2 = new Term(aFloat(-0.0000001f));
        assertThat(term1.getValue(), greaterThan(term2.getValue()));
        assertFalse(term1.equal(term2));

    }

    @Test
    public void givenTwoNegativeValuesCloseToZero_whenValue1IsEqualThanValue2_thenEquals() {
        Term term1 = new Term(aFloat(-0.0000001f));
        Term term2 = new Term(aFloat(-0.0000001f));
        assertThat(term1.getValue(), equalTo(term2.getValue()));
        assertTrue(term1.equal(term2));

    }

    @Test
    public void givenTwoValues_whenEqualToZeroButDiffenteSign_thenNonEquals() {
        Term term1 = new Term(aFloat(-0.00f));
        Term term2 = new Term(aFloat(0.00f));
        assertThat(term1.getValue(), lessThan(term2.getValue()));
        assertFalse(term1.equal(term2));
    }

    @Test(expected = AssertionError.class)
    public void givenAPositiveValue_whenClonned_thenReturnAValueEqualToIt() {
        Term term1 = new Term(aFloat(10000.00001f));
        term1.clon();
    }

    @Test(expected = AssertionError.class)
    public void givenANegativeValue_whenClonned_thenReturnAValueEqualToIt() {
        Term term1 = new Term(aFloat(-0.35f));
        term1.clon();
    }

    @Test(expected = AssertionError.class)
    public void givenAZeroValue_whenClonned_thenReturnAValueEqualToIt() {
        Term term1 = new Term(aFloat(-0.00000f));
        term1.clon();
    }

    @Test
    public void givenATerm_whenAskedForItsName_thenReturnFalse() {
        Term term1 = new Term(aFloat(0.0f));
        assertFalse(term1.hasName(varName("xyz")));
    }

    @Test
    public void givenATerm_whenAskedIfASetOfNamesContainItsName_thenReturnFalse() {
        Term term1 = new Term(aFloat(2.5f));
        Set<String> setNames = new HashSet<String>();
        setNames.add("variable");
        setNames.add("x");
        setNames.add("Y");
        setNames.add("Z");
        assertFalse(term1.hasName(setNames));
    }

    @Test
    public void givenABigPositiveValue_whenToString_thenReturnTheValueAsASingleString() {
        Term term1 = new Term(aFloat(100000.32f));
        assertThat(term1.toString(), equalTo("+100000.32"));
    }

    @Test
    public void givenASmallPositiveValue_whenToString_thenReturnTheValueAsASingleString() {
        Term term1 = new Term(aFloat(0.000000000077f));
        assertThat(term1.toString(), equalTo("+7.7E-11"));
    }

    @Test
    public void givenASmallNegativeValue_whenToString_thenReturnTheValueAsASingleString() {
        Term term1 = new Term(aFloat(-0.0000031f));
        assertThat(term1.toString(), equalTo("-3.1E-6"));
    }

    @Test
    public void givenABigNegativeValue_whenToString_thenReturnTheValueAsASingleString() {
        Term term1 = new Term(aFloat(-1000000.99f));
        assertThat(term1.toString(), equalTo("-1000001.0"));
    }

    @Test
    public void givenAPositiveValue_whenMultiplyByAPositiveValue_thenTheResultIsPositive() {
        Term term1 = new Term(aFloat(234.23f));
        term1.multiply(aFloat(345.09f));
        assertThat(term1.getValue(), equalTo(80830.43f));
    }

    @Test
    public void givenAPositiveValue_whenMultiplyByANegativeValue_thenTheResultIsNegative() {
        Term term1 = new Term(aFloat(234.23f));
        term1.multiply(aFloat(-345.09f));
        assertThat(term1.getValue(), equalTo(-80830.43f));
    }

    @Test
    public void givenANegativeValue_whenMultiplyByANegativeValue_thenTheResultIsPositive() {
        Term term1 = new Term(aFloat(-234.23f));
        term1.multiply(aFloat(-345.09f));
        assertThat(term1.getValue(), equalTo(80830.43f));
    }

    @Test
    public void givenAZeroNegativeValue_whenMultiplyByAZeroNegativeValue_thenTheResultIsPositive() {
        Term term1 = new Term(aFloat(-0.00f));
        term1.multiply(aFloat(-000.0f));
        assertThat(term1.getValue(), equalTo(0.0f));
    }
}
