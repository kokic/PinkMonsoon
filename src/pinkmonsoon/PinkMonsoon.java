package pinkmonsoon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aira.quasi.QuasiFunction.any_t;
import aira.quasi.QuasiFunction.one_t;

import static aira.Prelude.*;

public final class PinkMonsoon {

// -- aria --
    public static final one_t<Object, List<Object>> toList = xs -> {
        List<Object> rs = new ArrayList<Object>();
        final int length = Array.getLength(xs);
        for (int index = 0; index < length; index++)
            rs.add(Array.get(xs, index));
        return rs;
    };

    public static final one_t<String, List<Object>> toCharList = x -> toList.invoke(x.toCharArray());

    public static final one_t<Object, any_t<Object>> switchIt = x -> args -> {
        final int sup = args.length - 1;
        for (int index = 0; index < sup; index += 2) {
            Object key = args[index];
            Object val = args[index + 1];
            boolean isArray = key instanceof Object[];
            boolean caseElem = key.equals(x);
            boolean caseGroup = isArray && Arrays.asList((Object[]) key).contains(x);
            if (caseElem || caseGroup)
                return val;
        }
        return args[args.length - 1];
    };

//
    public static final Morph<String, Boolean> morphBool = 
        x -> (Boolean) switchIt.invoke(x).invoke(
            arr.invoke("0", "F", " "), false, 
            arr.invoke("1", "T", "."), true, 
            Boolean.valueOf(x));
    
}
