package test.io;

import unet.json.io.JsonReader;
import unet.json.io.JsonWriter;
import unet.json.variables.JsonArray;
import unet.json.variables.JsonObject;

import java.io.ByteArrayInputStream;

public class IOTest {

    public static void main(String[] args)throws Exception {
        System.out.println("-= I/O TEST =-");

        //String s = String.valueOf(Double.MAX_VALUE);
        //String s2 = String.valueOf(Double.MIN_VALUE);
        
        //System.out.println(s+"  "+s.length());
        //System.out.println(s2+"  "+s2.length());

        JsonObject json = new JsonObject();






        JsonObject k = new JsonObject();
        k.put("insert", "Insert value");
        json.put("object", k); //TEST LAYERED PUT
/*
        JsonArray l = new JsonArray();
        l.add("Test Array");
        json.put("array", l);
*/
        json.put("title", "Earth to Echo"); //TEST STRING
        json.put("zzz", "asdasd"); //TEST STRING
        json.put("coming", 1680303600); //TEST INT
        json.put("boolTest", true); //TEST BOOLEAN
        json.put("nullTest", null); //TEST NULL

        System.out.println("OUTPUT TEST");
        JsonWriter w = new JsonWriter(System.out);
        w.write(json);
        w.flush();

        System.out.println("\r\n\r\n");

        JsonReader r = new JsonReader(new ByteArrayInputStream(json.encode()));
        json = r.readJsonObject();
        System.out.println(json);
    }
}
