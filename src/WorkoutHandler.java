import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WorkoutHandler implements HttpHandler {
    private final WorkoutFacade workoutFacade;

    public WorkoutHandler(WorkoutFacade workoutFacade) {
        this.workoutFacade = workoutFacade;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        String method = exchange.getRequestMethod().toUpperCase();
        String path = exchange.getRequestURI().getPath();

        System.out.println("Received request: Method = " + method + ", Path = " + path);

        try {
            if ("POST".equals(method) && "/workouts/manage".equals(path)) {
                String query = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("Request body: " + query);
                String[] params = query.split("&");
                String action = params[0].split("=")[1];
                String name = params[1].split("=")[1];
                String responseMessage = "";

                if ("add".equals(action)) {
                    int duration = Integer.parseInt(params[2].split("=")[1]);
                    String intensity = params[3].split("=")[1];
                    workoutFacade.addWorkout(name, duration, intensity);
                    responseMessage = "Тренировка добавлена!";
                } else if ("delete".equals(action)) {
                    workoutFacade.deleteWorkout(name);
                    responseMessage = "Тренировка удалена!";
                } else if ("update".equals(action)) {
                    int duration = Integer.parseInt(params[2].split("=")[1]);
                    String intensity = params[3].split("=")[1];
                    workoutFacade.updateWorkout(name, duration, intensity);
                    responseMessage = "Тренировка обновлена!";
                }

                response = "<p>" + responseMessage + "</p><a href=\"/workouts\">Вернуться</a>";
            } else {
                // Добавляем сводку прогресса в HTML
                response = generateWorkoutListHtml() + "<p>" + workoutFacade.getProgressSummary() + "</p>";
            }

            // Отправка ответа
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "<p>Произошла ошибка на сервере. Пожалуйста, попробуйте позже.</p>";
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(500, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private String generateWorkoutListHtml() {
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

        for (WorkoutModel workout : workoutFacade.getAllWorkouts()) {
            html.append("<li>").append(workout.getName())
                    .append(" - ").append(workout.getDuration()).append(" мин - ")
                    .append(workout.getIntensity()).append("</li>");
        }
        html.append("</ul>");

        html.append("<h2>Управление тренировкой</h2>")
                .append("<form method=\"POST\" action=\"/workouts/manage\">")
                .append("Действие: ")
                .append("<select name=\"action\">")
                .append("<option value=\"add\">Добавить</option>")
                .append("<option value=\"update\">Обновить</option>")
                .append("<option value=\"delete\">Удалить</option>")
                .append("</select><br>")
                .append("Название: <input type=\"text\" name=\"name\"><br>");

        html.append("Длительность: <input type=\"number\" name=\"duration\"><br>")
                .append("Интенсивность: <input type=\"text\" name=\"intensity\"><br>");

        html.append("<input type=\"submit\" value=\"Выполнить\">")
                .append("</form></body></html>");

        return html.toString();
    }
}
