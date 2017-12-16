package aplugin.api;

import aplugin.classes.ActionBarMessage;
import aplugin.utils.io.BukkitUtils;
import org.bukkit.entity.Player;

/**
 * Created by Aristocrat on 12/16/2017.
 */
public class GameApiImpl implements GameApi {
    private BukkitUtils bukkitUtils;

    public GameApiImpl(BukkitUtils bukkitUtils) {
        this.bukkitUtils = bukkitUtils;
    }

    @Override
    public void sendActionBar(Player player, ActionBarMessage message) {
        bukkitUtils.sendActionBar(player, message);
    }

    @Override
    public void sendActionBarRaw(Player player, String message) {
        bukkitUtils.sendActionBarRaw(player, message);
    }
}
