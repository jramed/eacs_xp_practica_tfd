package es.upm.eacs.xp.practica.tfd;

import java.util.Iterator;
import java.util.Set;

public class EqualizationMethod extends SolutionMethod {

    // Awfully huge method just to follow the design provided
    // Comments left to indicate what do every part and facilitate a future
    // refactoring

    // Works partially if equation is x+y=cte
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
                "\n Equation System clon and simplified variable " + variableNames[0] + eqSysSimplified.toString());

        // Move variable to isolate from right to left side
        EquationSystem eqSysVariableinLeft = new EquationSystem();
        for (int pos = sizeOfEquationSystem; pos > 0; --pos) {
            Equation equationClon = eqSysSimplified.equationList.get(sizeOfEquationSystem - pos).clon();
            if (equationClon.members.get(Side.RIGHT).hasName(variableNames[0])) {
                Variable variable = new Variable(-equationClon.members.get(Side.RIGHT).getValue(variableNames[0]),
                        variableNames[0]);
                equationClon.members.get(Side.LEFT).add(variable);
                equationClon.members.get(Side.RIGHT).add(variable);
                equationClon.simplify(Side.LEFT, variableNames[0]);
                equationClon.simplify(Side.RIGHT, variableNames[0]);
            }
            eqSysVariableinLeft.add(equationClon);
        }
        System.out.println("Equation System clon and variable " + variableNames[0]
                + "\n moved to left side and simplified" + eqSysVariableinLeft.toString());

        float[] variableValues = new float[equationSystem.getNameSet().size()];

        // Obtain the values from each equation for the first variable name
        System.out.println("Variable " + variableNames[0]);
        for (int pos = sizeOfEquationSystem; pos > 0; --pos) {
            Equation equation = eqSysVariableinLeft.getLast(pos);
            System.out.println("For pos: " + pos + " Equation: " + equation.toString());
            variableValues[sizeOfEquationSystem - pos] = equation.getValue(variableNames[0]);
            System.out.println("equation: " + eqSysVariableinLeft.getLast(pos).toString() + " value: "
                    + variableValues[sizeOfEquationSystem - pos]);
        }

        // duplicate the equation system and reduce
        EquationSystem eqSysReduced = new EquationSystem();
        for (int pos = sizeOfEquationSystem; pos > 0; --pos) {
            Equation equationClon = eqSysVariableinLeft.equationList.get(sizeOfEquationSystem - pos).clon();
            if (variableValues[sizeOfEquationSystem - pos] != 0f) {
                equationClon.multiply(1 / variableValues[sizeOfEquationSystem - pos]);
            }
            eqSysReduced.add(equationClon);
            
        }
        System.out.println(
                "\n Equation System clon and reduced variable " + variableNames[0] + eqSysReduced.toString());

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
                    Variable variable = new Variable(-equationClon.members.get(Side.LEFT).getValue(varName), varName);
                    equationClon.add(Side.LEFT, variable);
                    equationClon.add(Side.RIGHT, variable);
                    equationClon.simplify(Side.LEFT, varName);
                    equationClon.simplify(Side.RIGHT, varName);
                }
            }
            System.out.println("Equation after move variable: " + equationClon.toString());
            
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

        
        // Move variable to isolate from right to left side
        EquationSystem eqSysVariableinLeft2 = new EquationSystem();
        int sizeOfEquationSystem2 = eqSysEqualized.equationList.size();
        for (int pos = sizeOfEquationSystem2; pos > 0; --pos) {
            Equation equationClon = eqSysEqualized.equationList.get(sizeOfEquationSystem2 - pos).clon();
            if (equationClon.members.get(Side.RIGHT).hasName(variableNames[1])) {
                Variable variable = new Variable(-equationClon.members.get(Side.RIGHT).getValue(variableNames[1]),
                        variableNames[1]);
                equationClon.members.get(Side.LEFT).add(variable);
                equationClon.members.get(Side.RIGHT).add(variable);
                equationClon.simplify(Side.LEFT, variableNames[1]);
                equationClon.simplify(Side.RIGHT, variableNames[1]);
            }
            eqSysVariableinLeft2.add(equationClon);
        }
        System.out.println("Equation System clon and variable " + variableNames[1]
                + "\n moved to left side and simplified" + eqSysVariableinLeft2.toString());
        
        // leave alone in left side the chosen variable for every equation
        // Fist of all duplicate the equation system
        EquationSystem eqSysIsolateVariable2 = new EquationSystem();
        for (int pos = sizeOfEquationSystem2; pos > 0; --pos) {
            Equation equationClon = eqSysVariableinLeft2.equationList.get(sizeOfEquationSystem2 - pos).clon();

            System.out.println("Equation before isolate var: " + variableNames[1] + " : " + equationClon.toString());
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
                if (varName != variableNames[1]) {
                    Variable variable = new Variable(-equationClon.members.get(Side.LEFT).getValue(varName), varName);
                    equationClon.add(Side.LEFT, variable);
                    equationClon.add(Side.RIGHT, variable);
                    equationClon.simplify(Side.LEFT, varName);
                    equationClon.simplify(Side.RIGHT, varName);
                }
            }
            System.out.println("Equation after move variable: " + equationClon.toString());
            
            eqSysIsolateVariable2.add(equationClon);
        }
        System.out.println("Equation System clon and isolate variable " + variableNames[1] + "\n"
                + eqSysIsolateVariable2.toString());
        
        // Work out the solution for the second variable
        Equation equationSolution2 = eqSysIsolateVariable2.getLast().clon();
        equationSolution2.multiply(1/equationSolution2.getValue(variableNames[1]));
        equationSystem.setSolution(variableNames[1], equationSolution2);
        
        System.out.println("Soluction for: " + variableNames[1] + equationSystem.getSolution(variableNames[1]));
        
        //Work out the solution for the first variable
        Equation equationSolution1 = eqSysIsolateVariable.getLast();
        equationSolution1.apply(variableNames[1], equationSystem.getSolution(variableNames[1]));
        equationSolution1.simplify(Side.RIGHT);
        equationSystem.setSolution(variableNames[0], equationSolution1);
        
        System.out.println("Soluction for: " + variableNames[0] + equationSystem.getSolution(variableNames[0]));
    }

}
