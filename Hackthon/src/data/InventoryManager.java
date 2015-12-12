package data;

public class InventoryManager {
	public static void init() {
		for (Fruit f : FruitManager.fruits) {
			f.setAverage(f.getNumber());
		}
	}
}
