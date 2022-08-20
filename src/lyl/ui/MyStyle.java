package lyl.ui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lyl.data.TranslateData;

public class MyStyle {
    public static class AllGridPane extends GridPane {
        public AllGridPane(Double height, Double width) {
            setAlignment(Pos.TOP_CENTER);
            setHgap(5);
            setVgap(5);
            setMinSize(width,height);
            setMaxSize(width,height);
        }
    }

    public static class AllText extends Text {
        public AllText(String text) {
            setText(text);
            setFont(Font.font(TranslateData.font_size));
        }
    }

    public static class TitleText extends Text {
        public TitleText(String text) {
            setText(text);
            setId("title-text");
            int big_font = TranslateData.font_size+ 4;
            setStyle("-fx-font-size: "+big_font);

        }
    }

    public static class ButtonStyle1 extends Button {

        public ButtonStyle1(String text, Double height, Double width) {
            setId("btn");
            setText(text);
            setMinSize(width,height);
            setMaxSize(width,height);
            setFont(Font.font(TranslateData.font_size));
        }
    }

    public static class ChoiceBoxStyle1<T> extends ChoiceBox<T> {
        public ChoiceBoxStyle1(Double height, Double width) {
            setMinSize(width,height);
            setMaxSize(width,height);
            int font_size = TranslateData.font_size;
            setStyle("-fx-font-size: "+font_size);
        }
    }

    public static class LeftLabel extends Label {
        // label的文本，父窗口（通过父窗口来设置控件大小）
        public LeftLabel(String text, Pane parent_pane) {
            setText(text);
            setId("left-label");
            setAlignment(Pos.CENTER_LEFT);
            setFont(Font.font(TranslateData.font_size));
            setMinHeight(parent_pane.getMinHeight()*0.1);
            setMinWidth(parent_pane.getMinWidth()-10);
            setMaxHeight(parent_pane.getMinHeight()*0.1);
            setMaxWidth(parent_pane.getMinWidth()-10);
        }
    }

    public static class TextFieldStyle1 extends TextField {

        public TextFieldStyle1(Double height, Double width) {
            setMinSize(width,height);
            setMaxSize(width,height);
            setPrefWidth(width);// 首选宽度

            setFont(Font.font(TranslateData.font_size));
        }
    }





}
