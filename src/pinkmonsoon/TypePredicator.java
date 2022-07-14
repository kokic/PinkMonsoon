package pinkmonsoon;

import static aira.Prelude.foldl;

import aira.quasi.QuasiFunction.one_bool;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.two_t;

public final class TypePredicator {

    public interface With<T> extends two_t<Boolean, T, Boolean> {}

    public static final two_t<With<Character>, String, Boolean> isDecimalPartial = 
        (f, xs) -> (Boolean) foldl.invoke(f, true, PinkMonsoon.toCharList.invoke(xs));

    public static final two_t<String, Character, Boolean> isUniqueChar = 
        (xs, x) -> xs.indexOf(x) == xs.lastIndexOf(x);


    public static final one_bool<Character> isPlusOrMinus = 
        x -> x == '+' || x == '-';

    public static final one_t<String, String> castUnsigned = 
        x -> isPlusOrMinus.invoke(x.charAt(0)) ? x.substring(1) : x;
    
// int

    public static final one_bool<Character> isDecimalIntSingle = 
        x -> '0' <= x && x <= '9';

    public static final With<Character> isDecimalIntWith = 
        (prev, x) -> prev && isDecimalIntSingle.invoke(x);
    
    public static final one_bool<String> isDecimalInt = 
        xs -> isDecimalPartial.invoke(isDecimalIntWith, castUnsigned.invoke(xs));

// frac
    public static final one_bool<Character> isDecimalFracSingle = 
        x -> '.' == x || isDecimalIntSingle.invoke(x);
    
    public static final one_bool<String> isUniqueDot = 
        x -> isUniqueChar.invoke(x, '.');

    public static final With<Character> isDecimalFracWith = 
        (prev, x) -> prev && isDecimalFracSingle.invoke(x);

    public static final one_bool<String> isDecimalFrac = 
        xs -> isUniqueDot.invoke(xs) && isDecimalPartial.invoke(
            isDecimalFracWith, castUnsigned.invoke(xs));
        
}

