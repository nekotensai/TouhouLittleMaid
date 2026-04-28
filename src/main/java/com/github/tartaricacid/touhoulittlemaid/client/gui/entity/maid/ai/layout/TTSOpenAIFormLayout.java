package com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.layout;

import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.TTSSite;
import com.github.tartaricacid.touhoulittlemaid.ai.service.tts.openai.TTSOpenAISite;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.FormField.*;
import static com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.Translations.*;

/**
 * OpenAI TTS: URL + optional Secret Key + model + voice list
 */
public class TTSOpenAIFormLayout extends TTSSiteFormLayout {
    public TTSOpenAIFormLayout(TTSSite sourceSite) {
        super(sourceSite);
    }

    @Override
    public List<FieldDescriptor> getFieldDescriptors() {
        TTSOpenAISite site = (TTSOpenAISite) this.sourceSite;
        return List.of(
                new FieldDescriptor(URL, site.url(), true, false),
                new FieldDescriptor(SECRET_KEY, site.getSecretKey(), true, true),
                new FieldDescriptor(MODEL, site.getModel(), true, false)
        );
    }

    @Override
    public boolean supportsModelRows() {
        return true;
    }

    @Override
    public Map<String, String> getInitialModels() {
        return ((TTSOpenAISite) this.sourceSite).models();
    }

    @Override
    public @Nullable TTSSite buildSite(Function<String, String> fieldValues, Map<String, String> models, Consumer<Component> showStatus) {
        TTSOpenAISite site = (TTSOpenAISite) this.sourceSite;
        String url = StringUtils.trimToEmpty(fieldValues.apply(URL));
        if (StringUtils.isBlank(url)) {
            showStatus.accept(URL_IS_EMPTY);
            return null;
        }
        String model = StringUtils.trimToEmpty(fieldValues.apply(MODEL));
        if (StringUtils.isBlank(model)) {
            showStatus.accept(MODEL_IS_EMPTY);
            return null;
        }
        if (models.isEmpty()) {
            showStatus.accept(VOICE_IS_EMPTY);
            return null;
        }
        return new TTSOpenAISite(site.id(), site.icon(), url, site.enabled(),
                StringUtils.trimToEmpty(fieldValues.apply(SECRET_KEY)), model,
                site.headers(), models);
    }
}
