import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkoutController implements Observer.Subject {
    private List<WorkoutModel> workouts;
    private List<Observer> observers;
    private ProgressTracker progressTracker;

    public WorkoutController(List<WorkoutModel> workouts, ProgressTracker progressTracker) {
        this.workouts = workouts;
        this.observers = new ArrayList<>();
        this.progressTracker = progressTracker;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public List<WorkoutModel> getAllWorkouts() {
        return workouts;
    }

    public void addWorkout(String name, int duration, String intensity) {
        int newId = workouts.size() + 1;
        workouts.add(new WorkoutModel(newId, name, duration, intensity));
        int calories = calculateCalories(duration, intensity);
        progressTracker.addWorkoutProgress(duration, calories);
        notifyObservers("Тренировка добавлена: " + name);
    }

    private int calculateCalories(int duration, String intensity) {
        int caloriesPerMinute;
        switch (intensity.toLowerCase()) {
            case "high":
                caloriesPerMinute = 12;
                break;
            case "medium":
                caloriesPerMinute = 8;
                break;
            case "low":
                caloriesPerMinute = 5;
                break;
            default:
                caloriesPerMinute = 8;
                break;
        }
        return caloriesPerMinute * duration;
    }

    public void deleteWorkout(int workoutId) {
        workouts.removeIf(workout -> workout.getId() == workoutId);
        notifyObservers("Тренировка удалена: ID " + workoutId);
    }

    public void updateWorkout(int workoutId, String newName, int newDuration, String newIntensity) {
        Optional<WorkoutModel> workoutOpt = workouts.stream()
                .filter(workout -> workout.getId() == workoutId)
                .findFirst();

        workoutOpt.ifPresent(workout -> {
            workout.setName(newName);
            workout.setDuration(newDuration);
            workout.setIntensity(newIntensity);
            int calories = calculateCalories(newDuration, newIntensity);
            progressTracker.addWorkoutProgress(newDuration, calories);
        });
        notifyObservers("Тренировка обновлена: ID " + workoutId);
    }

    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }
}
