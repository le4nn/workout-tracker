public class WorkoutModel {
    private int id;
    private String name;
    private int duration;
    private String intensity;

    public WorkoutModel(int id, String name, int duration, String intensity) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.intensity = intensity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }
}
