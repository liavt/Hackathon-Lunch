package data;

public class Fruit {
	private final String name, color;

	public Fruit(String name, String c) {
		this.name = name;
		this.color = c;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
