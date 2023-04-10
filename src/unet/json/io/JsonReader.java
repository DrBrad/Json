package unet.json.io;

import unet.json.variables.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonReader {

    //LETS HANDLE THIS IN A BETTER MANNER...

    private InputStream in;

    public JsonReader(InputStream in){
        this.in = in;
    }

    public JsonObject read()throws IOException {
        //in.read();
        /*
        trim();

        if(buf[pos] == '{'){
            HashMap<JsonBytes, JsonVariable> m = new HashMap<>();
            pos++;
            //trim();

            while(buf[pos] != '}'){
                trim();
                m.put(getBytes(), get());
            }
            pos++;
            return m;
        }*/

        decodeObject();

        return null;
    }

    public void close()throws IOException {
        in.close();
    }















    /*
    private List<JsonVariable> decodeArray(){
        trim();

        if(buf[pos] == '['){
            ArrayList<JsonVariable> a = new ArrayList<>();
            pos++;
            //trim();

            while(buf[pos] != ']'){
                trim();
                a.add(get());
            }
            pos++;
            return a;
        }
        return null;
    }
    */

    private Map<JsonBytes, JsonVariable> decodeObject()throws IOException {

        byte b = (byte) in.read();
        if(b == '{'){
            Map<JsonBytes, JsonVariable> m = new HashMap<>();

            while(true){//in.read() != '}'){
                JsonBytes k = getBytes();
                System.out.println(new String(k.getObject()));
                //trim();
                //m.put(getBytes(), get());
            }
        }


        System.out.println("1 "+(char) in.read());
        //trim();

        /*
        if(buf[pos] == '{'){
            HashMap<JsonBytes, JsonVariable> m = new HashMap<>();
            pos++;
            //trim();

            while(buf[pos] != '}'){
                trim();
                m.put(getBytes(), get());
            }
            pos++;
            return m;
        }*/
        System.out.println((char) in.read());

        return null;
    }

    /*
    private JsonVariable get()throws IOException {
        //IF CASE 0-9 = NUMBER
        //IF CASE T | F = BOOLEAN
        //IF CASE " = STRING
        //IF CASE { = MAP
        //IF CASE [ = LIST
        //IF CASE n = NULL
        //trim();

        //System.out.println((char)(buf[pos]));

        switch(buf[pos]){
            case '"':
                return getBytes();

            case 't':
                return getBoolean(true);

            case 'T':
                return getBoolean(true);

            case 'f':
                return getBoolean(false);

            case 'F':
                return getBoolean(false);

            case 'n':
                return getNull();

            case 'N':
                return getNull();

            case '{':
                return getMap();

            case '[':
                return getList();

            default:
                if(isNumber()){
                    return getNumber();
                }
        }

        return null;
    }

    private JsonNull getNull()throws IOException {
        //trim();
        pos += 5;
        trim();
        return new JsonNull();
    }

    private JsonBoolean getBoolean(boolean bool)throws IOException {
        //trim();
        pos += (bool) ? 5 : 6;
        trim();

        return new JsonBoolean(bool);
    }

    private JsonNumber getNumber()throws IOException {
        //trim();
        //pos++;

        int s = pos;
        while(isNumber()){
            pos++;
        }

        byte[] b = new byte[pos-s];
        System.arraycopy(buf, s, b, 0, b.length);

        pos++;
        trim();

        return new JsonNumber(new String(b));
    }

    private JsonBytes getBytes()throws IOException {
        //trim();
        pos++;

        int s = pos;
        while(buf[pos] != '"'){
            pos++;
        }

        byte[] b = new byte[pos-s];
        System.arraycopy(buf, s, b, 0, b.length);

        pos++;
        trim();

        return new JsonBytes(b);
    }

    private JsonArray getList()throws IOException {
        return new JsonArray(decodeArray());
    }

    private JsonObject getMap()throws IOException {
        return new JsonObject(decodeObject());
    }*/

    private JsonBytes getBytes()throws IOException {
        byte b = (byte) in.read();
        if(b == '"'){
            System.out.println("READ");

            byte[] buf = new byte[4096];
            int i = 0;
            while((b = (byte) in.read()) != '"'){
                buf[i] = b;
                i++;

                if(i >= buf.length){
                    //INCREASE SIZE...
                }
            }

            return new JsonBytes(buf);
        }
        return null;
    }







    private void trim()throws IOException {
        while(isTrimmable((byte) in.read()));
    }

    private boolean isTrimmable(byte b){
        return (b == 0x20 ||
                b == '\t' ||
                b == '\r' ||
                b == '\n' ||
                b == ':' ||
                b == ',');
    }

    //IS THE DECIMAL POINT CONSIDERED A NUMBER...?
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
                b == '.');
    }
}
