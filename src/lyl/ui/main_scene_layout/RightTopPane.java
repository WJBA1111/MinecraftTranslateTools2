package lyl.ui.main_scene_layout;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lyl.data.TranslateData;
import lyl.ui.MainScene;
import lyl.ui.MyStyle;
import lyl.utils.State;

import java.io.File;

import static lyl.data.TranslateData.element_height;
import static lyl.ui.MainScene.gridPaneContent;
import static lyl.ui.MainScene.gridPane_right;


public class RightTopPane {

    private static final GridPane right_top_pane = new MyStyle.AllGridPane(gridPaneContent.getMinHeight() * 0.6, gridPane_right.getMinWidth());

    public static final TextField input_path = new MyStyle.TextFieldStyle1(gridPane_right.getMinHeight()* element_height, right_top_pane.getMinWidth()*0.4);
    public static ChoiceBox<Object> choose_file_number;
    private static Text  text_regex;

    private static Text text_no_translate_old_file_path;
    private static Text text_already_translate_old_file;
    private static TextField input_regex;// 自定义正则表达式 输入框
    public static TextField input_file_extension;// 文件后缀输入框
    public static TextField input_no_translate_old_path;
    public static TextField input_already_translate_old_path;
    private static Button btn_choose_no_translate_old_file;
    private static Button btn_choose_already_translate_old_file;

    public static Pane getPane(){
        right_top_pane.setId("main-right-top-grid-pane");

        /* 右上具体控件:*/
        Text text_file_number = new MyStyle.AllText("文件数量:");
        choose_file_number = new MyStyle.ChoiceBoxStyle1<>(gridPane_right.getMinHeight()* element_height,right_top_pane.getMinWidth()*0.4);
        Text text_file_type = new MyStyle.AllText("文件格式:");
        ChoiceBox<Object> choose_file_format = new MyStyle.ChoiceBoxStyle1<>(gridPane_right.getMinHeight()* element_height,right_top_pane.getMinWidth()*0.4);
        Text text_translate_mode = new MyStyle.AllText("翻译模式:");
        ChoiceBox<Object> choose_translate_mode = new MyStyle.ChoiceBoxStyle1<>(gridPane_right.getMinHeight()* element_height,right_top_pane.getMinWidth()*0.4);
        text_regex = new MyStyle.AllText("正则表达式:");
        // 自定义正则表达式的输入框
        input_regex = new MyStyle.TextFieldStyle1(gridPane_right.getMinHeight()* element_height, right_top_pane.getMinWidth()*0.4);
        Text text_file_path = new MyStyle.AllText("文件路径:");
        // 选择文件按钮
        Button btn_choose_file = new MyStyle.ButtonStyle1("选择文件",gridPane_right.getMinHeight()* element_height, right_top_pane.getMinWidth()*0.27);
        Text text_file_extension = new MyStyle.AllText("文件后缀:");
        input_file_extension = new MyStyle.TextFieldStyle1(gridPane_right.getMinHeight()* element_height, right_top_pane.getMinWidth()*0.4);

        choose_file_format.setItems(FXCollections.observableArrayList(
                "properties格式",
                new Separator(),
                "json格式",
                new Separator(),
                "自定义正则"
        ));
        choose_file_format.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int new_index = newValue.intValue();
            int old_index = oldValue.intValue();
            if(old_index == 4){
                TranslateData.custom_regex =input_regex.getText();
                // 隐藏
                MainScene.gridPane_right_top.getChildren().removeAll(text_regex,input_regex);

            }
            // 通过模式设置不同的正则表达式
            if(new_index == 0){
                TranslateData.translate_file_format = 0;
            } else if(new_index == 2){
                TranslateData.translate_file_format = 2;
            } else if(new_index == 4){
                TranslateData.translate_file_format = 4;
                right_top_pane.add(text_regex,0,2);
                right_top_pane.add(input_regex,1,2);
                input_regex.setText(TranslateData.custom_regex);
            }

        });
        choose_file_format.getSelectionModel().select(TranslateData.translate_file_format);


        choose_translate_mode.setItems(FXCollections.observableArrayList(
                "全量翻译",
                new Separator(),
                "增量翻译"
        ));
        choose_translate_mode.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int new_index = newValue.intValue();
            int old_index = oldValue.intValue();
            // 通过模式设置不同的正则表达式
            if(new_index == 0 && old_index == 2 ){
                TranslateData.translate_mode = 0;
                text_file_path.setText("文件路径:");

                // 删除增加的选项
                MainScene.gridPane_right_top.getChildren().removeAll(text_no_translate_old_file_path, text_already_translate_old_file, input_no_translate_old_path, input_already_translate_old_path, btn_choose_no_translate_old_file, btn_choose_already_translate_old_file
                        );
            } else if(new_index == 2 && old_index == 0 ){
                TranslateData.translate_mode = 2;
                text_file_path.setText("没翻译的新文件路径:");

                // 如果对象没创建 就创建对象
                if (text_no_translate_old_file_path == null || text_already_translate_old_file == null ||input_no_translate_old_path == null ||input_already_translate_old_path == null ||btn_choose_no_translate_old_file == null ||btn_choose_already_translate_old_file == null){
                    // 文本
                    text_no_translate_old_file_path = new MyStyle.AllText("没翻译的旧文件路径:");
                    text_already_translate_old_file = new MyStyle.AllText("已翻译的旧文件路径:");


                    // 路径输入框2
                    input_no_translate_old_path = new MyStyle.TextFieldStyle1(gridPane_right.getMinHeight()* element_height, right_top_pane.getMinWidth()*0.4);
                    // 路径输入框3
                    input_already_translate_old_path = new MyStyle.TextFieldStyle1(gridPane_right.getMinHeight()* element_height, right_top_pane.getMinWidth()*0.4);

                    // 选择没有翻译过旧文件按钮
                    btn_choose_no_translate_old_file = new MyStyle.ButtonStyle1("选择文件",gridPane_right.getMinHeight()* element_height, right_top_pane.getMinWidth()*0.27);
                    btn_choose_no_translate_old_file.setOnAction(event -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("选择文件");
                        try{
                            File file = fileChooser.showOpenDialog(TranslateData.main_scene.getWindow());
                            input_no_translate_old_path.setText(file.getAbsolutePath());
                        }catch(NullPointerException e){
                            System.out.println("没有选择文件");
                        }
                    });

                    // 选择翻译过的旧文件按钮
                    btn_choose_already_translate_old_file = new MyStyle.ButtonStyle1("选择文件",gridPane_right.getMinHeight()* element_height, right_top_pane.getMinWidth()*0.27);
                    btn_choose_already_translate_old_file.setOnAction(event -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("选择文件");
                        try{
                            File file = fileChooser.showOpenDialog(TranslateData.main_scene.getWindow());
                            input_already_translate_old_path.setText(file.getAbsolutePath());
                        }catch(NullPointerException e){
                            System.out.println("没有选择文件");
                        }
                    });
                }

                right_top_pane.add(text_no_translate_old_file_path,0,6);
                right_top_pane.add(input_no_translate_old_path,1,6);
                right_top_pane.add(btn_choose_no_translate_old_file,2,6);
                right_top_pane.add(text_already_translate_old_file,0,7);
                right_top_pane.add(input_already_translate_old_path,1,7);
                right_top_pane.add(btn_choose_already_translate_old_file,2,7);
            }
        });
        choose_translate_mode.getSelectionModel().select(TranslateData.translate_mode);

        btn_choose_file.setOnAction(event -> {
            // 如果文件数量是单个，
            if (choose_file_number.getSelectionModel().getSelectedIndex()==0){
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("选择文件");
                try{
                    File file = fileChooser.showOpenDialog(TranslateData.main_scene.getWindow());
                    input_path.setText(file.getAbsolutePath());
                }catch(NullPointerException e){
                    System.out.println("没有选择文件");
                }
            }else if (choose_file_number.getSelectionModel().getSelectedIndex()==2){
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("选择目录");
                try{
                    File file = directoryChooser.showDialog(TranslateData.main_scene.getWindow());
                    input_path.setText(file.getAbsolutePath());
                }catch(NullPointerException e){
                    System.out.println("没有选择目录");
                }
            }
        });

        choose_file_number.setItems(FXCollections.observableArrayList(
                "单个文件",
                new Separator(),
                "目录下所有指定后缀的文件"
        ));
        choose_file_number.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int new_index = newValue.intValue();
            int old_index = oldValue.intValue();
            if (new_index == 2 && old_index == 0){
                TranslateData.translate_text_file_number = 2;
                btn_choose_file.setText("选择目录");
                if (choose_translate_mode.getSelectionModel().getSelectedIndex()!=0){
                    choose_translate_mode.getSelectionModel().select(0);
                }
                choose_translate_mode.setDisable(true);
                text_file_path.setText("目录路径");
                input_path.setText("");
                right_top_pane.add(text_file_extension,0,5);
                right_top_pane.add(input_file_extension,1,5);

            }else if (new_index == 0 && old_index == 2){
                TranslateData.translate_text_file_number = 0;
                btn_choose_file.setText("选择文件");
                choose_translate_mode.setDisable(false);
                text_file_path.setText("文件路径");
                input_path.setText("");
                right_top_pane.getChildren().removeAll(text_file_extension,input_file_extension);
                input_file_extension.setText("");

            }
        });
        choose_file_number.getSelectionModel().select(TranslateData.translate_text_file_number);

        /*添加控件到布局*/
        right_top_pane.add(text_file_number,0,0);
        right_top_pane.add(choose_file_number,1,0);

        right_top_pane.add(text_file_type,0,1);
        right_top_pane.add(choose_file_format,1,1);
        right_top_pane.add(text_translate_mode,0,3);
        right_top_pane.add(choose_translate_mode,1,3);
        right_top_pane.add(text_file_path,0,4);
        right_top_pane.add(input_path,1,4);
        right_top_pane.add(btn_choose_file,2,4);
        State.set_mouse_arrow_as_hand(choose_file_number,choose_file_format,choose_translate_mode,btn_choose_file);
        return right_top_pane;
    }
}
