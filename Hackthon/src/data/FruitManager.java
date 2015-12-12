package data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.jumbo.tools.JumboErrorHandler;

public class FruitManager {
	public static ArrayList<Fruit> fruits = new ArrayList<>();

	public static void init() {
		final File directory = new File("foods");
		final File[] files = directory.listFiles();
		for (File f : files) {
			try {
				String color = "FFFFFF";
				int num = 0;
				List<String> lines = null;
				try {
					lines = Files.readAllLines(f.toPath(), Charset.forName("UTF-16"));
				} catch (Exception e) {
					lines = Files.readAllLines(f.toPath(), Charset.forName("Cp1252"));
				}
				for (String s : lines) {
					if (s.contains("color: ")) {
						color = s.substring(7);
					} else if (s.contains("num: ")) {
						num = Integer.parseInt(s.substring(5));
					}
				}

				final Fruit fruit = new Fruit(f.getName(), color);
				fruit.setNumber(num);
				fruits.add(fruit);
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
