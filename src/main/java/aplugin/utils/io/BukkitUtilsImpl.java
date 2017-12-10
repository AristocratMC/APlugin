package aplugin.utils.io;

import aplugin.init.APlugin;
import org.bukkit.Bukkit;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public class BukkitUtilsImpl extends BukkitUtils {
    public BukkitUtilsImpl(APlugin plugin) {
        super(plugin);
    }

    @Override
    public void runAsyncTask(Runnable task, int delayTicks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delayTicks);
    }
}
