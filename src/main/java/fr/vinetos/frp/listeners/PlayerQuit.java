package fr.vinetos.frp.listeners;

import fr.vinetos.frp.ForcePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The MIT License (MIT)
 * Created on 26/12/2015.
 * Copyright (c) 2016 @author Vinetos
 */
public class PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        ForcePlayer.findByPlayer(e.getPlayer()).remove();
    }

}
