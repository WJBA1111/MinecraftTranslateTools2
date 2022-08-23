package lyl.ui.public_scene_layout;

import javafx.scene.layout.Pane;
import lyl.data.TranslateData;
import lyl.ui.MyStyle;

public class ContentPane {
    public static Pane getPane(){
        return  new MyStyle.AllGridPane(TranslateData.window_height * TranslateData.window_multiplying * 0.9, TranslateData.window_width * TranslateData.window_multiplying);
    }

}
