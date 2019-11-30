import java.io.Console;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Factorization {

    public static Factors Fermat_Factorization(BigInteger n)
    {
        boolean found = false;
        BigInteger k = new BigInteger("-1");
        BigInteger s = new BigInteger("0");

        while (!found)
        {
            k = k.add(BigInteger.ONE);
            s = n.add(k.pow(2));
            if (isPerfectSquare(s))
            {
                s = squareRoot(s).toBigInteger();
                found = true;
            }
        }
        return new Factors(s.add(k), s.subtract(k), k );
    }

    static boolean isPerfectSquare(BigInteger s)
    {
        return squareRoot(s).stripTrailingZeros().scale() <= 0;
    }

    static BigDecimal squareRoot(BigInteger n)
    {
        BigDecimal x = new BigDecimal(n.toString());
        MathContext mc = new MathContext(100);
        return x.sqrt(mc);
    }
}
