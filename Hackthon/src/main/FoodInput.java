package main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.jumbo.components.JumboColor;
import com.jumbo.components.entities.graphics.JumboButton;
import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.components.entities.graphics.JumboTextBox;
import com.jumbo.components.entities.graphics.ui.JumboUIButton;
import com.jumbo.components.entities.graphics.ui.JumboUINumberWheel;
import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboLaunchConfig;
import com.jumbo.rendering.JumboRenderer;
import com.jumbo.rendering.JumboScene;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.loaders.JumboStringHandler;

import data.Fruit;
import data.FruitManager;

public class FoodInput {
	public static int id = 266271;

	public static void main(String... args) {
		JumboSettings.logerrors = false;
		Jumbo.setMainaction(() -> {
			FruitManager.init();

			JumboRenderer.setRefreshcolor(1f, 0.9f, 0.7f);

			final JumboScene s = new JumboScene();

			final int size = FruitManager.fruits.size();
			for (int i = 0; i < size; i++) {
				final Fruit f = FruitManager.fruits.get(i);
				final JumboButton but = new JumboButton(new JumboTexture(JumboColor.fromHex(f.getColor())),
						new Rectangle((int) ((i / (float) size) * 560f) + 100, 400, 100, 100));
				but.setTexture(new JumboTexture(JumboColor.fromHex(f.getColor())));
				but.setDescriptor(new JumboText("<s24#000000>" + f.getName()));

				s.addEntity(but);

				final JumboUINumberWheel input = new JumboUINumberWheel(new Rectangle(0, -70, 100, 50));
				input.setPrefix("<#FFFFFF>");
				input.addParent(but);
				input.setConfirm(() -> {
					FruitManager.getFruit(f.getName()).setNumber(f.getNumber() + (int) input.getNumber());
				});
				s.addEntity(input);
			}

			final JumboUIButton submit = new JumboUIButton(new Rectangle(440, 10, 200, 75),
					new JumboText("<#FFFFFF>Submit"));
			submit.setClickAction(() -> {
				for (Fruit f : FruitManager.fruits) {
					JumboStringHandler.writeString("foods" + File.separator + f.getName(),
							String.format("color: " + f.getColor() + "%nnum: " + f.getNumber()));
				}
			});
			s.addEntity(submit);

			final JumboTextBox title = new JumboTextBox(new Rectangle(0, 520, 1080, 200), new JumboText("ID: " + id));
			s.addEntity(title);

			Jumbo.setScene(s);
		});
		Jumbo.start(new JumboLaunchConfig("Bastet", new Dimension(1080, 720), new BufferedImage[] {},
				"res" + File.separator + "assets" + File.separator + "fonts" + File.separator + "liavishis"));
	}
}
