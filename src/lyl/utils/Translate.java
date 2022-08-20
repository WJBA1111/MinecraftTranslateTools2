package lyl.utils;

import com.alibaba.fastjson.JSON;
import lyl.api.TencentTranslateAPI;
import lyl.data.TranslateData;
import lyl.ui.main_scene_layout.RightBottomPane;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Translate {
    private static final String sub = "$Z$";
    private static final String regex = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";


    // 创建 Pattern 对象
    private static final Pattern pattern = Pattern.compile(regex);

    private static final Pattern pattern1 = Pattern.compile("\n");


    // key为行，value为替换的值
    private static HashMap<String, ArrayList<String>> hm = new LinkedHashMap<>();


    public static void properties_mode(File file1, File file2, File file3) throws Exception {

        System.out.println("进入了Translate的properties_mode方法内");
        // 判断是全量翻译还是增量
        if (TranslateData.translate_mode == 0){
            OrderedProperties prop =  new OrderedProperties();
            try {
                prop.load(new FileReader(file1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(TranslateData.translate_api == 0){
                System.out.println("翻译api由百度翻译提供");

                TipsMessage.timing("并未实现百度翻译api",RightBottomPane.label);


            }else if(TranslateData.translate_api ==2){
                System.out.println("翻译api由腾讯翻译君提供");


                LinkedList<TencentTranslateAPI.OutputParams.Response> list = new LinkedList<>();

                StringBuilder sourceText = new StringBuilder();
                LinkedList<String> key_list = new LinkedList<>(prop.stringPropertyNames());

                System.out.println("keyset大小："+prop.keySet().size());
                System.out.println("LinkedList大小："+key_list.size());

                for (int index = 0;index < prop.keySet().size();index++) {
                    System.out.println("-------------------第"+index+"次循环-------------------");
                    String value = prop.getProperty(key_list.get(index));

                    // 把所有格式化占位符 全部替换 分割符
                    value = record_special_symbol(String.valueOf(index), value);


                    if (sourceText.length() + value.length() <= TranslateData.translate_char_length_limit && index+1 != key_list.size() ){
                        System.out.println("拼接没有超过上限，且不是最后一个");
                        // 拼接没有超过上限，且不是最后一个
                        sourceText.append(value).append("\n");

                    }else if (sourceText.length() + value.length() > TranslateData.translate_char_length_limit && index+1 != key_list.size()){
                        System.out.println("拼接超过上限，且不是最后一个");
                        // 拼接超过上限，且不是最后一个
                        TencentTranslateAPI.OutputParams.Response translate = TencentTranslateAPI.Translate(String.valueOf(sourceText),sub);
                        list.add(translate);
                        sourceText = new StringBuilder();
                        sourceText.append(value).append("\n");
                        Thread.sleep(TranslateData.sleep_time);

                    }else if(sourceText.length() + value.length() > TranslateData.translate_char_length_limit && index+1 == key_list.size()){
                        System.out.println("拼接超过上限，且是最后一个");
                        // 超过上限，且index是最后一个
                        TencentTranslateAPI.OutputParams.Response translate = TencentTranslateAPI.Translate(String.valueOf(sourceText),sub);
                        list.add(translate);
                        sourceText = new StringBuilder();
                        sourceText.append(value);
                        TencentTranslateAPI.OutputParams.Response translate1 = TencentTranslateAPI.Translate(String.valueOf(sourceText),sub);
                        list.add(translate1);
                    }else if(sourceText.length() + value.length()  <= TranslateData.translate_char_length_limit && index+1 == key_list.size() ){
                        System.out.println("没有超过上限，且index是最后一个");
                        // 没有超过上限，且index是最后一个
                        sourceText.append(value);
                        TencentTranslateAPI.OutputParams.Response translate1 = TencentTranslateAPI.Translate(String.valueOf(sourceText),sub);
                        list.add(translate1);
                    }

                }


                StringBuilder sbb2 = new StringBuilder();
                for (TencentTranslateAPI.OutputParams.Response response : list) {
                    sbb2.append(response.getTargetText());
                }


                String[] split = sbb2.toString().split("\n");
                System.out.println("翻译后的value数量是："+split.length);
                System.out.println("翻以前key的数量是"+key_list.size());

                if (split.length != key_list.size()){
                    TipsMessage.timing("翻译后的数量和翻译前数量不同，停止翻译",RightBottomPane.label);
                    return;
                }

                for (int i1 = 0; i1 < split.length; i1++) {
                    String key = key_list.get(i1);
                    String value = split[i1];

                    if(value.contains(sub)){
                        ArrayList<String> strings = hm.get(String.valueOf(i1));
                        for (String string : strings) {
                            value = value.replaceFirst(Matcher.quoteReplacement(sub), string);
                        }
                        System.out.println("进入了包含检测方法，value："+value);
                    }
                    System.out.println("key:"+key+";value:"+value);
                    prop.replace(key, value);
                }


                FileWriter fw = new FileWriter(file1.getParent()+File.separator+"translate_zh_cn.lang");
                prop.store(fw, null);
                fw.close();

            }else if (TranslateData.translate_mode == 2){

                System.out.println("为实现的方法");
            }


        }
    }

    public static void json_mode(File file1, File file2, File file3) {
        String s = IO.read_file_to_string(file1);
        Object json_object = JSON.parse(s);
    }
    public static void regex_mode(File file1, File file2, File file3) {

    }

    // 记录特殊符号，并在翻译后替换
    public static String record_special_symbol(String row, String str){

        Matcher m = pattern.matcher(str);
        Matcher m1 = pattern1.matcher(str);
        HashMap<Integer, String> hm_int_string = new HashMap<>();
        ArrayList<String> als = new ArrayList<>();

        while(m.find()){
            String group = m.group();
            int start = m.start();
            hm_int_string.put(start,group);
        }

        while (m1.find()){
            String group = m1.group();
            int start = m1.start();
            hm_int_string.put(start,group);
        }

        if (hm_int_string.size() > 0){
            // 对 map的key排序，由小到大
            List<Integer> collect = hm_int_string.keySet().stream().sorted(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1-o2;
                }
            }).collect(Collectors.toList());

            System.out.println(collect);
            for (Integer integer : collect) {
                als.add(hm_int_string.get(integer));
            }

            hm.put(row,als);
        }
        String s = m.replaceAll(Matcher.quoteReplacement(sub));
        String s1 = Pattern.compile("\n").matcher(s).replaceAll(Matcher.quoteReplacement(sub));
        System.out.println("s1是："+s1);
        return s1;
    }




}
