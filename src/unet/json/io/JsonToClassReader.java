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

public class JsonToClassReader {

    private InputStream in;

    private byte n;

    public JsonToClassReader(InputStream in){
        this.in = in;
    }

    //WE MAY WANT TO MOVE THIS TO ITS OWN CLASS...
    public Object readToClass(Class<?> c)throws ReflectiveOperationException, IOException, ParseException {
        //Constructor<?> constructor = c.getDeclaredConstructor();
        //Object i = constructor.newInstance();

        /*
        Map<String, Field> fields = new HashMap<>();

        //DO STUFF...
        for(Field field : c.getDeclaredFields()){
            if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).deserialize()){
                fields.put(field.getName(), field);
            }
        }
        */
        //c.getDeclaredField("a")


        read();
        //new JsonObject(getObject());

        return getObject(c);
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

    private Object get(Class<?> c)throws ReflectiveOperationException, IOException, ParseException {
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
                return true;

            case 'T':
                in.skip(3);
                read();
                return true;

            case 'f':
                in.skip(4);
                read();
                return false;

            case 'F':
                in.skip(4);
                read();
                return false;

            case 'n':
                in.skip(3);
                read();
                return null;

            case 'N':
                in.skip(3);
                read();
                return null;

            case '{':
                return getObject(c);

            case '[':
                //return getArray();

            default:
                if(isNumber(peek())){
                    return getNumber();
                }
        }

        return null;
    }

    private Object getObject(Class<?> c)throws ReflectiveOperationException, IOException, ParseException {
        Constructor<?> constructor = c.getDeclaredConstructor();
        Object i = constructor.newInstance();
        //Map<JsonString, JsonVariable> m = new HashMap<>();
        while(peek() != '}'){
            read();
            String k = getString();
            read();

            Object v = get(c);


            try{
                Field field = c.getDeclaredField(k);


                if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).deserialize()){
                    field.setAccessible(true);

                    switch(v.hashCode()){
                        case 0: //STRING
                            if(String.class.isAssignableFrom(field.getType())){
                                field.set(i, v);
                            }
                            break;

                        case 1: //NUMBER
                            if(field.getType().equals(int.class)){
                                field.set(i, ((Integer) v).doubleValue());

                            }else if(field.getType().equals(long.class)){
                                //}else if(Long.class.isAssignableFrom(method.getParameterTypes()[0])){
                                field.set(i, ((Long) v).longValue());

                            }else if(field.getType().equals(double.class)){
                                //}else if(Double.class.isAssignableFrom(method.getParameterTypes()[0])){
                                field.set(i, ((Double) v).doubleValue());
                            }
                            break;

                        case 2: //ARRAY
                            if(List.class.isAssignableFrom(field.getType())){
                                field.set(i, v);
                            }
                            break;
                    /*
                    case 3: //OBJECT
                        if(Map.class.isAssignableFrom(field.getType())){
                            field.set(i, v);

                        }else{
                            field.set(i, fromJson(field.getType(), (JsonObject) v));
                        }
                        break;

                    case 4: //BOOLEAN
                        if(field.getType().equals(boolean.class)){
                            field.set(i, v.getObject());
                        }
                        break;*/

                        case 5: //NULL
                            field.set(i, null);
                            break;
                    }
                }

            }catch(NoSuchFieldError e){

            }
        }

        read();

        return i;
    }

    private List<JsonVariable> getArray()throws IOException {
        List<JsonVariable> l = new ArrayList<>();
        while(peek() != ']'){
            read();
            //l.add(get());
        }

        read();

        return l;
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
