package es.upm.eacs.xp.practica.tfd;

import java.util.ArrayList;
import java.util.List;

public class EquationSystem {

    List<Equation> equationList;

    public EquationSystem() {
        this.equationList = new ArrayList<Equation>();
    }

    public void add(Equation equation) {
        this.equationList.add(equation.clon());
    }

    public boolean equal(EquationSystem equationSystem2) {
        if (this.equationList.size() != equationSystem2.equationList.size()) {
            return false;
        }

        for (Equation equation : equationList) {
            // if (equation)
        }
        return false;
    }

}
