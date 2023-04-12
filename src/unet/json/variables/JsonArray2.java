package unet.json.variables;

import unet.json.Json;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class JsonArray2 implements JsonVariable, JsonObserver {

    private List<Object> l = new ArrayList<>();
    private JsonObserver o;
    private int s = 2;

    public JsonArray2(){
    }

    public JsonArray2(List<?> l){
        for(Object v : l){
            add(v);
        }
        //this.l = l;
        /*
        for(Object v : l){
            if(v instanceof JsonVariable){
                add((JsonVariable) v);
            }else if(v instanceof Number){
                add(new JsonNumber(v.toString()));
            }else if(v instanceof String){
                add(new JsonBytes(((String) v).getBytes()));
            }else if(v instanceof byte[]){
                add(new JsonBytes((byte[]) v));
            }else if(v instanceof List<?>){
                add(new JsonArray2((List<?>) v));
            }else if(v instanceof Map<?, ?>){
                add(new JsonObject((Map<?, ?>) v));
            }
        }*/
    }

    public JsonArray2(byte[] buf){
        //this(new Json().decodeArray(buf, 0));
    }

    public JsonArray2(byte[] buf, int off){
        //this(new Json().decodeArray(buf, off));
    }

    /*
    private void add(JsonVariable v){
        l.add(v);
        setByteSize(v.byteSize()+1);

        if(v instanceof JsonObject){
            ((JsonObject) v).setObserver(this);
        }else if(v instanceof JsonArray2){
            ((JsonArray2) v).setObserver(this);
        }
    }
    */

    public void add(boolean b){
        setByteSize((b) ? 5 : 6);
        l.add(b);
    }

    public void add(Integer i){
        setByteSize(String.valueOf(i).getBytes().length+1);
        l.add(i);
    }

    public void add(Long n){
        setByteSize(String.valueOf(n).getBytes().length+1);
        l.add(n);
    }

    public void add(Double d){
        setByteSize(String.valueOf(d).getBytes().length+1);
        l.add(d);
    }

    public void add(JsonArray a){
        l.add(a);
        setByteSize(a.byteSize()+1);
        a.setObserver(this);
    }

    public void add(JsonObject o){
        l.add(o);
        setByteSize(o.byteSize()+1);
        o.setObserver(this);
    }

    /*
    public void add(JsonVariable v){
        l.add(v);
    }

    /*
    public void add(JsonArray a){
        l.add(a);
        setByteSize(a.byteSize()+1);
        a.setObserver(this);
    }

    public void add(JsonObject o){
        l.add(o);
        setByteSize(o.byteSize()+1);
        o.setObserver(this);
    }
    */

    public void add(Object v){
        if(v == null ||
                v instanceof String ||
                v instanceof Boolean ||
                v instanceof Integer ||
                v instanceof Long ||
                v instanceof Double ||
                v instanceof JsonObject2 ||
                v instanceof JsonArray2){
            l.add(v);

        }else if(v instanceof List<?>){
            l.add(new JsonArray((List<?>) v));

        }else if(v instanceof Map<?, ?>){
            l.add(new JsonObject2((Map<?, ?>) v));
        }
    }

    /*
    private void set(int i, JsonVariable v){
        l.set(i, v);
        //setByteSize(-l.get(i).byteSize()+v.byteSize());

        if(v instanceof JsonObject){
            ((JsonObject) v).setObserver(this);
        }else if(v instanceof JsonArray2){
            ((JsonArray2) v).setObserver(this);
        }
    }
    */

    public void set(int i, Boolean b){
        l.set(i, b);
    }

    public void set(int i, Integer j){
        l.set(i, j);
    }

    public void set(int i, Long n){
        l.set(i, n);
    }

    public void set(int i, Double d){
        l.set(i, d);
    }

    public void set(int i, JsonArray a){
        l.set(i, a);
        //setByteSize(-l.get(i).byteSize()+a.byteSize());
        a.setObserver(this);
    }

    public void set(int i, JsonObject o){
        l.set(i, o);
        //setByteSize(-l.get(i).byteSize()+o.byteSize());
        o.setObserver(this);
    }

    public void set(int i, Object v){
        if(v == null ||
                v instanceof String ||
                v instanceof Boolean ||
                v instanceof Integer ||
                v instanceof Long ||
                v instanceof Double){
            l.set(i, v);

        }else if(v instanceof List<?>){
            l.set(i, new JsonArray((List<?>) v));

        }else if(v instanceof Map<?, ?>){
            l.set(i, new JsonObject2((Map<?, ?>) v));
        }
    }
/*
    public JsonVariable valueOf(int i){
        return l.get(i);
    }
*/
    public Object get(int i){
        return l.get(i);
    }

    public Integer getInteger(int i){
        return ((Number) l.get(i)).intValue();
    }

    public Long getLong(int i){
        return ((Number) l.get(i)).longValue();
    }

    public Short getShort(int i){
        return ((Number) l.get(i)).shortValue();
    }

    public Double getDouble(int i){
        return ((Number) l.get(i)).doubleValue();
    }

    public Float getFloat(int i){
        return ((Number) l.get(i)).floatValue();
    }

    public Boolean getBoolean(int i){
        return ((Boolean) l.get(i)).booleanValue();
    }

    public String getString(int i){
        return (String) l.get(i);
    }
/*
    public byte[] getBytes(int i){
        return (byte[]) l.get(i).getObject();
    }
*/
    public JsonArray2 getJsonArray(int i){
        return (JsonArray2) l.get(i);
    }

    public JsonObject2 getJsonObject(int i){
        return (JsonObject2) l.get(i);
    }

    public boolean contains(Number n){
        return l.contains(new JsonNumber(n.toString()));
    }

    public boolean contains(Boolean b){
        return l.contains(new JsonBoolean(b));
    }

    public boolean contains(String s){
        return l.contains(new JsonBytes(s.getBytes()));
    }

    public boolean contains(byte[] b){
        return l.contains(new JsonBytes(b));
    }

    public boolean contains(List<?> l){
        return this.l.contains(new JsonArray2(l));
    }

    public boolean contains(Map<?, ?> m){
        return l.contains(new JsonObject2(m));
    }

    public boolean contains(JsonArray2 a){
        return l.contains(a);
    }

    public boolean contains(JsonObject2 o){
        return l.contains(o);
    }

    private void remove(JsonVariable v){
        if(l.contains(v)){
            l.remove(v);
            //setByteSize(-v.byteSize());
        }
    }

    public void remove(Number n){
        remove(n);
    }

    public void remove(byte[] b){
        remove(b);
    }

    public void remove(String s){
        remove(s);
    }

    private int indexOf(Object v){
        if(l.contains(v)){
            return l.indexOf(v);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int indexOf(Number n){
        return indexOf(n.toString());
    }
/*
    public int indexOf(byte[] b){
        return indexOf(new JsonBytes(b));
    }
*/
    public int indexOf(String s){
        return indexOf(s.getBytes());
    }

    public int size(){
        return l.size();
    }






    protected void setObserver(JsonObserver observer){
        o = observer;
    }

    private void setByteSize(int s){
        if(o != null){
            o.update(s);
        }
        this.s += s;
    }

    @Override
    public void update(int s){
        if(o != null){
            o.update(s);
        }
        this.s += s;
    }

    @Override
    public Object getObject(){
        /*
        ArrayList<Object> a = new ArrayList<>();
        for(JsonVariable v : l){
            a.add(v.getObject());
        }
        return a;
        */
        return l;
    }

    @Override
    public int byteSize(){
        return s-((l.isEmpty()) ? 0 : 1);
    }

    @Override
    public int hashCode(){
        return 2;
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder("[\r\n");

        for(Object v : l){
            if(v instanceof Number){
                b.append("\t\033[0;31m"+v+"\033[0m\r\n");

            }else if(v instanceof Boolean){
                b.append("\t\033[0;35m"+v+"\033[0m\r\n");

            }else if(v == null){
                b.append("\t\033[0;36m"+v+"\033[0m\r\n");

            }else if(v instanceof String){
                b.append("\t\033[0;34m"+v+"\033[0m\r\n");

            }else if(v instanceof JsonArray2){
                b.append("\t\033[0m"+v.toString().replaceAll("\\r?\\n", "\r\n\t")+"\r\n");

            }else if(v instanceof JsonObject2){
                b.append("\t\033[0m"+v.toString().replaceAll("\\r?\\n", "\r\n\t")+"\r\n");
            }
        }

        return b+"]";
    }

    public byte[] encode(){
        return new Json().encode(this);
    }
}
