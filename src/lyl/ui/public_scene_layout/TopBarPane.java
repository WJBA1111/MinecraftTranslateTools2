package lyl.ui.public_scene_layout;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import lyl.data.TranslateData;
import lyl.event.WindowMove;
import lyl.ui.MyStyle;
import lyl.utils.State;

public class TopBarPane {

    public static Pane getPane(){
        GridPane top_bar_pane = new MyStyle.AllGridPane(TranslateData.window_height * TranslateData.window_multiplying * 0.1, TranslateData.window_width * TranslateData.window_multiplying);
        top_bar_pane.setAlignment(Pos.CENTER_LEFT);
        top_bar_pane.setId("public-top-grid-pane");
        top_bar_pane.setHgap(2);

        ImageView imageView = new ImageView(new Image("/img/icon1.png"));
        imageView.setFitHeight(top_bar_pane.getMinHeight()*0.55);
        imageView.setFitWidth(imageView.getFitHeight());

        Text title_text = new MyStyle.AllText("我的世界翻译助手2.0");
        title_text.setId("public-top-title-text");


        Label mini_label = new Label();
        mini_label.setBackground(new Background(new BackgroundImage(new Image("img/mini.png"),null,null,null,new BackgroundSize(20,20,true,true,true,true))));
        mini_label.setMinHeight(top_bar_pane.getMinHeight()*0.95);
        mini_label.setMinWidth(mini_label.getMinHeight());
        mini_label.setId("public-top-mini-label");
        mini_label.setOnMouseClicked(event -> {
            if (event.getButton().toString().equals("PRIMARY") ){ //左键PRIMARY，右键SECONDARY
                TranslateData.only_stage.setIconified(true);
                System.out.println("你点击了左键，程序最小化");

            }
        });

        Label exit_label = new Label();

        exit_label.setBackground(new Background(new BackgroundImage(new Image("img/exit.png"),null,null,null,new BackgroundSize(20,20,true,true,true,true))));
        exit_label.setMinHeight(top_bar_pane.getMinHeight()*0.95);
        exit_label.setMinWidth(exit_label.getMinHeight());
        exit_label.setId("public-top-exit-label");
        exit_label.setOnMouseClicked(event -> {
            if (event.getButton().toString().equals("PRIMARY") ){ //左键PRIMARY，右键SECONDARY
                System.out.println("你点击了左键，程序结束运行");
                Platform.exit();
                System.exit(0);
            }
        });



        GridPane.setHalignment(mini_label, HPos.RIGHT);
        GridPane.setHgrow(mini_label,Priority.ALWAYS);// 设置按钮所在的单元格宽度占满剩余空间

        Label empty_label = new Label();//占位用
        empty_label.setMinWidth(5);


        top_bar_pane.add(imageView,0,0);
        top_bar_pane.add(title_text,1,0);
        top_bar_pane.add(mini_label,2,0);
        top_bar_pane.add(exit_label,3,0);
        top_bar_pane.add(empty_label,4,0);

        // 鼠标按下拖动窗口
        WindowMove windowMove = new WindowMove();
        top_bar_pane.setOnMousePressed(windowMove);
        top_bar_pane.setOnMouseDragged(windowMove);

        State.set_mouse_arrow_as_hand(mini_label,exit_label);
        return top_bar_pane;
    }


}
