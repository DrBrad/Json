package unet.json.variables;

import java.util.Arrays;

public class JsonString implements JsonVariable {

    private byte[] b;
    private int s;

    public JsonString(byte[] b){
        this.b = b;//sanitize(b);
        s = this.b.length+2;
    }

    public JsonString(String s){
        b = s.getBytes();//sanitize(b);
        this.s = b.length+2;
    }

    /*
    private byte[] sanitize(byte[] b){
        List<Integer> l = new ArrayList<>();
        int p = 0;
        for(int i = 0; i < b.length-1; i++){
            if(b[i] != '\\' && b[i+1] == '"'){
                i++;
                l.add(i-p);
                p = i+1;
            }
        }

        if(l.isEmpty()){
            return b;
        }

        byte[] r = new byte[b.length+l.size()];
        p = 0;
        int x = 0;
        for(int i : l){
            System.arraycopy(b, p, r, p+x, i);
            p += i+1;
            r[p+x-1] = '\\';
            r[p+x] = '"';
            x++;
        }
        System.arraycopy(b, p, r, p+x, b.length-p);

        return r;
    }
    */

    public byte[] getBytes(){
        byte[] r = new byte[s];
        r[0] = '"';
        System.arraycopy(b, 0, r, 1, b.length);
        r[r.length-1] = '"';
        return r;
    }

    @Override
    public String getObject(){
        return new String(b);
    }

    @Override
    public int byteSize(){
        return s;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof JsonString){
            return Arrays.equals(b, ((JsonString) o).b);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return 0;
    }
}
