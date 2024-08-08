import io.javalin.http.Context;

public class UserController {
    private static final UserRepository userRepository = new UserRepository();

    public static void register(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        userRepository.save(user);
        ctx.status(201).result("User registered");
    }

    public static void login(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        User foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            ctx.result("Login successful");
        } else {
            ctx.status(401).result("Invalid credentials");
        }
    }
}
