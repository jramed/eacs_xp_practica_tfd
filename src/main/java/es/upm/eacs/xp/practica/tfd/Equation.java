package es.upm.eacs.xp.practica.tfd;

public class Equation {

    public Expression leftExpression;
    public Expression rightExpression;

    public Equation() {
        this.leftExpression = new Expression();
        this.rightExpression = new Expression();
    }

    public Equation(Expression expression) {
        this.leftExpression = expression;
    }

    public boolean empty() {

        return leftExpression.empty() && rightExpression.empty();
    }

    public void add(Term term) {
        this.leftExpression.add(term);
    }

}
