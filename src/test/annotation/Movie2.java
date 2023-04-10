package test.annotation;

import unet.json.variables.JsonExpose;

public class Movie2 {

    @JsonExpose
    private String title, description, rating, time, genre, year, portrait, landscape;

    @JsonExpose
    private long coming;

    @JsonExpose
    private boolean boolTest;

    @JsonExpose
    private Object nullTest;

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getRating(){
        return rating;
    }

    public String getTime(){
        return time;
    }

    public String getGenre(){
        return genre;
    }

    public String getYear(){
        return year;
    }

    public String getPortrait(){
        return portrait;
    }

    public String getLandscape(){
        return landscape;
    }

    public long getComing(){
        return coming;
    }

    public boolean getBoolTest(){
        return boolTest;
    }

    public Object getNullTest(){
        return nullTest;
    }
}
