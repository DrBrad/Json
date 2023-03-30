package unet.json;

import unet.json.variables.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Json {

    private byte[] buf;
    private int pos = 0;

    //WE NEED TO WORRY ABOUT TRIM...
    //Make hasmaps and arraylists map and list
    //make the i/o streams

    public byte[] encode(JsonArray l){
        buf = new byte[l.byteSize()];
        put(l);
        return buf;
    }

    public byte[] encode(JsonObject m){
        buf = new byte[m.byteSize()];
        put(m);
        return buf;
    }

    public List<JsonVariable> decodeArray(byte[] buf, int off){
        this.buf = buf;
        pos = off;
        return decodeArray();
    }

    public Map<JsonBytes, JsonVariable> decodeObject(byte[] buf, int off){
        this.buf = buf;
        pos = off;
        return decodeObject();
    }

    private void put(JsonVariable v){
        if(v instanceof JsonBytes){
            put((JsonBytes) v);
        }else if(v instanceof JsonNumber){
            put((JsonNumber) v);
        }else if(v instanceof JsonArray){
            put((JsonArray) v);
        }else if(v instanceof JsonObject){
            put((JsonObject) v);
        }
    }

    private void put(JsonBytes v){
        byte[] b = v.getBytes();
        System.arraycopy(b, 0, buf, pos, b.length);
        pos += b.length;
    }

    private void put(JsonNumber n){
        byte[] b = n.getBytes();
        System.arraycopy(b, 0, buf, pos, b.length);
        pos += b.length;
    }

    private void put(JsonArray l){
        /*
        buf[pos] = 'l';
        pos++;

        for(int i = 0; i < l.size(); i++){
            put(l.valueOf(i));
        }
        buf[pos] = 'e';
        pos++;
        */
    }

    private void put(JsonObject m){
        /*
        buf[pos] = 'd';
        pos++;

        for(JsonBytes k : m.keySet()){
            put(k);
            put(m.valueOf(k));
        }
        buf[pos] = 'e';
        pos++;
        */
    }

    private List<JsonVariable> decodeArray(){
        if(buf[pos] == '['){
            ArrayList<JsonVariable> a = new ArrayList<>();
        }
        /*
        if(buf[pos] == 'l'){
            ArrayList<JsonVariable> a = new ArrayList<>();
            pos++;

            while(buf[pos] != 'e'){
                a.add(get());
            }
            pos++;
            return a;
        }
        */
        return null;
    }

    private Map<JsonBytes, JsonVariable> decodeObject(){
        if(buf[pos] == '{'){
            HashMap<JsonBytes, JsonVariable> m = new HashMap<>();
            pos++;

            while(buf[pos] != '}'){
                m.put(getBytes(), get());
                break;
            }
            pos++;
            return m;
        }
        /*
        if(buf[pos] == 'd'){
            HashMap<JsonBytes, JsonVariable> m = new HashMap<>();
            pos++;

            while(buf[pos] != 'e'){
                m.put(getBytes(), get());
            }
            pos++;
            return m;
        }
        */
        return null;
    }

    private JsonVariable get(){
        //IF CASE 0-9 = NUMBER
        //IF CASE T | F = BOOLEAN
        //IF CASE " = STRING
        //IF CASE { = MAP
        //IF CASE [ = LIST
        //IF CASE n = NULL
        pos++;

        switch(buf[pos]){
            case '"':
                return getBytes();

            case 't':
                //return getBoolean();

            case 'T':
                //return getBoolean();

            case 'f':
                //return getBoolean();

            case 'F':
                //return getBoolean();

            case 'n':
                //return getNull();

            case 'N':
                //return getNull();

            case '{':
                return getMap();

            case '[':
                return getList();

            default:
                if(isNumber(buf[pos])){
                    return getNumber();
                }
        }



        /*
        switch(buf[pos]){
            case 'i':
                return getNumber();
            case 'l':
                return getList();
            case 'd':
                return getMap();
            default:
                if(buf[pos] >= '0' && buf[pos] <= '9'){
                    return getBytes();
                }
        }
        */
        return null;
    }

    private JsonNumber getNumber(){
        pos++;

        int s = pos;
        while(isNumber(buf[pos])){
            System.out.println((char)(buf[pos]));
            pos++;
        }

        byte[] b = new byte[pos-s];
        System.arraycopy(buf, s, b, 0, b.length);

        pos++;

        return new JsonNumber(new String(b));
        /*
        char[] c = new char[32];
        pos++;
        int s = pos;
        while(buf[pos] != 'e'){
            c[pos-s] = (char) buf[pos];
            pos++;
        }

        pos++;
        return new JsonNumber(new String(c, 0, pos-s-1));
        */
        //return null;
    }

    private JsonBytes getBytes(){
        //TRIM THEN FIND "
        //TEMP TRIM
        pos++;

        int s = pos;
        while(buf[pos] != '"'){
            System.out.println((char)(buf[pos]));
            pos++;
        }

        byte[] b = new byte[pos-s];
        System.arraycopy(buf, s, b, 0, b.length);

        pos++;

        return new JsonBytes(b);
    }

    private JsonArray getList(){
        return new JsonArray(decodeArray());
    }

    private JsonObject getMap(){
        return new JsonObject(decodeObject());
    }

    private void trim(){
        //SKIP \r + \n + \t + SPACE
    }

    //IS THE DECIMAL POINT CONSIDERED A NUMBER...?
    private boolean isNumber(byte c){
        return (c == '0' ||
                c == '1' ||
                c == '2' ||
                c == '3' ||
                c == '4' ||
                c == '5' ||
                c == '6' ||
                c == '7' ||
                c == '8' ||
                c == '9' ||
                c == '.');
    }
}
