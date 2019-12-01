import java.math.BigInteger;

public class Factors {
    public BigInteger n;
    public BigInteger p;
    public BigInteger q;
    private BigInteger k;
    private boolean hasK = false;

    public Factors(BigInteger n)
    {
        this.n = n;
    }

    public void setK( BigInteger k)
    {
        this.k = k;
        BigInteger square = n.add(k.pow(2));
        p = square.sqrt().add(k);
        q = square.sqrt().subtract(k);
        hasK = true;
    }

    public boolean hasK() { return hasK; }

    public void printOutput()
    {
        System.out.println(p + " * " + q);
    }
}
