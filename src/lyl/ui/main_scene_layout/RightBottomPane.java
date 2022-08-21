package lyl.ui.main_scene_layout;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import java.io.File;

import lyl.data.TranslateData;
import lyl.ui.MyStyle;
import lyl.utils.TipsMessage;
import lyl.utils.Translate;

import static lyl.data.TranslateData.element_height;
import static lyl.ui.MainScene.gridPaneAll;
import static lyl.ui.MainScene.gridPane_right;

public class RightBottomPane {
    private static final GridPane right_bottom_pane = new MyStyle.AllGridPane(gridPaneAll.getMinHeight() * 0.4, gridPane_right.getMinWidth());
    public static TextArea input_logs;
    public static Label label;



    public static Pane getPane(){
        right_bottom_pane.setId("main-right-bottom-grid-pane");

        /* 右下具体控件 */
        input_logs = new TextArea("");
        input_logs.setEditable(false);// 设置不可编辑
        input_logs.setStyle("-fx-font-alignment: center");
        input_logs.setMinHeight(gridPane_right.getMinHeight()*0.25);
        input_logs.setMaxHeight(gridPane_right.getMinHeight()*0.25);
        input_logs.setMinWidth(right_bottom_pane.getMinWidth()-20);
        input_logs.setMaxWidth(right_bottom_pane.getMinWidth()-20);
        input_logs.setFont(Font.font(20));//字体大小
        input_logs.setWrapText(true);//允许自动换行
        input_logs.setPrefRowCount(5);//初始化设置行数

        label = new Label();
        label.setFont(Font.font(TranslateData.font_size));
        GridPane.setHalignment(label, HPos.CENTER);// 对表格中单独的标签设置对齐方式


        Button btn_run = new MyStyle.ButtonStyle1("开始翻译",gridPane_right.getMinHeight()* element_height, right_bottom_pane.getMinWidth()*0.27);
        GridPane.setHalignment(btn_run, HPos.RIGHT);// 对表格中单独的标签设置对齐方式


        btn_run.setOnAction(event->{
            // 读取路径
            File file1 = null;
            File file2 = null;
            File file3 = null;
            if(TranslateData.translate_mode == 0){
                file1 = new File(RightTopPane.input_path.getText());
                if (!file1.exists()){
                    TipsMessage.timing("文件不存在",label,3000);//定时方法
                    return;
                }else {
                    label.setText("");
                }

            }else if(TranslateData.translate_mode == 2){
                file1 = new File(RightTopPane.input_path.getText());
                file2 = new File(RightTopPane.input_already_translate_old_path.getText());
                file3 = new File(RightTopPane.input_no_translate_old_path.getText());

                if (!file1.exists() || !file2.exists()||!file3.exists()){
                    TipsMessage.timing("文件不存在", label,3000);//定时方法
                    return;
                }else {
                    label.setText("");
                }
            }

            if(TranslateData.translate_file_format == 0){
                System.out.println("选择了Properties模式");
                try {
                    Translate.properties_mode(file1,file2,file3);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if (TranslateData.translate_file_format == 2){
                System.out.println("选择了Json模式");
                try {
                    Translate.json_mode(file1,file2,file3);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if (TranslateData.translate_file_format == 4){
                Translate.regex_mode(file1,file2,file3);
                System.out.println("选择了自定义正则表达式模式");
            }
        });

        right_bottom_pane.add(input_logs,0,0);
        right_bottom_pane.add(btn_run,0,1);
        right_bottom_pane.add(label,0,1);

        return right_bottom_pane;
    }



}
