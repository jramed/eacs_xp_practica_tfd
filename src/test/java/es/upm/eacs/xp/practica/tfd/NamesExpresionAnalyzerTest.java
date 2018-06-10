package es.upm.eacs.xp.practica.tfd;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class NamesExpresionAnalyzerTest {

    public static float aFloat(float number) {
        return number;
    }

    public static String varName(String name) {
        return name;
    }

    @Test
    public void givenAListOfTerms_whenCreatedAnAnalyzer_thenTheVariableNamesSetIsGot() {
        List<Term> termList = new ArrayList<Term>();
        termList.add(new Constant(aFloat(4.56f)));
        termList.add(new Variable(aFloat(-5.3f), varName("X")));
        NamesExpressionAnalyzer namesExprAnalyzer = new NamesExpressionAnalyzer(termList);
        Set<String> myNameSet = new HashSet<String>();
        myNameSet.add(varName("X"));

        Set<String> nameSet = namesExprAnalyzer.getNameSet();
        assertThat(myNameSet, equalTo(nameSet));
    }

    @Test
    public void givenAListOfTermsAllVariables_whenCreatedAnAnalyzer_thenTheVariableNamesSetIsGot() {
        List<Term> termList = new ArrayList<Term>();
        termList.add(new Variable(aFloat(-5.3f), varName("X")));
        termList.add(new Variable(aFloat(4.56f), varName("Y")));
        termList.add(new Variable(aFloat(543.98f), varName("Z")));
        NamesExpressionAnalyzer namesExprAnalyzer = new NamesExpressionAnalyzer(termList);
        Set<String> myNameSet = new HashSet<String>();
        myNameSet.add(varName("X"));
        myNameSet.add(varName("Y"));
        myNameSet.add(varName("Z"));

        Set<String> nameSet = namesExprAnalyzer.getNameSet();
        assertThat(myNameSet, equalTo(nameSet));
    }

    @Test
    public void givenAListOfTermsAllConstants_whenAllAreVariables_thenTheVariableNamesSetIsEmpty() {
        List<Term> termList = new ArrayList<Term>();
        termList.add(new Constant(aFloat(4.56f)));
        termList.add(new Constant(aFloat(-5.3f)));
        termList.add(new Constant(aFloat(345f)));
        NamesExpressionAnalyzer namesExprAnalyzer = new NamesExpressionAnalyzer(termList);
        Set<String> myNameSet = new HashSet<String>();

        Set<String> nameSet = namesExprAnalyzer.getNameSet();
        assertThat(myNameSet, equalTo(nameSet));
    }

    @Test
    public void givenAListOfTermsWithRepeatedVariablesNames_whenCreatedAnAnalyzer_thenTheVariableNamesSetIsGot() {
        List<Term> termList = new ArrayList<Term>();
        termList.add(new Variable(aFloat(-5.3f), varName("X")));
        termList.add(new Variable(aFloat(4.56f), varName("X")));
        termList.add(new Variable(aFloat(543.98f), varName("X")));
        NamesExpressionAnalyzer namesExprAnalyzer = new NamesExpressionAnalyzer(termList);
        Set<String> myNameSet = new HashSet<String>();
        myNameSet.add(varName("X"));
        myNameSet.add(varName("X"));
        myNameSet.add(varName("X"));

        Set<String> nameSet = namesExprAnalyzer.getNameSet();
        assertThat(myNameSet, equalTo(nameSet));
    }
}
