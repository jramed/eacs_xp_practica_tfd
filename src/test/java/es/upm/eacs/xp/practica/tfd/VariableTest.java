package es.upm.eacs.xp.practica.tfd;

import org.junit.Test;

public class VariableTest {

	public static float aFloat(float number) {
		return number;
	}
	
	@Test
	public void give_when_then() {
		Variable variable1 = new Variable(2.5f,"x");
	}
}