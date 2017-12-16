package aplugin.api;

import aplugin.discord.DiscordMessageSender;

/**
 * Created by Aristocrat on 12/16/2017.
 */
public class DiscordApiImpl implements DiscordApi {
    private DiscordMessageSender discordMessageSender;

    public DiscordApiImpl(DiscordMessageSender discordMessageSender) {
        this.discordMessageSender = discordMessageSender;
    }

    @Override
    public void sendRawMessageToDiscord(String message) {
        discordMessageSender.sendRawMessageDiscord(message);
    }

    @Override
    public void sendMessageToDiscord(String username, String message) {
        discordMessageSender.sendMessageDiscord(message, username);
    }

    @Override
    public void sendMessageToMinecraft(String username, String message) {
        discordMessageSender.sendMessageMinecraft(message, username);
    }
}
