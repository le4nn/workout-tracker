import java.util.List;
import java.util.Optional;

public class WorkoutController {
    private List<WorkoutModel> workouts;

    public WorkoutController(List<WorkoutModel> workouts) {
        this.workouts = workouts;
    }

    // Метод для получения всех тренировок
    public List<WorkoutModel> getAllWorkouts() {
        return workouts;
    }

    // Метод для добавления новой тренировки
    public void addWorkout(String name, int duration, String intensity) {
        int newId = workouts.size() + 1;  // Пример создания нового ID
        workouts.add(new WorkoutModel(newId, name, duration, intensity));
    }

    // Метод для удаления тренировки по ID
    public void deleteWorkout(int workoutId) {
        workouts.removeIf(workout -> workout.getId() == workoutId);
    }

    // Метод для обновления тренировки по ID
    public void updateWorkout(int workoutId, String newName, int newDuration, String newIntensity) {
        Optional<WorkoutModel> workoutOpt = workouts.stream()
                .filter(workout -> workout.getId() == workoutId)
                .findFirst();

        workoutOpt.ifPresent(workout -> {
            workout.setName(newName);
            workout.setDuration(newDuration);
            workout.setIntensity(newIntensity);
        });
    }
}
