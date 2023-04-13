package unet.json;

import unet.json.variables.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Json {

    private byte[] buf;
    private int pos = 0;

    //SERIALIZE JSON - PRETTY AND NOT
    //REMOVE OBSERVER IF SET OR PUT
    //FINISH SANITIZATION...
    //Annotation to/from bytes
    //WE SHOULD BE USING HASH-CODES NOT INSTANCES...
    //HANDLE NUMBERS/BOOLEANS/NULL BETTER...
    //BETTER NUMBER PARSING...

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

    public static Object fromJson(Class<?> c, JsonObject j)throws ReflectiveOperationException {
        Constructor<?> constructor = c.getDeclaredConstructor();
        Object i = constructor.newInstance();

        for(Field field : c.getDeclaredFields()){
            if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).deserialize()){
                final String k = field.getName();

                if(j.containsKey(k)){
                    field.setAccessible(true);

                    JsonVariable v = j.valueOf(new JsonString(k));

                    switch(v.hashCode()){
                        case 0: //STRING
                            if(String.class.isAssignableFrom(field.getType())){
                                field.set(i, ((JsonString) v).getObject());
                            }
                            break;

                        case 1: //NUMBER
                            if(field.getType().equals(int.class)){
                                field.set(i, ((Integer) v.getObject()).doubleValue());

                            }else if(field.getType().equals(long.class)){
                            //}else if(Long.class.isAssignableFrom(method.getParameterTypes()[0])){
                                field.set(i, ((Long) v.getObject()).longValue());

                            }else if(field.getType().equals(double.class)){
                            //}else if(Double.class.isAssignableFrom(method.getParameterTypes()[0])){
                                field.set(i, ((Double) v.getObject()).doubleValue());
                            }
                            break;

                        case 2: //ARRAY
                            if(List.class.isAssignableFrom(field.getType())){
                                field.set(i, v.getObject());
                            }
                            break;

                        case 3: //OBJECT
                            if(Map.class.isAssignableFrom(field.getType())){
                                field.set(i, v.getObject());

                            }else{
                                field.set(i, fromJson(field.getType(), (JsonObject) v));
                            }
                            break;

                        case 4: //BOOLEAN
                            if(field.getType().equals(boolean.class)){
                                field.set(i, v.getObject());
                            }
                            break;

                        case 5: //NULL
                            field.set(i, null);
                            break;
                    }

                    /*
                    BYTES = 0
                    NUMBER = 1
                    ARRAY = 2
                    OBJECT = 3
                    BOOLEAN = 4
                    NULL = 5
                    */

                    //System.err.println(v.);
                    //if(v instanceof String){
                        //System.err.println("STRING  "+v);
                    //}
                    /*
                    if(String.class.isAssignableFrom(field.getType())){
                        field.set(i, j.getString(k));

                    }else if(field.getType().equals(int.class)){
                        field.set(i, j.getInteger(k));

                    }else if(field.getType().equals(long.class)){
                        //}else if(Long.class.isAssignableFrom(method.getParameterTypes()[0])){
                        field.set(i, j.getLong(k));

                    }else if(field.getType().equals(double.class)){
                        //}else if(Double.class.isAssignableFrom(method.getParameterTypes()[0])){
                        field.set(i, j.getDouble(k));

                    }else if(field.getType().equals(byte.class)){
                        //}else if(Byte.class.isAssignableFrom(method.getParameterTypes()[0])){
                        field.set(i, j.getBytes(k));

                    }else if(field.getType().equals(boolean.class)){
                        //}else if(Boolean.class.isAssignableFrom(method.getParameterTypes()[0])){
                        field.set(i, j.getBoolean(k));

                    }else if(List.class.isAssignableFrom(field.getType())){
                        field.set(i, j.getJsonArray(k).getObject());

                    }else{// if(Map.class.isAssignableFrom(field.getType())){

                        //System.out.println(field.getType()+"  "+field.getGenericType().getClass());

                        //ParameterizedType listType = (ParameterizedType) field.getType();
                        //Class<?> cl = Class.forName(field.getType().getActualTypeArguments()[0].getTypeName());

                        //Class<?> fieldType = field.getType();
                        //System.out.println(field.getType().equals(Object.class)+"  "+Object.class.isAssignableFrom(field.getType()));

                        //field.set(i, fromJson(field.getType(), j.getJsonObject(k)));
                        //field.set(i, j.getJsonObject(k).getObject());
                    }
                    */
                }
            }
        }

        /*
        for(Method method : i.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(JsonAnnotation.class)){
                if(method.getReturnType() == void.class){
                    final String k = method.getAnnotation(JsonAnnotation.class).key();

                    if(j.containsKey(k)){
                        if(String.class.isAssignableFrom(method.getParameterTypes()[0])){
                            method.invoke(i, j.getString(k));

                        }else if(method.getParameterTypes()[0] == int.class){
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
                    }
                }
            }
        }
        */

        return i;
    }

    public static JsonObject toJson(Object o)throws IllegalAccessException {
        JsonObject j = new JsonObject();
        /*
        for(Method method : o.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(JsonAnnotation.class)){
                try{
                    if(method.getReturnType() != void.class){
                        j.put(method.getAnnotation(JsonAnnotation.class).key(), method.invoke(o));
                    }
                }catch(ReflectiveOperationException e){
                    e.printStackTrace();
                }
            }
        }
        */

        for(Field field : o.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).serialize()){
                field.setAccessible(true);

                if(field.getType().isPrimitive() ||
                        field.getType() == Object.class ||
                        String.class.isAssignableFrom(field.getType()) ||
                        List.class.isAssignableFrom(field.getType()) ||
                        Map.class.isAssignableFrom(field.getType())){

                    j.put(field.getName(), field.get(o));

                }else if(field.get(o) != null){
                    j.put(field.getName(), toJson(field.get(o)));
                }
            }
        }

        return j;
    }


    public void decodeArray(JsonArray j, byte[] buf, int off){
        this.buf = buf;
        pos = off;

        trim();

        if(buf[pos] == '['){
            pos++;

            while(buf[pos] != ']'){
                trim();
                j.add(get());
            }
            pos++;
        }
    }

    public void decodeObject(JsonObject j, byte[] buf, int off){
        this.buf = buf;
        pos = off;

        trim();

        if(buf[pos] == '{'){
            pos++;

            while(buf[pos] != '}'){
                trim();
                j.put(getString(), get());
            }
            pos++;
        }
    }

    private void put(JsonVariable v){
        if(v instanceof JsonString){
            put((JsonString) v);
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
    private void put(JsonString v){
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
        for(int i = 0; i < l.size()-1; i++){
            put(l.valueOf(i));
            buf[pos] = ',';
            pos++;
        }
        put(l.valueOf(l.size()-1));
        buf[pos] = ']';
        pos++;
    }

    private void put(JsonObject m){
        buf[pos] = '{';
        pos++;

        int i = 0;
        for(JsonString k : m.keySet()){
            put(k);
            buf[pos] = ':';
            pos++;
            put(m.valueOf(k));

            i++;
            if(i < m.size()){
                buf[pos] = ',';
                pos++;
            }
        }
        buf[pos] = '}';
        pos++;
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
                return getString();

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
                return getObject();

            case '[':
                return getArray();

            default:
                if(isNumber()){
                    return getNumber();
                }
        }

        return null;
    }

    private JsonNull getNull(){
        pos += 5;
        trim();
        return new JsonNull();
    }

    private JsonBoolean getBoolean(boolean bool){
        pos += (bool) ? 5 : 6;
        trim();

        return new JsonBoolean(bool);
    }

    private JsonNumber getNumber(){
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

    private JsonString getString(){
        pos++;

        int s = pos;
        while(buf[pos] != '"'){
            pos++;
        }

        byte[] b = new byte[pos-s];
        System.arraycopy(buf, s, b, 0, b.length);

        pos++;
        trim();

        return new JsonString(b);
    }


    private JsonArray getArray(){
        trim();

        if(buf[pos] == '['){
            JsonArray j = new JsonArray();
            //List<JsonVariable> a = new ArrayList<>();
            pos++;

            while(buf[pos] != ']'){
                trim();
                j.add(get());
            }
            pos++;
            return j;
        }
        return null;
    }

    private JsonObject getObject(){
        trim();

        if(buf[pos] == '{'){
            JsonObject j = new JsonObject();
            //Map<JsonString, JsonVariable> m = new HashMap<>();
            pos++;

            while(buf[pos] != '}'){
                trim();
                j.put(getString(), get());
            }
            pos++;
            return j;
        }
        return null;
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
                buf[pos] == '-' ||
                buf[pos] == '.');
    }
}
