package lyl.ui.main_scene_layout;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lyl.data.TranslateData;
import lyl.ui.MainScene;
import lyl.ui.MyStyle;
import lyl.ui.SettingScene;

public class LeftPane {
    public static Pane getPane(){
        GridPane left_pane = new MyStyle.AllGridPane(MainScene.gridPaneAll.getMinHeight(), MainScene.gridPaneAll.getMinWidth() * 0.2);

        left_pane.setId("main-left-grid-pane");

        /* 左侧具体控件：*/
        Label text_setting = new MyStyle.LeftLabel("设置", left_pane);
        Label left_label_all_translate = new MyStyle.LeftLabel("主页", left_pane);
        left_pane.add(left_label_all_translate,0,0);
        left_pane.add(text_setting,0,1);

        left_label_all_translate.setOnMouseClicked(event -> {
            if (event.getButton().toString().equals("PRIMARY") ){ //左键PRIMARY，右键SECONDARY
                System.out.println("你点击了左键，什么都没有发生");
            } else {
                System.out.println("点击了其他键");
            }

        });
        text_setting.setOnMouseClicked(event -> {
            if (event.getButton().toString().equals("PRIMARY") ){ //左键PRIMARY，右键SECONDARY
                TranslateData.main_stage.setScene(SettingScene.getScene());
                System.out.println("点击了左键，进入了设置界面");
            } else {
                System.out.println("点击了其他键");
            }

        });






        return left_pane;
    }
}
