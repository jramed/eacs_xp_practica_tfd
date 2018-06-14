package es.upm.eacs.xp.practica.tfd;

public class ExpressionBuilder {
    private Expression expression;

    public ExpressionBuilder() {
        this.expression = new Expression();
    }

    public ExpressionBuilder term(float value) {
        this.expression.add(new Constant(value));
        return this;
    }

    public ExpressionBuilder term(float value, String name) {
        this.expression.add(new Variable(value, name));
        return this;
    }

    public ExpressionBuilder term(Constant cte) {
        this.expression.add(cte);
        return this;
    }

    public ExpressionBuilder term(Variable var) {
        this.expression.add(var);
        return this;
    }

    public Expression build() {
        return expression;
    }
}
