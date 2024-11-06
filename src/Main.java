import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ProgressTracker progressTracker = new ProgressTracker();
        WorkoutController controller = new WorkoutController(new ArrayList<>(), progressTracker);

        Observer.StatsModule statsModule = new Observer.StatsModule(progressTracker);
        controller.registerObserver(statsModule);

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/workouts", new WorkoutHandler(controller));

        server.setExecutor(null);
        server.start();

        System.out.println("Сервер запущен на http://localhost:8081/workouts");
    }
}

