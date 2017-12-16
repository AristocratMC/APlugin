package aplugin.api;

import aplugin.classes.ActionBarMessage;
import org.bukkit.entity.Player;

/**
 * Created by Aristocrat on 12/16/2017.
 */
public interface GameApi {
    /**
     * Sends a message to the player's Action Bar.
     *
     * TODO: The message should queued and sent to the player the next second.
     *
     * @param player Player to send the action bar to.
     * @param message Message to send the player.
     */
    void sendActionBar(Player player, ActionBarMessage message);

    /**
     * Sends a raw String to the player's Action Bar immediately.
     *
     * @param player Player to send the action bar to.
     * @param message Message to send the player.
     */
    void sendActionBarRaw(Player player, String message);
}
