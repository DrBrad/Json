package unet.json.annotation;

import unet.json.variables.JsonExpose;
import unet.json.variables.JsonExposeMethod;

import java.util.ArrayList;
import java.util.List;

public class Movie2 {

    @JsonExpose
    private String title, description, rating, time, genre, year, portrait, landscape;

    @JsonExpose
    private long coming;

    @JsonExpose
    private boolean boolTest;

    @JsonExpose
    private Object nullTest;

    @JsonExpose(deserialize = false)
    private MongoID id;
    //private Map<String, String> id = new HashMap<>();

    @JsonExpose
    private List<String> suggested = new ArrayList<>();

    //public Map<String, String> getId(){

    public MongoID getId(){
        return id;
    }

    @JsonExposeMethod(key = "id")
    public MongoID getMongoId(){
        id = new MongoID();
        return id;
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
    public List<String> getSuggested(){
        return suggested;
    }


    public static class MongoID {

        @JsonExpose
        private String $oid;

        public String getId(){
            return $oid;
        }
    }
}
