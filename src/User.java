import java.util.ArrayList;

public class User {
    private ArrayList<Chat> chats;
    private String id;
    private String name;
    private String password;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.chats = new ArrayList<>();
    }

    public Group getGroupById(String id) {
        for (Chat chat : chats) {
            if (chat instanceof Group &&
                    chat.getId().equals(id))
                return (Group) chat;
        }

        return null;
    }

    public Channel getChannelById(String id) {
        for (Chat chat : chats) {
            if (chat instanceof Channel &&
                    chat.getId().equals(id))
                return (Channel) chat;
        }

        return null;
    }

    public PrivateChat getPrivateChatById(String id) {
        for (Chat chat : chats) {
            if (chat instanceof PrivateChat &&
                    ((PrivateChat) chat).getId().equals(id))
                return (PrivateChat) chat;
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat) {
        chats.add(chat);
    }

    public void removeChat(Chat chat) {
        chats.remove(chat);
    }

    public void addGroup(Group group) {
        chats.add(group);
    }

    public void addChannel(Channel channel) {
        chats.add(channel);
    }

    public void addPrivateChat(PrivateChat pv) {
        chats.add(pv);
    }
}