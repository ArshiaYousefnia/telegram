import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    private String login(Matcher matcher) {
        matcher.matches();

        if (Messenger.getMemberById(matcher.group("id")) == null)
            return "No user with this id exists!";
        if (!Messenger.getMemberById(matcher.group("id")).getPassword().equals(matcher.group("password")))
            return "Incorrect password!";

        Messenger.setCurrentUser(Messenger.getMemberById(matcher.group("id")));
        return "User successfully logged in!";
    }

    private String register(Matcher matcher) {
        matcher.matches();

        if (Pattern.compile("\\W").matcher(matcher.group("username")).find())
            return "Username's format is invalid!";
        if (matcher.group("password").length() > 32 ||
                matcher.group("password").length() < 8 ||
                !Pattern.compile("[0-9]").matcher(matcher.group("password")).find() ||
                !Pattern.compile("[a-z]").matcher(matcher.group("password")).find() ||
                !Pattern.compile("[A-Z]").matcher(matcher.group("password")).find() ||
                !Pattern.compile("[*.!@$%^&(){}\\[\\]:;<>,?/~_+\\-=|]").matcher(matcher.group("password")).find())
            return "Password is weak!";
        if (Messenger.getMemberById(matcher.group("id")) != null)
            return "A user with this ID already exists!";

        Messenger.addUser(new User(matcher.group("id"), matcher.group("username"), matcher.group("password")));
        return "User has been created successfully!";
    }

    public void run(Scanner scanner) {
        Commands command = Commands.LOGIN;

        while (true) {
            String input = scanner.nextLine();
            String temporaryStorage;

            if (command.getMatcher(input, Commands.EXIT) != null)
                System.exit(0);
            if (command.getMatcher(input, Commands.REGISTER) != null) {
                System.out.println(register(command.getMatcher(input, Commands.REGISTER)));
                continue;
            }
            if (command.getMatcher(input, Commands.LOGIN) != null) {
                temporaryStorage = login(command.getMatcher(input, Commands.LOGIN));
                System.out.println(temporaryStorage);

                if (temporaryStorage.equals("User successfully logged in!")) {
                    MessengerMenu messengerMenu = new MessengerMenu();
                    messengerMenu.run(scanner);
                }

                continue;
            }

            System.out.println("Invalid command!");
        }
    }
}
