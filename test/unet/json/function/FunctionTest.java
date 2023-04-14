package unet.json.function;

import unet.json.variables.JsonArray;
import unet.json.variables.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionTest {

    public static void main(String[] args){
        System.out.println("-= OBJECT TEST =-");

        JsonObject json = new JsonObject();

        JsonObject k = new JsonObject();
        k.put("insert", "Insert value");
        json.put("object", k); //TEST LAYERED PUT
        k.put("observer", "Test Observer"); //TEST OBSERVER

        JsonArray l = new JsonArray();
        l.add("Test Array");
        json.put("array", l);

        json.put("title", "Earth to Echo"); //TEST STRING
        json.put("serial", "Test \"String\" serialize"); //TEST STRING w SERIALIZATION
        json.put("coming", 1680303600); //TEST INT
        json.put("boolTest", true); //TEST BOOLEAN
        json.put("nullTest", null); //TEST NULL

        System.out.println("GENERAL TEST");
        System.out.println(json);


        //TEST MAP TO OBJECT...
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> k2 = new HashMap<>();
        k2.put("insert", "Insert value");
        m.put("object", k2);

        List<Object> l2 = new ArrayList<>();
        l2.add("Test Array");
        m.put("array", l2);

        m.put("title", "Earth to Echo"); //TEST STRING
        m.put("serial", "Test \\\"String\"\n serialize"); //TEST STRING w SERIALIZATION
        m.put("coming", 1680303600); //TEST INT
        m.put("boolTest", true); //TEST BOOLEAN
        m.put("nullTest", null); //TEST NULL

        json = new JsonObject(m);

        System.out.println("\r\n\r\n");
        System.out.println("MAP TO OBJECT TEST");
        System.out.println(json);


        //TEST ENCODE / DECODE
        byte[] b = json.encode();
        System.out.println("\r\n\r\n");
        System.out.println("ENCODE TEST");
        System.out.println(new String(b));

        System.out.println("\r\n\r\n");
        System.out.println("DECODE TEST");
        System.out.println(new JsonObject(b));
    }
}
