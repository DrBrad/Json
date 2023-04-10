package unet.json.io;

import unet.json.variables.JsonObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonReader {

    //LETS HANDLE THIS IN A BETTER MANNER...

    private InputStream in;

    public JsonReader(InputStream in){
        this.in = in;
    }

    public JsonObject read(JsonObject j)throws IOException {
        //in.read();

        return null;
    }

    public void close()throws IOException {
        in.close();
    }
}
