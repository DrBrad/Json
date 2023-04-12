package unet.json.io;

import unet.json.variables.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.ParseException;
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

    public JsonArray2 readJsonArray()throws IOException {
        read();
        return getArray();
    }

    public JsonObject2 readJsonObject()throws IOException {
        read();
        return getObject();
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

    private Object get()throws IOException, ParseException {
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

    private /*Map<String, Object>*/JsonObject2 getObject()throws IOException {
        //Map<String, Object> m = new HashMap<>();
        JsonObject2 j = new JsonObject2();
        while(peek() != '}'){
            read();
            String k = getString();
            //JsonBytes k = getBytes();
            read();
            try{
                j.put(k, get());
            }catch(ParseException e){
            }
        }

        read();

        return j;
    }

    private JsonArray2 getArray()throws IOException {
        //List<Object> l = new ArrayList<>();
        JsonArray2 j = new JsonArray2();
        while(peek() != ']'){
            read();
            try{
                j.add(get());
            }catch(ParseException e){
            }
        }

        read();

        return j;
    }

    private String getString()throws IOException {
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
            return new String(r);
        }

        return new String(buf);
    }

    private Number getNumber()throws IOException, ParseException {
        byte[] buf = new byte[23];
        int i = 0;
        while(isNumber(peek())){
            buf[i] = read();
            i++;
        }

        if(i < buf.length){
            byte[] r = new byte[i];
            System.arraycopy(buf, 0, r, 0, i);
            return NumberFormat.getInstance().parse(new String(r));
        }

        return NumberFormat.getInstance().parse(new String(buf));
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
