package fr.umlv.projet.graphic;

/**
 * Class who represent a case 
 * in the game board.
 */
public class Case {
	private final int x;
	private final int y;
	
	/**
	 * Constructor of the class.
	 * 
	 * @param x The x coordinate of the case.
	 * @param y The y coordinate of the case.
	 */
	public Case(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x.
	 * 
	 * @return The value of x.
	 */
	public int x() {
		return x;
	}

	/**
	 * Getter for y.
	 * 
	 * @return The value of y.
	 */
	public int y() {
		return y;
	}
	
	
	/**
	 * Overriding of the hashCode method.
	 * 
	 * @return Like basic hashCode 
	 */
	@Override
	public int hashCode() {
		return x ^ y;
	}
	
	
	/**
	 * Overriding of the equals method.
	 * 
	 * @return true if equals and else false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Case))
			return false; 
		Case cas = (Case) obj;
		return x == cas.x && y == cas.y;
	}
}