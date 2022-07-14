package pinkmonsoon;

import static aira.Prelude.foldl;

import aira.quasi.QuasiFunction.one_bool;
import aira.quasi.QuasiFunction.two_t;

public final class TypePredicator {

    public static final one_bool<Character> isDecimalIntSingle = 
        x -> '0' <= x && x <= '9';

    public static final two_t<Boolean, Character, Boolean> isDecimalIntWith = 
        (prev, x) -> prev && isDecimalIntSingle.invoke(x);
    
    public static final one_bool<String> isDecimalInt = 
        xs -> (boolean) foldl.invoke(isDecimalIntWith, true, PinkMonsoon.toCharList.invoke(xs));
}

