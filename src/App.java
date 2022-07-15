
import static aira.Prelude.println;

import pinkmonsoon.PinkMonsoon;
import static pinkmonsoon.TypePredicator.*;

public class App {
    public static void main(String[] args) throws Exception {

        println.invoke(isDecimalInt.invoke("1003917294"));
        println.invoke(isDecimalFrac.invoke("-3.1415926535"));
        println.invoke(isHexInt.invoke("0x7a96"));
        println.invoke(isBinInt.invoke("0b101"));
        println.invoke(PinkMonsoon.morphBool.map("."));

        println.invoke("Baby Pink Monsoon!");
    }
}
