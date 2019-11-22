package dataraces;

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
        
        computeFact(19);
        
        computeFact(23);
    }
}
