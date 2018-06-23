package es.upm.eacs.xp.practica.tfd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EquationSystem {

    List<Equation> equationList;
    private Set<String> nameSet;
    private Map<String, Equation> solutions;
    private SolutionMethod solutionMethod;

    public EquationSystem() {
        this.equationList = new ArrayList<Equation>();
        this.nameSet = new HashSet<String>();
        this.solutions = new HashMap<String, Equation>();
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
            resultEqual &= result;
        }
        return resultEqual;
    }

    public Set<String> getNameSet() {
        return this.nameSet;
    }

    private Equation get(int index) {
        return this.equationList.get(index);
    }

    Equation getLast(int before) {
        int index = this.equationList.size() - before;
        return this.equationList.get(index);
    }

    Equation getLast() {
        return this.getLast(1);
    }

    void copyBefore(int before) {
        int index = this.equationList.size() - before;
        this.add(this.get(index).clon());
    }

    void copyBefore() {
        this.copyBefore(1);
    }

    void setSolution(String firstName, Equation equation) {
        this.solutions.put(firstName, equation);
    }

    public float getSolution(String name) {
        Equation equation = this.solutions.get(name);
        return equation.getValue(Side.RIGHT);
    }

    @Override
    public String toString() {
        String outputString = "\n";
        for (Equation equation : this.equationList) {
            outputString += equation.toString() + "\n";
        }
        return outputString;
    }

    public void set(SolutionMethod solutionMethod) {
        this.solutionMethod = solutionMethod;
        this.solutionMethod.set(this);
    }

    public void resolve() {
        this.solutionMethod.resolve();
    }

}
