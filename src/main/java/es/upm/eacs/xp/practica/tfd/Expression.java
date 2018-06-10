package es.upm.eacs.xp.practica.tfd;

import java.util.ArrayList;
import java.util.List;

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

}
