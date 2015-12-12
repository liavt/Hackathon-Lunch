package main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.components.entities.graphics.JumboTextBox;
import com.jumbo.components.entities.graphics.ui.JumboUIButton;
import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboLaunchConfig;
import com.jumbo.rendering.JumboScene;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.console.JumboConsole;

public class Main {
	public static void main(String... args) {
		JumboConsole.log("Hello World");
		JumboSettings.logerrors = false;
		Jumbo.setMainaction(() -> {
			final JumboScene s = new JumboScene();
			final JumboTextBox t = new JumboTextBox(new Rectangle(0, 0, 1080, 720),
					new JumboText("<#FFFFFF>Hello this is the sentient <#FF0000>AI<#FFFFFF> nice to meet <is64>you"));
			s.addEntity(t);

			final JumboUIButton butt = new JumboUIButton(new Rectangle(100, 100, 500, 100),
					new JumboText("<#FF0000>Big Red Button"));
			butt.setClickAction(() -> {
				butt.setDescriptor(new JumboText("<#00FF00>Big Green Button"));
			});
			s.addEntity(butt);
			Jumbo.setScene(s);
		});
		Jumbo.start(new JumboLaunchConfig("Bastet", new Dimension(1080, 720), new BufferedImage[] {},
				"res" + File.separator + "assets" + File.separator + "fonts" + File.separator + "liavishis"));
	}
}
