package unet.json.variables;

import java.util.Arrays;

public class JsonBoolean implements JsonVariable {

    private String n;
    private int s;

    public JsonBoolean(String n){
        this.n = n;
        s = n.getBytes().length+2;
    }

    public byte[] getBytes(){
        return ('i'+n+'e').getBytes();
    }

    @Override
    public Boolean getObject(){
        return Boolean.parseBoolean(n);
    }

    @Override
    public int byteSize(){
        return s;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof JsonBoolean){
            return Arrays.equals(getBytes(), ((JsonBoolean) o).getBytes());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return 4;
    }
}
