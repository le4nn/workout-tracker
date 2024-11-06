public class AddWorkoutCommand implements Command {
    private WorkoutController controller;
    private String name;
    private int duration;
    private String intensity;

    public AddWorkoutCommand(WorkoutController controller, String name, int duration, String intensity) {
        this.controller = controller;
        this.name = name;
        this.duration = duration;
        this.intensity = intensity;
    }

    @Override
    public void execute() {
        controller.addWorkout(name, duration, intensity);
    }
}
