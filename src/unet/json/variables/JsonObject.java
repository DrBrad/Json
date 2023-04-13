package unet.json.variables;

import unet.json.Json;

import java.util.*;

public class JsonObject  implements JsonVariable, JsonObserver {

    private Map<JsonString, JsonVariable> m = new HashMap<>();
    private JsonObserver o;
    private int s = 2;

    public JsonObject(){
    }

    public JsonObject(Map<?, ?> m){
        for(Object o : m.keySet()){
            JsonString k;

            if(o instanceof JsonString){
                k = (JsonString) o;
            }else if(o instanceof String){
                k = new JsonString((String) o);
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
                put(k, new JsonString((String) m.get(o)));
            }else if(m.get(o) instanceof List<?>){
                put(k, new JsonArray((List<?>) m.get(o)));
            }else if(m.get(o) instanceof Map<?, ?>){
                put(k, new JsonObject((Map<?, ?>) m.get(o)));
            }
        }
    }

    public JsonObject(byte[] buf){
        new Json().decodeObject(this, buf, 0);
        //this(new Json().decodeObject(buf, 0));
    }

    public JsonObject(byte[] buf, int off){
        new Json().decodeObject(this, buf, off);
        //this(new Json().decodeObject(buf, off));
    }

    public void put(JsonString k, JsonVariable v){
        m.put(k, v);
        setByteSize(k.byteSize()+v.byteSize()+2);

        if(v instanceof JsonObject){
            ((JsonObject) v).setObserver(this);
        }else if(v instanceof JsonArray){
            ((JsonArray) v).setObserver(this);
        }
    }

    public void put(String k, boolean b){
        put(new JsonString(k), new JsonBoolean(b));
    }

    public void put(String k, int i){
        put(new JsonString(k), new JsonNumber(Integer.toString(i)));
    }

    public void put(String k, long l){
        put(new JsonString(k), new JsonNumber(Long.toString(l)));
    }

    public void put(String k, double d){
        put(new JsonString(k), new JsonNumber(Double.toString(d)));
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
            put(new JsonString(k), new JsonNull());

        }else if(o instanceof String){
            put(new JsonString(k), new JsonString((String) o));

        }else if(o instanceof List<?>){
            put(new JsonString(k), new JsonArray((List<?>) o));

        }else if(o instanceof Map<?, ?>){
            put(new JsonString(k), new JsonObject((Map<?, ?>) o));

        }else if(o instanceof JsonVariable){
            put(new JsonString(k), (JsonVariable) o);

        }else if(o instanceof Boolean){
            put(new JsonString(k), new JsonBoolean((Boolean) o));

        }else if(o instanceof Integer){
            put(new JsonString(k), new JsonNumber(Integer.toString((Integer) o)));

        }else if(o instanceof Long){
            put(new JsonString(k), new JsonNumber(Long.toString((Long) o)));

        }else if(o instanceof Double){
            put(new JsonString(k), new JsonNumber(Double.toString((Double) o)));
        }
    }

    public JsonVariable valueOf(JsonString k){
        return m.get(k);
    }

    public Object get(String k){
        return m.get(new JsonString(k)).getObject();
    }

    public Integer getInteger(String k){
        return ((Number) m.get(new JsonString(k)).getObject()).intValue();
    }

    public Long getLong(String k){
        return ((Number) m.get(new JsonString(k)).getObject()).longValue();
    }

    public Short getShort(String k){
        return ((Number) m.get(new JsonString(k)).getObject()).shortValue();
    }

    public Double getDouble(String k){
        return ((Number) m.get(new JsonString(k)).getObject()).doubleValue();
    }

    public Float getFloat(String k){
        return ((Number) m.get(new JsonString(k)).getObject()).floatValue();
    }

    public Boolean getBoolean(String k){
        return ((Boolean) m.get(new JsonString(k)).getObject()).booleanValue();
    }

    public String getString(String k){
        return (String) m.get(new JsonString(k)).getObject();
    }

    public JsonArray getJsonArray(String k){
        return (JsonArray) m.get(new JsonString(k));
    }

    public JsonObject getJsonObject(String k){
        return (JsonObject) m.get(new JsonString(k));
    }

    public boolean containsKey(String k){
        return m.containsKey(new JsonString(k));
    }

    public boolean containsValue(Number n){
        return m.containsValue(new JsonNumber(n.toString()));
    }

    public boolean containsValue(Boolean b){
        return m.containsValue(new JsonBoolean(b));
    }

    public boolean containsValue(String s){
        return m.containsValue(new JsonString(s));
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
        JsonString b = new JsonString(k);
        if(m.containsKey(b)){
            setByteSize(-b.byteSize()-m.get(b).byteSize());
            m.remove(b);
        }
    }

    public Set<JsonString> keySet(){
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
        Map<String, Object> h = new HashMap<>();
        for(JsonString k : m.keySet()){
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

        for(JsonString o : m.keySet()){
            String k = new String(o.getObject());

            if(m.get(o) instanceof JsonNumber){
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;33m"+((JsonNumber) m.get(o)).getObject()+"\033[0m\r\n");

            }else if(m.get(o) instanceof JsonBoolean){
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;35m"+((JsonBoolean) m.get(o)).getObject()+"\033[0m\r\n");

            }else if(m.get(o) instanceof JsonNull){
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;36m"+m.get(o).getObject()+"\033[0m\r\n");

            }else if(m.get(o) instanceof JsonString){
                b.append("\t\033[0;31m"+k+"\033[0m:\033[0;34m"+m.get(o).getObject()+"\033[0m\r\n");

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
