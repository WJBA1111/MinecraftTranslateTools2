package lyl.ui;


import javafx.scene.layout.*;
import lyl.ui.main_scene_layout.*;
import lyl.ui.public_scene_layout.ContentPane;


// 主页面场景
public class MainScene{
    /* 内容布局 */
    public static GridPane gridPaneContent;
    /* 左侧布局 */
    public static GridPane gridPane_left;
    /* 右侧布局 */
    public static GridPane gridPane_right;
    /* 右侧顶部布局 */
    public static GridPane gridPane_right_top;
    /* 右侧底部布局 */
    public static GridPane gridPane_right_bottom;


    public static GridPane getGridPane(){
        gridPaneContent = (GridPane) ContentPane.getPane();
        gridPaneContent.setId("main-window-grid-pane");

        gridPane_left = (GridPane) LeftPane.getPane();
        gridPane_left.setHgap(4);
        gridPane_left.setVgap(4);

        gridPane_right = (GridPane) RightPane.getPane();
        gridPane_right_top = (GridPane) RightTopPane.getPane();
        gridPane_right_bottom = (GridPane) RightBottomPane.getPane();

        gridPane_right.add(gridPane_right_top,0,0);
        gridPane_right.add(gridPane_right_bottom,0,1);

        gridPaneContent.add(gridPane_left,0,0);
        gridPaneContent.add(gridPane_right,1,0);

        return gridPaneContent;
    }

}











