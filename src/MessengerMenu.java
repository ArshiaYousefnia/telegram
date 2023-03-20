import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessengerMenu {
    private Chat chat;
    private User currentUser;

    private String showAllChannels() {
        String output = "All channels:\n";
        int index = 0;

        for (Channel channel : Messenger.getChannels()) {
            index++;
            output += index + ". " + channel.getName() + ", id: " + channel.getId() + ", members: " + channel.getMembers().size() + "\n";
        }

        return output;
    }

    private String createChannel(Matcher matcher) {
        matcher.matches();

        if (Pattern.compile("\\W").matcher(matcher.group("name")).find())
            return "Channel name's format is invalid!";
        if (Messenger.getChannelById(matcher.group("id")) != null)
            return "A channel with this id already exists!";

        Channel newChannel = new Channel(Messenger.getCurrentUser(), matcher.group("id"), matcher.group("name"));
        Messenger.addChannel(newChannel);
        Messenger.getCurrentUser().addChannel(newChannel);
        return "Channel " + matcher.group("name") + " has been created successfully!";
    }

    private String joinChannel(Matcher matcher) {
        matcher.matches();

        if (Messenger.getChannelById(matcher.group("id")) == null)
            return "No channel with this id exists!";
        if (Messenger.getChannelById(matcher.group("id")).getMembers().contains(Messenger.getCurrentUser()))
            return "You're already a member of this channel!";

        Messenger.getChannelById(matcher.group("id")).addMember(Messenger.getCurrentUser());
        Messenger.getCurrentUser().addChannel(Messenger.getChannelById(matcher.group("id")));
        return "You have successfully joined the channel!";
    }

    private String showChats() {
        String output = "",
                type = "";
        int index = Messenger.getCurrentUser().getChats().size() + 1;

        for (Chat chat1 : Messenger.getCurrentUser().getChats()) {
            index--;

            if (chat1 instanceof Channel)
                type = "channel";
            else if (chat1 instanceof Group)
                type = "group";
            else type = "private chat";

            if (type.equals("private chat"))
                output = index + ". " + ((PrivateChat) chat1).getName() + ", id: " + ((PrivateChat) chat1).getId() + ", " + type + "\n" + output;
            else output = index + ". " + chat1.getName() + ", id: " + chat1.getId() + ", " + type + "\n" + output;
        }

        output = "Chats:\n" + output;
        return output;
    }

    private String createGroup(Matcher matcher) {
        matcher.matches();

        if (Pattern.compile("\\W").matcher(matcher.group("name")).find())
            return "Group name's format is invalid!";
        if (Messenger.getGroupById(matcher.group("id")) != null)
            return "A group with this id already exists!";

        Group newGroup = new Group(Messenger.getCurrentUser(), matcher.group("id"), matcher.group("name"));
        Messenger.addGroup(newGroup);
        Messenger.getCurrentUser().addGroup(newGroup);
        return "Group " + matcher.group("name") + " has been created successfully!";
    }

    private String createPrivateChat(Matcher matcher) {
        matcher.matches();

        if (Messenger.getCurrentUser().getPrivateChatById(matcher.group("id")) != null)
            return "You already have a private chat with this user!";
        if (Messenger.getMemberById(matcher.group("id")) == null)
            return "No user with this id exists!";

        PrivateChat privateChat = new PrivateChat(Messenger.getCurrentUser(), matcher.group("id"), Messenger.getMemberById(matcher.group("id")).getName());
        Messenger.getCurrentUser().addPrivateChat(privateChat);
        Messenger.getMemberById(matcher.group("id")).removeChat(privateChat);
        Messenger.getMemberById(matcher.group("id")).addChat(privateChat);
        return "Private chat with " + Messenger.getMemberById(matcher.group("id")).getName() + " has been started successfully!";
    }

    private String enterChat(Matcher matcher) {
        matcher.matches();

        if ((matcher.group("chatType").equals("channel") && Messenger.getCurrentUser().getChannelById(matcher.group("id")) == null) ||
                (matcher.group("chatType").equals("group") && Messenger.getCurrentUser().getGroupById(matcher.group("id")) == null) ||
                (matcher.group("chatType").equals("private chat") && Messenger.getCurrentUser().getPrivateChatById(matcher.group("id")) == null)) {
            return "You have no " + matcher.group("chatType") + " with this id!";
        }

        if (matcher.group("chatType").equals("channel"))
            chat = Messenger.getCurrentUser().getChannelById(matcher.group("id"));
        else if (matcher.group("chatType").equals("group"))
            chat = Messenger.getCurrentUser().getGroupById(matcher.group("id"));
        else chat = Messenger.getCurrentUser().getPrivateChatById(matcher.group("id"));

        return "You have successfully entered the chat!";
    }

    public void run(Scanner scanner) {

        while (true) {
            String input = scanner.nextLine();
            String temporaryStorage = "";

            if (Commands.getMatcher(input, Commands.LOGOUT) != null) {
                System.out.println("Logged out");
                LoginMenu loginMenu = new LoginMenu();
                loginMenu.run(scanner);
                continue;
            }
            if (Commands.getMatcher(input, Commands.SHOWALLCHANNELS) != null) {
                System.out.print(showAllChannels());
                continue;
            }
            if (Commands.getMatcher(input, Commands.CREATECHANNEL) != null) {
                System.out.println(createChannel(Commands.getMatcher(input, Commands.CREATECHANNEL)));
                continue;
            }
            if (Commands.getMatcher(input, Commands.JOINCHANNEL) != null) {
                System.out.println(joinChannel(Commands.getMatcher(input, Commands.JOINCHANNEL)));
                continue;
            }
            if (Commands.getMatcher(input, Commands.CREATEGROUP) != null) {
                System.out.println(createGroup(Commands.getMatcher(input, Commands.CREATEGROUP)));
                continue;
            }
            if (Commands.getMatcher(input, Commands.STARTPRIVATECHAT) != null) {
                System.out.println(createPrivateChat(Commands.getMatcher(input, Commands.STARTPRIVATECHAT)));
                continue;
            }
            if (Commands.getMatcher(input, Commands.SHOWALLCHATS) != null) {
                System.out.print(showChats());
                continue;
            }
            if (Commands.getMatcher(input, Commands.ENTERCHAT) != null) {
                temporaryStorage = enterChat(Commands.getMatcher(input, Commands.ENTERCHAT));
                System.out.println(temporaryStorage);

                if (temporaryStorage.equals("You have successfully entered the chat!")) {
                    ChatMenu chatMenu = new ChatMenu();
                    chatMenu.run(scanner, chat);
                }

                continue;
            }

            System.out.println("Invalid command!");
        }
    }
}
