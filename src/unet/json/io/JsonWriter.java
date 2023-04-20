package unet.json.io;

import unet.json.variables.*;

import java.io.IOException;
import java.io.OutputStream;

public class JsonWriter {

    private OutputStream out;

    public JsonWriter(OutputStream out){
        this.out = out;
    }

    public void write(JsonArray l)throws IOException {
        out.write('[');

        if(l.size() > 0){
            for(int i = 0; i < l.size()-1; i++){
                write(l.valueOf(i));
                out.write(',');
            }
            write(l.valueOf(l.size()-1));
        }
        out.write(']');
    }

    public void write(JsonObject m)throws IOException {
        out.write('{');

        if(m.size() > 0){
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
        }
        out.write('}');
    }

    public void flush()throws IOException {
        out.flush();
    }

    public void close()throws IOException {
        out.close();
    }

    private void write(JsonVariable v)throws IOException {
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
    }

    private void write(JsonString v)throws IOException {
        out.write(v.getBytes());
    }

    private void write(JsonNumber n)throws IOException {
        out.write(n.getBytes());
    }

    private void write(JsonBoolean b)throws IOException {
        out.write(b.getBytes());
    }

    private void write(JsonNull n)throws IOException {
        out.write(n.getBytes());
    }
}
