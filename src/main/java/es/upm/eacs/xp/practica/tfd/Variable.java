package es.upm.eacs.xp.practica.tfd;

import java.util.Set;

public class Variable {

    private float value;
    private String name;

    public Variable(float value, String name) {
        this.value = value;
        this.name = name;
    }

    public boolean hasName(String varName) {
        boolean isEqual = this.name.equalsIgnoreCase(varName);
        return isEqual;
    }

    public boolean hasName(Set<String> setNames) {
        boolean isEqual = false;
        for (String name : setNames) {
            if (name.equalsIgnoreCase(this.name)) {
                isEqual = true;
                break;
            }
        }
        return isEqual;
    }

    public float getValue() {
        return this.value;
    }

    // @Override
    public boolean equal(Variable obj) {
        boolean sameObjectClass = obj instanceof Variable;
        int sameValue = Float.compare(this.value, obj.getValue());
        boolean sameName = this.hasName(obj.getName());

        return sameObjectClass && (sameValue == 0) && sameName;
    }

    private String getName() {
        return this.name;
    }

    public Variable clon() {
        return new Variable(this.value, this.name);
    }

}
