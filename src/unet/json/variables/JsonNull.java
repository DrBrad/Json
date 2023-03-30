package unet.json.variables;

import java.util.Arrays;

public class JsonNull implements JsonVariable {

    private int s = 4;

    public JsonNull(){
    }

    public byte[] getBytes(){
        byte[] b = {
                'n',
                'u',
                'l',
                'l'
        };
        return b;
    }

    @Override
    public Object getObject(){
        return null;
    }

    @Override
    public int byteSize(){
        return s;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof JsonNull){
            return Arrays.equals(getBytes(), ((JsonNull) o).getBytes());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return 5;
    }
}
