package lyl.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lyl.data.TranslateData;

public class WindowMove implements EventHandler<MouseEvent> {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            TranslateData.only_stage.setX(event.getScreenX() - xOffset);
            if (event.getScreenY() - yOffset < 0) {
                TranslateData.only_stage.setY(0);
            } else {
                TranslateData.only_stage.setY(event.getScreenY() - yOffset);
            }
        }
    }


}
