package io.github.hello09x.devtools.core.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author tanyaofei
 * @since 2024/7/28
 **/
public abstract class ConfigUtils {

    public static int getMaxIfZero(int value) {
        if (value == 0) {
            return Integer.MAX_VALUE;
        }
        return value;
    }

    public static <E extends Enum<E>> E getEnum(@Nullable String name, @NotNull Class<E> clz, E defaultValue) {
        if (name == null || name.isEmpty()) {
            return defaultValue;
        }
        return Enum.valueOf(clz, name);
    }

}
