package lyl.utils;

import javafx.scene.Cursor;
import javafx.scene.Node;

// 各种监听器实现的状态
public class State{
    // 鼠标箭头经过控件时 变成 手
    public static void set_mouse_arrow_as_hand(Node...node){
        for (Node node1 : node) {
            node1.setOnMouseEntered(event -> {
                node1.setCursor(Cursor.HAND);
            });
        }
    }


}
