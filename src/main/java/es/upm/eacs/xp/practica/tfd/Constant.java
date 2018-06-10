package es.upm.eacs.xp.practica.tfd;

public class Constant extends Term {

    public Constant(float value) {
        super(value);
    }

    @Override
    public boolean equal(Term obj) {
        boolean sameObjectClass = obj instanceof Constant;
        int sameValue = Float.compare(this.value, obj.getValue());

        return sameObjectClass && (sameValue == 0);
    }

    @Override
    public Constant clon() {
        return new Constant(this.value);
    }

}
