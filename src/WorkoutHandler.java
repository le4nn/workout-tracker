import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WorkoutHandler implements HttpHandler {
    private WorkoutController controller;

    public WorkoutHandler(WorkoutController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            String query = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String[] params = query.split("&");
            String name = params[0].split("=")[1];
            int duration = Integer.parseInt(params[1].split("=")[1]);
            String intensity = params[2].split("=")[1];
            controller.addWorkout(name, duration, intensity);

            response = "<html><head><title>Тренировки</title>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }"
                    + "h1 { color: #333; }"
                    + "ul { list-style-type: none; padding: 0; }"
                    + "li { background: #fff; margin: 5px 0; padding: 10px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
                    + "form { margin-top: 20px; }"
                    + "input[type='text'], input[type='number'] { padding: 10px; margin: 5px 0; width: calc(100% - 22px); border: 1px solid #ccc; border-radius: 5px; }"
                    + "input[type='submit'] { background-color: #28a745; color: white; border: none; padding: 10px 15px; border-radius: 5px; cursor: pointer; }"
                    + "input[type='submit']:hover { background-color: #218838; }"
                    + "</style></head><body>"
                    + "<p>Тренировка добавлена!</p><a href=\"/workouts\">Вернуться</a></body></html>";
        } else {
            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Список тренировок</title>")
                    .append("<style>")
                    .append("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }")
                    .append("h1 { color: #333; }")
                    .append("ul { list-style-type: none; padding: 0; }")
                    .append("li { background: #fff; margin: 5px 0; padding: 10px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }")
                    .append("form { margin-top: 20px; }")
                    .append("input[type='text'], input[type='number'] { padding: 10px; margin: 5px 0; width: calc(100% - 22px); border: 1px solid #ccc; border-radius: 5px; }")
                    .append("input[type='submit'] { background-color: #28a745; color: white; border: none; padding: 10px 15px; border-radius: 5px; cursor: pointer; }")
                    .append("input[type='submit']:hover { background-color: #218838; }")
                    .append("</style></head><body>")
                    .append("<h1>Список тренировок</h1><ul>");
            for (WorkoutModel workout : controller.getAllWorkouts()) {
                html.append("<li>").append(workout.getName())
                        .append(" - ").append(workout.getDuration()).append(" мин - ")
                        .append(workout.getIntensity()).append("</li>");
            }
            html.append("</ul>");

            html.append("<h2>Добавить тренировку</h2>")
                    .append("<form method=\"POST\" action=\"/workouts\">")
                    .append("Название: <input type=\"text\" name=\"name\"><br>")
                    .append("Длительность: <input type=\"number\" name=\"duration\"><br>")
                    .append("Интенсивность: <input type=\"text\" name=\"intensity\"><br>")
                    .append("<input type=\"submit\" value=\"Добавить\">")
                    .append("</form></body></html>");
            response = html.toString();
        }

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
