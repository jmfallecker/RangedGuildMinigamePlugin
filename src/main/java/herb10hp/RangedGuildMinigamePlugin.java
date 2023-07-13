package herb10hp;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
		name = "Ranged Guild Minigame Plugin",
		tags = {"ranged", "ranging", "range", "minigame",
				"guild", "ranging guild", "ranged guild",
				"score", "hiscore", "high score"}
)
public class RangedGuildMinigamePlugin extends Plugin {

	private final int RANGED_GUILD_MINIGAME_TURN = 156;
	private final int RANGED_GUILD_MINIGAME_SCORE = 157;

	@Getter(AccessLevel.PACKAGE)
	private int personalHighscore;

	@Getter(AccessLevel.PACKAGE)
	private int turn;

	@Getter(AccessLevel.PACKAGE)
	private int currentScore;

	@Inject
	private RangedGuildMinigameConfig config;

	@Inject
	private RuneLiteConfig runeLiteConfig;

	@Inject
	private Client client;

	@Inject
	private Notifier notifier;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private RangedGuildMinigameOverlay overlay;

	@Override
	public void startUp() throws Exception {
		personalHighscore = 0;
		overlayManager.add(overlay);
	}

	@Override
	public void shutDown() throws Exception {
		personalHighscore = 0;
		overlayManager.remove(overlay);
	}

	@Provides
	RangedGuildMinigameConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RangedGuildMinigameConfig.class);
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event) {

		var id = event.getVarpId();
		var value = event.getValue();

		if (id == RANGED_GUILD_MINIGAME_TURN) {
			turn = value > 10 ? 10 : value;

			if (turn == 0 && config.isNotifyOnCompletion()) {
				notifier.notify("Ranged minigame is complete.");
			}
		}
		if (id == RANGED_GUILD_MINIGAME_SCORE) {
			currentScore = value;

			if (value > personalHighscore)
				personalHighscore = value;
		}
	}
}
