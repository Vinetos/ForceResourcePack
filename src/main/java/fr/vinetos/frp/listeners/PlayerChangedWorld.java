package fr.vinetos.frp.listeners;

import fr.vinetos.frp.ForcePlayer;
import fr.vinetos.frp.ForceResourcePack;
import fr.vinetos.frp.utils.ConfigUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * The MIT License (MIT)
 * Created on 26/12/2015.
 * Copyright (c) 2016 @author Vinetos
 *
 */
public class PlayerChangedWorld implements Listener {

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        final Player p = e.getPlayer();
        final String world = e.getPlayer().getWorld().getName();
        final ConfigUtils cu = ForceResourcePack.getConfigUtils();
        p.setResourcePack(cu.getWorldResourcePack(world));
        ForcePlayer.findByPlayer(p).sendRequest();
    }

}
