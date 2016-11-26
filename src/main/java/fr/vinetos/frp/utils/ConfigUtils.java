package fr.vinetos.frp.utils;

import fr.vinetos.frp.ForceResourcePack;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * The MIT License (MIT)
 * Created on 26/12/2015.
 * Copyright (c) 2016 @author Vinetos
 */
public class ConfigUtils {

    private FileConfiguration config;
    private final File file;

    public ConfigUtils(FileConfiguration config) {
        this.config = config;
        this.file = new File(ForceResourcePack.getInstance().getDataFolder(), "config.yml");
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    private boolean isValid(String path) {
        return config.get(path) != null;
    }

    public boolean getBoolean(String name) {
        return isValid(name) && config.getBoolean(name);
    }

    public String getMessage(MessagesType message) {
        if (isValid("messages." + message.getName())) {
            return config.getString("messages." + message.getName()).replaceAll("&", "§");
        }
        return null;
    }

    public String getPack(String name) {
        if (isValid("packs." + name)) {
            return config.getString("packs." + name);
        }
        return null;
    }

    public String getJoinPack() {
        if (isValid("join-pack") && isValid("packs." + config.getString("join-pack"))) {
            return config.getString("packs." + config.getString("join-pack"));
        }
        return null;
    }

    public String getWorldResourcePack(String world) {
        if (isValid("worlds." + world) && isValid("packs." + config.getString("worlds." + world))) {
            return config.getString("packs." + config.getString("worlds." + world));
        }
        return null;
    }

    public String get(String key) {
        if (!isValid(key))
            return "§c" + key + " is not a valid entry !";
        return String.valueOf(config.get(key));
    }

    public String set(String key, String value) {
        if (!isValid(key))
            return "§c" + key + " is not a valid entry !";
        config.set(key, value);
        try {
            config.save(file);
        } catch (IOException e) {
            return "§cCannot save config !";
        }
        return "New value: " + String.valueOf(config.get(key));
    }

    public boolean canBypass() {
        try {
            if (!isValid("bypass")) {
                config.set("bypass", true);
                config.save(file);
                return true;
            }
            return getBoolean("bypass");
        } catch (IOException ignored) {
        }

        return true;
    }

    public enum MessagesType {

        ACCEPTED("accepted"),
        DECLINED("declined"),
        FAILED("failed"),
        NO_RESPONSE("no-response"),
        SUCCESS("success");

        private String name;

        MessagesType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

}

