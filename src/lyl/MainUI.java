package lyl;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lyl.api.TencentTranslateAPI;
import lyl.data.LoadData;
import lyl.data.TranslateData;
import lyl.ui.MainScene;
import lyl.ui.WindowScene;

import java.util.Arrays;

public class MainUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("/img/icon1.png"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        // 获取屏幕分辨率
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        TranslateData.window_height = bounds.getHeight();
        TranslateData.window_width = bounds.getWidth();
        LoadData.init_data();// 把配置文件的数据加载到内存

        primaryStage.setMinHeight(TranslateData.window_height* TranslateData.window_multiplying);
        primaryStage.setMaxHeight(TranslateData.window_height* TranslateData.window_multiplying);
        primaryStage.setMinWidth(TranslateData.window_width* TranslateData.window_multiplying);
        primaryStage.setMaxHeight(TranslateData.window_height* TranslateData.window_multiplying);


        // 设置不可调节窗口大小
        primaryStage.setResizable(false);


        Scene main_scene = WindowScene.getScene(MainScene.getGridPane());
        TranslateData.main_scene = main_scene;
        main_scene.getStylesheets().addAll("lyl/ui/css/main_scene.css", "lyl/ui/css/window_frame.css");
        primaryStage.setScene(main_scene);
        // 手动设置高度和宽度，避免setResizable(false)时，primaryStage没有设置尺寸导致比场景的尺寸会大一些
        primaryStage.setMaxHeight(main_scene.getHeight());
        primaryStage.setMaxWidth(main_scene.getWidth());
        // 让TranslateData.main_stage指向这个方法中的对象
        TranslateData.only_stage = primaryStage;

        // 显示
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch("");

    }

}
