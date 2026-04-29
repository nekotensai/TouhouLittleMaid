package com.github.tartaricacid.touhoulittlemaid.ai.service.tts.openai;

import com.github.tartaricacid.touhoulittlemaid.ai.service.Client;
import com.github.tartaricacid.touhoulittlemaid.ai.manager.entity.TTSCallback;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSClient;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSConfig;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class TTSOpenAIClient implements TTSClient {
    private static final Duration MAX_TIMEOUT = Duration.ofSeconds(20);

    private final HttpClient httpClient;
    private final TTSOpenAISite site;

    public TTSOpenAIClient(HttpClient httpClient, TTSOpenAISite site) {
        this.httpClient = httpClient;
        this.site = site;
    }

    @Override
    public void play(String message, TTSConfig config, TTSCallback callback) {
        URI url = URI.create(Client.normalizeUrl(this.site.url()));
        String voice = config.model();

        TTSOpenAIRequest request = TTSOpenAIRequest.create()
                .setInput(message)
                .setModel(this.site.ttsModel())
                .setVoice(voice);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.toString())
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(request)))
                .timeout(MAX_TIMEOUT).uri(url);

        if (StringUtils.isNotBlank(this.site.secretKey())) {
            builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.site.secretKey());
        }

        this.site.headers().forEach(builder::header);
        HttpRequest httpRequest = builder.build();

        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray())
                .whenComplete((response, throwable) ->
                        handleResponse(callback, response, throwable, httpRequest));
    }
}
