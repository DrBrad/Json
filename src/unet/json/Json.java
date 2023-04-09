package unet.json;

import unet.json.variables.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
/*
    public JsonVariable decode(byte[] buf, int off){
        this.buf = buf;
        pos = off;

        switch(buf[pos]){
            case '{':
                return getMap();

            case '[':
                return getList();
        }

        return null;
    }
*/



    public static Object fromJson(Class c, JsonObject j)throws ReflectiveOperationException {
        Constructor<?> constructor = c.getDeclaredConstructor();
        Object i = constructor.newInstance();

        for(Method method : i.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(JsonAnnotation.class)){
                if(method.getReturnType() == void.class){
                    final String k = method.getAnnotation(JsonAnnotation.class).key();

                    if(j.containsKey(k)){
                        //if(j.get(k) == method.getReturnType()){

                        if(String.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getString(k));

                        }else if(method.getParameterTypes()[0] == int.class){
                        //}else if(Integer.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getInteger(k));

                        }else if(method.getParameterTypes()[0] == long.class){
                        //}else if(Long.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getLong(k));

                        }else if(method.getParameterTypes()[0] == double.class){
                        //}else if(Double.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getDouble(k));

                        }else if(method.getParameterTypes()[0] == byte.class){
                        //}else if(Byte.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getBytes(k));

                        }else if(method.getParameterTypes()[0] == boolean.class){
                        //}else if(Boolean.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getBoolean(k));

                        }else if(List.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getJsonArray(k));

                        }else if(Map.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getJsonObject(k));

                        }


                        /*
                        if(method.getParameterTypes()[0] == String.class){
                            method.invoke(i, j.getString(k));

                        }else if(method.getParameterTypes()[0] == Number.class){
                            method.invoke(i, j.getString(k));
                        }*/
                        //}
                    }
                }
            }
        }

        return i;
    }

    public static JsonObject toJson(Object o){
        JsonObject j = new JsonObject();
        for(Method method : o.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(JsonAnnotation.class)){
                try{
                    if(method.getReturnType() != void.class){
                        System.out.println(method.getReturnType()+"  - "+method.getAnnotation(JsonAnnotation.class).key()+" = "+method.invoke(o));
                        j.put(method.getAnnotation(JsonAnnotation.class).key(), method.invoke(o));
                    }
                }catch(ReflectiveOperationException e){
                    e.printStackTrace();
                }
            }
        }
        return j;
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
        }else if(v instanceof JsonBoolean){
            put((JsonBoolean) v);
        }else if(v instanceof JsonNull){
            put((JsonNull) v);
        }else if(v instanceof JsonArray){
            put((JsonArray) v);
        }else if(v instanceof JsonObject){
            put((JsonObject) v);
        }
    }

    //THIS SEEMS REDUNDANT....
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

    private void put(JsonBoolean n){
        byte[] b = n.getBytes();
        System.arraycopy(b, 0, buf, pos, b.length);
        pos += b.length;
    }

    private void put(JsonNull n){
        byte[] b = n.getBytes();
        System.arraycopy(b, 0, buf, pos, b.length);
        pos += b.length;
    }
    //THIS SEEMS REDUNDANT

    private void put(JsonArray l){
        buf[pos] = '[';
        pos++;

        for(int i = 0; i < l.size(); i++){
            put(l.valueOf(i));
            buf[pos] = ',';
            pos++;
        }
        buf[pos-1] = ']';
    }

    private void put(JsonObject m){
        buf[pos] = '{';
        pos++;

        for(JsonBytes k : m.keySet()){
            put(k);
            buf[pos] = ':';
            pos++;
            put(m.valueOf(k));
            buf[pos] = ',';
            pos++;
        }
        buf[pos-1] = '}';
    }

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

    private Map<JsonBytes, JsonVariable> decodeObject(){
        trim();

        if(buf[pos] == '{'){
            HashMap<JsonBytes, JsonVariable> m = new HashMap<>();
            pos++;
            //trim();

            while(buf[pos] != '}'){
                //final JsonBytes k = getBytes();
                trim();
                m.put(getBytes(), get());
                //trim();
                //m.put(getBytes(), get());
            }
            pos++;
            return m;
        }
        return null;
    }

    private JsonVariable get(){
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

    private JsonNull getNull(){
        //trim();
        pos += 5;
        trim();
        return new JsonNull();
    }

    private JsonBoolean getBoolean(boolean bool){
        //trim();
        pos += (bool) ? 5 : 6;
        trim();

        return new JsonBoolean(bool);
    }

    private JsonNumber getNumber(){
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

    private JsonBytes getBytes(){
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

    private JsonArray getList(){
        return new JsonArray(decodeArray());
    }

    private JsonObject getMap(){
        return new JsonObject(decodeObject());
    }

    private void trim(){
        while(isTrimmable()){
            pos++;
        }
    }

    private boolean isTrimmable(){
        return (buf[pos] == 0x20 ||
                buf[pos] == '\t' ||
                buf[pos] == '\r' ||
                buf[pos] == '\n' ||
                buf[pos] == ':' ||
                buf[pos] == ',');
    }

    //IS THE DECIMAL POINT CONSIDERED A NUMBER...?
    private boolean isNumber(){
        return (buf[pos] == '0' ||
                buf[pos] == '1' ||
                buf[pos] == '2' ||
                buf[pos] == '3' ||
                buf[pos] == '4' ||
                buf[pos] == '5' ||
                buf[pos] == '6' ||
                buf[pos] == '7' ||
                buf[pos] == '8' ||
                buf[pos] == '9' ||
                buf[pos] == '.');
    }
}
