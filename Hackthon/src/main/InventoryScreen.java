package main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

import com.jumbo.components.JumboColor;
import com.jumbo.components.entities.graphics.JumboButton;
import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.components.entities.graphics.JumboTextBox;
import com.jumbo.components.entities.graphics.ui.JumboUICreator;
import com.jumbo.components.entities.graphics.ui.JumboUIHandler;
import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboLaunchConfig;
import com.jumbo.rendering.JumboRenderer;
import com.jumbo.rendering.JumboScene;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.loaders.JumboImageHandler;

import data.Fruit;
import data.FruitManager;
import data.InventoryManager;

public class InventoryScreen {
	public static void main(String... args) {
		JumboSettings.logerrors = false;
		Jumbo.setMainaction(() -> {
			FruitManager.init();
			InventoryManager.init();

			JumboRenderer.setRefreshcolor(0.6392156863f, 0.3176470588f, 0f);

			JumboUIHandler.setCreator(new JumboUICreator() {
				@Override
				public JumboTexture create(int width, int height, int[] color, int compressionratio) {
					BufferedImage img = new BufferedImage(width / compressionratio, height / compressionratio,
							BufferedImage.TYPE_4BYTE_ABGR);
					WritableRaster data = img.getRaster();
					int datawidth = data.getWidth(), dataheight = data.getHeight();
					int[] pix = new int[4];
					pix[0] = 24;
					pix[1] = 190;
					pix[2] = 240;
					pix[3] = 255;
					for (int x = data.getMinX(); x < datawidth; x++) {
						for (int y = data.getMinY(); y < dataheight; y++) {
							data.setPixel(x, y, pix);
						}
					}
					pix[0] = color[0];
					pix[1] = color[1];
					pix[2] = color[2];
					pix[3] = color[3];
					for (int x = 1; x < datawidth - 1; x++) {
						for (int y = 1; y < dataheight - 1; y++) {
							data.setPixel(x, y, pix);
						}
					}
					img.setData(data);
					return new JumboTexture(img);
				}

			});
			JumboUIHandler.setPassivecolor(new int[] { 240, 132, 24, 255 });

			final JumboScene s = new JumboScene();

			final int size = FruitManager.fruits.size();
			for (int i = 0; i < size; i++) {
				final Fruit f = FruitManager.fruits.get(i);
				final JumboButton but = new JumboButton(new JumboTexture(JumboColor.fromHex(f.getColor())),
						new Rectangle((int) ((i / (float) size) * 560f) + 100, 400, 100, 100));
				but.setTexture(new JumboTexture(JumboColor.fromHex(f.getColor())));
				but.setDescriptor(new JumboText("<s16#000000>" + f.getName() + ":\n" + f.getAverage()));
				s.addEntity(but);
			}
			final JumboTextBox title = new JumboTextBox(new Rectangle(0, 520, 1080, 200),
					new JumboText("<s64#18BEF0>What you need to buy: "));
			s.addEntity(title);

			Jumbo.setScene(s);
		});
		Jumbo.start(new JumboLaunchConfig("Bastet", new Dimension(1080, 720),
				new BufferedImage[] { JumboImageHandler.getImage("res" + File.separator + "bastet.png") },
				"res" + File.separator + "assets" + File.separator + "fonts" + File.separator + "liavishis"));
	}

}
