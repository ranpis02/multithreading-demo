package Java.SyntaxTest;

@FunctionalInterface
interface MyFunctionalInterface {
    void myMethod();
}

public class LambdaTest {

    public static void main(String[] args) {

        int x = 10;
        StringBuilder sb = new StringBuilder("hello");

        MyFunctionalInterface myFunc = () -> {
            // x++;
            System.out.println("The value of x is: " + x);
            sb.append(", world!");
        };

        myFunc.myMethod();

        System.out.println("The value of sb is: " + sb.toString());

    }
}
