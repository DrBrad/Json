package unet.json.io;

import unet.json.variables.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonReader {

    private InputStream in;

    private byte n;

    public JsonReader(InputStream in){
        this.in = in;
    }

    public JsonArray readJsonArray()throws IOException {
        read();
        return getArray();
    }

    public JsonObject readJsonObject()throws IOException {
        read();
        return getObject();
    }

    public void close()throws IOException {
        in.close();
    }

    private byte read()throws IOException {
        byte p = n;
        n = (byte) in.read();
        return p;
    }

    private byte peek(){
        return n;
    }

    private JsonVariable get()throws IOException {
        //IF CASE 0-9 = NUMBER
        //IF CASE T | F = BOOLEAN
        //IF CASE " = STRING
        //IF CASE { = MAP
        //IF CASE [ = LIST
        //IF CASE n = NULL

        //byte b = (byte) in.read();
        switch(peek()){
            case '"':
                return getString();

            case 't':
                in.skip(3);
                read();
                return new JsonBoolean(true);

            case 'T':
                in.skip(3);
                read();
                return new JsonBoolean(true);

            case 'f':
                in.skip(4);
                read();
                return new JsonBoolean(false);

            case 'F':
                in.skip(4);
                read();
                return new JsonBoolean(false);

            case 'n':
                in.skip(3);
                read();
                return new JsonNull();

            case 'N':
                in.skip(3);
                read();
                return new JsonNull();

            case '{':
                return getObject();

            case '[':
                return getArray();

            default:
                if(isNumber(peek())){
                    return getNumber();
                }
        }

        return null;
    }

    private JsonObject getObject()throws IOException {
        JsonObject j = new JsonObject();
        //Map<JsonString, JsonVariable> m = new HashMap<>();
        while(peek() != '}'){
            read();
            JsonString k = getString();
            read();
            j.put(k, get());
        }

        read();

        return j;
    }

    private JsonArray getArray()throws IOException {
        JsonArray j = new JsonArray();
        while(peek() != ']'){
            read();
            j.add(get());
        }

        read();

        return j;
    }

    private JsonString getString()throws IOException {
        read();

        byte[] buf = new byte[1024];
        int i = 0;
        while(peek() != '"'){
            buf[i] = read();
            i++;

            if(i >= buf.length){
                byte[] r = new byte[buf.length+1024];
                System.arraycopy(buf, 0, r, 0, i);
                buf = r;
            }
        }

        read();

        if(i < buf.length){
            byte[] r = new byte[i];
            System.arraycopy(buf, 0, r, 0, i);
            return new JsonString(r);
        }

        return new JsonString(buf);
    }

    private JsonNumber getNumber()throws IOException {
        byte[] buf = new byte[23];
        int i = 0;
        while(isNumber(peek())){
            buf[i] = read();
            i++;
        }

        if(i < buf.length){
            byte[] r = new byte[i];
            System.arraycopy(buf, 0, r, 0, i);
            return new JsonNumber(new String(r));
        }

        return new JsonNumber(new String(buf));
    }

    private boolean isNumber(byte b){
        return (b == '0' ||
                b == '1' ||
                b == '2' ||
                b == '3' ||
                b == '4' ||
                b == '5' ||
                b == '6' ||
                b == '7' ||
                b == '8' ||
                b == '9' ||
                b == '-' ||
                b == '.');
    }
}
