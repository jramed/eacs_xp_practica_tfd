package es.upm.eacs.xp.practica.tfd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Equation {

    public Map<Side, Expression> members;

    public Equation() {
        this.members = new HashMap<Side, Expression>();
        members.put(Side.LEFT, new Expression());
        members.put(Side.RIGHT, new Expression());
    }

    public Equation(Expression[] expressions) {
        this();
        members.put(Side.LEFT, expressions[0]);
        members.put(Side.RIGHT, expressions[1]);
    }

    public void add(Term term) {
        for (Expression expression : this.members.values()) {
            expression.add(term);
        }
    }

    public void add(Side side, Term term) {
        this.members.get(side).add(term);
    }

    public void add(Equation equation) {
        this.members.get(Side.LEFT).add(equation.members.get(Side.LEFT));
        this.members.get(Side.RIGHT).add(equation.members.get(Side.RIGHT));
    }

    public boolean equal(Equation equation) {
        boolean identical1 = this.members.get(Side.LEFT).equal(equation.members.get(Side.LEFT))
                && this.members.get(Side.RIGHT).equal(equation.members.get(Side.RIGHT));
        boolean reverse1 = false;
        if (!identical1) {
            Expression expressionL = equation.members.get(Side.LEFT).clon();
            expressionL.multiply(-1f);
            Expression expressionR = equation.members.get(Side.RIGHT).clon();
            expressionR.multiply(-1f);
            reverse1 = this.members.get(Side.LEFT).equal(expressionR)
                    && this.members.get(Side.RIGHT).equal(expressionL);
        }

        return identical1 || reverse1;

    }

    public void multiply(float value) {
        this.members.get(Side.LEFT).multiply(value);
        this.members.get(Side.RIGHT).multiply(value);

    }

    public float getValue(String name) {
        float value = 0.0f;
        for (Expression expression : this.members.values()) {
            if (expression.hasName(name)) {
                return expression.getValue(name);
            }
        }
        return value;
    }

    public float getValue(Side side) {
        float value = 0.0f;
        value = this.members.get(side).getValue();
        return value;
    }

    public void simplify(Side side) {
        this.members.get(side).simplify();
    }

    public void simplify(Side side, String name) {
        this.members.get(side).simplify(name);
    }

    public Set<String> getNameSet() {
        Set<String> theNameSet = new HashSet<String>();

        for (Expression expression : this.members.values()) {
            theNameSet.addAll(expression.getNameSet());
        }

        return theNameSet;

    }

    public Equation clon() {
        Expression[] expressions1 = new Expression[] { this.members.get(Side.LEFT).clon(),
                this.members.get(Side.RIGHT).clon() };
        return new Equation(expressions1);
    }

    @Override
    public String toString() {
        String result1 = this.members.get(Side.LEFT).toString() + " = " + this.members.get(Side.RIGHT).toString();
        return result1;

    }

    public void apply(String name, float value) {
        for (Side side : Side.values()) {
            if (this.members.get(side).hasName(name)) {
                this.members.get(side).apply(name, value);
            }
        }
    }

    public void invert() {
        Expression copyLeft = this.members.get(Side.LEFT).clon();
        this.members.put(Side.LEFT, this.members.get(Side.RIGHT).clon());
        this.members.put(Side.RIGHT, copyLeft);
    }

}
