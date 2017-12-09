package aplugin.discord;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public class DiscordChatListenerHelper {
    private final DiscordMessageSender messageSender;

    public DiscordChatListenerHelper(DiscordMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendReceivedMessageToMinecraft(MessageReceivedEvent event) {
        // Ignore messages sent by the bot
        if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;

        // Get displayed Discord handle of message sender and raw unformatted message
        String authorName = event.getMember().getEffectiveName();
        String messageToSend = event.getMessage().getStrippedContent();

        messageSender.sendMessageMinecraft(messageToSend, authorName);
    }
}
