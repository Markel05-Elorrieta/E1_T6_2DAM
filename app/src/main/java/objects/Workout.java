package objects;

import java.util.ArrayList;

public class Workout {
    private String id;
    private String izena;
    private int maila;
    private String videoURL;
    private ArrayList<String> ariketakId;

    public Workout() {
    }

    public Workout(String id,String izena, int maila, String videoURL, ArrayList<String> ariketakId) {
        this.izena = izena;
        this.maila = maila;
        this.videoURL = videoURL;
        this.ariketakId = ariketakId;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getMaila() {
        return maila;
    }

    public void setMaila(int maila) {
        this.maila = maila;
    }

    public ArrayList<String> getAriketakId() {
        return ariketakId;
    }

    public void setAriketakId(ArrayList<String> ariketakId) {
        this.ariketakId = ariketakId;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id='" + id + '\'' +
                ", izena='" + izena + '\'' +
                ", maila=" + maila +
                ", videoURL='" + videoURL + '\'' +
                ", ariketakId=" + ariketakId +
                '}';
    }
}
