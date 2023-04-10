package unet.json.variables;

import unet.json.Json;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class JsonArray implements JsonVariable, JsonObserver {

    private ArrayList<JsonVariable> l = new ArrayList<>();
    private JsonObserver o;
    private int s = 2;

    public JsonArray(){
    }

    public JsonArray(List<?> l){
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
                add(new JsonArray((List<?>) v));
            }else if(v instanceof Map<?, ?>){
                add(new JsonObject((Map<?, ?>) v));
            }
        }
    }

    public JsonArray(byte[] buf){
        this(new Json().decodeArray(buf, 0));
    }

    public JsonArray(byte[] buf, int off){
        this(new Json().decodeArray(buf, off));
    }

    private void add(JsonVariable v){
        l.add(v);
        setByteSize(v.byteSize()+1);

        if(v instanceof JsonObject){
            ((JsonObject) v).setObserver(this);
        }else if(v instanceof JsonArray){
            ((JsonArray) v).setObserver(this);
        }
    }

    public void add(Boolean b){
        add(new JsonBoolean(b));
    }

    public void add(Integer i){
        add(new JsonNumber(Integer.toString(i)));
    }

    public void add(Long l){
        add(new JsonNumber(Long.toString(l)));
    }

    public void add(Double d){
        add(new JsonNumber(Double.toString(d)));
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

    public void add(Object o){
        if(o == null){
            add(new JsonNull());

        }else if(o instanceof String){
            add(new JsonBytes(((String) o).getBytes()));

        }else if(o instanceof byte[]){
            add(new JsonBytes((byte[]) o));

        }else if(o instanceof List<?>){
            add(new JsonArray((List<?>) o));

        }else if(o instanceof Map<?, ?>){
            add(new JsonObject((Map<?, ?>) o));

        }else if(o instanceof JsonVariable){
            add((JsonVariable) o);

        }else if(o instanceof Boolean){
            add(new JsonBoolean((Boolean) o));

        }else if(o instanceof Integer){
            add(new JsonNumber(Integer.toString((Integer) o)));

        }else if(o instanceof Long){
            add(new JsonNumber(Long.toString((Long) o)));

        }else if(o instanceof Double){
            add(new JsonNumber(Double.toString((Double) o)));
        }
    }

    private void set(int i, JsonVariable v){
        l.set(i, v);
        setByteSize(-l.get(i).byteSize()+v.byteSize());

        if(v instanceof JsonObject){
            ((JsonObject) v).setObserver(this);
        }else if(v instanceof JsonArray){
            ((JsonArray) v).setObserver(this);
        }
    }

    public void set(int i, Boolean b){
        set(i, new JsonBoolean(b));
    }

    public void set(int i, Integer j){
        set(i, new JsonNumber(Integer.toString(j)));
    }

    public void set(int i, Long l){
        set(i, new JsonNumber(Long.toString(l)));
    }

    public void set(int i, Double d){
        set(i, new JsonNumber(Double.toString(d)));
    }

    /*
    public void set(int i, JsonArray a){
        l.set(i, a);
        setByteSize(-l.get(i).byteSize()+a.byteSize());
        a.setObserver(this);
    }

    public void set(int i, JsonObject o){
        l.set(i, o);
        setByteSize(-l.get(i).byteSize()+o.byteSize());
        o.setObserver(this);
    }
    */

    public void set(int i, Object o){
        if(o == null){
            set(i, new JsonNull());

        }else if(o instanceof String){
            set(i, new JsonBytes(((String) o).getBytes()));

        }else if(o instanceof byte[]){
            set(i, new JsonBytes((byte[]) o));

        }else if(o instanceof List<?>){
            set(i, new JsonArray((List<?>) o));

        }else if(o instanceof Map<?, ?>){
            set(i, new JsonObject((Map<?, ?>) o));

        }else if(o instanceof JsonVariable){
            set(i, (JsonVariable) o);

        }else if(o instanceof Boolean){
            set(i, new JsonBoolean((Boolean) o));

        }else if(o instanceof Integer){
            set(i, new JsonNumber(Integer.toString((Integer) o)));

        }else if(o instanceof Long){
            set(i, new JsonNumber(Long.toString((Long) o)));

        }else if(o instanceof Double){
            set(i, new JsonNumber(Double.toString((Double) o)));
        }
    }

    public JsonVariable valueOf(int i){
        return l.get(i);
    }

    public Object get(int i){
        return l.get(i).getObject();
    }

    public Integer getInteger(int i){
        return ((Number) l.get(i).getObject()).intValue();
    }

    public Long getLong(int i){
        return ((Number) l.get(i).getObject()).longValue();
    }

    public Short getShort(int i){
        return ((Number) l.get(i).getObject()).shortValue();
    }

    public Double getDouble(int i){
        return ((Number) l.get(i).getObject()).doubleValue();
    }

    public Float getFloat(int i){
        return ((Number) l.get(i).getObject()).floatValue();
    }

    public Boolean getBoolean(int i){
        return ((Boolean) l.get(i).getObject()).booleanValue();
    }

    public String getString(int i){
        return new String((byte[]) l.get(i).getObject());
    }

    public byte[] getBytes(int i){
        return (byte[]) l.get(i).getObject();
    }

    public JsonArray getJsonArray(int i){
        return (JsonArray) l.get(i);
    }

    public JsonObject getJsonObject(int i){
        return (JsonObject) l.get(i);
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
        return this.l.contains(new JsonArray(l));
    }

    public boolean contains(Map<?, ?> m){
        return l.contains(new JsonObject(m));
    }

    public boolean contains(JsonArray a){
        return l.contains(a);
    }

    public boolean contains(JsonObject o){
        return l.contains(o);
    }

    private void remove(JsonVariable v){
        if(l.contains(v)){
            l.remove(v);
            setByteSize(-v.byteSize());
        }
    }

    public void remove(Number n){
        remove(new JsonNumber(n.toString()));
    }

    public void remove(byte[] b){
        remove(new JsonBytes(b));
    }

    public void remove(String s){
        remove(new JsonBytes(s.getBytes()));
    }

    private int indexOf(JsonVariable v){
        if(l.contains(v)){
            return l.indexOf(v);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int indexOf(Number n){
        return indexOf(new JsonNumber(n.toString()));
    }

    public int indexOf(byte[] b){
        return indexOf(new JsonBytes(b));
    }

    public int indexOf(String s){
        return indexOf(new JsonBytes(s.getBytes()));
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
        ArrayList<Object> a = new ArrayList<>();
        for(JsonVariable v : l){
            a.add(v.getObject());
        }
        return a;
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

        for(JsonVariable v : l){
            if(v instanceof JsonNumber){
                b.append("\t\033[0;31m"+v.getObject()+"\033[0m\r\n");

            }else if(v instanceof JsonBoolean){
                b.append("\t\033[0;35m"+v.getObject()+"\033[0m\r\n");

            }else if(v instanceof JsonNull){
                b.append("\t\033[0;36m"+v.getObject()+"\033[0m\r\n");

            }else if(v instanceof JsonBytes){
                if(Charset.forName("US-ASCII").newEncoder().canEncode(new String((byte[]) v.getObject()))){
                    b.append("\t\033[0;34m"+new String((byte[]) v.getObject(), StandardCharsets.UTF_8)+"\033[0m\r\n");

                }else{
                    b.append("\t\033[0;34mBASE64 { "+Base64.getEncoder().encodeToString((byte[]) v.getObject())+" }\033[0m\r\n");
                }

            }else if(v instanceof JsonArray){
                b.append("\t\033[0m"+v.toString().replaceAll("\\r?\\n", "\r\n\t")+"\r\n");

            }else if(v instanceof JsonObject){
                b.append("\t\033[0m"+v.toString().replaceAll("\\r?\\n", "\r\n\t")+"\r\n");
            }
        }

        return b+"]";
    }

    public byte[] encode(){
        return new Json().encode(this);
    }
}
