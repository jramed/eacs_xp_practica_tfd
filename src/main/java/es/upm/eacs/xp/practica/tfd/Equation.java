package es.upm.eacs.xp.practica.tfd;

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

    public boolean empty() {

        return leftExpression.empty() && rightExpression.empty();
    }

    public void add(Term term) {
        this.leftExpression.add(term);
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
        return this.leftExpression.equal(equation.leftExpression)
                && this.rightExpression.equal(equation.rightExpression);
    }

}
