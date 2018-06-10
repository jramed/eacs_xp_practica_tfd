package es.upm.eacs.xp.practica.tfd;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NamesExpressionAnalyzer implements TermVisitor {

    private List<Term> termList;
    private Set<String> nameSet;

    public NamesExpressionAnalyzer(List<Term> termList) {
        this.termList = termList;
        this.nameSet = new HashSet<String>();
        for (Term term : termList) {
            term.dispatch(this);
        }

    }

    public Set<String> getNameSet() {
        return nameSet;
    }

    public void visit(Variable variable) {
        nameSet.add(variable.getName());
    }

    public void visit(Constant constant) {
        // Do nothing, Constant has not name
    }

}
