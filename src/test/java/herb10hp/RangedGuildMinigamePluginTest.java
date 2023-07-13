package herb10hp;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RangedGuildMinigamePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(RangedGuildMinigamePlugin.class);
		RuneLite.main(args);
	}
}