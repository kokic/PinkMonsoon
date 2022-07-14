package pinkmonsoon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import aira.quasi.QuasiFunction.one_t;

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

//

    public static final Morph<String, Boolean> morphBoolFromString = 
        x -> Boolean.valueOf(x);
    
}
