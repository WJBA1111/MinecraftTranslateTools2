package lyl.ui.main_scene_layout;

import javafx.scene.layout.Pane;
import lyl.ui.MainScene;
import lyl.ui.MyStyle;

public class RightPane {
    public static Pane getPane(){

        return new MyStyle.AllGridPane(MainScene.gridPaneAll.getMinHeight(), MainScene.gridPaneAll.getMinWidth() * 0.8);
    }
}
