package io.github.hello09x.devtools.core.config;

import io.github.hello09x.devtools.core.event.ConfigReloadedEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.codehaus.plexus.util.IOUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tanyaofei
 * @since 2024/7/28
 **/
public abstract class PluginConfig {

    public static final String CONFIG_FILE_NAME = "config.yml";
    public static final String CONFIG_TMPL_FILE_NAME = "config.tmpl.yml";

    private final Plugin plugin;

    protected PluginConfig(@NotNull Plugin plugin, boolean reload) {
        this.plugin = plugin;
        if (reload) {
            this.reload0();
        }
    }

    protected PluginConfig(@NotNull Plugin plugin) {
        this(plugin, true);
    }

    public void reload() {
        this.reload0();
        Bukkit.getServer().getPluginManager().callEvent(new ConfigReloadedEvent(this.plugin));
    }

    protected void reload0() {
        var folder = this.plugin.getDataFolder();
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IllegalStateException("Failed to create data folder for plugin: " + plugin.getName());
        }

        var tmplFile = new File(this.plugin.getDataFolder(), "config.tmpl.yml");
        try {
            var jarConfig = plugin.getResource("config.yml");
            if (jarConfig != null) {
                IOUtil.copy(jarConfig, new FileOutputStream(tmplFile));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write config template file: " + tmplFile.getAbsolutePath(), e);
        }

        plugin.reloadConfig();
        this.reload(plugin.getConfig());
    }

    public boolean isFileExists() {
        return new File(this.plugin.getDataFolder(), "config.yml").exists();
    }

    public boolean isFileConfigurationOutOfDate() {
        var version = Optional.ofNullable(plugin.getConfig().getDefaults()).map(def -> def.getString("version")).orElse(null);
        if (version == null) {
            return false;
        }

        var current = plugin.getConfig().getString("version");
        return !Objects.equals(current, version);
    }

    protected abstract void reload(@NotNull FileConfiguration file);

}
