package es.upm.eacs.xp.practica.tfd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class VariableTest {

	public static float aFloat(float number) {
		return number;
	}
	
	public static String varName(String name) {
		return name;
	}
	
	@Test
	public void givenAVariable_whenAskedForItsName_thenReturnTrue() {
		Variable variable1 = new Variable(aFloat(0.0f),varName("x"));
		assertTrue(variable1.hasName(varName("x")));
	}
	
	@Test
	public void givenAVariable_whenAskedForItsNameInDifferentCase_thenReturnTrue() {
		Variable variable1 = new Variable(aFloat(2.5f),varName("x"));
		assertTrue(variable1.hasName(varName("X")));
	}
	
	@Test
	public void givenAVariable_whenAskedForAnotherName_thenReturnFalse() {
		Variable variable1 = new Variable(aFloat(-72.89f),varName("y"));
		assertFalse(variable1.hasName(varName("x")));
	}
	
	@Test
	public void givenAVariable_whenAskedForSetOfNamesThanContainItsName_thenReturnTrue() {
		Variable variable1 = new Variable(aFloat(2.5f),varName("variable"));
		Set<String> setNames = new HashSet<String>();
		setNames.add("variable");
		setNames.add("x");
		setNames.add("Y");
		setNames.add("Z");
		assertTrue(variable1.hasName(setNames));
	}
	
	@Test
	public void givenAVariable_whenAskedForSetOfNamesThanContainItsNameInDifferentCase_thenReturnTrue() {
		Variable variable1 = new Variable(aFloat(2.5f),varName("VARiable"));
		Set<String> setNames = new HashSet<String>();
		setNames.add("variable");
		setNames.add("x");
		setNames.add("Y");
		setNames.add("Z");
		assertTrue(variable1.hasName(setNames));
	}
	
	@Test
	public void givenAVariable_whenAskedForSetOfNameThanNonContainItsName_thenReturnFalse() {
		Variable variable1 = new Variable(aFloat(2.5f),varName("pepe"));
		Set<String> setNames = new HashSet<String>();
		setNames.add("variable");
		setNames.add("x");
		setNames.add("Y");
		setNames.add("Z");
		assertFalse(variable1.hasName(setNames));
	}
}