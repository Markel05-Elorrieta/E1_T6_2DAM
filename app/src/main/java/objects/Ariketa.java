package objects;

public class Ariketa {
    private String name;
    private String description;
    private String workedMuscle;
    private double denbora;
    private String videoUrl;

    public Ariketa() {

    }

    public Ariketa(String name, String description, String workedMuscle, double denbora, String videoUrl) {
        this.name = name;
        this.description = description;
        this.workedMuscle = workedMuscle;
        this.denbora = denbora;
        this.videoUrl = videoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkedMuscle() {
        return workedMuscle;
    }

    public void setWorkedMuscle(String workedMuscle) {
        this.workedMuscle = workedMuscle;
    }

    public double getDenbora() {
        return denbora;
    }

    public void setDenbora(double denbora) {
        this.denbora = denbora;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "Ariketa{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", workedMuscle='" + workedMuscle + '\'' +
                ", denbora=" + denbora +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
