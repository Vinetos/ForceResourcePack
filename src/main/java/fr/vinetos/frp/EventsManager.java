package fr.vinetos.frp;

import fr.vinetos.frp.listeners.PlayerChangedWorld;
import fr.vinetos.frp.listeners.PlayerJoin;
import fr.vinetos.frp.listeners.PlayerQuit;
import fr.vinetos.frp.listeners.PlayerResourcePackStatus;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 *
 * File <b>EventsManager</b> located on fr.vinetos.frp
 * EventsManager is a part of ForceResourcePack.
 *
 * Copyright (c) 2016 Vinetos <b>https://vinetos.fr</b> and contributors.
 *
 * ForceResourcePack is a free software: See the MIT License
 * Public License for more details (LICENSE.txt file)
 *
 * @author VINETOS
 * Created the 26/12/2015 at 12:51
 */
public class EventsManager {

    private Plugin pl;

    public EventsManager(Plugin pl){
        this.pl = pl;
    }

    public void registerEvents(){
        PluginManager pm = pl.getServer().getPluginManager();

        pm.registerEvents(new PlayerJoin(), pl);
        pm.registerEvents(new PlayerQuit(), pl);
        if(ForceResourcePack.getConfigUtils().getBoolean("multi-worlds"))
            pm.registerEvents(new PlayerChangedWorld(), pl);
        pm.registerEvents(new PlayerResourcePackStatus(), pl);
    }

}
