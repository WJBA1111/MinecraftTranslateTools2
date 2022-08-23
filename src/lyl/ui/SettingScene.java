package lyl.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import lyl.ui.public_scene_layout.ContentPane;
import lyl.ui.setting_scene_layout.BottomPane;
import lyl.ui.setting_scene_layout.TopPane;

// 设置页面场景
public class SettingScene {
    public static GridPane gridPaneContent;
    /* 顶部布局 */
    public static GridPane gridPane_top;
    /* 底部布局 */
    public static GridPane gridPane_bottom;

    public static GridPane getGridPane(){
        gridPaneContent = (GridPane) ContentPane.getPane();
        gridPaneContent.setId("setting-window-grid-pane");
        gridPane_top = (GridPane) TopPane.getPane();
        gridPane_top.setId("setting-top-grid-pane");
        gridPane_bottom = (GridPane) BottomPane.getPane();
        gridPane_bottom.setId("setting-bottom-grid-pane");



        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);// 水平从不滚动
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);// 垂直看需要滚动
        sp.setPrefViewportHeight(gridPaneContent.getMinHeight()*0.85);




        sp.setContent(gridPane_top);
        sp.setId("setting-top-scroll-pane");// 设置id，方便css 的id选择器设置样式

        gridPaneContent.add(sp, 0, 0);
        gridPaneContent.add(gridPane_bottom,0,1);

        return gridPaneContent;
    }



}
