package com.example.anime2;

public class Episode {

    private int mal_id;
    private String title;
    private String aired;  // Fecha de emisión del episodio
    private String score;  // Puntuación del episodio

    // Getter y Setter para mal_id
    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    // Getter y Setter para title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter y Setter para aired
    public String getAired() {
        return aired;
    }

    public void setAired(String aired) {
        this.aired = aired;
    }

    // Getter y Setter para score
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

