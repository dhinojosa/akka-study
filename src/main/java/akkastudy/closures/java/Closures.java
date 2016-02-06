package akkastudy.closures.java;

import java.util.function.Function;

public class Closures {
    public static Integer foo(Function<Integer, Integer> f) {
        return f.apply(5);
    }

    public static void main(String[] args) {
        Integer x = 3;
        Function<Integer, Integer> y = (Integer z) -> x + z;
        System.out.println(foo(y));
    }
}