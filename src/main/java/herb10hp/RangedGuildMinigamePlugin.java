package herb10hp;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.*;
@PluginDescriptor(
		name = "Ranged Guild Minigame Plugin",
		tags = {"ranged", "ranging", "range", "minigame",
				"guild", "ranging guild", "ranged guild",
				"score", "hiscore", "high score"}
)
public class RangedGuildMinigamePlugin extends Plugin {

	@Getter(AccessLevel.PACKAGE)
	private int personalHighscore;

	@Getter(AccessLevel.PACKAGE)
	private int turn;

	@Getter(AccessLevel.PACKAGE)
	private int currentScore;

	public boolean getCanPlayerShootOnCurrentTile() {
		final int REGION_ID = 10549;

		var playerPosition = client.getLocalPlayer().getWorldLocation();

		if (playerPosition.getRegionID() != REGION_ID) return false;

		var validTiles = new int[][] {
			{45, 25}, {45, 26}, {45, 28}, {46, 24}, {46, 25}, {46, 26}, {46, 27}, {46, 28},
			{46, 29}, {47, 25}, {47, 26}, {47, 27}, {47, 28}, {48, 25}, {48, 26}, {48, 27},
			{48, 28}, {48, 29}, {49, 24}, {49, 25}, {49, 26}, {49, 27}, {49, 28}, {50, 25},
			{50, 26}, {51, 26}
		};

		var playerPositionX = playerPosition.getRegionX();
		var playerPositionY = playerPosition.getRegionY();

		return Arrays.stream(validTiles).anyMatch(tile -> tile[0] == playerPositionX && tile[1] == playerPositionY);
	}

	@Inject
	private RangedGuildMinigameConfig config;

	@Inject
	private Client client;

	@Inject
	private Notifier notifier;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private RangedGuildMinigameOverlay overlay;

	@Override
	public void startUp() {
		overlayManager.add(overlay);
	}

	@Override
	public void shutDown() {
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

		final int RANGED_GUILD_MINIGAME_TURN = 156;
		final int RANGED_GUILD_MINIGAME_SCORE = 157;

		if (id == RANGED_GUILD_MINIGAME_TURN) {
			turn = Math.min(value, 10);

			if (turn == 0 && config.isNotifyOnCompletion()) {
				notifier.notify("Ranged Guild Minigame is complete.");
			}
		}
		else if (id == RANGED_GUILD_MINIGAME_SCORE) {
			currentScore = value;

			if (value > personalHighscore)
				personalHighscore = value;
		}
	}
}
