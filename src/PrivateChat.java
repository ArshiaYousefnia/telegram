public class PrivateChat extends Chat {
    public PrivateChat(User admin, String id, String name) {
        super(admin, id, name);
        addMember(admin);

        if (!Messenger.getMemberById(id).equals(admin))
            addMember(Messenger.getMemberById(id));
    }

    @Override
    public String getName() {
        for (User user : getMembers()) {
            if (!user.equals(Messenger.getCurrentUser())) return user.getName();
        }

        return getMembers().get(0).getName();
    }

    @Override
    public String getId() {
        for (User user : getMembers()) {
            if (!user.equals(Messenger.getCurrentUser())) return user.getId();
        }

        return getMembers().get(0).getId();
    }
}
