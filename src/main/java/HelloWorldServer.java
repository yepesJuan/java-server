import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldServer {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldServer.class);

    public static void main(String[] args) {
        int port = getPortFromArgs(args);

        try (Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> cors.add(it -> it.anyHost()));
            config.plugins.enableDevLogging();
        })) {
            app.start(port);

            logger.info("Server started on port {}", port);

            app.get("/", HelloWorldServer::handleRoot);
            app.get("/hello", HelloWorldServer::handleHello);

            app.exception(Exception.class, HelloWorldServer::handleException);

            // Keep the server running
            synchronized (HelloWorldServer.class) {
                HelloWorldServer.class.wait();
            }
        } catch (InterruptedException e) {
            logger.error("Server interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private static void handleRoot(Context ctx) {
        ctx.result("Hello World");
    }

    private static void handleHello(Context ctx) {
        ctx.result("test");
    }

    private static void handleException(Exception e, Context ctx) {
        logger.error("Unhandled exception", e);
        ctx.status(500).result("Internal server error");
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.error("Invalid port number, using default 8080", e);
            }
        }
        return 8080;
    }
}
