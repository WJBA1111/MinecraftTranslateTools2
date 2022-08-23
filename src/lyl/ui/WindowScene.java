package lyl.ui;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lyl.ui.public_scene_layout.FramePane;
import lyl.ui.public_scene_layout.TopBarPane;

public class WindowScene {
    /* 整体框架布局 */
    public static GridPane gridPaneFrame;
    /* 内容布局 */
    public static GridPane gridPaneContent;
    /* 顶栏布局 */
    public static GridPane gridPaneTopBar;

    public static Scene getScene(GridPane gridPane){
        gridPaneFrame = (GridPane) FramePane.getPane();
        gridPaneTopBar = (GridPane) TopBarPane.getPane();

        gridPaneContent = gridPane;


        gridPaneFrame.add(gridPaneTopBar,0,0);
        gridPaneFrame.add(gridPaneContent,0,1);

        return new Scene(gridPaneFrame);
    }



}
