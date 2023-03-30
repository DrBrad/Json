package unet.json.variables;

import java.util.Arrays;

public class JsonBytes implements JsonVariable {

    private byte[] b;
    private int s;

    public JsonBytes(byte[] b){
        this.b = b;
        s = (b.length+":").getBytes().length+b.length;
    }

    public byte[] getBytes(){
        byte[] r = new byte[s];
        byte[] l = (b.length+":").getBytes();
        System.arraycopy(l, 0, r, 0, l.length);
        System.arraycopy(b, 0, r, l.length, b.length);
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
