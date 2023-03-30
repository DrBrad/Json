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
JsonArray bar = new JsonArray(l);

//FROM BYTES
byte[] b; //ARRAY OF BYTES
JsonArray bar = new JsonArray(b);

//CREATE JSON
JsonArray bar = new JsonArray();
```

**Json Object | Map**
```Java
//FROM MAP
HashMap<String, String> l = new HashMap<>();
JsonObject bob = new JsonObject(l);

//FROM BYTES
byte[] b; //ARRAY OF BYTES
JsonObject bob = new JsonObject(b);

//CREATE JSON
JsonObject bob = new JsonObject();
```

**Put | Get data**
```Java
//ARRAY
bar.put(1000);
bar.get(0);

//MAP
bob.put("KEY", 100);
bob.get("KEY");
```

**Encoding to byte array**
```Java
bar.encode();
```

**Readable String**
```Java
System.out.println(bar.toString());
```
