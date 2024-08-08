package io.github.hello09x.devtools.core.translation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * @author tanyaofei
 * @since 2024/7/27
 **/
public class PluginTranslatorRenderer extends TranslatableComponentRenderer<Locale> {

    private final PluginTranslator translator;

    public PluginTranslatorRenderer(PluginTranslator translator) {
        this.translator = translator;
    }

    @Override
    protected @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale context) {
        return translator.translate(key, context);
    }

    @Override
    protected @NotNull Component renderTranslatable(@NotNull TranslatableComponent component, @NotNull Locale context) {
        return super.renderTranslatable(component, context);
    }
}
