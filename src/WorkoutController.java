import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkoutController implements Observer.Subject {
    private List<WorkoutModel> workouts;
    private List<Observer> observers; // Список наблюдателей

    public WorkoutController(List<WorkoutModel> workouts) {
        this.workouts = workouts;
        this.observers = new ArrayList<>();
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
            observer.update(message); // Уведомление каждого подписчика
        }
    }

    public List<WorkoutModel> getAllWorkouts() {
        return workouts;
    }

    public void addWorkout(String name, int duration, String intensity) {
        int newId = workouts.size() + 1;
        workouts.add(new WorkoutModel(newId, name, duration, intensity));
        notifyObservers("Тренировка добавлена: " + name);
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
        });
        notifyObservers("Тренировка обновлена: ID " + workoutId);
    }
}
