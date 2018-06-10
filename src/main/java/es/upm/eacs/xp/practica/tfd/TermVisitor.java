package es.upm.eacs.xp.practica.tfd;

public interface TermVisitor {
    public void visit(Variable variable);

    public void visit(Constant constant);
}
