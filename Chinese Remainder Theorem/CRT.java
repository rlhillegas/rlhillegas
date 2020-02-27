// CRT.java
// The purpose of this program is to solve simultaneous systems of congruences using the Chinese Remainder Theorem. 
// The program takes advantage of Java's built in BigInteger package to mitigate integer overflow issues.
//
// Riley Hillegas
// 02/26/2020

import java.math.BigInteger;

public class CRT {
    private static BigInteger[] ExtendedEuclidean ( BigInteger num1, BigInteger num2 ) {
        BigInteger a = num1.abs();
        BigInteger b = num2.abs();

        BigInteger u = BigInteger.ONE;
        BigInteger v;

        BigInteger x = BigInteger.ZERO;
        BigInteger y = b;

        BigInteger g = a;

        while (!y.equals(BigInteger.ZERO)) {
            BigInteger results[] = g.divideAndRemainder(y);
            BigInteger q = results[0];
            BigInteger t = results[1];

            BigInteger s = u.subtract(q.multiply(x));

            u = x;
            g = y;
            x = s;
            y = t;
        }

        v = (g.subtract(a.multiply(u))).divide(b);

        return new BigInteger[] {u, v};
    }

    private static void ChineseRemainderTheorem(BigInteger[][] c) {
        // Check that gcd(mod a, mod b) == 1 for all a, b
        for (int a = 0; a < c.length; a++) {
            for (int b = a+1; b < c.length; b++) {
                BigInteger mod1 = c[a][1];
                BigInteger mod2 = c[b][1];
                BigInteger gcd = mod1.gcd(mod2);

                if (!gcd.equals(BigInteger.ONE)) {
                    System.out.println("GCD of " + mod1 + " and " + mod2 + " is " + gcd + ", not 1.  No solutions :(");
                    return;
                }
            }
        }

        // Generate B (product of all modulus)
        BigInteger B = BigInteger.ONE;
        for (BigInteger[] i : c) {
            B = B.multiply(i[1]);
        }

        // Solve each individual B_x (B sub x) iteratively while generating their sum
        BigInteger solution = BigInteger.ZERO;
        for (BigInteger[] i : c) {
            BigInteger B_x = B.divide(i[1]);
            BigInteger C_x = i[0];

            BigInteger m = i[1];
            BigInteger a = B_x.mod(m);
            BigInteger p = ExtendedEuclidean(a, m)[0];
            BigInteger X_x = p.divide(a.gcd(m)).mod(m);

            solution = solution.add(B_x.multiply(C_x).multiply(X_x));

            System.out.println("x = " + i[0] + " (mod " + i[1] + ")");
        }

        // Print solution
        System.out.println("\nSolutions: " + solution.mod(B) + " + n * " + B + " for any integer n.");
    }


    public static void main(String args[]) {
        // Elements represent individual congruences. Ex: First index
        // listed below represents x = 37 (mod 43).
        BigInteger[][] congruences = new BigInteger[][] {
            new BigInteger[] {new BigInteger("37"), new BigInteger("43")},
            new BigInteger[] {new BigInteger("22"), new BigInteger("49")},
            new BigInteger[] {new BigInteger("18"), new BigInteger("71")},
        };

        ChineseRemainderTheorem(congruences);
    }
}
