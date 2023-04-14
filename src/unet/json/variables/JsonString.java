package unet.json.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonString implements JsonVariable {

    private byte[] b;
    private int s;
    private List<Integer> e;

    public JsonString(byte[] b){
        this.b = b;
        escape();
        s = this.b.length+e.size()+2;
    }

    public JsonString(String s){
        b = s.getBytes();
        escape();
        this.s = b.length+e.size()+2;
    }

    private void escape(){
        e = new ArrayList<>();
        /*
        int p = 0, a = 0;
        for(int i = 0; i < b.length-1; i++){
            if(b[i] == '\\'){
                a++;
            }else{
                a = 0;
            }

            if(a%2 == 0 && b[i+1] == '"'){
                i++;
                e.add(i-p);
                p = i+1;
            }
        }
        */

        int p = 0;//, a = 0;
        for(int i = 0; i < b.length-1; i++){
            if(isEscapable(b[i])){
                e.add(i-p);
                p = i+1;
            }

/*
            if(b[i] == '\\'){
                //a++;
                i++;
                e.add(i-p);
                p = i+1;
            //}else{
            //    a = 0;
            }
/*
            if(a%2 == 1){
                i++;
                e.add(i-p);
                p = i+1;
            }*/
        }

        //System.out.println(e.size()+" - "+new String(b)+" - "+new String(sanitize()));
    }

    private boolean isEscapable(byte b){
        return (b == '\\' ||
                b == '\"' ||
                b == '\n' ||
                b == '\r' ||
                b == '\f' ||
                b == '\b' ||
                b == '\t');
    }

    private byte[] sanitize(){
        if(e.isEmpty()){
            return b;
        }

        byte[] r = new byte[b.length+e.size()];
        int p = 0, x = 0;
        for(int i : e){
            System.arraycopy(b, p, r, p+x, i);
            p += i+1;
            r[p+x-1] = '\\';

            switch(b[p-1]){
                case '\n':
                    r[p+x] = 'n';
                    break;

                case '\r':
                    r[p+x] = 'r';
                    break;

                case '\f':
                    r[p+x] = 'f';
                    break;

                case '\b':
                    r[p+x] = 'b';
                    break;

                case '\t':
                    r[p+x] = 't';
                    break;

                default:
                    r[p+x] = b[p-1];
            }

            //r[p+x] = (byte) ((b[p-1] == '\n') ? 'n' : b[p-1]);//'"';
            x++;
        }
        System.arraycopy(b, p, r, p+x, b.length-p);

        return r;
    }

    public byte[] getBytes(){
        byte[] r = new byte[s];
        r[0] = '"';
        System.arraycopy(sanitize(), 0, r, 1, s-2);
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
