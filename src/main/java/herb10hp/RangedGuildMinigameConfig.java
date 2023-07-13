package herb10hp;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("RangedGuildMinigame")
public interface RangedGuildMinigameConfig extends Config {
	@ConfigSection(
			position = 1,
			name = "Notification Settings",
			description = "Enable/disable notifications"
	)
	String notifications = "notifications";

	@ConfigItem(
			keyName = "isNotifyOnCompletion",
			name = "Notify when minigame is over",
			description = "It's recommended to go to RuneLite settings and allow notifications when the client screen is focused.",
			section = "notifications",
			position = 1
	)
	default boolean isNotifyOnCompletion()
	{
		return true;
	}
}
