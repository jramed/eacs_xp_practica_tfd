package es.upm.eacs.xp.practica.tfd;

import java.util.Set;

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
        int sameValue = Float.compare(this.value, obj.getValue());
        return sameObjectClass && (sameValue == 0);
    }

    public Term clon() {
        return new Term(this.value);
    }

    public boolean hasName(String varName) {
        return false;
    }

    public boolean hasName(Set<String> setNames) {
        return false;
    }

}
