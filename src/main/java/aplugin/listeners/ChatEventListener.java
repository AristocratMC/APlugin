package aplugin.listeners;

import aplugin.discord.DiscordMessageSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
//import org.dynmap.DynmapWebChatEvent;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public class ChatEventListener implements Listener {
    private DiscordMessageSender discordMessageSender;

    public ChatEventListener(DiscordMessageSender discordMessageSender) {
        this.discordMessageSender = discordMessageSender;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        discordMessageSender.sendMessageDiscord(e.getMessage(), e.getPlayer().getName());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        discordMessageSender.sendRawMessageDiscord(String.format("*%s has joined the game.*", e.getPlayer().getDisplayName()));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        discordMessageSender.sendRawMessageDiscord(String.format("*%s has left the game.*", e.getPlayer().getDisplayName()));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent e) {
        discordMessageSender.sendRawMessageDiscord(e.getDeathMessage());
    }
}
