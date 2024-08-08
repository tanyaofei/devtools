package io.github.hello09x.devtools.command;

import com.google.common.base.Throwables;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.executors.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author tanyaofei
 * @since 2024/7/29
 **/
public class Commands {

    public static @NotNull CommandAPICommand command(@NotNull String name) {
        return new CommandAPICommand(name);
    }

    public static @NotNull CommandTree commandTree(@NotNull String name) {
        return new CommandTree(name);
    }

    public static @NotNull IntegerArgument int32(@NotNull String name) {
        return new IntegerArgument(name);
    }

    public static @NotNull IntegerArgument int32(@NotNull String name, int min) {
        return new IntegerArgument(name, min);
    }

    public static @NotNull IntegerArgument int32(@NotNull String name, int min, int max) {
        return new IntegerArgument(name, min, max);
    }

    public static @NotNull LongArgument int64(@NotNull String name) {
        return new LongArgument(name);
    }

    public static @NotNull LongArgument int64(@NotNull String name, long min) {
        return new LongArgument(name, min);
    }

    public static @NotNull LongArgument int64(@NotNull String name, long min, long max) {
        return new LongArgument(name, min, max);
    }

    public static @NotNull FloatArgument float32(@NotNull String name) {
        return new FloatArgument(name);
    }

    public static @NotNull FloatArgument float32(@NotNull String name, float min) {
        return new FloatArgument(name, min);
    }

    public static @NotNull FloatArgument float32(@NotNull String name, float min, float max) {
        return new FloatArgument(name, min, max);
    }

    public static @NotNull DoubleArgument float64(@NotNull String name) {
        return new DoubleArgument(name);
    }

    public static @NotNull DoubleArgument float64(@NotNull String name, double min) {
        return new DoubleArgument(name, min);
    }

    public static @NotNull DoubleArgument float64(@NotNull String name, double min, double max) {
        return new DoubleArgument(name, min, max);
    }

    public static @NotNull MultiLiteralArgument literals(@NotNull String name, @NotNull List<String> literals) {
        return new MultiLiteralArgument(name, literals.toArray(String[]::new));
    }

    public static @NotNull LiteralArgument literal(@NotNull String literal) {
        return new LiteralArgument(literal);
    }

    public static @NotNull LiteralArgument literal(@NotNull String name, @NotNull String literal) {
        return new LiteralArgument(name, literal);
    }

    public static @NotNull CommandArgument cmd(@NotNull String name) {
        return new CommandArgument(name);
    }

    public static <E extends Enum<E>> @NotNull Argument<E> enumerate(@NotNull String name, Class<E> enumClass) {
        return new CustomArgument<>(
                new StringArgument(name),
                info -> {
                    E value;
                    try {
                        value = Enum.valueOf(enumClass, info.input());
                    } catch (IllegalArgumentException e) {
                        throw CustomArgument.CustomArgumentException.fromString("Invalid value: " + info.input());
                    }

                    return value;
                }).replaceSuggestions(ArgumentSuggestions.strings(Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toArray(
                String[]::new)));
    }

    public static @NotNull Argument<File> file(@NotNull File folder, @NotNull String name, @Nullable String suffix) {
        return new CustomArgument<>(
                new GreedyStringArgument(name),
                info -> {
                    var filename = info.input();
                    var file = new File(folder, filename);
                    if (!file.exists()) {
                        throw CustomArgument.CustomArgumentException.fromString("File not found: " + file.getPath());
                    }
                    if (suffix != null && !file.getName().endsWith(suffix)) {
                        throw CustomArgument.CustomArgumentException.fromString("Unsupported file: " + file.getPath());
                    }
                    return file;
                }
        ).replaceSuggestions(ArgumentSuggestions.strings(info -> {
            var arg = info.currentArg();
            return Arrays.stream(Optional.ofNullable(folder.listFiles((dir, file) -> file.contains(arg) && (suffix == null || file.endsWith(
                                                 suffix))))
                                         .orElse(new File[0]))
                         .map(File::getName)
                         .toArray(String[]::new);
        }));
    }

    public static @NotNull LocationArgument location(@NotNull String name) {
        return new LocationArgument(name);
    }

    public static @NotNull RotationArgument rotation(@NotNull String name) {
        return new RotationArgument(name);
    }

    public static @NotNull WorldArgument world(@NotNull String name) {
        return new WorldArgument(name);
    }

    public static @NotNull TextArgument text(@NotNull String name) {
        return new TextArgument(name);
    }

    public static @NotNull PlayerArgument player(@NotNull String name) {
        return new PlayerArgument(name);
    }

    public static @NotNull OfflinePlayerArgument offlinePlayer(@NotNull String name) {
        return new OfflinePlayerArgument(name);
    }

    public static @NotNull UUIDArgument uuid(@NotNull String name) {
        return new UUIDArgument(name);
    }

    public static @NotNull FloatRangeArgument floatRange(@NotNull String nodeName) {
        return new FloatRangeArgument(nodeName);
    }

    public static @NotNull IntegerRangeArgument integerRange(@NotNull String name) {
        return new IntegerRangeArgument(name);
    }

    public static @NotNull String usage(@NotNull String command, @NotNull String description) {
        return "§6" + command + " §7-" + " §f" + description;
    }

    public static @NotNull GreedyStringArgument strings(@NotNull String name) {
        return new GreedyStringArgument(name);
    }

    public static @NotNull ItemStackArgument itemStack(@NotNull String name) {
        return new ItemStackArgument(name);
    }

}
