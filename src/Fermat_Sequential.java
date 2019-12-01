import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Fermat_Sequential {

    public static Factors Fermat_Factorization(BigInteger n)
    {
        Factors factors = new Factors(n);
        boolean found = false;
        BigInteger k = BigInteger.ZERO;
        BigInteger square;

        while (!found)
        {
            square = n.add(k.pow(2));
            if (isPerfectSquare(square))
            {
                found = true;
                factors.setK(k);
            }
            k = k.add(BigInteger.ONE);
        }
        return factors;
    }

    static boolean isPerfectSquare(BigInteger s)
    {
        return squareRoot(s).stripTrailingZeros().scale() <= 0;
    }

    static BigDecimal squareRoot(BigInteger n)
    {
        BigDecimal x = new BigDecimal(n.toString());
        MathContext mc = new MathContext(50);
        return x.sqrt(mc);
    }
}
