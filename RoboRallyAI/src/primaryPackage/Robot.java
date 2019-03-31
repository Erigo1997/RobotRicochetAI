package primaryPackage;

public class Robot implements Cloneable {
	
	String colour;
	int x, y;
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Robot)) {
			return false;
		}
		Robot other = (Robot) o;
		if (colour.equals(other.colour) &&
				x == other.x &&
				y == other.y) {
			return true;
		} else {
			return false;
		}
			
	}

}
