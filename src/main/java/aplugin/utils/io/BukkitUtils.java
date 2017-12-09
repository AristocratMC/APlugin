package aplugin.utils.io;

import aplugin.init.APlugin;
import org.bukkit.Bukkit;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public abstract class BukkitUtils {
    APlugin plugin;

    public BukkitUtils(APlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void runAsyncTask(Runnable task, int delayTicks);
}
