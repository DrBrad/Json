package unet.json.io;

import unet.json.variables.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
        return new JsonArray(getArray());
    }

    public JsonObject readJsonObject()throws IOException {
        read();
        return new JsonObject(getObject());
    }

    //WE MAY WANT TO MOVE THIS TO ITS OWN CLASS...
    public Object readToClass(Class<?> c)throws ReflectiveOperationException {
        Constructor<?> constructor = c.getDeclaredConstructor();
        Object i = constructor.newInstance();

        //DO STUFF...
        for(Field field : c.getDeclaredFields()){
            if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).deserialize()){
                final String k = field.getName();
            }
        }

        return i;
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
                return getBytes();

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
                return new JsonObject(getObject());

            case '[':
                return new JsonArray(getArray());

            default:
                if(isNumber(peek())){
                    return getNumber();
                }
        }

        return null;
    }

    private Map<JsonBytes, JsonVariable> getObject()throws IOException {
        Map<JsonBytes, JsonVariable> m = new HashMap<>();
        while(peek() != '}'){
            read();
            JsonBytes k = getBytes();
            read();
            m.put(k, get());
        }

        read();

        return m;
    }

    private List<JsonVariable> getArray()throws IOException {
        List<JsonVariable> l = new ArrayList<>();
        while(peek() != ']'){
            read();
            l.add(get());
        }

        read();

        return l;
    }

    private JsonBytes getBytes()throws IOException {
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
            return new JsonBytes(r);
        }

        return new JsonBytes(buf);
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
