package lyl.ui.main_scene_layout;

import javafx.scene.layout.Pane;
import lyl.data.TranslateData;
import lyl.ui.MyStyle;

public class AllPane {
    public static Pane getPane(){
        return  new MyStyle.AllGridPane(TranslateData.window_height * TranslateData.window_multiplying, TranslateData.window_width * TranslateData.window_multiplying);
    }

}
