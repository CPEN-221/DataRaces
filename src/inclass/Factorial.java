package inclass;

import java.math.BigInteger;

public class Factorial {

    /**
     * Computes n! and prints it on standard output.
     * @param n must be >= 0
     */
    private static void computeFact(final int n) {
        BigInteger result = new BigInteger("1");
        for (int i = 1; i <= n; ++i) {
            System.out.println("working on fact(" + n + ")");
            result = result.multiply(new BigInteger(String.valueOf(i)));
        }
        System.out.println("fact(" + n + ") = " + result);
    }
    
    public static void main(String[] args) {
        
        Thread t = new Thread(new Runnable() {
            public void run() {
                computeFact(19);
            }
        });
        t.start(); // don't forget to start the thread
        
        // computeFact(19);
        
        computeFact(23);
    }
}
