import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Fermat_Loop implements Runnable {

    Factors factors;
    ThreadCancellation cancellation;

    public void run()
    {
        BigInteger n = factors.n;
        Thread t = Thread.currentThread();

        //  Static Scheduling:
        //  Each thread tests k values, starting with it's own thread number
        //     and continuing, adding NUM_THREADS each time
        //     With 8 threads: Thread 0 checks 0, 8, 16, ...
        long k = Long.parseLong(t.getName());
        while(k < Long.parseLong(n.sqrt().toString()))
        {
            try {
                BigInteger square = n.add(BigInteger.valueOf((long) Math.pow(k, 2)));
                if (isPerfectSquare(square)) {
                    factors.setK(BigInteger.valueOf(k));
                    cancellation.stopThreads();
                    break;
                }
                k += Testing.NUM_THREADS;
            }
            catch (InterruptedException e) {
                // Break loop if thread receives interrupt from handling class
                break;
            }
        }
    }

    static boolean isPerfectSquare(BigInteger s)
    {
        return squareRoot(s).scale() <= 0;
    }

    public static BigDecimal squareRoot(BigInteger n)
    {
        BigDecimal x = new BigDecimal(n.toString());
        MathContext mc = new MathContext(50);
        return x.sqrt(mc);
    }

    //  Class Constructor
    public Fermat_Loop(Factors f, ThreadCancellation stopThreads) {
        this.factors = f;
        cancellation = stopThreads;
    }
}
