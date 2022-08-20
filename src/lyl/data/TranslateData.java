package lyl.data;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Properties;

public class TranslateData {

    public static int translate_api = 2; //  可选参数0，2  百度翻译 0  腾讯云翻译2   默认腾讯云翻译，因为腾讯云六百万字符，百度就几万
    public static int translate_file_format = 0;// 可选参数0，2，4  对应：properties、json、自定义正则
    public static int translate_mode = 0; // 可选参数 0 ， 2 对应全量翻译和增量
    public static String appid;  // 百度翻译的接口id
    public static String security_key; // 百度翻译的接口key

    public static String secret_id; // 腾讯云的接口id

    public static String secret_key; // 腾讯云的接口key
    public static Properties prop = new Properties();// 读取配置文件的对象

    public static Stage main_stage; // 主窗口对象
    public static Scene main_scene;// 主页面场景对象
    public static Scene setting_scene;// 设置页面场景对象
    public static double window_height;// 窗口高度
    public static double window_width;// 窗口宽度
    public static double window_multiplying = 0.5; // 窗口大小倍率
    public static int sleep_time = 2000;//翻译间隔

    public static int translate_char_length_limit = 3000;//调用api翻译时字符长度上限

//    public static final String properties_regex = "(?<==).*\\S";// 匹配lang文件格式，aaa=bbb
//    public static final String json_regex = "(?<=: {0,50}\")[\\w\\W]*?(?=\")";// 匹配json文件格式 "":"" 或 "" : ""
    public static String custom_regex = "";// 自定义正则

    public static int font_size = 18;
    public static double element_height = 0.08;//统一控件高度
}
