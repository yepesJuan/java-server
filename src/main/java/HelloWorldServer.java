import io.javalin.Javalin;

public class HelloWorldServer {
    public static void main(String[] args) {
         Javalin app = Javalin.create().start(8080);

         app.get("/", ctx -> ctx.result("Hello World"));
         app.get("/hello", ctx -> ctx.result("test"));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            app.stop(); // Stop Javalin when shutting down
            System.out.println("Server stopped");
        }));
    }
}