package es.upm.eacs.xp.practica.tfd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Expression {
    public List<Term> termList;

    public Expression() {
        termList = new ArrayList<Term>();
    }

    public boolean empty() {
        return termList.isEmpty();
    }

    public void add(Term term) {
        termList.add(term);
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

    public Set<String> getNameSet() {
        assert !this.empty();

        Set<String> myNameSet = new NamesExpressionAnalyzer(this.termList).getNameSet();

        return myNameSet;
    }

}
