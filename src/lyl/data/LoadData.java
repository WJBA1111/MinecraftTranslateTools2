package lyl.data;

import java.io.*;

// 配置文件保存和读取相关的类
public class LoadData {
    private static final File file_setting = new File("setting.properties");
    // 初始化数据
    public static void init_data(){
        if (file_setting.exists()){
            read_config_file();
        } else {
            save_config_file();
        }
    }

    // 读取配置文件
    public static void read_config_file(){
        try(FileInputStream fis = new FileInputStream(file_setting)) {
            TranslateData.prop.load(fis);
            TranslateData.secret_id = TranslateData.prop.getProperty("secret_id");
            TranslateData.secret_key = TranslateData.prop.getProperty("secret_key");
            TranslateData.translate_api = Integer.parseInt(TranslateData.prop.getProperty("translate_api"));
            TranslateData.appid = TranslateData.prop.getProperty("appid");
            TranslateData.security_key = TranslateData.prop.getProperty("security_key");
            TranslateData.sleep_time = Integer.parseInt(TranslateData.prop.getProperty("sleep_time"));
            TranslateData.element_height = Double.parseDouble(TranslateData.prop.getProperty("element_height"));
            TranslateData.window_multiplying = Double.parseDouble(TranslateData.prop.getProperty("window_multiplying"));
            TranslateData.font_size = Integer.parseInt(TranslateData.prop.getProperty("font_size"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // 保存为配置文件
    public static void save_config_file(){

        if(TranslateData.appid == null){
            TranslateData.appid = "";
        }
        if (TranslateData.security_key == null){
            TranslateData.security_key = "";
        }

        if (TranslateData.secret_id == null){
            TranslateData.secret_id = "";
        }

        if (TranslateData.secret_key == null){
            TranslateData.secret_key = "";
        }
        TranslateData.prop.setProperty("translate_api", String.valueOf(TranslateData.translate_api));

        TranslateData.prop.setProperty("secret_id", TranslateData.secret_id);
        TranslateData.prop.setProperty("secret_key",TranslateData.secret_key);

        TranslateData.prop.setProperty("appid",TranslateData.appid);
        TranslateData.prop.setProperty("security_key",TranslateData.security_key);

        TranslateData.prop.setProperty("sleep_time", String.valueOf(TranslateData.sleep_time));
        TranslateData.prop.setProperty("element_height", String.valueOf(TranslateData.element_height));
        TranslateData.prop.setProperty("window_multiplying", String.valueOf(TranslateData.window_multiplying));
        TranslateData.prop.setProperty("font_size", String.valueOf(TranslateData.font_size));
        try {
            FileWriter fw = new FileWriter(file_setting);
            TranslateData.prop.store(fw,null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
