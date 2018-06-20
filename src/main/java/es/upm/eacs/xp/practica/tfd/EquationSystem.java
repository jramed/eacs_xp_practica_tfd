package es.upm.eacs.xp.practica.tfd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquationSystem {

    List<Equation> equationList;
    private Set<String> nameSet;

    public EquationSystem() {
        this.equationList = new ArrayList<Equation>();
        this.nameSet = new HashSet<String>();
    }

    public void add(Equation equation) {
        this.equationList.add(equation.clon());
        nameSet.addAll(equation.getNameSet());
    }

    public boolean equal(EquationSystem equationSystem2) {
        if (this.equationList.size() != equationSystem2.equationList.size()) {
            return false;
        }

        boolean results[] = new boolean[this.equationList.size()];
        int pos = 0;
        for (Equation equationOut : equationList) {
            boolean isEqual = false;
            for (Equation equationIn : equationSystem2.equationList) {
                if (equationIn.equal(equationOut)) {
                    isEqual = true;
                    break;
                }
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

    public Set<String> getNameSet() {
        return this.nameSet;
    }

    public Equation get(int index) {
        return this.equationList.get(index);
    }

}
