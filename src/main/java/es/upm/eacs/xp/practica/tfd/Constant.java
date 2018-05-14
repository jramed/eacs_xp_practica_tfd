package es.upm.eacs.xp.practica.tfd;

public class Constant implements Cloneable {

	private float value;
	
	public Constant(float value) {
		this.value = value;
	}
	
//	@Override
	public boolean equal(Constant obj) {
		boolean sameObjectClass = obj instanceof Constant;
		int sameValue = Float.compare(this.value, ((Constant) obj).getValue());
		return sameObjectClass && (sameValue == 0);
	}

//	@Override
	public Constant clon() {
		return new Constant(this.value);
	}
	
	public float getValue() {
		return this.value;
	}

}
