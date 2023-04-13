package unet.json.io;

import unet.json.variables.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class JsonFromClassWriter {

    private OutputStream out;

    public JsonFromClassWriter(OutputStream out){
        this.out = out;
    }

    public void write(Object o)throws ReflectiveOperationException, IOException {
        out.write('{');

        for(Field field : o.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).serialize()){
                field.setAccessible(true);


                write(field.getName());
                out.write(':');

                if(field.get(o) == null){
                    out.write(new byte[]{
                            'n',
                            'u',
                            'l',
                            'l'
                    });
                    out.write(',');

                }else if(field.getType().isPrimitive() ||
                        field.getType() == Object.class ||
                        String.class.isAssignableFrom(field.getType()) ||
                        List.class.isAssignableFrom(field.getType()) ||
                        Map.class.isAssignableFrom(field.getType())){

                    write(field.get(o));
                    //System.err.println(field.get(o));
                    //j.put(field.getName(), field.get(o));

                }else{// if(field.get(o) != null){
                    //j.put(field.getName(), toJson(field.get(o)));
                }
            }
        }
        /*
        int i = 0;
        for(JsonString k : m.keySet()){
            write(k);
            out.write(':');
            write(m.valueOf(k));

            i++;
            if(i < m.size()){
                out.write(',');
            }
        }
        */
        out.write('}');
    }

    public void flush()throws IOException {
        out.flush();
    }

    public void close()throws IOException {
        out.close();
    }

    //private void write(Object v)throws IOException {
        /*
        if(v instanceof JsonString){
            write((JsonString) v);
        }else if(v instanceof JsonNumber){
            write((JsonNumber) v);
        }else if(v instanceof JsonBoolean){
            write((JsonBoolean) v);
        }else if(v instanceof JsonNull){
            write((JsonNull) v);
        }else if(v instanceof JsonArray){
            write((JsonArray) v);
        }else if(v instanceof JsonObject){
            write((JsonObject) v);
        }
        */
        //return;
    //}

    private void write(String v)throws IOException {
        out.write('"');
        out.write(v.getBytes());
        out.write('"');
    }

    private void write(Number n)throws IOException {
        out.write(n.toString().getBytes());
    }

    private void write(Boolean b)throws IOException {
        out.write((b) ? new byte[]{
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
            });
    }

    private void write()throws IOException {
        out.write(new byte[]{
                'n',
                'u',
                'l',
                'l'
            });
    }
}
