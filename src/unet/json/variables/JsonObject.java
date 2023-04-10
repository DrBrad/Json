package unet.json.variables;

import unet.json.Json;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonObject  implements JsonVariable, JsonObserver {

    private HashMap<JsonBytes, JsonVariable> m = new HashMap<>();
    private JsonObserver o;
    private int s = 2;

    public JsonObject(){
    }

    public JsonObject(Map<?, ?> m){
        for(Object o : m.keySet()){
            JsonBytes k;

            if(o instanceof JsonBytes){
                k = (JsonBytes) o;
            }else if(o instanceof String){
                k = new JsonBytes(((String) o).getBytes());
            }else{
                throw new IllegalArgumentException("Map keys must be in byte, string, or bencodebyte form.");
            }

            if(m.get(o) == null){
                put(k, new JsonNull());
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
                put(k, new JsonObject((Map<?, ?>) m.get(o)));
            }
        }
    }

    public JsonObject(byte[] buf){
        this(new Json().decodeObject(buf, 0));
    }

    public JsonObject(byte[] buf, int off){
        this(new Json().decodeObject(buf, off));
    }

    private void put(JsonBytes k, JsonVariable v){
        m.put(k, v);
        setByteSize(k.byteSize()+v.byteSize()+2);

        if(v instanceof JsonObject){
            ((JsonObject) v).setObserver(this);
        }else if(v instanceof JsonArray){
            ((JsonArray) v).setObserver(this);
        }
    }

    public void put(String k, boolean b){
        put(new JsonBytes(k.getBytes()), new JsonBoolean(b));
    }

    public void put(String k, int i){
        put(new JsonBytes(k.getBytes()), new JsonNumber(Integer.toString(i)));
    }

    public void put(String k, long l){
        put(new JsonBytes(k.getBytes()), new JsonNumber(Long.toString(l)));
    }

    public void put(String k, double d){
        put(new JsonBytes(k.getBytes()), new JsonNumber(Double.toString(d)));
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

    public void put(String k, Object o){
        if(o == null){
            put(new JsonBytes(k.getBytes()), new JsonNull());

        }else if(o instanceof String){
            put(new JsonBytes(k.getBytes()), new JsonBytes(((String) o).getBytes()));

        }else if(o instanceof byte[]){
            put(new JsonBytes(k.getBytes()), new JsonBytes((byte[]) o));

        }else if(o instanceof List<?>){
            put(new JsonBytes(k.getBytes()), new JsonArray((List<?>) o));

        }else if(o instanceof Map<?, ?>){
            put(new JsonBytes(k.getBytes()), new JsonObject((Map<?, ?>) o));

        }else if(o instanceof JsonVariable){
            put(new JsonBytes(k.getBytes()), (JsonVariable) o);

        }else if(o instanceof Boolean){
            put(new JsonBytes(k.getBytes()), new JsonBoolean((Boolean) o));

        }else if(o instanceof Integer){
            put(new JsonBytes(k.getBytes()), new JsonNumber(Integer.toString((Integer) o)));

        }else if(o instanceof Long){
            put(new JsonBytes(k.getBytes()), new JsonNumber(Long.toString((Long) o)));

        }else if(o instanceof Double){
            put(new JsonBytes(k.getBytes()), new JsonNumber(Double.toString((Double) o)));
        }
    }

    public JsonVariable valueOf(JsonBytes k){
        return m.get(k);
    }

    public Object get(String k){
        return m.get(new JsonBytes(k.getBytes())).getObject();
    }

    public Integer getInteger(String k){
        return ((Number) m.get(new JsonBytes(k.getBytes())).getObject()).intValue();
    }

    public Long getLong(String k){
        return ((Number) m.get(new JsonBytes(k.getBytes())).getObject()).longValue();
    }

    public Short getShort(String k){
        return ((Number) m.get(new JsonBytes(k.getBytes())).getObject()).shortValue();
    }

    public Double getDouble(String k){
        return ((Number) m.get(new JsonBytes(k.getBytes())).getObject()).doubleValue();
    }

    public Float getFloat(String k){
        return ((Number) m.get(new JsonBytes(k.getBytes())).getObject()).floatValue();
    }

    public Boolean getBoolean(String k){
        return ((Boolean) m.get(new JsonBytes(k.getBytes())).getObject()).booleanValue();
    }

    public String getString(String k){
        return new String((byte[]) m.get(new JsonBytes(k.getBytes())).getObject());
    }

    public byte[] getBytes(String k){
        return (byte[]) m.get(new JsonBytes(k.getBytes())).getObject();
    }

    public JsonArray getJsonArray(String k){
        return (JsonArray) m.get(new JsonBytes(k.getBytes()));
    }

    public JsonObject getJsonObject(String k){
        return (JsonObject) m.get(new JsonBytes(k.getBytes()));
    }

    public boolean containsKey(String s){
        return m.containsKey(new JsonBytes(s.getBytes()));
    }

    public boolean containsValue(Number n){
        return m.containsValue(new JsonNumber(n.toString()));
    }

    public boolean containsValue(Boolean b){
        return m.containsValue(new JsonBoolean(b));
    }

    public boolean containsValue(String s){
        return m.containsValue(new JsonBytes(s.getBytes()));
    }

    public boolean containsValue(byte[] b){
        return m.containsValue(new JsonBytes(b));
    }

    public boolean containsValue(List<?> l){
        return m.containsValue(new JsonArray(l));
    }

    public boolean containsValue(Map<?, ?> m){
        return this.m.containsValue(new JsonObject(m));
    }

    public boolean containsValue(JsonArray a){
        return m.containsValue(a);
    }

    public boolean containsValue(JsonObject o){
        return m.containsValue(o);
    }

    public void remove(String k){
        JsonBytes b = new JsonBytes(k.getBytes());
        if(m.containsKey(b)){
            setByteSize(-b.byteSize()-m.get(b).byteSize());
            m.remove(b);
        }
    }

    public Set<JsonBytes> keySet(){
        return m.keySet();
    }

    public List<JsonVariable> values(){
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
    public Map<String, ?> getObject(){
        HashMap<String, Object> h = new HashMap<>();
        for(JsonBytes k : m.keySet()){
            h.put(new String(k.getObject()), m.get(k).getObject());
        }
        return h;
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

        for(JsonBytes o : m.keySet()){
            String k = new String(o.getObject());

            if(m.get(o) instanceof JsonNumber){
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;33m"+((JsonNumber) m.get(o)).getObject()+"\033[0m\r\n");

            }else if(m.get(o) instanceof JsonBoolean){
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;35m"+((JsonBoolean) m.get(o)).getObject()+"\033[0m\r\n");

            }else if(m.get(o) instanceof JsonNull){
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;36m"+m.get(o).getObject()+"\033[0m\r\n");

            }else if(m.get(o) instanceof JsonBytes){
                if(Charset.forName("US-ASCII").newEncoder().canEncode(new String(((JsonBytes) m.get(o)).getObject()))){
                    b.append("\t\033[0;31m"+k+"\033[0m:\033[0;34m"+new String(((JsonBytes) m.get(o)).getObject(), StandardCharsets.UTF_8)+"\033[0m\r\n");

                }else{
                    b.append("\t\033[0;31m"+k+"\033[0m:\033[0;34m BASE64 { "+Base64.getEncoder().encodeToString(((JsonBytes) m.get(o)).getObject())+" }\033[0m\r\n");
                }

            }else if(m.get(o) instanceof JsonArray){
                b.append("\t\033[0;32m"+k+"\033[0m:"+((JsonArray) m.get(o)).toString().replaceAll("\\r?\\n", "\r\n\t")+"\r\n");

            }else if(m.get(o) instanceof JsonObject){
                b.append("\t\033[0;32m"+k+"\033[0m:"+((JsonObject) m.get(o)).toString().replaceAll("\\r?\\n", "\r\n\t")+"\r\n");
            }
        }

        return b+"}";
    }

    public byte[] encode(){
        return new Json().encode(this);
    }
}
