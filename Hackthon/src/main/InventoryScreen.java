package main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.jumbo.components.JumboColor;
import com.jumbo.components.entities.graphics.JumboButton;
import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.components.entities.graphics.JumboTextBox;
import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboLaunchConfig;
import com.jumbo.rendering.JumboRenderer;
import com.jumbo.rendering.JumboScene;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.JumboSettings;

import data.Fruit;
import data.FruitManager;
import data.InventoryManager;

public class InventoryScreen {
	public static void main(String... args) {
		JumboSettings.logerrors = false;
		Jumbo.setMainaction(() -> {
			FruitManager.init();
			InventoryManager.init();

			JumboRenderer.setRefreshcolor(1f, 0.9f, 0.7f);

			final JumboScene s = new JumboScene();

			final int size = FruitManager.fruits.size();
			for (int i = 0; i < size; i++) {
				final Fruit f = FruitManager.fruits.get(i);
				final JumboButton but = new JumboButton(new JumboTexture(JumboColor.fromHex(f.getColor())),
						new Rectangle((int) ((i / (float) size) * 560f) + 100, 400, 100, 100));
				but.setTexture(new JumboTexture(JumboColor.fromHex(f.getColor())));
				but.setDescriptor(new JumboText("<s24#000000>" + f.getName() + ": " + f.getAverage()));
				s.addEntity(but);
			}
			final JumboTextBox title = new JumboTextBox(new Rectangle(0, 520, 1080, 200),
					new JumboText("What you need to buy: "));
			s.addEntity(title);

			Jumbo.setScene(s);
		});
		Jumbo.start(new JumboLaunchConfig("Bastet", new Dimension(1080, 720), new BufferedImage[] {},
				"res" + File.separator + "assets" + File.separator + "fonts" + File.separator + "liavishis"));
	}

}
