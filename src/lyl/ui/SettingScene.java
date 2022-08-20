package lyl.ui;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import lyl.data.TranslateData;
import lyl.ui.main_scene_layout.AllPane;
import lyl.ui.setting_scene_layout.BottomPane;
import lyl.ui.setting_scene_layout.TopPane;

// 设置页面场景
public class SettingScene {

    /* 总布局 */
    public static GridPane gridPaneAll;
    /* 顶部布局 */
    public static GridPane gridPane_top;
    /* 底部布局 */
    public static GridPane gridPane_bottom;

    public static Scene getScene(){
        gridPaneAll = (GridPane) AllPane.getPane();
        gridPaneAll.setId("setting-window-grid-pane");
        gridPane_top = (GridPane) TopPane.getPane();
        gridPane_top.setId("setting-top-grid-pane");
        gridPane_bottom = (GridPane) BottomPane.getPane();
        gridPane_bottom.setId("setting-bottom-grid-pane");



        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);// 水平从不滚动
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);// 垂直看需要滚动
        sp.setPrefViewportHeight(gridPaneAll.getMinHeight()*0.85);




        sp.setContent(gridPane_top);
        sp.setId("setting-top-scroll-pane");// 设置id，方便css 的id选择器设置样式

        gridPaneAll.add(sp, 0, 0);
        gridPaneAll.add(gridPane_bottom,0,1);

        Scene scene = new Scene(gridPaneAll);
        scene.getStylesheets().add("lyl/ui/css/setting_scene.css");
        TranslateData.setting_scene = scene;

        return scene;
    }



}
