package lyl.api;

import com.alibaba.fastjson.JSONObject;
import lyl.data.TranslateData;
import lyl.utils.HttpRequest;
import lyl.utils.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class BaiDuTranslateAPI {
    // 翻译一个字符串
    public static BaiDuTransResult.ResultString translate(String q){
        String url= "http://api.fanyi.baidu.com/api/trans/vip/translate";  // https://fanyi-api.baidu.com/api/trans/vip/translate
        String salt = System.currentTimeMillis()-5L+""; // 随机码
        // 拼接字符串，生成md5密钥
        String sign = TranslateData.appid +""+q+salt+TranslateData.security_key;
        sign = MD5.md5(sign);

        // 将q传递给query参数，并将参数URL encode
        String query="";
        try {
            // 将字符串编码化，解码使用URLDecoder.decode()方法
            query = URLEncoder.encode(q, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("q",query);
        map.put("from","en");
        map.put("to","zh");
        map.put("appid", TranslateData.appid);
        map.put("salt", salt);
        map.put("sign", sign);
        String s = HttpRequest.sendGet(url, map);

        JSONObject jsonObject = JSONObject.parseObject(s);
        BaiDuTransResult baiDuTransResult = JSONObject.toJavaObject(jsonObject, BaiDuTransResult.class);

        return baiDuTransResult.getTrans_result();



    }
    public static class BaiDuTransResult {
        private ResultString trans_result;
        private String from;
        private String to;

        public ResultString getTrans_result() {
            return trans_result;
        }

        public void setTrans_result(ResultString trans_result) {
            this.trans_result = trans_result;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        @Override
        public String toString() {
            return "BaiDuTransResult{" +
                    "trans_result=" + trans_result +
                    ", from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    '}';
        }

        public static class ResultString {

            // 翻译后
            private String dst;
            // 原来的
            private String src;

            public String getDst() {
                return dst;
            }

            public void setDst(String dst) {
                this.dst = dst;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            @Override
            public String toString() {
                return "{" +
                        "dst='" + dst + '\'' +
                        ", src='" + src + '\'' +
                        '}';
            }
        }
    }

}
