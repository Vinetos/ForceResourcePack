package fr.vinetos.frp.listeners;

import fr.vinetos.frp.ForcePlayer;
import fr.vinetos.frp.ForceResourcePack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * The MIT License (MIT)
 * Created on 26/12/2015.
 * Copyright (c) 2016 @author Vinetos
 */
public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        ForcePlayer fp = new ForcePlayer(p);
        if (!ForceResourcePack.getConfigUtils().getBoolean("join-enabled")) return;
        if (p.hasPermission("frp.bypass") && ForceResourcePack.getConfigUtils().canBypass()) {
            p.sendMessage("§cYou have bypass the request !");
            return;
        }
        /*Send the resource pack*/
        fp.sendJoinRequest(ForceResourcePack.getConfigUtils().getJoinPack());
    }

}
