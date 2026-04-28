package com.github.tartaricacid.touhoulittlemaid.ai.service.stt.openai;

import com.github.tartaricacid.touhoulittlemaid.ai.service.SerializableSite;
import com.github.tartaricacid.touhoulittlemaid.ai.service.stt.STTApiType;
import com.github.tartaricacid.touhoulittlemaid.ai.service.stt.STTClient;
import com.github.tartaricacid.touhoulittlemaid.ai.service.stt.STTSite;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.layout.STTOpenAIFormLayout;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.layout.STTSiteFormLayout;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * OpenAI-compatible STT site using the standard /v1/audio/transcriptions (Whisper) API.
 * Works with LocalAI, faster-whisper-server, and any OpenAI-compatible STT server.
 */
public class STTOpenAISite implements STTSite {
    public static final String API_TYPE = STTApiType.OPENAI_STT.getName();

    private final String id;
    private final ResourceLocation icon;

    private boolean enabled;
    private String url;
    private String secretKey;
    private String model;

    public STTOpenAISite(String id, ResourceLocation icon, boolean enabled, String url, String secretKey, String model) {
        this.id = id;
        this.icon = icon;
        this.enabled = enabled;
        this.url = url;
        this.secretKey = secretKey;
        this.model = model;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public boolean enabled() {
        return this.enabled;
    }

    @Override
    public ResourceLocation icon() {
        return this.icon;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public Map<String, String> headers() {
        return Map.of();
    }

    @Override
    public String getApiType() {
        return API_TYPE;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getModel() {
        return model;
    }

    @Override
    public STTClient client() {
        return new STTOpenAIClient(STT_HTTP_CLIENT, this);
    }

    @Override
    public STTSiteFormLayout formLayout() {
        return new STTOpenAIFormLayout(this);
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

    public static class Serializer implements SerializableSite<STTOpenAISite> {
        public static final Codec<STTOpenAISite> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf(ID).forGetter(STTOpenAISite::id),
                ResourceLocation.CODEC.fieldOf(ICON).forGetter(STTOpenAISite::icon),
                Codec.BOOL.fieldOf(ENABLED).forGetter(STTOpenAISite::enabled),
                Codec.STRING.fieldOf(URL).forGetter(STTOpenAISite::url),
                Codec.STRING.fieldOf(SECRET_KEY).forGetter(STTOpenAISite::getSecretKey),
                Codec.STRING.fieldOf("model").forGetter(STTOpenAISite::getModel)
        ).apply(instance, STTOpenAISite::new));

        @Override
        public Codec<STTOpenAISite> codec() {
            return CODEC;
        }

        @Override
        public STTOpenAISite defaultSite() {
            return new STTOpenAISite(
                    API_TYPE,
                    SerializableSite.defaultIcon(API_TYPE),
                    false,
                    StringUtils.EMPTY,
                    StringUtils.EMPTY,
                    "whisper-1"
            );
        }
    }
}
