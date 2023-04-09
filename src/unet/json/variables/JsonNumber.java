package unet.json.variables;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;

public class JsonNumber implements JsonVariable {

    private String n;
    private int s;

    public JsonNumber(String n){
        this.n = n;
        s = n.getBytes().length;
    }

    public byte[] getBytes(){
        return n.getBytes();
    }

    @Override
    public Number getObject(){
        try{
            return NumberFormat.getInstance().parse(n);
        }catch(ParseException e){
            return 0;
        }
    }

    @Override
    public int byteSize(){
        return s;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof JsonNumber){
            return Arrays.equals(getBytes(), ((JsonNumber) o).getBytes());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return 1;
    }
}
