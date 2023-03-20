import java.util.Scanner;
import java.util.regex.Matcher;

public class ChatMenu {
    private Chat chat;
    private User currentUser;

    private String sendMessage(Matcher matcher) {
        matcher.matches();

        if (chat instanceof Channel
                && !chat.getOwner().equals(Messenger.getCurrentUser())) {
            return "You don't have access to send a message!";
        }

        chat.addMessage(new Message(Messenger.getCurrentUser(), matcher.group("message")));

        for (User user : chat.getMembers()) {
            user.removeChat(chat);
            user.addChat(chat);
        }

        return "Message has been sent successfully!";
    }

    private String showMessages() {
        String output = "Messages:\n";

        for (Message message : chat.getMessages()) {
            output += message.getOwner().getName() + "(" + message.getOwner().getId() + "): \"" + message.getContent() + "\"\n";
        }

        return output;
    }

    private String showMembers() {
        if (chat instanceof PrivateChat) {
            return "Invalid command!\n";
        }

        String output = "Members:\n";

        for (User user : chat.getMembers()) {
            output += "name: " + user.getName() + ", id: " + user.getId();

            if (chat.getOwner() == user) {
                output += " *owner";
            }

            output += "\n";
        }

        return output;
    }

    private String addMember(Matcher matcher) {
        matcher.matches();

        if (chat instanceof PrivateChat)
            return "Invalid command!";
        if (!chat.getOwner().equals(Messenger.getCurrentUser()))
            return "You don't have access to add a member!";
        if (Messenger.getMemberById(matcher.group("id")) == null)
            return "No user with this id exists!";
        if (chat.getMembers().contains(Messenger.getMemberById(matcher.group("id"))))
            return "This user is already in the chat!";

        chat.addMember(Messenger.getMemberById(matcher.group("id")));
        Messenger.getMemberById(matcher.group("id")).addChat(chat);

        if (chat instanceof Group) {
            chat.addMessage(new Message(Messenger.getCurrentUser(), Messenger.getMemberById(matcher.group("id")).getName() + " has been added to the group!"));

            for (User user : chat.getMembers()) {
                user.removeChat(chat);
                user.addChat(chat);
            }
        }

        return "User has been added successfully!";
    }

    public void run(Scanner scanner, Chat chat) {
        this.chat = chat;

        while (true) {
            String input = scanner.nextLine();

            if (Commands.getMatcher(input, Commands.SENDMESSAGE) != null) {
                System.out.println(sendMessage(Commands.getMatcher(input, Commands.SENDMESSAGE)));
                continue;
            }
            if (Commands.getMatcher(input, Commands.SHOWMESSAGES) != null) {
                System.out.print(showMessages());
                continue;
            }
            if (Commands.getMatcher(input, Commands.ADDMEMBER) != null) {
                System.out.println(addMember(Commands.getMatcher(input, Commands.ADDMEMBER)));
                continue;
            }
            if (Commands.getMatcher(input, Commands.SHOWMEMBERS) != null) {
                System.out.print(showMembers());
                continue;
            }
            if (Commands.getMatcher(input, Commands.BACK) != null) {
                MessengerMenu messengerMenu = new MessengerMenu();
                messengerMenu.run(scanner);
                continue;
            }

            System.out.println("Invalid command!");
        }
    }
}