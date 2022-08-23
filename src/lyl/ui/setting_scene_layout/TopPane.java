package lyl.ui.setting_scene_layout;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lyl.data.TranslateData;
import lyl.ui.MyStyle;
import lyl.ui.SettingScene;

import static lyl.data.TranslateData.element_height;
import static lyl.ui.SettingScene.gridPaneContent;

public class TopPane {
    public static TextField text_field_appid;
    public static TextField text_field_key;

    public static TextField text_field_secret_id;

    public static TextField text_field_secret_key;
    public static MyStyle.ChoiceBoxStyle1<Object> input_choose;

    public static TextField text_field_sleep_time;
    public static TextField text_field_element_height;
    public static TextField text_field_window_multiplying;
    public static TextField text_field_font_size;

    public static Pane getPane(){
        GridPane top_pane = new GridPane();
        top_pane.setAlignment(Pos.TOP_CENTER);
        top_pane.setHgap(5);
        top_pane.setVgap(5);
        top_pane.setMinWidth(gridPaneContent.getMinWidth());
        top_pane.setMinHeight(gridPaneContent.getMinHeight()*2);

        Text text_user = new MyStyle.TitleText("账号：");
        Text text_appid = new MyStyle.AllText("百度翻译id");
        Text text_key = new MyStyle.AllText("百度翻译key");
        Text text_secret_id = new MyStyle.AllText("腾讯云id");
        Text text_secret_key = new MyStyle.AllText("腾讯云key");

        Text text_api = new MyStyle.AllText("翻译API：");
        input_choose = new MyStyle.ChoiceBoxStyle1<>(gridPaneContent.getMinHeight()* element_height,gridPaneContent.getMinWidth()*0.4);

        input_choose.setItems(FXCollections.observableArrayList(
                "百度翻译api",
                new Separator(),
                "腾讯云翻译api"
        ));




        Text text_system = new MyStyle.TitleText("系统参数：");
        Text text_sleep_time = new MyStyle.AllText("翻译间隔");

        Text text_ui = new MyStyle.TitleText("UI：");
        Text text_element_height = new MyStyle.AllText("控件高度倍率");
        Text text_window_multiplying = new MyStyle.AllText("窗口大小倍率");
        Text text_font_size = new MyStyle.AllText("字体大小");

        text_field_appid = new MyStyle.TextFieldStyle1(gridPaneContent.getMinHeight()* element_height, gridPaneContent.getMinWidth()*0.4);
        text_field_key = new MyStyle.TextFieldStyle1(gridPaneContent.getMinHeight()* element_height, gridPaneContent.getMinWidth()*0.4);

        text_field_secret_id = new MyStyle.TextFieldStyle1(gridPaneContent.getMinHeight()* element_height, gridPaneContent.getMinWidth()*0.4);
        text_field_secret_key = new MyStyle.TextFieldStyle1(gridPaneContent.getMinHeight()* element_height, gridPaneContent.getMinWidth()*0.4);

        text_field_sleep_time = new MyStyle.TextFieldStyle1(gridPaneContent.getMinHeight()* element_height, gridPaneContent.getMinWidth()*0.4);
        text_field_element_height = new MyStyle.TextFieldStyle1(gridPaneContent.getMinHeight()* element_height, gridPaneContent.getMinWidth()*0.4);
        text_field_window_multiplying = new MyStyle.TextFieldStyle1(gridPaneContent.getMinHeight()* element_height, gridPaneContent.getMinWidth()*0.4);
        text_field_font_size = new MyStyle.TextFieldStyle1(gridPaneContent.getMinHeight()* element_height, gridPaneContent.getMinWidth()*0.4);



        text_field_appid.setText(TranslateData.appid);
        text_field_key.setText(TranslateData.security_key);

        text_field_secret_id.setText(TranslateData.secret_id);
        text_field_secret_key.setText(TranslateData.secret_key);

        text_field_sleep_time.setText(String.valueOf(TranslateData.sleep_time));
        text_field_element_height.setText(String.valueOf(TranslateData.element_height));
        text_field_window_multiplying.setText(String.valueOf(TranslateData.window_multiplying));
        text_field_font_size.setText(String.valueOf(TranslateData.font_size));




        input_choose.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int new_index = newValue.intValue();
            int old_index = oldValue.intValue();
            if(new_index == 0 && old_index == -1){
                top_pane.add(text_appid,0,2);
                top_pane.add(text_field_appid,1,2);
                top_pane.add(text_key,0,3);
                top_pane.add(text_field_key,1,3);
            } else if(new_index == 2 && old_index == -1){
                top_pane.add(text_secret_id,0,2);
                top_pane.add(text_field_secret_id,1,2);
                top_pane.add(text_secret_key,0,3);
                top_pane.add(text_field_secret_key,1,3);
            }else if(new_index == 0 && old_index == 2){
                SettingScene.gridPane_top.getChildren().removeAll(text_secret_id,text_field_secret_id,text_secret_key,text_field_secret_key);
                top_pane.add(text_appid,0,2);
                top_pane.add(text_field_appid,1,2);
                top_pane.add(text_key,0,3);
                top_pane.add(text_field_key,1,3);

            } else if (new_index == 2 && old_index == 0) {
                SettingScene.gridPane_top.getChildren().removeAll(text_appid,text_field_appid,text_key,text_field_key);

                top_pane.add(text_secret_id,0,2);
                top_pane.add(text_field_secret_id,1,2);
                top_pane.add(text_secret_key,0,3);
                top_pane.add(text_field_secret_key,1,3);
            }


        });

        input_choose.getSelectionModel().select(TranslateData.translate_api);




        top_pane.add(text_user,0,0);

        top_pane.add(text_api,0,1);
        top_pane.add(input_choose,1,1);


        top_pane.add(new Text(""),0,4);

        top_pane.add(text_system,0,5);


        top_pane.add(text_sleep_time,0,6);
        top_pane.add(text_field_sleep_time,1,6);

        top_pane.add(new Text(""),0,7);

        top_pane.add(text_ui,0,8);

        top_pane.add(text_element_height,0,9);
        top_pane.add(text_field_element_height,1,9);

        top_pane.add(text_window_multiplying,0,10);
        top_pane.add(text_field_window_multiplying,1,10);

        top_pane.add(text_font_size,0,11);
        top_pane.add(text_field_font_size,1,11);


        return top_pane;
    }


}
