package test.io;

import unet.json.io.JsonReader;
import unet.json.io.JsonWriter;
import unet.json.variables.JsonArray;
import unet.json.variables.JsonArray2;
import unet.json.variables.JsonObject;
import unet.json.variables.JsonObject2;

import java.io.ByteArrayInputStream;

public class IOTest {

    public static void main(String[] args)throws Exception {
        System.out.println("-= I/O TEST =-");

        JsonObject2 json = new JsonObject2();

        JsonObject2 k = new JsonObject2();
        k.put("insert", "Insert value");
        json.put("object", k); //TEST LAYERED PUT

        JsonArray2 l = new JsonArray2();
        l.add("Test Array");
        json.put("array", l);

        json.put("title", "Earth to Echo"); //TEST STRING
        json.put("coming", 1680303600); //TEST INT
        json.put("boolTest", true); //TEST BOOLEAN
        json.put("nullTest", null); //TEST NULL

        System.out.println("OUTPUT TEST");
        //JsonWriter w = new JsonWriter(System.out);
        //w.write(json);
        //w.flush();

        System.out.println("\r\n\r\n");

        System.out.println("INPUT TEST");
        JsonReader r = new JsonReader(new ByteArrayInputStream(json.encode()));
        json = r.readJsonObject();
        System.out.println(json);
    }
}
