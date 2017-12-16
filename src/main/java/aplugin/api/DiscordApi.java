package aplugin.api;

import java.io.File;
import java.util.Collection;

/**
 * Created by Aristocrat on 12/16/2017.
 */
public interface DiscordApi {
    /**
     * Sends the provided message as-is to the DiscordApp text channel
     * specified in the configuration.
     *
     * @param message The text message to send. Markdown is acceptable.
     */
    void sendRawMessageToDiscord(String message);

    /**
     * Sends the provided message to the DiscordApp text channel
     * specified in the configuration, prepended by **username:**
     *
     * Use for passing chat messages with an author.
     *
     * @param username The author's username, displayed with the message.
     * @param message The text message to send. Markdown is acceptable.
     */
    void sendMessageToDiscord(String username, String message);

    /**
     * Sends the provided message to the Minecraft server chat,
     * prepended by <username>.
     *
     * Use for passing chat messages with an author.
     *
     * @param username The author's username, displayed with the message.
     * @param message The text message to send. Markdown is acceptable.
     */
    void sendMessageToMinecraft(String username, String message);
}
