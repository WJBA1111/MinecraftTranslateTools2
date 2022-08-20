package lyl.utils;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class TipsMessage {

    private static Timer timer = new Timer();
    private static boolean flag = false;// 判断定时器是否在运行

    // 对指定的label设置3秒的消息文本
    public static void timing(String message, Label label){

        if (!flag){
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (label != null){
                            label.setText("");
                        }
                        System.out.println("执行了定时器");

                        flag = false;
                    });

                }
            };
            label.setText(message);
            System.out.println("添加了定时器");
            flag = true;
            timer.schedule(timerTask,3000);
            return;
        }
        timer.cancel();
        System.out.println("定时器已取消");
        timer = new Timer();
        flag = false;
        timing(message, label);
    }





}
