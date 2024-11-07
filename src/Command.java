public interface Command {
    void execute();

    class AddWorkoutCommand implements Command {
        private final WorkoutController controller;
        private final String name;
        private final int duration;
        private final String intensity;

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

    class DeleteWorkoutCommand implements Command {
        private final WorkoutController controller;
        private final int workoutId;

        public DeleteWorkoutCommand(WorkoutController controller, int workoutId) {
            this.controller = controller;
            this.workoutId = workoutId;
        }

        @Override
        public void execute() {
            controller.deleteWorkout(workoutId);
        }
    }

    class UpdateWorkoutCommand implements Command {
        private final WorkoutController controller;
        private final int workoutId;
        private final String newName;
        private final int newDuration;
        private final String newIntensity;

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
}
