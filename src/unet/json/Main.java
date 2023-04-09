package unet.json;

import unet.json.variables.JsonAnnotation;
import unet.json.variables.JsonArray;
import unet.json.variables.JsonObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Main {

    //I/O STREAM FOR JSON
    //NULL PORTION
    //SERIALIZE JSON - PRETTY AND NOT
    //Annotation system
    //REMOVE OBSERVER IF SET OR PUT
    //FINISH SANITIZATION...


    public static void main(String[] args)throws Exception {

        //String t = "{\"type\":0,\"test\":null,\"result\":{\"_id\":{\"$oid\":\"63e4624f17f6714b6207e5df\"},\"title\":\"Tommy Boy\",\"description\":\"After his beloved father (Brian Dennehy) dies, dimwitted Tommy Callahan (Chris Farley) inherits a near-bankrupt automobile parts factory in Sandusky, Ohio. His brand new stepmother, Beverly (Bo Derek), wants to cash out and close, but Tommy's sentimental attachment to his father's employees spurs him to make one last-ditch effort to find someone who will buy their products. With his father's tightly wound assistant, Richard (David Spade), in tow, Tommy hits the road to scare up some new clients.\",\"rating\":\"PG-13\",\"year\":\"1995\",\"genre\":\"Adventure\",\"portrait\":\"8998d7d103b9549211de53e4928bd68c37baf270e64194cad9cf91882a245c3a\",\"landscape\":\"eee16f9ae95441fce776aa3447a68d905cef3c956d088c26a76756d7dd2ff672\",\"video\":\"c35692e499ea0e3f3df291e125d9c63b3b78dd51a1b272de7eb52e783a20f8c1\",\"type\":\"movie\",\"extra\":\"1h 37m\"}}";
/*
        String t = "[\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624f17f6714b6207e5df\"\n" +
                "    },\n" +
                "    \"title\": \"Tommy Boy\",\n" +
                "    \"description\": \"After his beloved father (Brian Dennehy) dies, dimwitted Tommy Callahan (Chris Farley) inherits a near-bankrupt automobile parts factory in Sandusky, Ohio. His brand new stepmother, Beverly (Bo Derek), wants to cash out and close, but Tommy's sentimental attachment to his father's employees spurs him to make one last-ditch effort to find someone who will buy their products. With his father's tightly wound assistant, Richard (David Spade), in tow, Tommy hits the road to scare up some new clients.\",\n" +
                "    \"rating\": \"PG-13\",\n" +
                "    \"time\": \"1h 37m\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2020\",\n" +
                "    \"coming\": 1680303600,\n" +
                "    \"test\": True,\n" +
                "       \"test2\":null," +
                "    \"portrait\": \"8998d7d103b9549211de53e4928bd68c37baf270e64194cad9cf91882a245c3a\",\n" +
                "    \"landscape\": \"eee16f9ae95441fce776aa3447a68d905cef3c956d088c26a76756d7dd2ff672\"\n" +
                "  }\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624f17f6714b6207e5ab\"\n" +
                "    },\n" +
                "    \"title\": \"Tommy Boy\",\n" +
                "    \"title\": \"The Dark Kingdom\",\n" +
                "    \"description\": \"To save their kingdom from an army of undead, a group of warriors must travel through the forbidden lands fighting the fearsome beasts that call the Dark Kingdom their home.\",\n" +
                "    \"rating\": \"PG\",\n" +
                "    \"time\": \"1h 25m\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2020\",\n" +
                "    \"coming\": 1680303600,\n" +
                "    \"portrait\": \"b652e2908204cc37d50ce62db80c1208d04475a9bf14be9de2795febbbaaf095\",\n" +
                "    \"landscape\": \"8c8c8760be4db7c05915e1b37428999ad600deaafdb031e7479d7fe753169ac2\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624e17f6714b6207e58c\"\n" +
                "    },\n" +
                "    \"title\": \"Honey I shrunk the Kids\",\n" +
                "    \"description\": \"When kids sneak into inventor Wayne Szalinski's (Rick Moranis) upstairs lab to retrieve an errant baseball, his experimental shrink ray miniaturizes them. When Szalinski returns home, he destroys the device -- which he thinks is a failure -- and dumps it in the trash, throwing out the kids along with it. The four children, now 1/4-inch tall, must survive the journey back to the house through a yard where sprinklers bring treacherous storms and garden-variety ants stampede like elephants.\",\n" +
                "    \"rating\": \"PG\",\n" +
                "    \"time\": \"1h 41m\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2020\",\n" +
                "    \"coming\": 1766704600,\n" +
                "    \"portrait\": \"9d95d3928f7ee1752b886601229b4c2f2ec1acc557bd33291c94d54c2200d23c\",\n" +
                "    \"landscape\": \"b7c740b7dd32d3bf50bd209fbf9512a243746eea1d7380cd21f5a37fc52a04d5\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624e17f6714b6207e582\"\n" +
                "    },\n" +
                "    \"title\": \"Road Trip\",\n" +
                "    \"description\": \"A group of students embark on an epic road trip from Ithaca, New York, to Austin, Texas, in a race against time to save their friend's relationship. The young man picks up a girl at a wild fraternity party and films their raunchy antics. Somehow, the home-made movie gets mailed to his girlfriend in Texas. Can the guys intercept the incriminating tape before it reaches her VCR?\",\n" +
                "    \"rating\": \"R\",\n" +
                "    \"time\": \"1h 34m\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2021\",\n" +
                "    \"coming\": 1766704600,\n" +
                "    \"portrait\": \"77afa6aefd20e2044777ea478e49243d53c0a8d96aad9ec825c52c72ab53351b\",\n" +
                "    \"landscape\": \"2240938f790efdb43b9e61cc507b577c254ca7dddcf1648d7f17570bca4d19da\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624e17f6714b6207e561\"\n" +
                "    },\n" +
                "    \"title\": \"Three Amigos\",\n" +
                "    \"description\": \"Three cowboy movie stars from the silent era -- Dusty Bottoms (Chevy Chase), Lucky Day (Steve Martin) and Ned Nederlander (Martin Short) -- are fired when one of their movies bombs. In what seems to be a career-saving offer, young Mexican woman Carmen (Patrice Martinez) offers them a high-paying gig in her village. The three jump at the opportunity, expecting to do their typical act, but Carmen believes they are really heroes and asks them to rid her village of bad guy El Guapo (Alfonso Arau).\",\n" +
                "    \"rating\": \"PG\",\n" +
                "    \"time\": \"1h 45m\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2021\",\n" +
                "    \"coming\": 1766704600,\n" +
                "    \"portrait\": \"48901225875e7c37982a16695c68effb201085f847553a13f90725bcba7389a4\",\n" +
                "    \"landscape\": \"d8fdf08eaa71fdefda2acac08e3636df1140a966ec06b27670699dcc6ed11ee7\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624e17f6714b6207e560\"\n" +
                "    },\n" +
                "    \"title\": \"Year One\",\n" +
                "    \"description\": \"After Neanderthal hunter Zed (Jack Black) is exiled for eating forbidden fruit, he and his sardonic buddy Oh (Michael Cera) leave their village and begin an epic journey through history. The pals encounter biblical characters such as Cain (David Cross) and Abel, Abraham (Hank Azaria), and others, and wind up in Sodom. Along the way, Zed debunks commonly held beliefs and replaces them with his own delusions of grandeur.\",\n" +
                "    \"rating\": \"PG-13\",\n" +
                "    \"time\": \"1h 40m\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2019\",\n" +
                "    \"coming\": 1680303600,\n" +
                "    \"portrait\": \"422b97754b9089be771db66cec1fe047b2e7ed8cb865d588f10711a8ed31673a\",\n" +
                "    \"landscape\": \"5dbb4c3216e07b12f2b223ba9a94aa35cff9c1a55f26adcc56ef35f9bcb64c5d\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624e17f6714b6207e4f9\"\n" +
                "    },\n" +
                "    \"title\": \"Mad Max Fuey Road\",\n" +
                "    \"description\": \"Years after the collapse of civilization, the tyrannical Immortan Joe enslaves apocalypse survivors inside the desert fortress the Citadel. When the warrior Imperator Furiosa (Charlize Theron) leads the despot's five wives in a daring escape, she forges an alliance with Max Rockatansky (Tom Hardy), a loner and former captive. Fortified in the massive, armored truck the War Rig, they try to outrun the ruthless warlord and his henchmen in a deadly high-speed chase through the Wasteland.\",\n" +
                "    \"rating\": \"R\",\n" +
                "    \"time\": \"2h\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2019\",\n" +
                "    \"coming\": 1680303600,\n" +
                "    \"portrait\": \"de2f8b1e978527d2878a371747e711734a9b95a46249a4624ed822ff5880abf5\",\n" +
                "    \"landscape\": \"9825fc9635a7c70037771dfe2bc73737713f33d550f9d9931640ffd3487a48e2\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624e17f6714b6207e460\"\n" +
                "    },\n" +
                "    \"title\": \"Good Boys\",\n" +
                "    \"description\": \"Invited to his first kissing party, 12-year-old Max asks his best friends Lucas and Thor for some much-needed help on how to pucker up. When they hit a dead end, Max decides to use his father's drone to spy on the teenage girls next door. When the boys lose the drone, they skip school and hatch a plan to retrieve it before Max's dad can figure out what happened.\",\n" +
                "    \"rating\": \"R\",\n" +
                "    \"time\": \"1h 30m\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2019\",\n" +
                "    \"coming\": 1680303600,\n" +
                "    \"portrait\": \"18f1bd19c913681773ce23b975b00b2017ad2eea0a5cefc404f45ba7d77d24bc\",\n" +
                "    \"landscape\": \"32627fa7101c94cf2ff1a9a6b5c22e2c8507e394fc04d9786168c00464cb552e\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": {\n" +
                "      \"$oid\": \"63e4624e17f6714b6207e3f0\"\n" +
                "    },\n" +
                "    \"title\": \"Earth to Echo\",\n" +
                "    \"description\": \"After a construction project begins in their neighborhood, best friends Tuck (Brian \\\"Astro\\\" Bradley), Alex (Teo Halm) and Munch (Reese C. Hartwig) begin receiving strange, encoded messages on their cell phones. They immediately inform their parents and the authorities, but when no one takes them seriously, the youths decide to crack the code themselves and trace the messages to their source. The youths' curiosity leads them to a robotic extraterrestrial who desperately needs their help.\",\n" +
                "    \"rating\": \"PG\",\n" +
                "    \"time\": \"1h 40m\",\n" +
                "    \"genre\": \"Adventure\",\n" +
                "    \"year\": \"2019\",\n" +
                "    \"coming\": 1680303600,\n" +
                "    \"portrait\": \"2a3bbd82940dad5e4d950c156e0aef090297d638225243ca7295dee6b53290d2\",\n" +
                "    \"landscape\": \"9ea45ba00f32dce67e70ed712cac466158c0ce1c3c51a70f828c1eebb4de0230\"\n" +
                "  }\n" +
                "]";

        JsonArray json = new JsonArray(t.getBytes());
        //JsonObject json = new JsonObject(t.getBytes());

        //for(JsonBytes k : json.keySet()){
        //    System.out.println(new String(k.getBytes())+" : ");//+"  "+json.get(k.toString()).toString());
        //}
/*
        if(json.getInteger("type") == 0){
            System.out.println("SUCCESS");
        }else{
            System.out.println("FAILED");
        }
*/


        //JsonArray json = new JsonArray();
        JsonObject json = new JsonObject();
        JsonObject k = new JsonObject();
        k.put("$oid", "63e4624e17f6714b6207e3f0");
        json.put("id", k);
        k.put("test", "asd");
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
        //json.put("nullTest", null);
        //json.add(o);

        /*
        JsonArray json = new JsonArray();
        json.add(100);
        json.add(200);
        json.add(300.0);
        json.add("hello");
        json.add(false);
        json.add(new JsonObject());

        json.getJsonObject(5).put("Test", 0);*/

        //System.out.println(json.toString());

        //System.out.println(new String(json.encode()));

        Y y = (Y) Json.fromJson(Y.class, json);

        //System.out.println(y.getBoolTest());

        json = Json.toJson(y);

        System.out.println(json);

        //System.out.println(json.getJsonObject(2).getJsonObject("_id").getString("$oid"));


        //System.out.println(new String(sanitize("ds Tuck (Brian \"Astro\" Bradley), Alex (Teo Hal".getBytes())));

        //Class<Y> yClass = Y.class;

        // Get all the declared methods of the class
        //Method[] methods = yClass.getDeclaredMethods();

        /*
        Map<String, String> a = new HashMap<>();
        a.put("portrait", "TEST");

        Y y = (Y) testSet(Y.class, a);

        System.out.println(y.getPortrait());

        //Y y = new Y();
        testGet(y);
        */
    }


    /*
            k.put("$oid", "63e4624e17f6714b6207e3f0");
        o.put("id", k);
        k.put("test", "asd");
        o.put("title", "Earth to Echo");
        o.put("description", "After a construction project begins in their neighborhood, best friends Tuck (Brian \"Astro\" Bradley), Alex (Teo Halm) and Munch (Reese C. Hartwig) begin receiving strange, encoded messages on their cell phones. They immediately inform their parents and the authorities, but when no one takes them seriously, the youths decide to crack the code themselves and trace the messages to their source. The youths' curiosity leads them to a robotic extraterrestrial who desperately needs their help.");
        o.put("rating", "PG");
        o.put("time", "1h 40m");
        o.put("genre", "Adventure");
        o.put("year", "2019");
        o.put("coming", 1680303600);
        o.put("portrait", "2a3bbd82940dad5e4d950c156e0aef090297d638225243ca7295dee6b53290d2");
        o.put("landscape", "9ea45ba00f32dce67e70ed712cac466158c0ce1c3c51a70f828c1eebb4de0230");
        o.put("boolTest", false);
        o.put("nullTest", null);
    */
    public static class Y {

        private String title, description, rating, time, genre, year, portrait, landscape;
        private long coming;
        private boolean bool;

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
        public long getComing(){
            return coming;
        }

        @JsonAnnotation(key = "coming")
        public void setComing(long coming){
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
    }
}