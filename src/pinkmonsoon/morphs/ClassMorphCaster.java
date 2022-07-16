package pinkmonsoon.morphs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

import static aira.Prelude.*;
import aira.Prelude.Map;
import aira.quasi.Unsafe;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.two_t;
import pinkmonsoon.Morph;

public final class ClassMorphCaster {

    public static final one_t<Class<?>, Integer> sizeof = 
        clazz -> (int) switchIt.invoke(clazz).invoke(
            arr.invoke(byte.class, Byte.class), Byte.BYTES, 
            arr.invoke(char.class, Character.class), Character.BYTES, 
            arr.invoke(short.class, Short.class), Short.BYTES, 
            arr.invoke(int.class, Integer.class), Integer.BYTES, 
            arr.invoke(float.class, Float.class), Float.BYTES,
            arr.invoke(long.class, Long.class), Long.BYTES, 
            arr.invoke(double.class, Double.class), Double.BYTES, 
            Integer.BYTES );
    
    public interface Matcher<X> extends two_t<X[], X[], HashMap<X, X>> {}

    public static final Matcher<Field> fieldsMatch = (xs, ys) -> {
        int offsetX = 0, offsetY = 0;
        int supX = xs.length, supY = ys.length;
        var result = new HashMap<Field, Field>();
        for (int i = 0; i < supX; i++) {
            var typeX = xs[i].getType();
            offsetX += sizeof.invoke(typeX);
            offsetY = 0;
            for (int j = 0; j < supY; j++) {
                var typeY = ys[j].getType();
                offsetY += sizeof.invoke(typeY);
                var matched = offsetX == offsetY && typeX == typeY;
                if (!matched) continue;
                result.put(xs[i], ys[j]);
            }
        }
        return result;
    };


    public static final one_t<Class<?>, Constructor> simplestConstruction = x -> {
        var constructors = x.getDeclaredConstructors();
        Constructor<?> constructor = constructors[0];
        int simplestCount = constructor.getParameterCount();
        for (var elem : constructors) {
            int count = elem.getParameterCount();
            if (count < simplestCount) {
                simplestCount  = count;
                constructor = elem;
            }
        }
        return constructor;
    };

    public static final <X, Y> Morph<X, Y> alignCast(Class<X> clazzX, Class<Y> clazzY) {
        
        var fieldsX = clazzX.getDeclaredFields();
        var fieldsY = clazzY.getDeclaredFields();

        final var constructor = simplestConstruction.invoke(clazzY); 
        Y instance = Unsafe.as(eval.invoke(() -> constructor.newInstance()));

        var hashmap = fieldsMatch.invoke(fieldsY, fieldsX);


        Morph<X, Y> morph = x -> instance;

        for (var elem : hashmap.keySet()) {
            System.out.println(elem);
            // elem.set(instance, hashmap.get(elem).get(x));
        }

        return null;
    }

}
