package unet.json.variables;

import unet.json.Json;

import java.util.ArrayList;
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
        read();

        JsonObject j = new JsonObject();
        //Map<JsonString, JsonVariable> m = new HashMap<>();
        while(peek() != '}'){
            trim();
            j.put(getString(), get());
        }

        read();
        trim();

        return j;
    }

    private JsonArray getArray()throws IOException {
        read();

        JsonArray j = new JsonArray();
        while(peek() != ']'){
            //read();

            trim();

            if(peek() == ']'){
                break;
            }

            j.add(get());
        }

        read();
        trim();

        return j;
    }

    private JsonString getString()throws IOException {
        read();

        /*
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
        */
        byte[] b = new byte[1024];
        int i = 0, a = 0;
        while(peek() != '"' || (a%2 == 1 && peek() == '"')){
            if(peek() == '\\'){
                read();

                switch(peek()){
                    case '\"':
                        a++;
                        b[i] = '\"';
                        break;

                    case 'n':
                        b[i] = '\n';
                        break;

                    case 'r':
                        b[i] = '\r';
                        break;

                    case 'f':
                        b[i] = '\f';
                        break;

                    case 'b':
                        b[i] = '\b';
                        break;

                    case 't':
                        b[i] = '\t';
                        break;

                    default:
                        a = 0;
                        b[i] = peek();
                        read();
                        i++;
                }

            }else{
                a = 0;
                b[i] = read();
                i++;
            }

            if(i >= b.length){
                byte[] r = new byte[b.length+1024];
                System.arraycopy(b, 0, r, 0, i);
                b = r;
            }
        }

        read();

        if(i < b.length){
            byte[] r = new byte[i];
            System.arraycopy(b, 0, r, 0, i);
            trim();
            return new JsonString(r);
        }

        trim();
        return new JsonString(b);
    }

    private JsonNumber getNumber()throws IOException {
        byte[] buf = new byte[23];
        int i = 0;
        while(isNumber(peek())){
            buf[i] = read();
            i++;
        }

        /*
        if(i < buf.length){
            byte[] r = new byte[i];
            System.arraycopy(buf, 0, r, 0, i);
            return new JsonNumber(new String(r));
        }

        return new JsonNumber(new String(buf));
        */
        trim();
        return new JsonNumber(new String(buf, 0, i));
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

    private void trim()throws IOException {
        while(isTrimmable()){
            read();
        }
    }

    private boolean isTrimmable(){
        return (n == 0x20 ||
                n == '\t' ||
                n == '\r' ||
                n == '\n' ||
                n == ':' ||
                n == ',');
    }
}
