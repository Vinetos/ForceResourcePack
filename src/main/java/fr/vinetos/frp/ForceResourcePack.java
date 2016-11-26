package fr.vinetos.frp;

import fr.vinetos.frp.commands.FrpCommand;
import fr.vinetos.frp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

/**
 *
 * File <b>ForceResourcePack</b> located on fr.vinetos.frp
 * ForceResourcePack is a part of ForceResourcePack.
 *
 * Copyright (c) 2016 Vinetos <b>https://vinetos.fr</b> and contributors.
 *
 * ForceResourcePack is a free software: See the MIT License
 * Public License for more details (LICENSE.txt file)
 *
 * @author VINETOS
 * Created the 26/12/2015 at 13:04
 */
public class ForceResourcePack extends JavaPlugin {

    private static ConfigUtils configUtils;
    private static ForceResourcePack instance;
    private boolean is1_9OrNewer = MINECRAFT_VERSION.getFromString(getPackageName()).isNewerThan(MINECRAFT_VERSION.v1_7);

    @Override
    public void onEnable() {
        instance = this;
        if(!is1_9OrNewer){
            System.out.println("[ForceResourcePack] The plugin work only on a 1.8 or newer version.");
            getServer().getPluginManager().disablePlugin(instance);
            return;
        }
        /*Configurations*/
        saveDefaultConfig();
        configUtils = new ConfigUtils(getConfig());
        /*Events*/
        new EventsManager(this).registerEvents();

        /*Commands*/
        final PluginCommand command = getCommand("forceresourcepack");
        command.setAliases(Collections.singletonList("frp"));
        command.setExecutor(new FrpCommand());

        /*If server was reloaded*/
        for(Player player : Bukkit.getOnlinePlayers())
            new ForcePlayer(player);

    }

    public static ConfigUtils getConfigUtils(){
        return configUtils;
    }

    public static ForceResourcePack getInstance(){
        return instance;
    }

    private String getPackageName() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }



    /**
     * Check the version of the Server
     * @author Vinetos
     *
     */
    private enum MINECRAFT_VERSION {

        V1_11(11, "v1_11"),
        v1_10(10, "v1_10"),
        v1_9(9, "v1_9"),
        v1_8(8, "v1_8"),
        v1_7(7, "v1_7"),
        V1_6(6, "v1_6"),
        V1_5(5, "v1_5"),
        UNKNOWN(0, "unknown");

        private String version;
        private int versionId;

        MINECRAFT_VERSION(int versionId, String version) {
            this.versionId = versionId;
            this.version = version;
        }

        public String getVersion() {
            return version;
        }

        public int getVersionId() {
            return versionId;
        }


        public boolean isNewerThan(MINECRAFT_VERSION version) {
            return version == MINECRAFT_VERSION.UNKNOWN || this.versionId > version.getVersionId();
        }

        public static MINECRAFT_VERSION getFromString(String s) {
            for(MINECRAFT_VERSION version : MINECRAFT_VERSION.values())
                if (s.contains(version.getVersion()))
                    return version;
            return MINECRAFT_VERSION.UNKNOWN;
        }


    }
}
