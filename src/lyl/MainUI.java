package lyl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;

import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lyl.data.LoadData;
import lyl.data.TranslateData;
import lyl.ui.MainScene;

public class MainUI extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("我的世界翻译助手2.0");
        // 获取屏幕分辨率
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        TranslateData.window_height = bounds.getHeight();
        TranslateData.window_width = bounds.getWidth();

        primaryStage.getIcons().add(new Image("/img/icon1.png"));

        primaryStage.setOnCloseRequest(t -> {
            System.out.println("程序结束运行");
            Platform.exit();
            System.exit(0);
        });



        LoadData.init_data();// 把配置文件的数据加载到内存

        primaryStage.setMinHeight(TranslateData.window_height* TranslateData.window_multiplying);
        primaryStage.setMinWidth(TranslateData.window_width* TranslateData.window_multiplying);

        // 设置不可调节窗口大小
        primaryStage.setResizable(false);
        TranslateData.main_scene = MainScene.getScene();
        primaryStage.setScene(TranslateData.main_scene);

        // 让TranslateData.main_stage指向这个方法中的对象
        TranslateData.main_stage = primaryStage;

        // 显示
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch("");

    }

}
