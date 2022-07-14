
import static aira.Prelude.println;

import pinkmonsoon.TypePredicator;

public class App {
    public static void main(String[] args) throws Exception {

        println.invoke(TypePredicator.isDecimalInt.invoke("1003917294"));
        println.invoke(TypePredicator.isDecimalFrac.invoke("-3.1415926535"));
        println.invoke("Baby Pink Monsoon!");
    }
}
