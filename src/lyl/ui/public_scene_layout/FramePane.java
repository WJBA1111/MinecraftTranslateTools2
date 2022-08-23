package lyl.ui.public_scene_layout;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lyl.data.TranslateData;
import lyl.ui.MyStyle;

public class FramePane {

    public static Pane getPane(){
        GridPane framePane = new MyStyle.AllGridPane(TranslateData.window_height * TranslateData.window_multiplying, TranslateData.window_width * TranslateData.window_multiplying);
        framePane.setId("window-frame-grid-pane");
        framePane.setHgap(0);
        framePane.setVgap(0);
        return framePane;
    }

}
