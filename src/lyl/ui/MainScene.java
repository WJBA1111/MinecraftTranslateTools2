package lyl.ui;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import lyl.ui.main_scene_layout.*;

// 主页面场景
public class MainScene{

    /* 总布局 */
    public static GridPane gridPaneAll;
    /* 左侧布局 */
    public static GridPane gridPane_left;
    /* 右侧布局 */
    public static GridPane gridPane_right;
    /* 右侧顶部布局 */
    public static GridPane gridPane_right_top;
    /* 右侧底部布局 */
    public static GridPane gridPane_right_bottom;


    public static Scene getScene(){

        gridPaneAll = (GridPane) AllPane.getPane();
        gridPaneAll.setId("main-window-grid-pane");
        gridPaneAll.setHgap(0);
        gridPaneAll.setVgap(0);
        gridPane_left = (GridPane) LeftPane.getPane();
        gridPane_left.setHgap(4);
        gridPane_left.setVgap(4);
        gridPane_right = (GridPane) RightPane.getPane();
        gridPane_right_top = (GridPane) RightTopPane.getPane();
        gridPane_right_bottom = (GridPane) RightBottomPane.getPane();

        gridPane_right.add(gridPane_right_top,0,0);
        gridPane_right.add(gridPane_right_bottom,0,1);
        gridPaneAll.add(gridPane_left,0,0);
        gridPaneAll.add(gridPane_right,1,0);

        Scene scene = new Scene(gridPaneAll);
        scene.getStylesheets().add("lyl/ui/css/main_scene.css");
        return scene;
    }

}











