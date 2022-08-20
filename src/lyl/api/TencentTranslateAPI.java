package lyl.api;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lyl.data.TranslateData;
import lyl.utils.HttpRequest;
import lyl.utils.Sha256;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// 腾讯云的翻译api
public class TencentTranslateAPI {


    // 方法调用腾讯云的文本翻译，post请求
    // 公共参数由post的请求头设置，非公共参数直接以json形式写入payload；非公共参数需要和传递的json字符串要一样
    public static OutputParams.Response Translate(String text, String noTranslateStr) throws Exception {
        String algorithm = "TC3-HMAC-SHA256";
        String version = "2018-03-21";//腾讯云文本翻译的api版本
        String url = "https://tmt.tencentcloudapi.com";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = sdf.format(new Date(Long.parseLong(timestamp + "000")));
        String signed_headers = "content-type;host"; // 参与签名的头部信息
        String credential_scope = date+"/tmt/tc3_request"; // 凭证范围

        InputParams inputParams = new InputParams();
        inputParams.setProjectId(0);
        inputParams.setSource("en");
        inputParams.setTarget("zh");
        inputParams.setSourceText(text);
        if(!noTranslateStr.equals("")){
            inputParams.setUntranslatedText(noTranslateStr);
        }
        String payload = JSONObject.toJSONString(inputParams);


        // 第一步 获取规范请求
        String canonical_request = "POST" + "\n" +
                "/" + "\n" +
                "" + "\n" +
                "content-type:application/json; charset=utf-8" + "\n" +
                "host:tmt.tencentcloudapi.com\n" + "\n" +
                signed_headers + "\n" +
                Sha256.sha256(payload);
        // 第二步 获取签名
        String StringToSign =
                algorithm + "\n" +
                        timestamp + "\n" +
                        credential_scope + "\n" +
                        Sha256.sha256(canonical_request);
        // 第三步  计算签名
        String service = "tmt";
        byte[] secret_date = Sha256.hmacSha256(("TC3" + TranslateData.secret_key).getBytes(StandardCharsets.UTF_8), date);
        byte[] secret_service = Sha256.hmacSha256(secret_date, service);
        byte[] secret_signing = Sha256.hmacSha256(secret_service, "tc3_request");
        String signature = DatatypeConverter.printHexBinary(Sha256.hmacSha256(secret_signing, StringToSign)).toLowerCase();
        // 第四步 拼接 Authorization
        String authorization =
                algorithm + " " +
                        "Credential=" + TranslateData.secret_id + "/" + credential_scope + ", " +
                        "SignedHeaders=" + signed_headers + ", " +
                        "Signature=" + signature;


        HttpURLConnection connection = HttpRequest.getPostConnection(url);

        connection.setRequestProperty("Authorization",authorization);
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Host","tmt.tencentcloudapi.com");
        connection.setRequestProperty("X-TC-Action","TextTranslate");
        connection.setRequestProperty("X-TC-Version",version);
        connection.setRequestProperty("X-TC-Timestamp", timestamp);
        connection.setRequestProperty("X-TC-Region","ap-guangzhou");
        connection.connect();
        OutputStream outputStream = connection.getOutputStream();
        byte[] b = payload.getBytes(StandardCharsets.UTF_8);// 传输json字符串到腾讯云的api
        outputStream.write(b);


        // 发送出去，并得到响应，接收到字节流
        InputStream inputStream = connection.getInputStream();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = inputStream.read()) != -1) {
            baos.write(i);
        }

        String response_json_text = String.valueOf(baos);

        outputStream.close();
        inputStream.close();
        baos.close();
        OutputParams outputParams = JSONObject.parseObject(response_json_text, OutputParams.class);
        OutputParams.Response response_json_object = JSONObject.parseObject(outputParams.getResponse(), OutputParams.Response.class);
        response_json_object.setSrcText(text);
        return response_json_object;

    }



    // 调用文本翻译接口需要的参数
    private static class InputParams {
        @JSONField(name = "SourceText")
        String SourceText;
        @JSONField(name = "Source")
        String Source;
        @JSONField(name = "Target")
        String Target;
        @JSONField(name = "ProjectId")
        Integer ProjectId;
        @JSONField(name = "UntranslatedText")
        String UntranslatedText;


        public String getSourceText() {
            return SourceText;
        }

        public void setSourceText(String sourceText) {
            SourceText = sourceText;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String source) {
            Source = source;
        }

        public String getTarget() {
            return Target;
        }

        public void setTarget(String target) {
            Target = target;
        }

        public Integer getProjectId() {
            return ProjectId;
        }

        public void setProjectId(Integer projectId) {
            ProjectId = projectId;
        }

        public String getUntranslatedText() {
            return UntranslatedText;
        }

        public void setUntranslatedText(String untranslatedText) {
            UntranslatedText = untranslatedText;
        }

        @Override
        public String toString() {
            return "InputParams{" +
                    "SourceText='" + SourceText + '\'' +
                    ", Source='" + Source + '\'' +
                    ", Target='" + Target + '\'' +
                    ", ProjectId=" + ProjectId +
                    ", UntranslatedText='" + UntranslatedText + '\'' +
                    '}';
        }
    }

    public static class OutputParams {

        private String Response;
        public String getResponse() {
            return Response;
        }

        public void setResponse(String response) {
            Response = response;
        }

        @Override
        public String toString() {
            return "OutputParams{" +
                    "Response='" + Response + '\'' +
                    '}';
        }
        public static class Response {
            private String SrcText;// 翻以前的文本
            private String TargetText; //翻译后的文本
            private String Source; //源语言
            private String Target; //目标语言
            private String RequestId; //请求id

            public String getSrcText() {
                return SrcText;
            }

            public void setSrcText(String srcText) {
                SrcText = srcText;
            }

            public String getTargetText() {
                return TargetText;
            }

            public void setTargetText(String targetText) {
                TargetText = targetText;
            }

            public String getSource() {
                return Source;
            }

            public void setSource(String source) {
                Source = source;
            }

            public String getTarget() {
                return Target;
            }

            public void setTarget(String target) {
                Target = target;
            }

            public String getRequestId() {
                return RequestId;
            }

            public void setRequestId(String requestId) {
                RequestId = requestId;
            }

            @Override
            public String toString() {
                return "Response{" +
                        "SrcText='" + SrcText + '\'' +
                        ", TargetText='" + TargetText + '\'' +
                        ", Source='" + Source + '\'' +
                        ", Target='" + Target + '\'' +
                        ", RequestId='" + RequestId + '\'' +
                        '}';
            }
        }


    }



}
