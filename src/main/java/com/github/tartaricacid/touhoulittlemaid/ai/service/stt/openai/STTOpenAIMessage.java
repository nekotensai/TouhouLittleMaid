package com.github.tartaricacid.touhoulittlemaid.ai.service.stt.openai;

import com.google.gson.annotations.SerializedName;

public class STTOpenAIMessage {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }
}
