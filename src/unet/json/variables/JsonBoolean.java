package unet.json.variables;

import java.util.Arrays;

public class JsonBoolean implements JsonVariable {

    private boolean b;
    private int s;

    public JsonBoolean(boolean b){
        this.b = b;
        s = (b) ? 4 : 5;
    }

    public byte[] getBytes(){
        return (b) ? new byte[]{
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
        };
    }

    @Override
    public Boolean getObject(){
        return b;
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
