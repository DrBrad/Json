package test.annotation;

import unet.json.Json;
import unet.json.variables.JsonObject;

public class AnnotationTest {

    public static void main(String[] args)throws Exception {
        System.out.println("-= ANNOTATION TEST =-");

        JsonObject json = new JsonObject();
        //JsonObject k = new JsonObject();
        //k.put("$oid", "63e4624e17f6714b6207e3f0");
        //json.put("id", k);
        //k.put("test", "asd");
        json.put("title", "Earth to Echo");
        json.put("description", "After a construction project begins in their neighborhood, best friends Tuck (Brian \"Astro\" Bradley), Alex (Teo Halm) and Munch (Reese C. Hartwig) begin receiving strange, encoded messages on their cell phones. They immediately inform their parents and the authorities, but when no one takes them seriously, the youths decide to crack the code themselves and trace the messages to their source. The youths' curiosity leads them to a robotic extraterrestrial who desperately needs their help.");
        json.put("rating", "PG");
        json.put("time", "1h 40m");
        json.put("genre", "Adventure");
        json.put("year", "2019");
        json.put("coming", 1680303600);
        json.put("portrait", "2a3bbd82940dad5e4d950c156e0aef090297d638225243ca7295dee6b53290d2");
        json.put("landscape", "9ea45ba00f32dce67e70ed712cac466158c0ce1c3c51a70f828c1eebb4de0230");
        json.put("boolTest", true);
        json.put("nullTest", null);

        Movie m = (Movie) Json.fromJson(Movie.class, json);
        json = Json.toJson(m);
        System.out.println(json);
    }
}
