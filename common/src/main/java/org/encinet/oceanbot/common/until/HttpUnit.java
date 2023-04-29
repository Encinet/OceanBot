package org.encinet.oceanbot.common.until;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * 网络类工具
 */
public class HttpUnit {
    public static String get(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                // 执行请求的URL
                .uri(URI.create(url))
                // 指定请求超时的时长
                .timeout(Duration.ofMinutes(1))
                // 指定请求头
                .header("Content-Type", "text/html")
                // 创建GET请求
                .GET()
                .build();
        // HttpResponse.BodyHandlers.ofString()指定将服务器响应转化成字符串
        HttpResponse.BodyHandler<String> bh = HttpResponse.BodyHandlers.ofString();
        // 发送请求，获取服务器响应
        HttpResponse<String> response = client.send(request, bh);
        return response.body();
    }
}
