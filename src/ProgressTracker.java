public class ProgressTracker {
    private int totalDuration;
    private int totalCalories;

    public ProgressTracker() {
        this.totalDuration = 0;
        this.totalCalories = 0;
    }

    public void addWorkoutProgress(int duration, int calories) {
        totalDuration += duration;
        totalCalories += calories;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public String getProgressSummary() {
        return String.format("Общее время тренировок: %d минут, Сожженные калории: %d", totalDuration, totalCalories);
    }
}
