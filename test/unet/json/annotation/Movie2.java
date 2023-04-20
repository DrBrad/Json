package unet.json.annotation;

import unet.json.variables.JsonExpose;
import unet.json.variables.JsonExposeMethod;

public class Movie2 {

    @JsonExpose
    private String title, description, rating, time, genre, year, portrait, landscape;

    @JsonExpose
    private long coming;

    @JsonExpose
    private boolean boolTest;

    @JsonExpose
    private Object nullTest;

    //@JsonExpose
    private MongoID id;
    //private Map<String, String> id = new HashMap<>();

    //public Map<String, String> getId(){

    public MongoID getId(){
        return id;
    }

    @JsonExposeMethod(key = "id")
    public void setId(MongoID id){
        this.id = id;
    }

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
/*
    public Object getNullTest(){
        return nullTest;
    }
*/
    public static class MongoID {

        @JsonExpose
        private String $oid;

        public String getId(){
            return $oid;
        }
    }
}
