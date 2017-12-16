package aplugin.utils.io;

import aplugin.classes.ActionBarMessage;
import aplugin.init.APlugin;
import aplugin.utils.nms.PacketUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public class BukkitUtilsImpl implements BukkitUtils {
    private APlugin plugin;
    private Map<UUID, Long> lastPlayerMessageTimes;

    public BukkitUtilsImpl(APlugin plugin) {
        this.plugin = plugin;
        lastPlayerMessageTimes = new HashMap<>();
    }

    @Override
    public void runAsyncTask(Runnable task, int delayTicks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delayTicks);
    }

    @Override
    public void sendActionBar(Player player, ActionBarMessage message) {
        lastPlayerMessageTimes.put(player.getUniqueId(), System.currentTimeMillis());
        PacketUtils.sendToActionbar(player, message.toString());
    }

    @Override
    public void sendActionBarRaw(Player player, String message) {
        lastPlayerMessageTimes.put(player.getUniqueId(), System.currentTimeMillis());
        PacketUtils.sendToActionbar(player, message);
    }
}
