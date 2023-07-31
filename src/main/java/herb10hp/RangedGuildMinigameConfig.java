package herb10hp;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("RangedGuildMinigame")
public interface RangedGuildMinigameConfig extends Config {

	@ConfigItem(
			keyName = "isNotifyOnCompletion",
			name = "Notify when minigame is over",
			description = "It's recommended to go to RuneLite settings and allow notifications when the client screen is focused.",
			position = 1
	)
	default boolean isNotifyOnCompletion()
	{
		return true;
	}
}
