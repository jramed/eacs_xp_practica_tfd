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

        // Get the last equation #5 (#4) and add equation #3 (5-3 = 2 remember base 0)
        equationSystem.getLast().add(equationSystem.getLast(3));
        System.out.println("After sum equations 3 and 4" + equationSystem.toString());

        // Duplicate equation #5
        // #6
        equationSystem.copyBefore();
        // Simplify left side using First Variable name
        equationSystem.getLast().simplify(Side.LEFT, firstName);
        System.out.println("After simplify left side the variable " + firstName + equationSystem.toString());

        // Duplicate equation #6
        // #7
        equationSystem.copyBefore();
        // Simplify left side by using seconds variable name
        equationSystem.getLast().simplify(Side.LEFT, secondName);
        System.out.println("After simplify left side the variable " + secondName + equationSystem.toString());

        // Duplicate equation #7
        // #8
        equationSystem.copyBefore();
        // Simplify Constant on the right side
        equationSystem.getLast().simplify(Side.RIGHT);
        System.out.println("After simplify right side: " + equationSystem.toString());

        // Duplicate equation #8
        // #9
        equationSystem.copyBefore();
        // Worked out variable secondName
        equationSystem.getLast().multiply(1 / equationSystem.getLast(2).getValue(secondName));
        System.out.println("After worked out variable " + secondName + equationSystem.toString());

        equationSystem.setSolution(secondName, equationSystem.getLast());

        // Duplicate equation #1
        // #10
        equationSystem.copyBefore(9);
        // Get value of the secondName variable. 2 -> before the last, 1 -> last
        equationSystem.getLast().apply(secondName, equationSystem.getLast(2).getValue(Side.RIGHT));
        System.out.println("Apply value " + equationSystem.getLast(2).getValue(Side.RIGHT) + " of  variable "
                + secondName + equationSystem.toString());

        // Duplicate equation #10
        // #11
        equationSystem.copyBefore();
        // Obtain the constant from the left side of the before the last equation and
        // add the opposite to left side.
        equationSystem.getLast().add(new Constant(-equationSystem.getLast(2).getValue(Side.LEFT)));
        System.out.println("After  " + equationSystem.toString());

        // Duplicate equation #11
        // #12
        equationSystem.copyBefore();
        // Simplify left side to reduce the constant
        equationSystem.getLast().simplify(Side.LEFT);

        // Duplicate equation #12
        // #13
        equationSystem.copyBefore();
        // Simplify right side to reduce the constant
        equationSystem.getLast().simplify(Side.RIGHT);

        // Duplicate equation #13
        // #14
        equationSystem.copyBefore();
        // Work out the variable firstName
        equationSystem.getLast().multiply(1 / equationSystem.getLast(2).getValue(firstName));
        System.out.println("After worked out variable " + firstName + equationSystem.toString());

        equationSystem.setSolution(firstName, equationSystem.getLast());
    }

}
