public class DeleteWorkoutCommand implements Command {
    private WorkoutController controller;
    private int workoutId;

    public DeleteWorkoutCommand(WorkoutController controller, int workoutId) {
        this.controller = controller;
        this.workoutId = workoutId;
    }

    @Override
    public void execute() {
        controller.deleteWorkout(workoutId);
    }
}
