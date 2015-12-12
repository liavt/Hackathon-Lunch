package main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jumbo.components.JumboColor;
import com.jumbo.components.LambdaObject;
import com.jumbo.components.entities.JumboGraphicsGroup;
import com.jumbo.components.entities.JumboTimer;
import com.jumbo.components.entities.graphics.JumboImage;
import com.jumbo.components.entities.graphics.JumboText;
import com.jumbo.components.entities.graphics.JumboTextBox;
import com.jumbo.rendering.Jumbo;
import com.jumbo.rendering.JumboLaunchConfig;
import com.jumbo.rendering.JumboRenderer;
import com.jumbo.rendering.JumboScene;
import com.jumbo.rendering.JumboTexture;
import com.jumbo.tools.JumboErrorHandler;
import com.jumbo.tools.JumboSettings;
import com.jumbo.tools.loaders.JumboImageHandler;

import data.Fruit;
import data.FruitManager;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class PublicScreen {
	private final static String twitteraccount = "PlanoAHS";

	public static void main(String... args) {
		JumboSettings.logerrors = false;
		Jumbo.setMainaction(() -> {
			FruitManager.init();
			// A35100
			JumboRenderer.setRefreshcolor(0.6392156863f, 0.3176470588f, 0f);
			JumboSettings.tickdelay = 100;

			final JumboScene s = new JumboScene();
			final StringBuilder b = new StringBuilder("<is64#6E6F71>Menu:<i>\n\n\n");
			for (Fruit f : FruitManager.fruits) {
				b.append(f.getName()).append("\n\n\n");
			}

			final JumboImage background = new JumboImage(new Rectangle(0, 0, 300, 720),
					new JumboTexture(JumboColor.fromHex("F08418")));
			background.setMaintainheight(false);
			s.addEntity(background);

			final JumboTextBox t = new JumboTextBox(new Rectangle(20, 0, 200, 720), new JumboText(b.toString()));
			t.setMaintainingPosition(true);
			t.setMaintainheight(false);
			s.addEntity(t);

			// 780 720
			final JumboImage logo = new JumboImage(new Rectangle(420, 110, 500, 500),
					new JumboTexture(JumboImageHandler.getImage("res" + File.separator + "logo.png")));
			// s.addEntity(logo);

			final JumboText time = new JumboText("");
			s.addEntity(time);

			final LambdaObject<Boolean> colon = new LambdaObject<>(false);
			final JumboTimer timer = new JumboTimer(1000, () -> {
				colon.set(!colon.get());
				if (colon.get()) {
					time.setText("<s32#6E6F71>" + new SimpleDateFormat("MMM d HH:mm:ss").format(new Date()));
				} else {
					time.setText("<s32#6E6F71>" + new SimpleDateFormat("MMM d HH mm ss").format(new Date()));
				}
			});
			s.addEntity(timer);

			final ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey("cc3LB0owP5FvfcqQICh3MGbvZ");
			cb.setOAuthConsumerSecret("wp1KoumfP3qWl2r5xsLm9MSNMZU3kVUyZmZ6KS19aTXjsEJ3dF");
			cb.setOAuthAccessToken("4461465195-RbZA9GhnhWZIYxMQ8FwlLfVq2U6UmN2tlAvWXgA");
			cb.setOAuthAccessTokenSecret("Lab4P7IkkJ6y2W7M0z4fECKN0MZqDmEP6cJlzsr2cCJN3");
			// cb.setOAuthRequestTokenURL("https://api.twitter.com/oauth/request_token");
			// cb.setOAuthAuthorizationURL("
			// https://api.twitter.com/oauth/authorize");
			// cb.setOAuthAccessTokenURL("https://api.twitter.com/oauth/access_token");
			// cb.setOAuthAuthenticationURL("https://api.twitter.com/oauth2/token");

			final Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			final Paging paging = new Paging(1, 300);
			final JumboGraphicsGroup tweets = new JumboGraphicsGroup(new Rectangle(300, 0, 780, 0)) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void tick() {
					super.tick();
					increasePosition(0, 1);
				}
			};
			try {
				final List<Status> statuses = twitter.getUserTimeline(twitteraccount, paging);
				boolean blue = false;
				for (int i = 0; i < statuses.size(); i++) {
					blue = !blue;
					Status stat = statuses.get(i);
					final StringBuilder text = new StringBuilder("<s21#AAAAAAi>[")
							.append(stat.getCreatedAt().toString()).append("] <#FFFFFFi>").append(stat.getText());
					final JumboImage tweetbackground = new JumboImage(new Rectangle(),
							blue ? new JumboTexture(JumboColor.fromHex("F08418").darker())
									: new JumboTexture(new JumboColor(0, 0, 0, 0)));
					tweetbackground.setMaintainingPosition(true);
					tweetbackground.setMaintainwidth(false);
					tweets.array.add(tweetbackground);
					final JumboTextBox tweet = new JumboTextBox(new Rectangle(0, 670 - (50 * i), 880, 50),
							new JumboText(text.toString().trim())) {
						/**
						* 
						*/
						private static final long serialVersionUID = 1L;

						@Override
						public Rectangle additionalCalculations(Rectangle bounds) {
							bounds = super.additionalCalculations(bounds);
							tweetbackground.setBounds(bounds);
							return bounds;
						}
					};
					tweet.setMaintainwidth(false);
					tweet.setMaintainy(true);
					tweets.array.add(tweet);
				}
			} catch (Exception e) {
				JumboErrorHandler.handle(e);
			}
			tweets.setMaintainingPosition(true);
			tweets.setMaintainwidth(false);
			s.addEntity(tweets);
			Jumbo.setScene(s);
		});
		final JumboLaunchConfig c = new JumboLaunchConfig("Bastet", new Dimension(1080, 720),
				new BufferedImage[] { JumboImageHandler.getImage("res" + File.separator + "bastet.png") },
				"res" + File.separator + "assets" + File.separator + "fonts" + File.separator + "arial");
		c.resizable = true;
		Jumbo.start(c);
	}
}
