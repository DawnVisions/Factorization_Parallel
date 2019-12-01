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
        while(k < Long.parseLong(n.sqrt().toString()) && !t.isInterrupted())
        {
            try {
                // square = N + k^2
                BigInteger square = n.add(BigInteger.valueOf((long) Math.pow(k, 2)));
                BigDecimal decimalSquare = new BigDecimal(square.toString());
                // sqrt ( square)
                decimalSquare = decimalSquare.sqrt(new MathContext(50));
                if (t.isInterrupted()) {
                    return;
                }
                // Is square a perfect square?
                //      If so, set k, p, and q in factors
                //      stop the other threads
                if (decimalSquare.scale() <= 0) {
                    factors.setK(BigInteger.valueOf(k));
                    cancellation.stopThreads();
                    return;
                }
                // If not: Next k value to check
                k += Testing.NUM_THREADS;
            }
            catch (InterruptedException e) {
                // Break loop if thread receives interrupt from handling class
                return;
            }
        }
    }

    public Fermat_Loop(Factors f, ThreadCancellation stopThreads) {
        this.factors = f;
        cancellation = stopThreads;
    }
}
