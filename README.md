Json
========

This is an implementation of Json for Java. Json is used for almost all data serialization on the internet. Its a lightweight fast data serialization.
[Wikipedia](https://en.wikipedia.org/wiki/JSON)

Benchmarks
-----
Here are some examples of this library compared to other major data serialization methods.

Serialization / Encoding
| Method  | Time in Mills |
| ------------- | ------------- |
| Bencode  | 57  |
| JSON  | ~  |

Parsing
| Method  | Time in Mills |
| ------------- | ------------- |
| Bencode  | 83  |
| JSON  | ~  |

Byte Size when encoded
| Method  | Bytes |
| ------------- | ------------- |
| Bencode  | 10134606  |
| JSON  | ~  |

Library
-----
The JAR for the library can be found here: [Json JAR](https://github.com/DrBrad/Json)

Usage
-----
Here are some examples of how to use the Json library.

**Json Array**
```Java
//FROM LIST
ArrayList<String> l = new ArrayList<>();
JsonArray jar = new JsonArray(l);

//FROM BYTES
byte[] b; //ARRAY OF BYTES
JsonArray jar = new JsonArray(b);

//CREATE JSON
JsonArray jar = new JsonArray();

//ADD | GET
jar.add(1000);
System.out.println(jar.get(0));
```

**Json Object | Map**
```Java
//FROM MAP
HashMap<String, String> l = new HashMap<>();
JsonObject job = new JsonObject(l);

//FROM BYTES
byte[] b; //ARRAY OF BYTES
JsonObject job = new JsonObject(b);

//CREATE JSON
JsonObject job = new JsonObject();

//PUT | GET
job.put("KEY", 100);
System.out.println(job.get("KEY"));
```

**Encoding to byte array**
```Java
bar.encode();
```

**Readable String**
```Java
System.out.println(bar.toString());
```

**Annotations**
```Java
public static void main(String[] args)throws Exception {
    JsonObject json = new JsonObject();
    json.put("title", "Annotation Test");
    Foo f = (Foo) Json.fromJson(Foo.class, json);

    System.out.println(f.getTitle()); //SHOULD RETURN - "Annotation Test"

    json = Json.toJson(f);
}

public class Foo {

    private String title;

    @JsonAnnotation(key = "title")
    public String getTitle(){
        return title;
    }

    @JsonAnnotation(key = "title")
    public void setTitle(String title){
        this.title = title;
    }
}
```
