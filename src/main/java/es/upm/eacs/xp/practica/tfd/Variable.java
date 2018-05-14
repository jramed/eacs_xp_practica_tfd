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
		boolean isEqual = name.equalsIgnoreCase(varName);
		return isEqual;	
	}

	public boolean hasName(Set<String> setNames) {
		boolean isEqual = false;
		for (String name: setNames ) {
			if (name.equalsIgnoreCase(this.name)) {
				isEqual = true;
				break;
			}		
		}
		return isEqual;
	}

}
