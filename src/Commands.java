import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    REGISTER("^register i (?<id>\\S+) u (?<username>\\S+) p (?<password>\\S+)$"),
    LOGIN("^login i (?<id>\\S+) p (?<password>\\S+)$"),
    EXIT("^exit$"),
    SHOWALLCHANNELS("^show all channels$"),
    SHOWALLCHATS("^show my chats$"),
    CREATECHANNEL("^create new channel i (?<id>\\S+) n (?<name>\\S+)$"),
    JOINCHANNEL("^join channel i (?<id>\\S+)$"),
    CREATEGROUP("^create new group i (?<id>\\S+) n (?<name>\\S+)$"),
    STARTPRIVATECHAT("^start a new private chat with i (?<id>\\S+)$"),
    LOGOUT("^logout$"),
    ENTERCHAT("^enter (?<chatType>(group)|(channel)|(private chat)) i (?<id>\\S+)$"),
    SENDMESSAGE("^send a message c (?<message>.+)$"),
    SHOWMESSAGES("^show all messages$"),
    ADDMEMBER("^add member i (?<id>\\S+)$"),
    BACK("^back$"),
    SHOWMEMBERS("^show all members$");
    private String regex;

    private Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Commands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}