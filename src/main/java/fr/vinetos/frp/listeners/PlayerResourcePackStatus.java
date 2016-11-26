package fr.vinetos.frp.listeners;

import fr.vinetos.frp.ForcePlayer;
import fr.vinetos.frp.ForceResourcePack;
import fr.vinetos.frp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

/**
 * The MIT License (MIT)
 * Created on 26/12/2015.
 * Copyright (c) 2016 @author Vinetos
 *
 */
public class PlayerResourcePackStatus implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChangeStateResourcePack(PlayerResourcePackStatusEvent e){
        final Player p = e.getPlayer();
        final ForcePlayer fp = ForcePlayer.findByPlayer(p);
        final ConfigUtils cu = ForceResourcePack.getConfigUtils();
        PlayerResourcePackStatusEvent.Status status = e.getStatus();
        if(status == PlayerResourcePackStatusEvent.Status.ACCEPTED){
            p.sendMessage(cu.getMessage(ConfigUtils.MessagesType.ACCEPTED));
            return;
        }
        final String reason = "";
        switch (status) {
            case DECLINED: cu.getMessage(ConfigUtils.MessagesType.DECLINED); break;
            case SUCCESSFULLY_LOADED: cu.getMessage(ConfigUtils.MessagesType.SUCCESS); break;// TODO: 17/06/2016 Remove if success 
            case FAILED_DOWNLOAD: cu.getMessage(ConfigUtils.MessagesType.FAILED); break;
            default: break;
        }
        if(ForceResourcePack.getConfigUtils().getBoolean("kick"))
            Bukkit.getScheduler().runTask(ForceResourcePack.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.kickPlayer(reason);
                }
            });
    }

}
