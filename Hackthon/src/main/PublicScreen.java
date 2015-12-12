package main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.jumbo.components.entities.graphics.JumboImage;
import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.components.entities.graphics.JumboTextBox;
import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboLaunchConfig;
import com.jumbo.rendering.JumboRenderer;
import com.jumbo.rendering.JumboScene;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.loaders.JumboImageHandler;

import data.Fruit;
import data.FruitManager;

public class PublicScreen {
	public static void main(String... args) {
		JumboSettings.logerrors = false;
		Jumbo.setMainaction(() -> {
			FruitManager.init();

			JumboRenderer.setRefreshcolor(1f, 0.9f, 0.7f);

			final JumboScene s = new JumboScene();
			final StringBuilder b = new StringBuilder("<i>Menu:<i>\n");
			for (Fruit f : FruitManager.fruits) {
				b.append("<#").append(f.getColor()).append(">").append(f.getName()).append("\n");
			}

			final JumboTextBox t = new JumboTextBox(new Rectangle(0, 0, 200, 720), new JumboText(b.toString()));
			s.addEntity(t);

			final JumboImage logo = new JumboImage(new Rectangle(490, 220, 500, 500),
					new JumboTexture(JumboImageHandler.getImage("res" + File.separator + "logo.png")));
			s.addEntity(logo);

			final JumboText time = new JumboText("");
			s.addEntity(time);

			Jumbo.setScene(s);
		});
		Jumbo.start(new JumboLaunchConfig("Bastet", new Dimension(1080, 720), new BufferedImage[] {},
				"res" + File.separator + "assets" + File.separator + "fonts" + File.separator + "liavishis"));
	}
}
