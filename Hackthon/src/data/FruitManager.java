package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import com.jumbo.tools.JumboErrorHandler;

public class FruitManager {
	public static ArrayList<Fruit> fruits = new ArrayList<>();

	public static void init() {
		final File directory = new File("foods");
		final File[] files = directory.listFiles();
		for (File f : files) {
			try {
				for (String s : Files.readAllLines(f.toPath())) {
					String color = "FFFFFF";
					if (s.contains("color: ")) {
						color = s.substring(7);
					}
					fruits.add(new Fruit(f.getName(), color));
				}
			} catch (IOException e) {
				JumboErrorHandler.handle(e);
			}
		}
	}

	public static Fruit getFruit(String name) {
		for (Fruit f : fruits) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}
}
