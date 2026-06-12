package Java.SyntaxTest;

class TryCatchFinallySeq {
    public static void main(String[] args) {
        int res = tryWithReturn();

        System.out.println("The result of tryWithcReture is: " + res);
    }

    /**
     * If there is a return statement in finally block, the return value of the try
     * or catch block will be ignored, and the return value of the finally block
     * will be returned instead.
     */
    public static int tryWithReturn() {
        try {
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            System.out.println("This is the finally block.");
        }
    }
}