package unet.json.variables;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonExpose {

    boolean serialize() default true;

    boolean deserialize() default true;
}
