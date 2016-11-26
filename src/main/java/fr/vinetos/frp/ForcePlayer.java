package fr.vinetos.frp;

import fr.vinetos.frp.utils.ConfigUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * The MIT License (MIT)
 * Created on 17/06/2016.
 * Copyright (c) 2016 @author Vinetos
 */
public class ForcePlayer {

    private static Map<Player, ForcePlayer> forcePlayerMap = new HashMap<Player, ForcePlayer>();

    private final Player player;

    public ForcePlayer(Player player) {
        Validate.notNull(player);
        this.player = player;

        forcePlayerMap.put(player, this);
    }

    public static ForcePlayer findByPlayer(Player player) {
        return forcePlayerMap.get(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void remove() {
        forcePlayerMap.remove(player);
    }

    public void sendJoinRequest(final String url) {
        Bukkit.getScheduler().runTaskLater(ForceResourcePack.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(!getPlayer().isValid()) {
                    sendJoinRequest(url);
                    return;
                }
                getPlayer().setResourcePack(url);
                sendRequest();
            }
        }, 20);
    }

    public void sendRequest() {
        Bukkit.getScheduler().runTaskLater(ForceResourcePack.getInstance(), new Runnable() {
            @Override
            public void run() {
                String message = ForceResourcePack.getConfigUtils().getMessage(ConfigUtils.MessagesType.NO_RESPONSE);
                player.sendMessage(message);
                if (ForceResourcePack.getConfigUtils().getBoolean("kick"))
                    player.kickPlayer(message);

            }
        }, 200);//10s
    }

}
