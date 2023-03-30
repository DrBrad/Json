package unet.json;

import unet.json.variables.JsonBytes;
import unet.json.variables.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){

        String t = "{\"type\":0,\"result\":{\"_id\":{\"$oid\":\"63e4624f17f6714b6207e5df\"},\"title\":\"Tommy Boy\",\"description\":\"After his beloved father (Brian Dennehy) dies, dimwitted Tommy Callahan (Chris Farley) inherits a near-bankrupt automobile parts factory in Sandusky, Ohio. His brand new stepmother, Beverly (Bo Derek), wants to cash out and close, but Tommy's sentimental attachment to his father's employees spurs him to make one last-ditch effort to find someone who will buy their products. With his father's tightly wound assistant, Richard (David Spade), in tow, Tommy hits the road to scare up some new clients.\",\"rating\":\"PG-13\",\"year\":\"1995\",\"genre\":\"Adventure\",\"portrait\":\"8998d7d103b9549211de53e4928bd68c37baf270e64194cad9cf91882a245c3a\",\"landscape\":\"eee16f9ae95441fce776aa3447a68d905cef3c956d088c26a76756d7dd2ff672\",\"video\":\"c35692e499ea0e3f3df291e125d9c63b3b78dd51a1b272de7eb52e783a20f8c1\",\"type\":\"movie\",\"extra\":\"1h 37m\"}}";


        JsonObject json = new JsonObject(t.getBytes());

        for(JsonBytes k : json.keySet()){
            System.out.println(new String(k.getBytes())+" : ");//+"  "+json.get(k.toString()).toString());
        }

    }
}