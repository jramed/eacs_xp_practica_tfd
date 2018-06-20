package es.upm.eacs.xp.practica.tfd;

import java.util.HashSet;
import java.util.Set;

public class Equation {

    public Expression leftExpression;
    public Expression rightExpression;

    public Equation() {
        this.leftExpression = new Expression();
        this.rightExpression = new Expression();
    }

    public Equation(Expression[] expressions) {
        this();

        this.leftExpression.add(expressions[0]);
        this.rightExpression.add(expressions[1]);
    }

    public void add(Term term) {
        this.leftExpression.add(term);
        this.rightExpression.add(term);
    }

    public void add(Side side, Term term) {
        if (Side.LEFT == side) {
            this.leftExpression.add(term);
        } else {
            this.rightExpression.add(term);
        }
    }

    public void add(Equation equation) {
        this.leftExpression.add(equation.leftExpression.clon());
        this.rightExpression.add(equation.rightExpression.clon());
    }

    public boolean equal(Equation equation) {
        boolean identical = this.leftExpression.equal(equation.leftExpression)
                && this.rightExpression.equal(equation.rightExpression);
        boolean reverse = false;
        if (!identical) {
            Expression expressionL = equation.leftExpression.clon();
            expressionL.multiply(-1f);
            Expression expressionR = equation.rightExpression.clon();
            expressionR.multiply(-1f);
            reverse = this.leftExpression.equal(expressionR) && this.rightExpression.equal(expressionL);
        }
        return identical || reverse;
    }

    public void multiply(float value) {
        this.leftExpression.multiply(value);
        this.rightExpression.multiply(value);

    }

    public float getValue(String name) {
        if (this.leftExpression.hasName(name)) {
            return this.leftExpression.getValue(name);
        } else if (this.rightExpression.hasName(name)) {
            return this.rightExpression.getValue(name);
        }
        return 0.0f;
    }

    public float getValue(Side side) {
        if (Side.LEFT == side) {
            return this.leftExpression.getValue();
        } else if (Side.RIGHT == side) {
            return this.rightExpression.getValue();
        }
        return 0.0f;
    }

    public void simplify(Side side) {
        if (Side.LEFT == side) {
            this.leftExpression.simplify();
        } else if (Side.RIGHT == side) {
            this.rightExpression.simplify();
        }
    }

    public void simplify(Side side, String name) {
        if (Side.LEFT == side) {
            this.leftExpression.simplify(name);
        } else if (Side.RIGHT == side) {
            this.rightExpression.simplify(name);
        }
    }

    public Set<String> getNameSet() {
        Set<String> theNameSet = new HashSet<String>();
        for (String name : this.leftExpression.getNameSet()) {
            theNameSet.add(name);
        }
        for (String name : this.rightExpression.getNameSet()) {
            theNameSet.add(name);
        }

        return theNameSet;
    }

    public Equation clon() {
        Expression[] expressions = new Expression[] { this.leftExpression.clon(), this.rightExpression.clon() };
        return new Equation(expressions);
    }

    @Override
    public String toString() {
        return this.leftExpression.toString() + " = " + this.rightExpression.toString();
    }

    public void apply(String name, float value) {
        this.leftExpression.apply(value, name);
        this.rightExpression.apply(value, name);
    }

    public void invert() {
        Expression copy = this.leftExpression.clon();
        this.leftExpression = this.rightExpression.clon();
        this.rightExpression = copy;
    }

}
