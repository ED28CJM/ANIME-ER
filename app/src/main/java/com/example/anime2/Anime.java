package com.example.anime2;

public class Anime {
    private int mal_id;
    private String title;
    private Images images;
    private String episodes;

    // Getters y Setters
    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    // Clase interna para manejar las imágenes
    public static class Images {
        private Image jpg;

        // Getter y Setter para jpg
        public Image getJpg() {
            return jpg;
        }

        public void setJpg(Image jpg) {
            this.jpg = jpg;
        }

        // Clase interna para manejar la URL de la imagen jpg
        public static class Image {
            private String image_url;

            // Getter y Setter para image_url
            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
        }
    }
}


