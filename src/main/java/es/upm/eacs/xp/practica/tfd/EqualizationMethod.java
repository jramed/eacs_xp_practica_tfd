package es.upm.eacs.xp.practica.tfd;

import java.util.Iterator;
import java.util.Set;

public class EqualizationMethod extends SolutionMethod {

    // Awfully huge method just to follow the design provided
    // Comments left to indicate what do every part and facilitate a future
    // refactoring
    @Override
    public void resolve() {
        Set<String> nameSet = equationSystem.getNameSet();
        assert nameSet.size() == equationSystem.equationList.size() && equationSystem.equationList.size() > 0;

        String[] variableNames = new String[equationSystem.getNameSet().size()];
        Iterator<String> nameIterator = nameSet.iterator();

        // Obtain all the variable names
        System.out.println("Varible names: ");
        for (int pos = 0; nameIterator.hasNext(); ++pos) {
            variableNames[pos] = nameIterator.next();
            System.out.println("position: " + pos + " value: " + variableNames[pos]);
        }

        int sizeOfEquationSystem = equationSystem.equationList.size();

        // duplicate the equation system and simplify
        EquationSystem eqSysSimplified = new EquationSystem();
        for (int pos = sizeOfEquationSystem; pos > 0; --pos) {
            Equation equationClon = equationSystem.equationList.get(sizeOfEquationSystem - pos).clon();
            equationClon.simplify(Side.LEFT);
            equationClon.simplify(Side.RIGHT);
            for (String varName : variableNames) {
                System.out.println("Variable to be simplified: " + varName);
                equationClon.simplify(Side.LEFT, varName);
                equationClon.simplify(Side.RIGHT, varName);
                System.out.println(equationClon.toString());
            }
            eqSysSimplified.add(equationClon);
        }
        System.out.println(
                "Equation System clon and simplified variable " + variableNames[0] + "\n" + eqSysSimplified.toString());

        float[] variableValues = new float[equationSystem.getNameSet().size()];

        // Obtain the values from each equation for the first variable name
        System.out.println("Variable " + variableNames[0]);
        for (int pos = sizeOfEquationSystem; pos > 0; --pos) {
            Equation equation = eqSysSimplified.getLast(pos);
            System.out.println("For pos: " + pos + " Equation: " + equation.toString());
            variableValues[sizeOfEquationSystem - pos] = equation.getValue(variableNames[0]);
            System.out.println("equation: " + eqSysSimplified.getLast(pos).toString() + " value: "
                    + variableValues[sizeOfEquationSystem - pos]);
        }

        // duplicate the equation system and reduce
        EquationSystem eqSysReduced = new EquationSystem();
        for (int pos = sizeOfEquationSystem; pos > 0; --pos) {
            Equation equationClon = eqSysSimplified.equationList.get(sizeOfEquationSystem - pos).clon();
            equationClon.multiply(1 / variableValues[sizeOfEquationSystem - pos]);
            eqSysReduced.add(equationClon);
        }
        System.out.println(
                "Equation System clon and simplified variable " + variableNames[0] + "\n" + eqSysReduced.toString());

        // leave alone in left side the chosen variable for every equation
        // Fist of all duplicate the equation system
        EquationSystem eqSysIsolateVariable = new EquationSystem();
        for (int pos = sizeOfEquationSystem; pos > 0; --pos) {
            Equation equationClon = eqSysReduced.equationList.get(sizeOfEquationSystem - pos).clon();

            System.out.println("Equation before isolate var: " + variableNames[0] + " : " + equationClon.toString());
            // Move Constant from left to right
            float value = equationClon.getValue(Side.LEFT);
            if (value != 0f) {
                Constant cte = new Constant(-value);
                equationClon.add(Side.LEFT, cte);
                equationClon.add(Side.RIGHT, cte);
                equationClon.simplify(Side.LEFT);
                equationClon.simplify(Side.RIGHT);
            }
            System.out.println("Equation after move cte: " + equationClon.toString());

            // Move other variables from left to right
            Iterator<String> nameSetIterator = equationClon.getNameSet().iterator();
            while (nameSetIterator.hasNext()) {
                String varName = nameSetIterator.next();
                if (varName != variableNames[0]) {
                    Variable variable = new Variable(-equationClon.getValue(varName), varName);
                    equationClon.add(Side.LEFT, variable);
                    equationClon.add(Side.RIGHT, variable);
                    equationClon.simplify(Side.LEFT, varName);
                    equationClon.simplify(Side.RIGHT, varName);
                }
            }

            eqSysIsolateVariable.add(equationClon);
        }
        System.out.println("Equation System clon and isolate variable " + variableNames[0] + "\n"
                + eqSysIsolateVariable.toString());

        // Create new equation by equalization
        EquationSystem eqSysEqualized = new EquationSystem();

        Expression rightSideExpr = new Expression();
        if (1 == sizeOfEquationSystem) {
            eqSysEqualized.add(eqSysIsolateVariable.getLast(sizeOfEquationSystem).clon());
        } else {
            rightSideExpr = eqSysIsolateVariable.getLast(sizeOfEquationSystem).members.get(Side.RIGHT);
        }

        for (int pos = sizeOfEquationSystem - 1; pos > 0; --pos) {
            Equation equation = eqSysIsolateVariable.equationList.get(sizeOfEquationSystem - pos);
            eqSysEqualized.add(
                    new Equation(new Expression[] { rightSideExpr.clon(), equation.members.get(Side.RIGHT).clon() }));

        }
        System.out.println("Equation System clon and equalized " + variableNames[0] + "\n" + eqSysEqualized.toString());

        // duplicate the equation system and reduce
        EquationSystem eqSysReduced2 = new EquationSystem();
        {
            Equation equationClon = eqSysEqualized.equationList.get(0).clon();
            equationClon.multiply(1 / variableValues[1]);
            eqSysReduced2.add(equationClon);
        }
        System.out.println(
                "Equation System clon and simplified variable " + variableNames[1] + "\n" + eqSysReduced2.toString());

    }

}
