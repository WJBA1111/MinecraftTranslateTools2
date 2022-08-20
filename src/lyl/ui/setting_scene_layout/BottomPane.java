package lyl.ui.setting_scene_layout;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lyl.utils.Judge;
import lyl.data.LoadData;
import lyl.data.TranslateData;
import lyl.ui.MyStyle;

import static lyl.data.TranslateData.element_height;
import static lyl.ui.SettingScene.gridPaneAll;

public class BottomPane {
    public static Pane getPane(){
        GridPane bottom_pane = new MyStyle.AllGridPane(gridPaneAll.getMinHeight()*0.15, gridPaneAll.getMinWidth());
        Button btn_back = new MyStyle.ButtonStyle1("返回",gridPaneAll.getMinHeight()*element_height,gridPaneAll.getMinWidth()*0.27);
        Button btn_save = new MyStyle.ButtonStyle1("保存",gridPaneAll.getMinHeight()*element_height,gridPaneAll.getMinWidth()*0.27);
        btn_back.setOnMouseClicked(event -> {
            if (event.getButton().toString().equals("PRIMARY") ){ //左键PRIMARY，右键SECONDARY
                TranslateData.main_stage.setScene(TranslateData.main_scene);
                System.out.println("你点击了左键");
            } else {
                System.out.println("点击了其他键");
            }
        });
        btn_save.setOnMouseClicked(event -> {
            if (event.getButton().toString().equals("PRIMARY") ){ //左键PRIMARY，右键SECONDARY
                String appid = TopPane.text_field_appid.getText();
                String security_key = TopPane.text_field_key.getText();
                String secret_id = TopPane.text_field_secret_id.getText();
                String secret_key = TopPane.text_field_secret_key.getText();
                int translate_api = TopPane.input_choose.getSelectionModel().getSelectedIndex();
                String sleep_time = TopPane.text_field_sleep_time.getText();
                String element_height = TopPane.text_field_element_height.getText();
                String window_multiplying = TopPane.text_field_window_multiplying.getText();
                String font_size = TopPane.text_field_font_size.getText();


                if (appid.equals(TranslateData.appid) &&
                        security_key.equals(TranslateData.security_key) &&
                        secret_id.equals(TranslateData.secret_id) &&
                        secret_key.equals(TranslateData.secret_key) &&
                        translate_api == TranslateData.translate_api &&
                        sleep_time.equals(String.valueOf(TranslateData.sleep_time)) &&
                        element_height.equals(String.valueOf(TranslateData.element_height))&&
                        window_multiplying.equals(String.valueOf(TranslateData.window_multiplying)) &&
                        font_size.equals(String.valueOf(TranslateData.font_size))){
                    System.out.println("配置文件没有发生改变，没有进行保存操作");
                    TranslateData.main_stage.setScene(TranslateData.main_scene);// 回到主页
                    return;
                }

                // 校验参数是否合理，这里没有做限制大小，只看是否为数字
                if (Judge.isAllNum(sleep_time,element_height,window_multiplying,font_size)){
                    TranslateData.appid = appid;
                    TranslateData.security_key = security_key;
                    TranslateData.secret_id = secret_id;
                    TranslateData.secret_key = secret_key;
                    TranslateData.translate_api = translate_api;
                    TranslateData.sleep_time = Integer.parseInt(sleep_time);
                    TranslateData.element_height = Double.parseDouble(element_height);
                    TranslateData.window_multiplying = Double.parseDouble(window_multiplying);
                    TranslateData.font_size = Integer.parseInt(font_size);
                    LoadData.save_config_file();// 保存为配置文件
                    System.out.println("保存成功");
                    TranslateData.main_stage.setScene(TranslateData.main_scene);// 回到主页
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeight(300d);
                    alert.setTitle("消息");
                    alert.setContentText("格式不对，ui相关必须为纯数字");
                    alert.setHeaderText("保存失败");
                    alert.initOwner(TranslateData.main_stage);
                    alert.showAndWait();
                }
                System.out.println("你点击了左键，开始保存配置文件，并设置参数");


            } else {
                System.out.println("点击了其他键");
            }
        });


        bottom_pane.add(btn_back,0,0);
        bottom_pane.add(btn_save,1,0);



        return bottom_pane;
    }
}
