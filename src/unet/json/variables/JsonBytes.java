package unet.json.variables;

import java.util.Arrays;

public class JsonBytes implements JsonVariable {

    private byte[] b;
    private int s;

    public JsonBytes(byte[] b){
        this.b = b;
        s = b.length+2;
    }

    private void sanitize(){
        for(int i = 0; i < b.length-1; i++){
            if(b[i] != '\\' && b[i+1] == '"'){

            }
        }
    }

    public byte[] getBytes(){
        byte[] r = new byte[s];
        r[0] = '"';
        System.arraycopy(b, 0, r, 1, b.length);
        r[r.length-1] = '"';
        return r;
    }

    @Override
    public byte[] getObject(){
        return b;
    }

    @Override
    public int byteSize(){
        return s;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof JsonBytes){
            return Arrays.equals(getBytes(), ((JsonBytes) o).getBytes());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return 0;
    }
}
