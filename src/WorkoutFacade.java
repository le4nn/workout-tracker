import java.util.List;

public class WorkoutFacade {
    private final WorkoutController workoutController;
    private final DataAdapter dataAdapter;

    public WorkoutFacade(WorkoutController workoutController, DataAdapter dataAdapter) {
        this.workoutController = workoutController;
        this.dataAdapter = dataAdapter;
    }

    public void addWorkout(String name, int duration, String intensity) {
        Command addWorkoutCommand = new Command.AddWorkoutCommand(workoutController, name, duration, intensity);
        addWorkoutCommand.execute();
        dataAdapter.saveData("Добавлена тренировка: " + name + ", " + duration + " мин, интенсивность: " + intensity);
    }

    public void deleteWorkout(String name) {
        int workoutId = findWorkoutIdByName(name);
        if (workoutId != -1) {
            Command deleteWorkoutCommand = new Command.DeleteWorkoutCommand(workoutController, workoutId);
            deleteWorkoutCommand.execute();
            dataAdapter.saveData("Удалена тренировка: " + name);
        } else {
            System.out.println("Тренировка не найдена: " + name);
        }
    }

    public void updateWorkout(String name, int newDuration, String newIntensity) {
        int workoutId = findWorkoutIdByName(name);
        if (workoutId != -1) {
            Command updateWorkoutCommand = new Command.UpdateWorkoutCommand(workoutController, workoutId, name, newDuration, newIntensity);
            updateWorkoutCommand.execute();
            dataAdapter.saveData("Обновлена тренировка: " + name + ", " + newDuration + " мин, интенсивность: " + newIntensity);
        } else {
            System.out.println("Тренировка не найдена: " + name);
        }
    }

    public List<WorkoutModel> getAllWorkouts() {
        return workoutController.getAllWorkouts();
    }

    public String getProgressSummary() {
        return workoutController.getProgressTracker().getProgressSummary();
    }

    private int findWorkoutIdByName(String name) {
        return workoutController.getAllWorkouts().stream()
                .filter(workout -> workout.getName().equals(name))
                .map(WorkoutModel::getId)
                .findFirst()
                .orElse(-1);
    }
}
