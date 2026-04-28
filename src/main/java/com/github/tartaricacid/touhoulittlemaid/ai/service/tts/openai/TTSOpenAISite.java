package com.github.tartaricacid.touhoulittlemaid.ai.service.tts.openai;

import com.github.tartaricacid.touhoulittlemaid.ai.service.SerializableSite;
import com.github.tartaricacid.touhoulittlemaid.ai.service.SupportModelSelect;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSApiType;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSClient;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSSite;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.layout.TTSOpenAIFormLayout;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.layout.TTSSiteFormLayout;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * OpenAI-compatible TTS site using the standard /v1/audio/speech API.
 * Works with LocalAI, Kokoro-FastAPI, and any OpenAI-compatible TTS server.
 */
public class TTSOpenAISite implements TTSSite, SupportModelSelect {
    public static final String API_TYPE = TTSApiType.OPENAI_TTS.getName();

    private final String id;
    private final ResourceLocation icon;
    private final Map<String, String> headers;
    private final Map<String, String> voices;

    private String url;
    private boolean enabled;
    private String secretKey;
    private String model;

    public TTSOpenAISite(String id, ResourceLocation icon, String url, boolean enabled,
                         String secretKey, String model,
                         Map<String, String> headers, Map<String, String> voices) {
        this.id = id;
        this.icon = icon;
        this.url = url;
        this.enabled = enabled;
        this.secretKey = secretKey;
        this.model = model;
        this.headers = headers;
        this.voices = voices;
    }

    @Override
    public String getApiType() {
        return API_TYPE;
    }

    @Override
    public TTSClient client() {
        return new TTSOpenAIClient(TTS_HTTP_CLIENT, this);
    }

    @Override
    public TTSSiteFormLayout formLayout() {
        return new TTSOpenAIFormLayout(this);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public ResourceLocation icon() {
        return icon;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public Map<String, String> models() {
        return voices;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getModel() {
        return model;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public static class Serializer implements SerializableSite<TTSOpenAISite> {
        public static final Codec<TTSOpenAISite> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf(ID).forGetter(TTSOpenAISite::id),
                ResourceLocation.CODEC.fieldOf(ICON).forGetter(TTSOpenAISite::icon),
                Codec.STRING.fieldOf(URL).forGetter(TTSOpenAISite::url),
                Codec.BOOL.fieldOf(ENABLED).forGetter(TTSOpenAISite::enabled),
                Codec.STRING.fieldOf(SECRET_KEY).forGetter(TTSOpenAISite::getSecretKey),
                Codec.STRING.fieldOf("model").forGetter(TTSOpenAISite::getModel),
                Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf(HEADERS).forGetter(TTSOpenAISite::headers),
                Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("voices").forGetter(TTSOpenAISite::models)
        ).apply(instance, TTSOpenAISite::new));

        @Override
        public TTSOpenAISite defaultSite() {
            return new TTSOpenAISite(
                    API_TYPE,
                    SerializableSite.defaultIcon(API_TYPE),
                    StringUtils.EMPTY,
                    false,
                    StringUtils.EMPTY,
                    StringUtils.EMPTY,
                    Map.of(),
                    Map.of()
            );
        }

        @Override
        public Codec<TTSOpenAISite> codec() {
            return CODEC;
        }
    }
}
