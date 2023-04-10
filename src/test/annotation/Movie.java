package test.annotation;

import unet.json.variables.JsonAnnotation;

public class Movie {

    private String title, description, rating, time, genre, year, portrait, landscape;
    private int coming;
    private boolean bool;
    private Object nill;

    @JsonAnnotation(key = "title")
    public String getTitle(){
        return title;
    }

    @JsonAnnotation(key = "title")
    public void setTitle(String title){
        this.title = title;
    }

    @JsonAnnotation(key = "description")
    public String getDescription(){
        return description;
    }

    @JsonAnnotation(key = "description")
    public void setDescription(String description){
        this.description = description;
    }

    @JsonAnnotation(key = "rating")
    public String getRating(){
        return rating;
    }

    @JsonAnnotation(key = "rating")
    public void setRating(String rating){
        this.rating = rating;
    }

    @JsonAnnotation(key = "time")
    public String getTime(){
        return time;
    }

    @JsonAnnotation(key = "time")
    public void setTime(String time){
        this.time = time;
    }

    @JsonAnnotation(key = "genre")
    public String getGenre(){
        return genre;
    }

    @JsonAnnotation(key = "genre")
    public void setGenre(String genre){
        this.genre = genre;
    }

    @JsonAnnotation(key = "year")
    public String getYear(){
        return year;
    }

    @JsonAnnotation(key = "year")
    public void setYear(String year){
        this.year = year;
    }

    @JsonAnnotation(key = "portrait")
    public String getPortrait(){
        return portrait;
    }

    @JsonAnnotation(key = "portrait")
    public void setPortrait(String portrait){
        this.portrait = portrait;
    }

    @JsonAnnotation(key = "landscape")
    public String getLandscape(){
        return landscape;
    }

    @JsonAnnotation(key = "landscape")
    public void setLandscape(String landscape){
        this.landscape = landscape;
    }

    @JsonAnnotation(key = "coming")
    public int getComing(){
        return coming;
    }

    @JsonAnnotation(key = "coming")
    public void setComing(int coming){
        this.coming = coming;
    }

    @JsonAnnotation(key = "boolTest")
    public boolean getBoolTest(){
        return bool;
    }

    @JsonAnnotation(key = "boolTest")
    public void setBoolTest(boolean bool){
        this.bool = bool;
    }

    @JsonAnnotation(key = "nullTest")
    public Object getNullTest(){
        return nill;
    }

    @JsonAnnotation(key = "nullTest")
    public void setNullTest(Object nill){
        this.nill = nill;
    }
}
