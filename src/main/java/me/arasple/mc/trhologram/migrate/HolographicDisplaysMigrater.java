package me.arasple.mc.trhologram.migrate;

import com.google.common.collect.Lists;
import io.izzel.taboolib.module.inject.TSchedule;
import io.izzel.taboolib.module.locale.TLocale;
import me.arasple.mc.trhologram.TrHologram;
import me.arasple.mc.trhologram.hologram.HologramManager;
import me.arasple.mc.trhologram.utils.Locations;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Arasple
 * @date 2020/1/30 11:19
 * 迁移你妈啦个逼
 */
public class HolographicDisplaysMigrater {

    private static final double yOffset = 1.5;

    @TSchedule
    public static void migrate() throws IOException {
        File file = new File(TrHologram.getPlugin().getDataFolder().getParentFile(), "HolographicDisplays/database.yml");
        if (file.exists()) {
            YamlConfiguration database = YamlConfiguration.loadConfiguration(file);
            List<String> migrated = Lists.newArrayList();
            database.getKeys(false).forEach(id -> {
                ConfigurationSection hologram = database.getConfigurationSection(id);
                if (hologram.contains("location") && hologram.contains("lines")) {
                    migrated.add(id);
                    List<String> contents = hologram.getStringList("lines");
                    if (contents.isEmpty()) {
                        contents.add("Default hologram. Chnage it with &b/trhd edit " + id);
                    }
                    HologramManager.INSTANCE.createHologram(id, Locations.from(hologram.getString("location")).subtract(0, yOffset, 0), contents.toArray(new String[0]));
                    database.set(id, null);
                }
            });
            if (migrated.size() > 0) {
                TLocale.sendToConsole("MIGRATED", migrated.size());
                database.save(file);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "HolographicDisplays:HD RELOAD");
            }
        }
    }

}
