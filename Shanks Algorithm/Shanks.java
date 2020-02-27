// Shanks.java
// The purpose of this program is to apply Shank's babystep-giantstep method to solve descrete
// logrithm problems.  For a problem g^x = h in F(p), define g, h, and p in the "main" method.
// the output will be solution for x.  The program takes advantage of Java's built in
// BigInteger package to mitigate integer overflow issues.
//
// Riley Hillegas
// 02/26/2020

import java.math.BigInteger;

public class Shanks {

    // Returns floor(sqrt(n)) when given n and 1.
    private static BigInteger newtonIteration(BigInteger n, BigInteger x0)
    {
        final BigInteger x1 = n.divide(x0).add(x0).shiftRight(1);
        return x0.equals(x1)||x0.equals(x1.subtract(BigInteger.ONE)) ? x0 : newtonIteration(n, x1);
    }

    // Shanks algorithm for solving discrete log problems
    private static void shankAlgorithm(BigInteger g, BigInteger h, BigInteger p) {
        BigInteger n = BigInteger.ONE.add(newtonIteration(p, BigInteger.ONE));

        // Two lists for comparison
        BigInteger[] list1 = new BigInteger[n.intValueExact()];
        BigInteger[] list2 = new BigInteger[n.intValueExact()];

        // Generate list contents
        for (BigInteger i = BigInteger.ONE; i.compareTo(n) < 1; i = i.add(BigInteger.ONE)) {
            list1[i.intValueExact() - 1] = g.modPow(i, p);
            list2[i.intValueExact() - 1] = h.multiply(g.modPow(i.multiply(n).negate(), p)).mod(p);
        }

        // Look for a collision
        BigInteger solution = BigInteger.ZERO;
        for (BigInteger i = BigInteger.ZERO; i.compareTo(n.subtract(BigInteger.ONE)) < 1; i = i.add(BigInteger.ONE)) {
            for (BigInteger j = BigInteger.ZERO; j.compareTo(n.subtract(BigInteger.ONE)) < 1; j = j.add(BigInteger.ONE)) {
                if (list1[i.intValueExact()].equals(list2[j.intValueExact()])) {
                    solution = i.add(BigInteger.ONE).add(j.add(BigInteger.ONE).multiply(n));
                    break;
                }
            }
        }

        // Output the result
        System.out.println(solution);
    }

    public static void main( String args[] ) {
	// For a function g^x = h in F(p)
        BigInteger g = new BigInteger("650");
        BigInteger h = new BigInteger("2213");
        BigInteger p = new BigInteger("3571");

        shankAlgorithm(g, h, p);
    }
}
