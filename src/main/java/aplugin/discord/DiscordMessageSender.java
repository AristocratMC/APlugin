package aplugin.discord;

import java.io.File;
import java.util.Collection;

/**
 * Created by Aristocrat on 12/9/2017.
 */
public interface DiscordMessageSender {
    // TODO Add Javadocs

    public void sendMessageDiscord(String msg, String name);
    public void sendMessageDiscord(String msg, String name, File file);
    public void sendPM(String msg, String username);
    public void sendRawMessageDiscord(String msg);
    public void sendRawMessageDiscord(String msg, File file);
    public void sendBufferedRawMessageDiscord(Collection<String> msgs);
    public void sendUnformattedMessageDiscord(String msg);

    @Deprecated
    public void sendItalicizedRawMessageDiscord(String msg);

    public void sendMessageMinecraft(String msg, String name);
    public void stopAPI();
    public void postFile(File file);
    public void sendPMOnline(String msg, String username);
    public String getStreamUrl();
    public void setStreamUrl(String streamUrl);
    public String getStreamUser();
    public void setStreamUser(String streamUser);
    public boolean setPlaying(String nowPlaying);
    public boolean setStreaming(String nowPlaying, String streamUrl);
    public String getUserMention(String name);

    public boolean isBotChannel(String id);
}
