package es.upm.eacs.xp.practica.tfd;

public class Term {
    private float value;

    public Term(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    public boolean equal(Term obj) {
        boolean sameObjectClass = obj instanceof Term;
        int sameValue = Float.compare(this.value, ((Term) obj).getValue());
        return sameObjectClass && (sameValue == 0);
    }

}
