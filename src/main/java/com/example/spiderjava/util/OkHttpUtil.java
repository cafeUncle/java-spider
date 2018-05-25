package com.example.spiderjava.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OkHttpUtil {

    public static final Integer GET = 0;
    public static final Integer POST = 1;

    public static Response connect(String url, Map<String, Object> paramsMap, Map<String, String> headers, Integer method) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        if (headers != null) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (GET.equals(method)) {
            if (paramsMap != null) {
                String params = paramsMap.entrySet()
                        .stream()
                        .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("&"));
                url = String.format("%s?%s", url, params);
            }
            builder = builder.url(url);
        } else if (POST.equals(method)) {

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JSONObject.toJSONString(paramsMap));
            builder = builder.url(url).post(requestBody);
        } else {
            throw new UnsupportedOperationException();
        }

        Request request = builder.build();

        // 通过enqueue而不是execute来添加到执行队列, 可以实现异步执行，需要传入Callable接口参数并重写方法
        Response response = okHttpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }

//        String body = response.body().string();

        return response;
    }

}
