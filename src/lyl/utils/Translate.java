package lyl.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lyl.api.BaiDuTranslateAPI;
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

public class Translate {
    private static final String special_symbol_sub = "$Z$";// 将正则匹配的内容替换成这个字符，并在翻译后替换回去
    //    private static final String baidu_sub = "$S$";
    private static final String regex = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";

    // 创建 Pattern 对象
    private static final Pattern pattern = Pattern.compile(regex);
    private static final Pattern pattern1 = Pattern.compile("\n");

    public static void properties_mode(File file1, File file2, File file3) throws Exception {

        if (TranslateData.translate_mode == 0) {//全量翻译
            OrderedProperties prop = new OrderedProperties();
            try {
                prop.load(new FileReader(file1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 创建存放key和value的两个list
            LinkedList<String> key_list = new LinkedList<>(prop.stringPropertyNames());
            List<String> value_list = key_list.stream().map(prop::getProperty).collect(Collectors.toList());

            if (key_list.size() != value_list.size() || key_list.size() <= 0) {
                TipsMessage.timing("文件中没有匹配到需要翻译的字符串", RightBottomPane.label, 5000);
                return;
            }


            // 创建最终替换字符串的下标和多个字符串的map
            LinkedHashMap<Integer, ArrayList<String>> final_replace_hash_map = new LinkedHashMap<>();

            // 将正则匹配到的下标和字符串list 存入 上面的final_replace_hash_map
            for (int i = 0; i < value_list.size(); i++) {
                LinkedHashMap<Integer, String> map = null;
                String value = value_list.get(i);
                Matcher m = pattern.matcher(value);
                Matcher m1 = pattern1.matcher(value);
                while (m.find()) {
                    if (map == null) {
                        map = new LinkedHashMap<>(); // 存放正则匹配到的下标和字符串
                    }
                    int start = m.start();
                    String group = m.group();
                    map.put(start, group);
                }
                while (m1.find()) {
                    if (map == null) {
                        map = new LinkedHashMap<>(); // 存放正则匹配到的下标和字符串
                    }
                    int start = m1.start();
                    String group = m1.group();
                    map.put(start, group);
                }
                if (map != null && map.size() > 0) {// 如果map大小不为0，也就是说正则匹配到了，那就进行替换指定字符串，并且记录位置和顺序
                    // 对 map的key排序，由小到大
                    List<Integer> collect = map.keySet().stream().sorted(Comparator.comparingInt(o -> o)).collect(Collectors.toList());

                    ArrayList<String> list = new ArrayList<>();// 存放排序后的字符串

                    for (Integer integer : collect) {
                        list.add(map.get(integer));
                    }

                    final_replace_hash_map.put(i, list);
                    String first = m.replaceAll(Matcher.quoteReplacement(special_symbol_sub));
                    String second = pattern1.matcher(first).replaceAll(Matcher.quoteReplacement(special_symbol_sub));
                    value_list.set(i, second);
                }

            }

            // 进行翻译操作
            LinkedList<String> after_translation_list = new LinkedList<>();
            StringBuilder sourceText = new StringBuilder();

            if (TranslateData.translate_api == 0) { //如果api是百度翻译
                System.out.println("使用百度翻译api翻译");
                for (int i = 0; i < value_list.size(); i++) {
                    System.out.println("-------------------第" + i + "次循环-------------------");
                    String value = value_list.get(i);
                    if (sourceText.length() + value.length() + "\n\n".length() <= TranslateData.translate_char_length_limit && i + 1 != value_list.size()) {
                        // 拼接没有超过上限，且不是最后一个
                        System.out.println("拼接没有超过上限，且不是最后一个");
                        sourceText.append(value).append("\n");

                    } else if (sourceText.length() + value.length() + "\n\n".length() > TranslateData.translate_char_length_limit && i + 1 != value_list.size()) {
                        // 拼接超过上限，且不是最后一个
                        System.out.println("拼接超过上限，且不是最后一个");
                        after_translation_list.add(BaiDuTranslateAPI.translate(String.valueOf(sourceText)));
                        sourceText.delete(0, sourceText.length());
                        sourceText.append(value).append("\n");
                        Thread.sleep(TranslateData.sleep_time);

                    } else if (sourceText.length() + value.length() + "\n\n".length() > TranslateData.translate_char_length_limit && i + 1 == value_list.size()) {
                        // 超过上限，且index是最后一个
                        System.out.println("拼接超过上限，且是最后一个");
                        after_translation_list.add(BaiDuTranslateAPI.translate(String.valueOf(sourceText)));
                        sourceText.delete(0, sourceText.length());
                        sourceText.append(value);
                        Thread.sleep(TranslateData.sleep_time);
                        after_translation_list.add(BaiDuTranslateAPI.translate(String.valueOf(sourceText)));
                    } else if (sourceText.length() + value.length() + "\n\n".length() <= TranslateData.translate_char_length_limit && i + 1 == value_list.size()) {
                        // 没有超过上限，且index是最后一个
                        System.out.println("没有超过上限，且index是最后一个");
                        sourceText.append(value);
                        after_translation_list.add(BaiDuTranslateAPI.translate(String.valueOf(sourceText)));
                    }
                }
            } else if (TranslateData.translate_api == 2) { //如果api是腾讯翻译
                System.out.println("使用了腾讯翻译君api翻译");
                for (int i = 0; i < value_list.size(); i++) {
                    System.out.println("-------------------第" + i + "次循环-------------------");
                    String value = value_list.get(i);
                    if (sourceText.length() + value.length() <= TranslateData.translate_char_length_limit && i + 1 != value_list.size()) {
                        // 拼接没有超过上限，且不是最后一个
                        System.out.println("拼接没有超过上限，且不是最后一个");
                        sourceText.append(value).append("\n");

                    } else if (sourceText.length() + value.length() > TranslateData.translate_char_length_limit && i + 1 != value_list.size()) {
                        // 拼接超过上限，且不是最后一个
                        System.out.println("拼接超过上限，且不是最后一个");
                        after_translation_list.add(TencentTranslateAPI.Translate(String.valueOf(sourceText), special_symbol_sub).getTargetText());
                        sourceText.delete(0, sourceText.length());
                        sourceText.append(value).append("\n");
                        Thread.sleep(TranslateData.sleep_time);

                    } else if (sourceText.length() + value.length() > TranslateData.translate_char_length_limit && i + 1 == value_list.size()) {
                        // 超过上限，且index是最后一个
                        System.out.println("拼接超过上限，且是最后一个");
                        after_translation_list.add(TencentTranslateAPI.Translate(String.valueOf(sourceText), special_symbol_sub).getTargetText());
                        sourceText.delete(0, sourceText.length());
                        sourceText.append(value);
                        Thread.sleep(TranslateData.sleep_time);
                        after_translation_list.add(TencentTranslateAPI.Translate(String.valueOf(sourceText), special_symbol_sub).getTargetText());
                    } else if (sourceText.length() + value.length() <= TranslateData.translate_char_length_limit && i + 1 == value_list.size()) {
                        // 没有超过上限，且index是最后一个
                        System.out.println("没有超过上限，且index是最后一个");
                        sourceText.append(value);
                        after_translation_list.add(TencentTranslateAPI.Translate(String.valueOf(sourceText), special_symbol_sub).getTargetText());
                    }
                }

            }

            // 得到翻译后文本
            String translatedText = after_translation_list.stream().map(String::valueOf).collect(Collectors.joining());
            String[] split = translatedText.split("\n");
            System.out.println("翻译后的value数量是：" + split.length);
            System.out.println("翻以前key的数量是" + key_list.size());
            if (split.length != key_list.size()) {
                TipsMessage.timing("翻译后的数量和翻译前数量不同，停止翻译", RightBottomPane.label, 3000);
                return;
            }

            // 对翻译后带有不翻译字符进行替换
            for (int i1 = 0; i1 < split.length; i1++) {
                String key = key_list.get(i1);
                String value = split[i1];

                if (value.contains(special_symbol_sub)) {
                    ArrayList<String> strings = final_replace_hash_map.get(i1);
                    for (String string : strings) {
                        value = value.replaceFirst(Matcher.quoteReplacement(special_symbol_sub), Matcher.quoteReplacement(string));
                    }
                }
                prop.replace(key, value);
            }


            // 保存翻译后的properties格式的文本
            FileWriter fw = new FileWriter(file1.getParent() + File.separator + "translate_zh_cn.lang");
            prop.store(fw, null);
            fw.close();
            TipsMessage.timing("翻译成功", RightBottomPane.label, 5000);


        } else if (TranslateData.translate_mode == 2) {//增量翻译
            TipsMessage.timing("增量模式未实现", RightBottomPane.label, 3000);
        }


    }

    public static void json_mode(File file1, File file2, File file3) throws Exception {

        if (TranslateData.translate_mode == 0) { //如果是全量翻译
            String s = IO.read_file_to_string(file1);
            LinkedHashMap<String, String> hashMap = JSON.parseObject(s, LinkedHashMap.class, Feature.OrderedField);
            LinkedList<String> key_list = new LinkedList<>(hashMap.keySet());
            LinkedList<String> value_list = new LinkedList<>(hashMap.values());

            if (key_list.size() != value_list.size() || key_list.size() <= 0) {
                TipsMessage.timing("文件中没有匹配到需要翻译的字符串", RightBottomPane.label, 5000);
                return;
            }
            // 创建最终替换字符串的下标和多个字符串的map
            LinkedHashMap<Integer, ArrayList<String>> final_replace_hash_map = new LinkedHashMap<>();

            // 将正则匹配到的下标和字符串list 存入 上面的final_replace_hash_map
            for (int i = 0; i < value_list.size(); i++) {
                LinkedHashMap<Integer, String> map = null;
                String value = value_list.get(i);
                Matcher m = pattern.matcher(value);
                Matcher m1 = pattern1.matcher(value);
                while (m.find()) {
                    if (map == null) {
                        map = new LinkedHashMap<>(); // 存放正则匹配到的下标和字符串
                    }
                    int start = m.start();
                    String group = m.group();
                    map.put(start, group);
                }
                while (m1.find()) {
                    if (map == null) {
                        map = new LinkedHashMap<>(); // 存放正则匹配到的下标和字符串
                    }
                    int start = m1.start();
                    String group = m1.group();
                    map.put(start, group);
                }
                if (map != null && map.size() > 0) {// 如果map大小不为0，也就是说正则匹配到了，那就进行替换指定字符串，并且记录位置和顺序
                    // 对 map的key排序，由小到大
                    List<Integer> collect = map.keySet().stream().sorted(Comparator.comparingInt(o -> o)).collect(Collectors.toList());

                    ArrayList<String> list = new ArrayList<>();// 存放排序后的字符串

                    for (Integer integer : collect) {
                        list.add(map.get(integer));
                    }

                    final_replace_hash_map.put(i, list);
                    String first = m.replaceAll(Matcher.quoteReplacement(special_symbol_sub));
                    String second = pattern1.matcher(first).replaceAll(Matcher.quoteReplacement(special_symbol_sub));
                    value_list.set(i, second);
                }

            }

            // 进行翻译操作
            LinkedList<String> after_translation_list = new LinkedList<>();
            StringBuilder sourceText = new StringBuilder();
            if (TranslateData.translate_api == 0) { //如果是百度翻译api
                System.out.println("使用百度翻译api翻译");
                for (int i = 0; i < value_list.size(); i++) {
                    System.out.println("-------------------第" + i + "次循环-------------------");
                    String value = value_list.get(i);
                    if (sourceText.length() + value.length() + "\n\n".length() <= TranslateData.translate_char_length_limit && i + 1 != value_list.size()) {
                        // 拼接没有超过上限，且不是最后一个
                        System.out.println("拼接没有超过上限，且不是最后一个");
                        sourceText.append(value).append("\n");

                    } else if (sourceText.length() + value.length() + "\n\n".length() > TranslateData.translate_char_length_limit && i + 1 != value_list.size()) {
                        // 拼接超过上限，且不是最后一个
                        System.out.println("拼接超过上限，且不是最后一个");
                        after_translation_list.add(BaiDuTranslateAPI.translate(String.valueOf(sourceText)));
                        sourceText.delete(0, sourceText.length());
                        sourceText.append(value).append("\n");
                        Thread.sleep(TranslateData.sleep_time);

                    } else if (sourceText.length() + value.length() + "\n\n".length() > TranslateData.translate_char_length_limit && i + 1 == value_list.size()) {
                        // 超过上限，且index是最后一个
                        System.out.println("拼接超过上限，且是最后一个");
                        after_translation_list.add(BaiDuTranslateAPI.translate(String.valueOf(sourceText)));
                        sourceText.delete(0, sourceText.length());
                        sourceText.append(value);
                        Thread.sleep(TranslateData.sleep_time);
                        after_translation_list.add(BaiDuTranslateAPI.translate(String.valueOf(sourceText)));
                    } else if (sourceText.length() + value.length() + "\n\n".length() <= TranslateData.translate_char_length_limit && i + 1 == value_list.size()) {
                        // 没有超过上限，且index是最后一个
                        System.out.println("没有超过上限，且index是最后一个");
                        sourceText.append(value);
                        after_translation_list.add(BaiDuTranslateAPI.translate(String.valueOf(sourceText)));
                    }
                }

            } else if (TranslateData.translate_api == 2) { //如果是腾讯翻译君api
                for (int i = 0; i < value_list.size(); i++) {
                    System.out.println("-------------------第" + i + "次循环-------------------");
                    String value = value_list.get(i);
                    if (sourceText.length() + value.length() <= TranslateData.translate_char_length_limit && i + 1 != value_list.size()) {
                        // 拼接没有超过上限，且不是最后一个
                        System.out.println("拼接没有超过上限，且不是最后一个");
                        sourceText.append(value).append("\n");

                    } else if (sourceText.length() + value.length() > TranslateData.translate_char_length_limit && i + 1 != value_list.size()) {
                        // 拼接超过上限，且不是最后一个
                        System.out.println("拼接超过上限，且不是最后一个");
                        after_translation_list.add(TencentTranslateAPI.Translate(String.valueOf(sourceText), special_symbol_sub).getTargetText());
                        sourceText.delete(0, sourceText.length());
                        sourceText.append(value).append("\n");
                        Thread.sleep(TranslateData.sleep_time);

                    } else if (sourceText.length() + value.length() > TranslateData.translate_char_length_limit && i + 1 == value_list.size()) {
                        // 超过上限，且index是最后一个
                        System.out.println("拼接超过上限，且是最后一个");
                        after_translation_list.add(TencentTranslateAPI.Translate(String.valueOf(sourceText), special_symbol_sub).getTargetText());
                        sourceText.delete(0, sourceText.length());
                        sourceText.append(value);
                        Thread.sleep(TranslateData.sleep_time);
                        after_translation_list.add(TencentTranslateAPI.Translate(String.valueOf(sourceText), special_symbol_sub).getTargetText());
                    } else if (sourceText.length() + value.length() <= TranslateData.translate_char_length_limit && i + 1 == value_list.size()) {
                        // 没有超过上限，且index是最后一个
                        System.out.println("没有超过上限，且index是最后一个");
                        sourceText.append(value);
                        after_translation_list.add(TencentTranslateAPI.Translate(String.valueOf(sourceText), special_symbol_sub).getTargetText());
                    }
                }
            }

            // 得到翻译后文本
            String translatedText = after_translation_list.stream().map(String::valueOf).collect(Collectors.joining());
            String[] split = translatedText.split("\n");
            System.out.println("翻译后的value数量是：" + split.length);
            System.out.println("翻以前key的数量是" + key_list.size());
            if (split.length != key_list.size()) {
                TipsMessage.timing("翻译后的数量和翻译前数量不同，停止翻译", RightBottomPane.label, 3000);
                return;
            }
            // 对翻译后带有不翻译字符进行替换
            for (int i1 = 0; i1 < split.length; i1++) {
                String key = key_list.get(i1);
                String value = split[i1];

                if (value.contains(special_symbol_sub)) {
                    ArrayList<String> strings = final_replace_hash_map.get(i1);
                    for (String string : strings) {
                        value = value.replaceFirst(Matcher.quoteReplacement(special_symbol_sub), Matcher.quoteReplacement(string));
                    }
                }
                hashMap.replace(key, value);
            }


            // 保存翻译后的properties格式的文本
            FileWriter fw = new FileWriter(file1.getParent() + File.separator + "translate_zh_cn.json");
            String s1 = JSON.toJSONString(hashMap, SerializerFeature.PrettyFormat); // 将map变成json字符串，并且格式化
            fw.write(s1);
            fw.close();
            TipsMessage.timing("翻译成功", RightBottomPane.label, 5000);


        } else if (TranslateData.translate_mode == 2) { //如果是增量翻译
            TipsMessage.timing("增量模式未实现", RightBottomPane.label, 3000);
        }

    }

    public static void regex_mode(File file1, File file2, File file3) {

    }
}
