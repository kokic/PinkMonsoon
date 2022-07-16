package pinkmonsoon;

import static aira.Prelude.arr;
import static aira.Prelude.switchIt;

public final class PinkMonsoon {

    public static final Morph<String, Boolean> morphBool = 
        x -> (Boolean) switchIt.invoke(x).invoke(
            arr.invoke("0", "F", " "), false, 
            arr.invoke("1", "T", "."), true, 
            Boolean.valueOf(x));
        
}
