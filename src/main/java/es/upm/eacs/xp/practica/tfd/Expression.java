package es.upm.eacs.xp.practica.tfd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Expression {
    public List<Term> termList;

    public Expression() {
        this.termList = new ArrayList<Term>();
    }

    public boolean empty() {
        return this.termList.isEmpty();
    }

    public void add(Term term) {
        this.termList.add(term.clon());
    }

    public void add(Expression expr1) {
        for (Term term : expr1.termList) {
            this.add(term);
        }
    }

    public float getValue() {
        assert !this.empty();

        Set<String> aNameSet = this.getNameSet();
        for (Term term : this.termList) {
            if (!term.hasName(aNameSet)) {
                return term.getValue();
            }
        }

        return 0.0f;
    }

    public float getValue(String name) {
        assert !this.empty();

        for (Term term : this.termList) {
            if (term.hasName(name)) {
                return term.getValue();
            }
        }

        return 0.0f;
    }

    public Set<String> getNameSet() {
        assert !this.empty();

        Set<String> myNameSet = new NamesExpressionAnalyzer(this.termList).getNameSet();

        return myNameSet;
    }

    public void multiply(float value) {
        assert !this.empty();

        for (Term term : termList) {
            term.multiply(value);
        }
    }

    public void simplify() {
        assert !this.empty();

        Set<String> myNameSet = this.getNameSet();
        Expression expression = new Expression();
        float value = 0.0f;

        for (Term term : termList) {
            if (!term.hasName(myNameSet)) {
                value = value + term.getValue();
            } else {
                expression.add(term.clon());
            }
        }

        if (0f != value || 0 == expression.termList.size()) {
            expression.add(new Constant(value));
        }
        this.termList = expression.termList;

    }

    public void simplify(String name) {
        assert !this.empty();

        Expression expression = new Expression();
        float value = 0.0f;

        for (Term term : this.termList) {
            if (term.hasName(name)) {
                value = value + term.getValue();
            } else {
                expression.add(term.clon());
            }
        }

        if (0f != value) {
            expression.add(new Variable(value, name));
        }
        this.termList = expression.termList;

    }

    public boolean equal(Expression expression) {

        if (this == expression) {
            System.out.println("expression equal");
            return true;
        }

        if (expression == null) {
            System.out.println("Expression null");
            return false;
        }

        // We could simplify both expression before do the comparison
        // but that should be done before calling to this method
        if (this.termList.size() != expression.termList.size()) {
            System.out.println("Size different: " + this.termList.size() + " versus " + expression.termList.size());
            return false;
        }

        boolean results[] = new boolean[this.termList.size()];
        int pos = 0;
        for (Term termThis : this.termList) {
            boolean isEqual = false;
            for (Term termObj : expression.termList)
                if (termThis.equal(termObj)) {
                    System.out.println(
                            "Term : " + termObj.getValue() + " is in the set of this.terms: " + termThis.getValue());
                    isEqual = true;
                    break;
                }
            results[pos] = isEqual;
            pos++;
        }

        boolean resultEqual = true;
        for (boolean result : results) {
            System.out.println("previous equality: " + resultEqual + " the result for this term is: " + result);
            resultEqual &= result;
        }
        return resultEqual;
    }

    public Expression clon() {
        Expression expression = new Expression();
        expression.add(this);
        return expression;
    }

    public boolean hasName(String name) {
        for (Term term : termList) {
            if (term.hasName(name))
                return true;
        }
        return false;
    }

    // precondition: the expression has been previously simplify for all the terms.
    public void apply(float value, String name) {
        Expression expression = new Expression();
        Constant cte = null;

        for (Term term : termList) {
            if (term.hasName(name)) {
                cte = new Constant(term.getValue() * value);
            } else {
                expression.add(term);
            }
        }
        if (cte != null) {
            expression.termList.add(cte);
        }
        if (expression.termList.size() > 0) {
            expression.simplify();
            this.termList = expression.termList;
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (Term term : termList) {
            output += term.toString();
        }

        return output;
    }

}
