import java.io.Console;
import java.math.BigInteger;

public class Factors {
    public BigInteger p;
    public BigInteger q;
    private BigInteger k;

    public Factors(BigInteger p, BigInteger q, BigInteger k)
    {
        this.p = p;
        this.q = q;
        this.k = k;
    }

    public void printOutput()
    {
        System.out.println(p + " * " + q);
    }
}
