package lyl.utils;

import java.io.*;
public class IO {
    // 保存字符串为文件
    public static boolean save_string_to_file(String str, File file){
        try (FileWriter fw = new FileWriter(file)){
            fw.write(str);
            fw.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 读取文件内容为字符串
    public static String read_file_to_string(File file){
        StringBuilder sb = new StringBuilder();
        try (FileReader fis = new FileReader(file)){
            char[] buffer = new char[1024];
            int len;
            while((len = fis.read(buffer))!= -1){
                sb.append(new String(buffer,0,len));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

}
