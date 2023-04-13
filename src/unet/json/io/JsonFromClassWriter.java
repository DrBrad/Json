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

    public void writeClass(Object o)throws ReflectiveOperationException, IOException {
        out.write('{');

        boolean r = false;
        for(Field field : o.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(JsonExpose.class) && field.getAnnotation(JsonExpose.class).serialize()){
                field.setAccessible(true);

                if(r){
                    out.write(',');
                }else{
                    r = true;
                }

                write(field.getName());
                out.write(':');
                write(field.get(o));

            }
        }
        out.write('}');
    }

    public void flush()throws IOException {
        out.flush();
    }

    public void close()throws IOException {
        out.close();
    }

    private void write(Object v)throws ReflectiveOperationException, IOException {
        if(v == null){
            write();
        //}else if(v instanceof JsonVariable){
        //    add((JsonVariable) v);
        }else if(v instanceof Number){
            write((Number) v);
        }else if(v instanceof Boolean){
            write((Boolean) v);
        }else if(v instanceof String){
            write((String) v);
        }else if(v instanceof List<?>){
            write((List<?>) v);
        }else if(v instanceof Map<?, ?>){
            write((Map<?, ?>) v);
        }else{
            writeClass(v);
        }
    }

    private void write(List<?> l)throws ReflectiveOperationException, IOException {
        out.write('[');
        for(int i = 0; i < l.size()-1; i++){
            write(l.get(i));
            out.write(',');
        }
        write(l.get(l.size()-1));
        out.write(']');
    }

    private void write(Map<?, ?> m)throws ReflectiveOperationException, IOException {
        out.write('{');

        int i = 0;
        for(Object k : m.keySet()){
            i++;
            if(k instanceof String){
                write((String) k);
                out.write(':');
                write(m.get(k));

                if(i < m.size()){
                    out.write(',');
                }
            }
        }
        out.write('}');
    }

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
