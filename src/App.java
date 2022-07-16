
import static aira.Prelude.println;

import pinkmonsoon.PinkMonsoon;
import pinkmonsoon.morphs.ClassMorphCaster;

import static pinkmonsoon.TypePredicator.*;

import aira.Prelude;
import aira.quasi.QuasiFunction;

public class App {

    static class A {
        int off4;
        byte off5;
        byte off6;
        byte byte7;
    }

    static class B {
        char off2;
        char off4;
        byte off5;
    }

    public static void main(String[] args) throws Exception {
        
        
        A a = new A(); 
        
        var caster = ClassMorphCaster.alignCast(A.class, B.class);
        
        // caster.map(a);

        // predicatorTest();
        println.invoke("Baby Pink Monsoon!");
    }

    public static void predicatorTest() {
        println.invoke(isDecimalInt.invoke("1003917294"));
        println.invoke(isDecimalFrac.invoke("-3.1415926535"));
        println.invoke(isHexInt.invoke("0x7a96"));
        println.invoke(isBinInt.invoke("0b101"));
        println.invoke(PinkMonsoon.morphBool.map("."));
    }
}
