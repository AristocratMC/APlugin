package aplugin.discord;

import com.google.common.base.Splitter;
import aplugin.init.APlugin;
import aplugin.utils.io.BukkitUtils;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public class DiscordMessageSenderImpl implements DiscordMessageSender {
    private final String BOT_TOKEN;
    private final String GUILD_ID;
    private final String CHANNEL_ID;
    private final boolean ENABLED;

    private JDA API;
    private APlugin plugin;
    private BukkitUtils utils;

    private long start;

    private String streamUrl = null;
    private String streamUser = null;

    public DiscordMessageSenderImpl(APlugin plugin, ConfigurationSection config) {
        this.plugin = plugin;
        utils = plugin.getUtils();

        ENABLED = config.getBoolean("enabled");
        BOT_TOKEN = config.getString("token");
        GUILD_ID = config.getString("guild-id");
        CHANNEL_ID = config.getString("channel-id");

        plugin.getLogger().info("Discord integration is " + (ENABLED ? "enabled" : "disabled") + ".");
        plugin.getLogger().info("Bot token is " + BOT_TOKEN);
        plugin.getLogger().info("Channel ID is " + CHANNEL_ID);
        plugin.getLogger().info("Guild ID is " + GUILD_ID);

        start = System.currentTimeMillis();

        try {
            API = new JDABuilder(AccountType.BOT)
                    .setToken(BOT_TOKEN)
                    .addEventListener(new DiscordChatListener(this))
                    .buildAsync();
            plugin.getLogger().info("Discord module loading took " + (System.currentTimeMillis() - start) + " ms.");
        } catch (Exception e) {
            plugin.getLogger().info("Failed to create JDA instance and connect to server!");
            e.printStackTrace();
        }
    }

    public void sendMessageDiscord(String msg, String name) {
        sendMessageDiscord(msg, name, null);
    }

    public void sendMessageDiscord(String msg, String name, File file) {
        if (API != null) {
            try {
                final boolean containsPing = msg.contains("sigonasr2");

                if (msg.contains("@")) {
                    for (Member channelMember : API.getTextChannelById(CHANNEL_ID).getMembers()) {
                        String match = "@" + channelMember.getEffectiveName();
                        if (msg.contains(match)) {
                            msg = msg.replaceAll(match, channelMember.getAsMention());
                        }
                    }
                }

                final String finalMsg = "**%user%** %message%".replaceAll("%user%", name).replaceAll("%message%", formatMoney(msg)).replaceAll("§.", "");

                if (containsPing) {
                    sendPM(finalMsg, "sigonasr2");
                }
//				Utils.runAsyncTask(() -> API.getTextChannelById(BOTTEXTCHANNEL).sendMessage(finalMsg), 1);
//				plugin.getLogger().info(name + " - " + msg);

                if (file != null) {
                    API.getTextChannelById(CHANNEL_ID).sendFile(file, new MessageBuilder().append(finalMsg).build()).queue();
                } else {
                    API.getTextChannelById(CHANNEL_ID).sendMessage(finalMsg).queue();
                }

            } catch (Exception ex) {
                plugin.getLogger().severe(ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }
    }


    public void sendPM(String msg, String username) {
        if (API != null) {
            try {
                for (Member channelMember : API.getTextChannelById(CHANNEL_ID).getMembers()) {
                    if (channelMember.getEffectiveName().contains(username)) {
                        String finalMsg = msg.replaceAll("§.", "");

                        int init = 0;
                        do {
                            final int initcopy = init;
                            channelMember.getUser().openPrivateChannel().queue(pchannel ->
                                    pchannel.sendMessage(finalMsg.substring(initcopy, Math.min(initcopy + 1990, finalMsg.length()))).queue());
                            init += 1990;
                        } while (init < finalMsg.length());
                    }
                }
            } catch (Exception ex) {
                plugin.getLogger().severe(ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }
    }

    public void sendBufferedPM(User user, Collection<String> msgs) {
        if (API != null) {
            try {
                List<StringBuilder> stringBuilders = new ArrayList<StringBuilder>();
                StringBuilder sbCurr = new StringBuilder("");
                stringBuilders.add(sbCurr);
                for (String msg : msgs) {
                    if (sbCurr.length() + msg.length() < 1990) {
                        sbCurr.append('\n');
                        sbCurr.append(msg);
                    } else {
                        sbCurr = new StringBuilder("");
                        stringBuilders.add(sbCurr);
                    }
                }

//				int delay = 0;
//				for (StringBuilder sb : stringBuilders) {
//					sendDelayedPM(user, sb.toString(), delay);
//					delay += 20;
//				}

                for (StringBuilder sb : stringBuilders) {
                    user.openPrivateChannel().queue(pchannel -> pchannel.sendMessage(sb.toString()).queue());
                }
            } catch (Exception ex) {
                plugin.getLogger().severe(ex.getLocalizedMessage());
                ex.printStackTrace();
            }
            return;
        }
    }

    @Deprecated
    public void sendDelayedPM(User user, String msg, int delay) {
        if (API != null) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                try {
                    user.openPrivateChannel().queue(pchannel -> pchannel.sendMessage(msg).queue());
                } catch (Exception ex) {
                    plugin.getLogger().severe(ex.getLocalizedMessage());
                    ex.printStackTrace();
                }
            }, delay);
        }
    }

    public void sendRawMessageDiscord(String msg) {
        sendRawMessageDiscord(msg, null);
    }

    public void sendRawMessageDiscord(String msg, File file) {
        if (API != null) {
            try {
                Message finalMsg = new MessageBuilder().append("%message%".replaceAll("%message%", formatMoney(msg)).replaceAll("§.", "")).build();
                if (file == null) {
                    API.getTextChannelById(CHANNEL_ID).sendMessage(finalMsg).queue();
                } else {
                    API.getTextChannelById(CHANNEL_ID).sendFile(file, finalMsg).queue();
                }
            } catch (Exception ex) {
                plugin.getLogger().severe(ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }
    }
    
    public void sendBufferedRawMessage(Collection<String> messageBuffer, TextChannel targetChannel) {
        if (API != null) {
            try {
                List<StringBuilder> stringBuilders = new ArrayList<StringBuilder>();
                StringBuilder sbCurr = new StringBuilder();
                stringBuilders.add(sbCurr);

//				StringBuilder sbTemp = new StringBuilder();
//				int msgIndex = 0;
                for (String msg : messageBuffer) {
//					sbTemp.append("Message #" + ++msgIndex + " has length " + msg.length() + '\n');
                    if (msg.length() > 2000) {
                        final Iterable<String> split = Splitter.on(" ").split(msg);

                        for (String str : split) {
                            if (sbCurr.length() + str.length() < 1900) {
                                sbCurr.append(str);
                                sbCurr.append(' ');
                            } else {
                                sbCurr = new StringBuilder(str);
                                sbCurr.append(' ');
                                stringBuilders.add(sbCurr);
                            }
                        }
                    } else if (sbCurr.length() + msg.length() < 1900) {
                        sbCurr.append('\n');
                        sbCurr.append(msg);
                    } else {
                        sbCurr = new StringBuilder(msg);
                        stringBuilders.add(sbCurr);
                    }
                }
//				int delay = 0;
//				int builderIndex = 0;
//				sendPM(sbTemp.toString(), "Aristocrat");
                for (StringBuilder sb : stringBuilders) {
                    targetChannel.sendMessage(sb.toString()).queue();
//					sendPM("Builder #" + ++builderIndex + " has length " + sb.length() + " with delay " + delay, "Aristocrat");
//					sendDelayedRawMessage(sb.toString(), targetChannel, delay);
//					delay += 25;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendBufferedRawMessageDiscord(Collection<String> msgs) {
        sendBufferedRawMessage(msgs, API.getTextChannelById(CHANNEL_ID));
    }

    public void sendUnformattedMessageDiscord(String msg) {
        if (API != null) {
            try {
                API.getTextChannelById(CHANNEL_ID).sendMessage(msg).queue();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Deprecated
    public void sendItalicizedRawMessageDiscord(String msg) {
        if (API != null) {
            try {
                API.getTextChannelById(CHANNEL_ID).sendMessage("*%message%*".replaceAll("%message%", formatMoney(msg)).replaceAll("§.", ""))
                        .queue();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageMinecraft(String msg, String name) {
        if (API != null) {
            plugin.getLogger().info("[#minecraft] " + name + " - " + msg);
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("§f<%usernick%> %message%".replaceAll("%usernick%", name).replaceAll("%message%", formatMoney(msg)));
            }
            return;
        }
    }

    public void stopAPI() {
        if (API != null) {
            API.shutdown();
        }
    }

    private String formatMoney(String input) {
        return input.replace("\\", "\\\\").replace("$", "\\$");
    }

    public void postFile(File file) {
        if (API != null) {
            utils.runAsyncTask(() -> {
                try {
                    API.getTextChannelById(CHANNEL_ID).sendFile(file, null).queue();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }, 1);
        }
    }

    public void sendPMOnline(String msg, String username) {
        if (API != null) {
            try {
                for (Member channelMember : API.getTextChannelById(CHANNEL_ID).getMembers()) {
                    if (channelMember.getEffectiveName().contains(username) &&
                            !channelMember.getOnlineStatus().equals(OnlineStatus.OFFLINE)) {
                        String finalMsg = msg.replaceAll("§.", "");

                        int init = 0;
                        do {
                            final int initcopy = init;
                            channelMember.getUser().openPrivateChannel().queue(pchannel -> pchannel.sendMessage(finalMsg.substring(initcopy, Math.min(initcopy + 1990, finalMsg.length()))).queue());
                            init += 1990;
                        } while (init < finalMsg.length());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getStreamUser() {
        return streamUser;
    }

    public void setStreamUser(String streamUser) {
        this.streamUser = streamUser;
    }

    public void sendBufferedPingingMessage(Collection<String> buffer, TextChannel textChannelById) {
        List<String> newBuffer = new ArrayList<>();
        for (String msg : buffer) {
            String newMsg = msg;
            if (newMsg.contains("@")) {
                for (Member channelMember : textChannelById.getMembers()) {
                    String match = "@" + channelMember.getEffectiveName();
                    if (newMsg.contains(match)) {
                        newMsg = newMsg.replaceAll(match, channelMember.getAsMention());
                    }
                }
            }
            newBuffer.add(newMsg);
        }
        sendBufferedRawMessage(newBuffer, textChannelById);
    }

    public boolean setPlaying(String nowPlaying) {
        if (API != null) {
            if (getStreamUrl() == null) {
                utils.runAsyncTask(() -> API.getPresence().setGame(Game.of(Game.GameType.DEFAULT, nowPlaying)), 1);
            } else {
                utils.runAsyncTask(() -> API.getPresence().setGame(Game.of(Game.GameType.STREAMING, getStreamUser() + " | " + nowPlaying, getStreamUrl())), 1);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean setStreaming(String nowPlaying, String streamUrl) {
        if (API != null) {
            utils.runAsyncTask(() -> API.getPresence().setGame(Game.of(Game.GameType.STREAMING, nowPlaying, streamUrl)), 1);
            return true;
        } else {
            return false;
        }
    }

    public String getUserMention(String name) {
        if (API != null) {
            for (Member channelMember : API.getTextChannelById(CHANNEL_ID).getMembers()) {
                if (channelMember.getEffectiveName().equalsIgnoreCase(name)) {
                    return channelMember.getAsMention();
                }
            }
        }
        return name;
    }

    @Override
    public boolean isBotChannel(String id) {
        return CHANNEL_ID.equals(id);
    }
}
