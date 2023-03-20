import java.util.ArrayList;

public class Chat {
    private ArrayList<User> members;
    private ArrayList<Message> messages;
    private User owner;
    private String id;
    private String name;

    public Chat(User admin, String id, String name) {
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.owner = admin;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public String getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }
}
