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

    public Object readToClass(Class<?> c)throws ReflectiveOperationException, IOException, ParseException {
        read();
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
        while(peek() != '}'){
            read();
            String k = getString();
            read();

            try{
                Field field = c.getDeclaredField(k);

                if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).deserialize()){
                    field.setAccessible(true);

                    if(field.getType().isPrimitive() ||
                            field.getType() == Object.class ||
                            String.class.isAssignableFrom(field.getType()) ||
                            List.class.isAssignableFrom(field.getType()) ||
                            Map.class.isAssignableFrom(field.getType())){

                        field.set(i, get(c));
                        continue;

                    }else{
                        field.set(i, get(field.getType()));
                        continue;
                    }
                }
            }catch(NoSuchFieldError e){
            }

            get(c);
        }

        read();

        return i;
    }

    private List<Object> getArray(Class<?> c)throws ReflectiveOperationException, IOException, ParseException {
        List<Object> l = new ArrayList<>();
        while(peek() != ']'){
            read();
            l.add(get(c));
        }

        read();

        return l;
    }

    private String getString()throws IOException {
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
                }

                read();

            }else{
                a = 0;
                b[i] = read();
            }

            i++;

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
            return new String(r);
        }

        return new String(b);
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
