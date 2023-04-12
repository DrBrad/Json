package unet.json.variables;

import unet.json.Json;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonObject2 implements JsonVariable, JsonObserver {

    private Map<String, Object> m = new HashMap<>();
    private JsonObserver o;
    private int s = 2;

    public JsonObject2(){
    }

    public JsonObject2(Map<?, ?> m){
        for(Object o : m.keySet()){
            String k;

            if(o instanceof String){
                k = (String) o;
            }else{
                throw new IllegalArgumentException("Map keys must be in string form.");
            }

            put(k, m.get(o));
        }

        //this.m = m;
        return;
        /*
        for(Object o : m.keySet()){
            String k;

            if(o instanceof String){
                k = (String) o;
            }
            /*
            JsonBytes k;

            if(o instanceof JsonBytes){
                k = (JsonBytes) o;
            }else if(o instanceof String){
                k = new JsonBytes(((String) o).getBytes());
            }else{
                throw new IllegalArgumentException("Map keys must be in byte, string, or bencodebyte form.");
            }

            if(m.get(o) == null){
                put(k, null);
            }else if(m.get(o) instanceof JsonVariable){
                put(k, (JsonVariable) m.get(o));
            }else if(m.get(o) instanceof Number){
                put(k, new JsonNumber((m.get(o)).toString()));
            }else if(m.get(o) instanceof Boolean){
                put(k, new JsonBoolean((Boolean) m.get(o)));
            }else if(m.get(o) instanceof String){
                put(k, new JsonBytes(((String) m.get(o)).getBytes()));
            }else if(m.get(o) instanceof byte[]){
                put(k, new JsonBytes((byte[]) m.get(o)));
            }else if(m.get(o) instanceof List<?>){
                put(k, new JsonArray((List<?>) m.get(o)));
            }else if(m.get(o) instanceof Map<?, ?>){
                put(k, new JsonObject2((Map<?, ?>) m.get(o)));
            }
            */
        //}
    }

    public JsonObject2(byte[] buf){
        //this(new Json().decodeObject(buf, 0));
    }

    public JsonObject2(byte[] buf, int off){
        //this(new Json().decodeObject(buf, off));
    }
/*
    private void put(String k, Object v){
        m.put(k, v);
        //setByteSize(k.getBytes().byteSize()+v.byteSize()+2);

        if(v instanceof JsonObject2){
            ((JsonObject2) v).setObserver(this);
        }else if(v instanceof JsonArray){
            ((JsonArray) v).setObserver(this);
        }
    }
*/
    public void put(String k, boolean b){
        setByteSize((b) ? k.getBytes().length+6 : k.getBytes().length+7);
        m.put(k, b);
    }

    public void put(String k, int i){
        setByteSize(k.getBytes().length+String.valueOf(i).getBytes().length+2);
        m.put(k, i);
    }

    public void put(String k, long l){
        setByteSize(k.getBytes().length+String.valueOf(l).getBytes().length+2);
        m.put(k, l);
    }

    public void put(String k, double d){
        setByteSize(k.getBytes().length+String.valueOf(d).getBytes().length+2);
        m.put(k, d);
    }

    private void put(String k, JsonObject2 o){
        setByteSize(k.getBytes().length+o.byteSize()+2);
        o.setObserver(this);
        m.put(k, o);
    }

    private void put(String k, JsonArray2 a){
        setByteSize(k.getBytes().length+a.byteSize()+2);
        a.setObserver(this);
        m.put(k, a);
    }

    /*
    public void put(String k, JsonArray a){
        put(new JsonBytes(k.getBytes()), a);
        a.setObserver(this);
    }

    public void put(String k, JsonObject o){
        put(new JsonBytes(k.getBytes()), o);
        o.setObserver(this);
    }
    */

    public void put(String k, Object v){
        if(v == null ||
                v instanceof String ||
                v instanceof Boolean ||
                v instanceof Integer ||
                v instanceof Long ||
                v instanceof Double ||
                v instanceof JsonObject2 ||
                v instanceof JsonArray2){
            m.put(k, v);

        }else if(v instanceof List<?>){
            m.put(k, new JsonArray2((List<?>) v));

        }else if(v instanceof Map<?, ?>){
            m.put(k, new JsonObject2((Map<?, ?>) v));
        }
    }
/*
    public JsonVariable valueOf(JsonBytes k){
        return m.get(k);
    }
*/
    public Object get(String k){
        return m.get(k);
    }

    public Integer getInteger(String k){
        return ((Number) m.get(k)).intValue();
    }

    public Long getLong(String k){
        return ((Number) m.get(k)).longValue();
    }

    public Short getShort(String k){
        return ((Number) m.get(k)).shortValue();
    }

    public Double getDouble(String k){
        return ((Number) m.get(k)).doubleValue();
    }

    public Float getFloat(String k){
        return ((Number) m.get(k)).floatValue();
    }

    public Boolean getBoolean(String k){
        return ((Boolean) m.get(k)).booleanValue();
    }

    public String getString(String k){
        return (String) m.get(k);
    }

    /*
    public byte[] getBytes(String k){
        return (byte[]) m.get(new JsonBytes(k.getBytes()));
    }
    */

    public JsonArray2 getJsonArray(String k){
        return (JsonArray2) m.get(k);
    }

    public JsonObject2 getJsonObject(String k){
        return (JsonObject2) m.get(k);
    }

    public boolean containsKey(String k){
        return m.containsKey(k);
    }

    public boolean containsValue(Number n){
        return m.containsValue(n);
    }

    public boolean containsValue(Boolean b){
        return m.containsValue(b);
    }

    public boolean containsValue(String s){
        return m.containsValue(s);
    }
/*
    public boolean containsValue(byte[] b){
        return m.containsValue(new JsonBytes(b));
    }
*/
    public boolean containsValue(List<?> l){
        return m.containsValue(new JsonArray2(l));
    }

    public boolean containsValue(Map<?, ?> m){
        return this.m.containsValue(new JsonObject2(m));
    }

    public boolean containsValue(JsonArray2 a){
        return m.containsValue(a);
    }

    public boolean containsValue(JsonObject2 o){
        return m.containsValue(o);
    }

    public void remove(String k){
        //JsonBytes b = new JsonBytes(k.getBytes());
        if(m.containsKey(k)){
            //WE NEED TO DETERMINE THE TYPE...
            //setByteSize(-k.getBytes().length-m.get(b).byteSize());
            m.remove(k);
        }
    }

    public Set<String> keySet(){
        return m.keySet();
    }

    public List<Object> values(){
        return new ArrayList<>(m.values());
    }

    public int size(){
        return m.size();
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
    public Map<?, ?> getObject(){
        /*
        HashMap<String, Object> h = new HashMap<>();
        for(JsonBytes k : m.keySet()){
            h.put(new String(k.getObject()), m.get(k).getObject());
        }
        return h;
        */
        return m;
    }

    @Override
    public int byteSize(){
        return s-((m.isEmpty()) ? 0 : 1);
    }

    @Override
    public int hashCode(){
        return 3;
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder("{\r\n");

        for(String k : m.keySet()){
            //String k = new String(o.getObject());
            //System.out.println("K: "+k+"  "+((m.get(k) != null) ? m.get(k).getClass() : ""));

            if(m.get(k) instanceof Number){
                //System.out.println("I: "+k);
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;33m"+m.get(k)+"\033[0m\r\n");

            }else if(m.get(k) instanceof Boolean){
                //System.out.println("B: "+k);
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;35m"+m.get(k)+"\033[0m\r\n");

            }else if(m.get(k) == null){
                //System.out.println("N: "+k);
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;36m"+m.get(k)+"\033[0m\r\n");

            }else if(m.get(k) instanceof String){
                //System.out.println("S: "+k);
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;34m"+m.get(k)+"\033[0m\r\n");

            }else if(m.get(k) instanceof JsonArray2){
                b.append("\t\033[0;32m"+k+"\033[0m:"+m.get(k).toString().replaceAll("\\r?\\n", "\r\n\t")+"\r\n");

            }else if(m.get(k) instanceof JsonObject2){
                //System.out.println("OBJ - 2");
                b.append("\t\033[0;32m"+k+"\033[0m:"+m.get(k).toString().replaceAll("\\r?\\n", "\r\n\t")+"\r\n");
            }
        }

        return b+"}";
    }

    public byte[] encode(){
        return new Json().encode(this);
    }
}
