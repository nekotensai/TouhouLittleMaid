package com.github.tartaricacid.touhoulittlemaid.ai.service.tts.openai;

import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.Format;
import com.google.gson.annotations.SerializedName;

public class TTSOpenAIRequest {
    @SerializedName("model")
    private String model;

    @SerializedName("input")
    private String input;

    @SerializedName("voice")
    private String voice;

    @SerializedName("response_format")
    private String responseFormat = Format.OPUS.getId();

    private TTSOpenAIRequest() {
    }

    public static TTSOpenAIRequest create() {
        return new TTSOpenAIRequest();
    }

    public TTSOpenAIRequest setModel(String model) {
        this.model = model;
        return this;
    }

    public TTSOpenAIRequest setInput(String input) {
        this.input = input;
        return this;
    }

    public TTSOpenAIRequest setVoice(String voice) {
        this.voice = voice;
        return this;
    }
}
