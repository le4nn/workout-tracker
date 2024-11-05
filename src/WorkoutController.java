import java.util.ArrayList;
import java.util.List;

public class WorkoutController {
    private List<WorkoutModel> workouts = new ArrayList<>();

    public List<WorkoutModel> getAllWorkouts() {
        return workouts;
    }

    public void addWorkout(String name, int duration, String intensity) {
        workouts.add(new WorkoutModel(name, duration, intensity));
    }
}
