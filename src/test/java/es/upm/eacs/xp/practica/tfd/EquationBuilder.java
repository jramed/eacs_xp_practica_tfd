package es.upm.eacs.xp.practica.tfd;

public class EquationBuilder {

    private Equation equation;
    private Side side;

    public EquationBuilder() {
        this.equation = new Equation();
        this.side = Side.LEFT;
    }

    public EquationBuilder term(float value, String name) {
        this.equation.add(side, new Variable(value, name));
        return this;
    }

    public EquationBuilder term(float value) {
        this.equation.add(side, new Constant(value));
        return this;
    }

    public EquationBuilder assign() {
        this.side = Side.RIGHT;
        return this;
    }

    public Equation build() {
        assert Side.RIGHT == this.side;
        return equation;
    }
}
