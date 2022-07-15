package pinkmonsoon;

import static aira.Prelude.foldl; 

import aira.quasi.QuasiFunction.one_bool;
import aira.quasi.QuasiFunction.one_t;
import aira.quasi.QuasiFunction.two_t;

public final class TypePredicator {

    public interface With<T> extends two_t<Boolean, T, Boolean> {}

    public static final two_t<String, Character, Boolean> isUniqueChar = 
        (xs, x) -> xs.indexOf(x) == xs.lastIndexOf(x);
    
    public static final one_bool<Character> isPlusOrMinus = 
        x -> x == '+' || x == '-';

    public static final one_t<String, String> castUnsigned = 
        x -> isPlusOrMinus.invoke(x.charAt(0)) ? x.substring(1) : x;

    public static final two_t<one_bool<Character>, String, Boolean> isUnsignedNumberPartial = 
        (f, xs) -> (Boolean) foldl.invoke((With<Character>) (prev, x) -> prev && f.invoke(x), 
            true, PinkMonsoon.toCharList.invoke(xs));

    public static final two_t<one_bool<Character>, String, Boolean> isNumberPartial = 
        (f, xs) -> isUnsignedNumberPartial.invoke(f, castUnsigned.invoke(xs));
    

    public static final one_bool<Character> isDecimalIntSingle = 
        x -> '0' <= x && x <= '9';

    public static final one_bool<String> isDecimalInt = 
        xs -> isNumberPartial.invoke(isDecimalIntSingle, xs);


    public static final one_bool<String> isDecimalFrac = 
        xs -> isUniqueChar.invoke(xs, '.') && isNumberPartial.invoke(
            x -> '.' == x || isDecimalIntSingle.invoke(x), 
            castUnsigned.invoke(xs));
            
    public static final one_bool<String> isBinInt = 
        s -> ((one_bool<String>) xs -> xs.charAt(0) == '0' && xs.charAt(1) == 'b' && 
            isUnsignedNumberPartial.invoke(x -> x == '0' || x == '1', 
            xs.substring(2))).invoke(castUnsigned.invoke(s).toLowerCase());

    public static final one_bool<String> isHexInt = 
        s -> ((one_bool<String>) xs -> xs.charAt(0) == '0' && xs.charAt(1) == 'x' && 
            isUnsignedNumberPartial.invoke(x -> isDecimalIntSingle.invoke(x) || 
                ('A' <= x && x <= 'F') || ('a' <= x && x <= 'f'), 
            xs.substring(2))).invoke(castUnsigned.invoke(s).toLowerCase());
    
    
}

