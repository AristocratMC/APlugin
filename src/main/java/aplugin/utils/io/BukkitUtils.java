package aplugin.utils.io;

import aplugin.classes.ActionBarMessage;
import aplugin.init.APlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public interface BukkitUtils {
    /**
     * Schedules a task asynchronously using Bukkit functionality.
     * @param task Runnable task to schedule.
     * @param delayTicks Number of ticks before task is executed.
     */
    void runAsyncTask(Runnable task, int delayTicks);

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
