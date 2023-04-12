package unet.json.io;

import unet.json.variables.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {

    //LETS HANDLE THIS IN A BETTER MANNER...

    private InputStream in;

    private byte n; //UNFORTUNATELY WE NEED TO SEEK AHEAD FOR NUMBERS...

    public JsonReader(InputStream in){
        this.in = in;
    }

    public JsonObject readJsonObject()throws IOException {
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
        //byte b = (byte) in.read();
        //n = (byte) in.read();
        read();

        return new JsonObject(decodeObject());
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

        //readObject = {
        //read so we can see {
        //reaad one so we can see "


        Map<JsonBytes, JsonVariable> m = new HashMap<>();
        while(peek() != '}'){

            read();
            JsonBytes k = getBytes();
            read();
            //JsonBytes v = (JsonBytes) get();
            //System.out.println("ZZZ:  "+new String(k.getObject())+" = "+new String(v.getObject()));

            m.put(k, get());

            //break;
        }

        read();

        /*
        do{
            if(in.read() == '}'){ //SKIP "
                break;
            }

            JsonBytes k = getBytes();
            System.out.println(new String(k.getObject()));
            //in.read();
            in.read();
            m.put(k, get());

        }while(in.read() != '}');
        */


        return m;
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
    private JsonVariable get()throws IOException {
        //IF CASE 0-9 = NUMBER
        //IF CASE T | F = BOOLEAN
        //IF CASE " = STRING
        //IF CASE { = MAP
        //IF CASE [ = LIST
        //IF CASE n = NULL
        //trim();

        //System.out.println((char)(buf[pos]));

        //byte b = (byte) in.read();
        switch(peek()){
            case '"':
                return getBytes();

            case 't':
                in.skip(3);
                return new JsonBoolean(true);

            case 'T':
                in.skip(3);
                return new JsonBoolean(true);

            case 'f':
                in.skip(4);
                return new JsonBoolean(false);

            case 'F':
                in.skip(4);
                return new JsonBoolean(false);

            case 'n':
                in.skip(3);
                return new JsonNull();

            case 'N':
                in.skip(3);
                return new JsonNull();

            case '{':
                return new JsonObject(decodeObject());

            case '[':
                //System.out.println("LIST");
                //return getList();

            default:
                if(isNumber(peek())){
                    return getNumber();
                }
        }

        return null;
    }

    private JsonBytes getBytes()throws IOException {
        read();

        byte[] buf = new byte[1024];
        int i = 0;
        //byte b;
        while(peek() != '"'){
        //while((b = read()) != '"'){
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
            return new JsonBytes(r);
        }

        return new JsonBytes(buf);
    }

    private JsonNumber getNumber()throws IOException {
        //read();

        byte[] buf = new byte[20];
        int i = 0;
        while(isNumber(peek())){
            buf[i] = read();
            i++;
        }

        if(i < buf.length){
            byte[] r = new byte[i];
            System.arraycopy(buf, 0, r, 0, i);
            System.out.println("K: "+new String(r));
            return new JsonNumber(new String(r));
        }

        System.out.println("K: "+new String(buf));
        return new JsonNumber(new String(buf));
    }


/*
    private JsonNumber getNumber()throws IOException {
        byte[] buf = new byte[1024];
        int i = 0;
        byte b;
        while((b = (byte) in.read()) != '"'){
            buf[i] = b;
            i++;

            if(i >= buf.length){
                byte[] r = new byte[buf.length+1024];
                System.arraycopy(buf, 0, r, 0, i);
                buf = r;
            }
        }

        if(i < buf.length){
            byte[] r = new byte[i];
            System.arraycopy(buf, 0, r, 0, i);
            return new JsonBytes(r);
        }

        return new JsonBytes(buf);
    }


/*
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
*/

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
                b == '-' ||
                b == '.');
    }
}
