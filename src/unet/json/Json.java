package unet.json;

import unet.json.variables.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Json {

    private byte[] buf;
    private int pos = 0;

    //SERIALIZE JSON - PRETTY AND NOT
    //REMOVE OBSERVER IF SET OR PUT
    //FINISH SANITIZATION...
    //Annotation object/arrays within object... - SHOULD ALLOW SUB CLASSES...
    //Annotation Input Stream to/from i/o
    //Annotation to/from bytes
    //WE SHOULD BE USING HASH-CODES NOT INSTANCES...
    //BETTER NUMBER SYSTEM
    //BETTER CHAR COUNTER SYSTEM...

    public byte[] encode(JsonArray2 l){
        buf = new byte[l.byteSize()];
        put(l);
        return buf;
    }

    public byte[] encode(JsonObject2 m){
        buf = new byte[m.byteSize()];
        put(m);
        return buf;
    }

    public static Object fromJson(Class<?> c, JsonObject2 j)throws ReflectiveOperationException {
        Constructor<?> constructor = c.getDeclaredConstructor();
        Object i = constructor.newInstance();
/*
        for(Field field : c.getDeclaredFields()){
            if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).deserialize()){
                final String k = field.getName();

                if(j.containsKey(k)){
                    field.setAccessible(true);

                    JsonVariable v = j.valueOf(new JsonBytes(k.getBytes()));

                    switch(v.hashCode()){
                        case 0: //STRING
                            if(String.class.isAssignableFrom(field.getType())){
                                field.set(i, new String(((JsonBytes) v).getObject()));
                            }else if(field.getType().equals(byte[].class)){
                                field.set(i, v.getObject());
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
                    *//*
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

    public static JsonObject2 toJson(Object o)throws IllegalAccessException {
        JsonObject2 j = new JsonObject2();
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

                }else{
                    j.put(field.getName(), toJson(field.get(o)));
                }

            }
        }

        return j;
    }

/*
    public JsonArray2 decodeArray(byte[] buf, int off){
        this.buf = buf;
        pos = off;
        return getArray();
    }
*/
    public void decodeArray(JsonArray2 j, byte[] buf, int off){
        this.buf = buf;
        pos = off;
        trim();

        if(buf[pos] == '['){
            pos++;

            while(buf[pos] != ']'){
                trim();
                try{
                    j.add(get());
                }catch(ParseException e){
                }
            }
            pos++;
        //}else{
            //FAILED TO PARSE....
            //throw new
        }
    }

    public void decodeObject(JsonObject2 j, byte[] buf, int off){
        this.buf = buf;
        pos = off;
        trim();

        if(buf[pos] == '{'){
            //JsonObject2 j = new JsonObject2();
            pos++;

            while(buf[pos] != '}'){
                trim();
                //try{
                    System.out.println("K"+getString());
                    break;
                //    j.put(getString(), get());
                //}catch(ParseException e){
                //}
            }
            pos++;
        //}else{
            //FAILED TO PARSE....
            //throw new
        }
    }
    /*
    public JsonObject2 decodeObject(byte[] buf, int off){
        this.buf = buf;
        pos = off;
        return getObject();
    }
*/
    private void put(Object v){
        if(v instanceof String){
            put((String) v);

        }else if(v instanceof Number){
            put((Number) v);

        }else if(v instanceof Boolean){
            put((boolean) v);

        }else if(v instanceof JsonArray2){
            put((JsonArray2) v);

        }else if(v instanceof JsonObject2){
            put((JsonObject2) v);

        }else{
            byte[] b = { 'n', 'u', 'l', 'l' };
            System.arraycopy(b, 0, buf, pos, b.length);
            pos += b.length;
        }
        /*
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
        */
    }

    //THIS SEEMS REDUNDANT....
    private void put(String v){
        byte[] b = v.getBytes();
        buf[pos] = '"';
        System.arraycopy(b, 0, buf, pos+1, b.length);
        pos += b.length+2;
        buf[pos-1] = '"';
    }

    private void put(Number n){
        byte[] b = n.toString().getBytes();
        System.arraycopy(b, 0, buf, pos, b.length);
        pos += b.length;
    }

    private void put(boolean n){
        byte[] b = (n) ? new byte[]{
                't',
                'r',
                'u',
                'e'
        } : new byte[]{
                'f',
                'a',
                'l',
                's',
                'e'
        };
        System.arraycopy(b, 0, buf, pos, b.length);
        pos += b.length;
    }
/*
    private void put(JsonNull n){
        byte[] b = n.getBytes();
        System.arraycopy(b, 0, buf, pos, b.length);
        pos += b.length;
    }
    //THIS SEEMS REDUNDANT
*/
    private void put(JsonArray2 l){
        buf[pos] = '[';
        pos++;
        for(int i = 0; i < l.size()-1; i++){
            put(l.get(i));
            buf[pos] = ',';
            pos++;
        }
        put(l.get(l.size()-1));
        buf[pos] = ']';
        pos++;
    }

    private void put(JsonObject2 m){
        buf[pos] = '{';
        pos++;

        int i = 0;
        for(String k : m.keySet()){
            put(k);
            buf[pos] = ':';
            pos++;
            put(m.get(k));

            i++;
            if(i < m.size()){
                buf[pos] = ',';
                pos++;
            }
        }
        buf[pos] = '}';
        pos++;
    }















    private Object get()throws ParseException {
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
                return true;//getBoolean(true);

            case 'T':
                return true;//getBoolean(true);

            case 'f':
                return false;//getBoolean(false);

            case 'F':
                return false;//getBoolean(false);

            case 'n': //CAN WE JUST SKIP SOME-HOW??? - DEFAULT
                return null;//getNull();

            case 'N': //CAN WE JUST SKIP SOME-HOW??? - DEFAULT
                return null;//getNull();

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


    private JsonArray2 getArray(){
        trim();

        if(buf[pos] == '['){
            JsonArray2 j = new JsonArray2();
            pos++;

            while(buf[pos] != ']'){
                trim();
                try{
                    j.add(get());
                }catch(ParseException e){
                }
            }
            pos++;
            return j;
        }
        return null;
    }

    private JsonObject2 getObject(){
        trim();

        if(buf[pos] == '{'){
            JsonObject2 j = new JsonObject2();
            pos++;

            while(buf[pos] != '}'){
                trim();
                try{
                    j.put(getString(), get());
                }catch(ParseException e){
                }
            }
            pos++;
            return j;
        }
        return null;
    }
/*
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
*/
    private Number getNumber()throws ParseException {
        int s = pos;
        while(isNumber()){
            pos++;
        }

        byte[] b = new byte[pos-s];
        System.arraycopy(buf, s, b, 0, b.length);

        pos++;
        trim();

        return NumberFormat.getInstance().parse(new String(b));
    }

    private String getString(){
        pos++;

        int s = pos;
        while(buf[pos] != '"'){
            pos++;
        }

        byte[] b = new byte[pos-s];
        System.arraycopy(buf, s, b, 0, b.length);

        pos++;
        trim();

        return new String(b);
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
