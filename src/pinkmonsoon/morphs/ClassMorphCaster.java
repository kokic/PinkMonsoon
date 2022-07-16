package pinkmonsoon.morphs;

import static aira.Prelude.arr;
import static aira.Prelude.foreach;
import static aira.Prelude.pack;
import static aira.Prelude.switchIt;
import static aira.Prelude.trial;
import static aira.Prelude.trialEval;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.two_t;
import aira.quasi.Unsafe;
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
                xs[i].setAccessible(true);
                ys[j].setAccessible(true);
            }
        }
        return result;
    };

    interface SimplestConstruction extends one_t<Class<?>, Constructor<?>> {}
    public static final SimplestConstruction simplestCons = x -> {
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

    public static final <X, Y> Morph<X, Y> cast(Class<X> clazzX, Class<Y> clazzY) {
        
        var fieldsX = clazzX.getDeclaredFields();
        var fieldsY = clazzY.getDeclaredFields();

        final var constructor = simplestCons.invoke(clazzY);
        constructor.setAccessible(true);
        var instance = Unsafe.<Y>as(trialEval.invoke(() -> constructor.newInstance()));
        var hashmap = fieldsMatch.invoke(fieldsY, fieldsX);
        var preloop = foreach.invoke(hashmap.keySet().toArray());
 
        Morph<X, Y> morph = x -> ((two_t<Object, Y, Y>) (code, u) -> u)
            .invoke(pack.invoke(() -> preloop.invoke(elem -> trial
            .invoke(() -> ((Field) elem).set(instance, hashmap
            .get(elem).get(x))))), instance);
        
        return morph;
    }

}
