package herb10hp;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;

public class RangedGuildMinigameOverlay extends Overlay {

    @Inject
    private RangedGuildMinigamePlugin plugin;

    @Inject
    private RangedGuildMinigameConfig config;

    private PanelComponent panelComponent = new PanelComponent();

    @Inject
    public RangedGuildMinigameOverlay(){
        setPosition(OverlayPosition.TOP_CENTER);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        panelComponent.getChildren().clear();

        panelComponent.getChildren().add(
                LineComponent.builder()
                        .left("Turn:")
                        .right(Integer.toString(plugin.getTurn()))
                        .build());

        panelComponent.getChildren().add(
                LineComponent.builder()
                        .left("Current Score:")
                        .right(Integer.toString(plugin.getCurrentScore()))
                        .build());

        panelComponent.getChildren().add(
                LineComponent.builder()
                        .left("Hiscore:")
                        .right(Integer.toString(plugin.getPersonalHighscore()))
                        .build());

        return panelComponent.render(graphics);
    }
}
