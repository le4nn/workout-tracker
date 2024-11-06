public class UpdateWorkoutCommand implements Command {
    private WorkoutController controller;
    private int workoutId;
    private String newName;
    private int newDuration;
    private String newIntensity;

    public UpdateWorkoutCommand(WorkoutController controller, int workoutId, String newName, int newDuration, String newIntensity) {
        this.controller = controller;
        this.workoutId = workoutId;
        this.newName = newName;
        this.newDuration = newDuration;
        this.newIntensity = newIntensity;
    }

    @Override
    public void execute() {
        controller.updateWorkout(workoutId, newName, newDuration, newIntensity);
    }
}
