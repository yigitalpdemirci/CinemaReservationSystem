public class Film {
    private String name;
    private String trailerPath;
    private int duration;

    public Film(String name, String trailerPath, int duration) {
        this.name = name;
        this.trailerPath = trailerPath;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String trailerPath) {
        this.trailerPath = trailerPath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return name;
    }
}
