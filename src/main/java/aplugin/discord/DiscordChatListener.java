package aplugin.discord;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public class DiscordChatListener extends ListenerAdapter {
    private DiscordChatListenerHelper helper;
    private DiscordMessageSender messageSender;

    public DiscordChatListener(DiscordMessageSender messageSender) {
        helper = new DiscordChatListenerHelper(messageSender);
        this.messageSender = messageSender;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Send received messages to Minecraft
        if (event.isFromType(ChannelType.TEXT)) {
            if (messageSender.isBotChannel(event.getTextChannel().getId())) {
                helper.sendReceivedMessageToMinecraft(event);
            }
        }
    }
}
