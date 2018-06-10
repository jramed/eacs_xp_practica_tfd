package es.upm.eacs.xp.practica.tfd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TermTest {

    public static float aFloat(float number) {
        return number;
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

    @Test
    public void givenAPositiveValue_whenClonned_thenReturnAValueEqualToIt() {
        Term term1 = new Term(aFloat(10000.00001f));
        Term term2 = term1.clon();
        assertThat(term1.getValue(), equalTo(term2.getValue()));
        assertTrue(term1.equal(term2));

    }

    @Test
    public void givenANegativeValue_whenClonned_thenReturnAValueEqualToIt() {
        Term term1 = new Term(aFloat(-0.35f));
        Term term2 = term1.clon();
        assertThat(term1.getValue(), equalTo(term2.getValue()));
        assertTrue(term1.equal(term2));
    }

    @Test
    public void givenAZeroValue_whenClonned_thenReturnAValueEqualToIt() {
        Term term1 = new Term(aFloat(-0.00000f));
        Term term2 = term1.clon();
        assertThat(term1.getValue(), equalTo(term2.getValue()));
        assertTrue(term1.equal(term2));
    }
}
