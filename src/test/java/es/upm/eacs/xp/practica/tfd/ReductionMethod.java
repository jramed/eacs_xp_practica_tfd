package es.upm.eacs.xp.practica.tfd;

import java.util.Iterator;

public class ReductionMethod extends SolutionMethod {

    @Override
    public void resolve() {
        assert equationSystem.getNameSet().size() == equationSystem.equationList.size()
                && equationSystem.getNameSet().size() >= 2 && equationSystem.getNameSet().size() >= 1;

        Iterator<String> nameIterator = equationSystem.getNameSet().iterator();
        String firstName = nameIterator.next();
        String secondName = nameIterator.next();
        System.out.println("firstName= " + firstName + "\nSecondName= " + secondName);

        int sizeOfEquationSystem = equationSystem.equationList.size();
        float value1 = equationSystem.getLast(sizeOfEquationSystem).getValue(firstName);
        float value2 = equationSystem.getLast().getValue(firstName);
        System.out.print("FirstName value= " + value1 + "\nfirstName value= " + value2);

        // multiply each equation with the value of the variable to reduce
        // the sign of one of the value is changed
        // #3
        equationSystem.copyBefore(sizeOfEquationSystem);
        equationSystem.getLast().multiply(value2);
        // #4
        equationSystem.copyBefore(sizeOfEquationSystem);
        equationSystem.getLast().multiply(-value1);
        System.out.println("After multiply by value of variable " + equationSystem.toString());

        // Duplicate the last equation in the system
        // the number #4
        // #5
        equationSystem.copyBefore();
        System.out.println("After duplicate last equation " + equationSystem.toString());

        // Get the last equation #5 (#4) and add equation #3
        equationSystem.getLast().add(equationSystem.getLast(3));
        System.out.println(" " + equationSystem.toString());

        // Duplicate equation #5
        // #6
        equationSystem.copyBefore();
        equationSystem.getLast().simplify(Side.LEFT, firstName);
        // #7
        equationSystem.copyBefore();
        equationSystem.getLast().simplify(Side.LEFT, secondName);
        // #8
        equationSystem.copyBefore();
        equationSystem.getLast().simplify(Side.RIGHT);
        // #9
        equationSystem.copyBefore();
        equationSystem.getLast().multiply(1 / equationSystem.getLast(2).getValue(secondName));
        equationSystem.setSolution(secondName, equationSystem.getLast());

        equationSystem.copyBefore(9);
        equationSystem.getLast().apply(secondName, equationSystem.getLast(2).getValue(Side.RIGHT));
        equationSystem.copyBefore();
        equationSystem.getLast().add(new Constant(-equationSystem.getLast(2).getValue(Side.LEFT)));
        equationSystem.copyBefore();
        equationSystem.getLast().simplify(Side.LEFT);
        equationSystem.copyBefore();
        equationSystem.getLast().simplify(Side.RIGHT);
        equationSystem.copyBefore();
        equationSystem.getLast().multiply(1 / equationSystem.getLast(2).getValue(firstName));
        equationSystem.setSolution(firstName, equationSystem.getLast());
    }

}
