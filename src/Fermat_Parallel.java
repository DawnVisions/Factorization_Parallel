import java.math.BigInteger;

public class Fermat_Parallel {
    Thread[] threads;

    public Factors Fermat_Factorization(BigInteger n)
    {
        Factors factors = new Factors(n);

        // Initiate threads and store in threads array
        threads = new Thread[Testing.NUM_THREADS];
        for ( int i = 0; i < Testing.NUM_THREADS; i++)
        {
            // Fermat_Loop (Runnable class) contains the work that each thread must complete
            Fermat_Loop work = new Fermat_Loop(factors, this::stopThreads);
            Thread object = new Thread(work, String.valueOf(i));
            threads[i] = object;
            object.start();
        }

        for ( int i = 0; i < Testing.NUM_THREADS; i++)
        {
            try{
                threads[i].join();
            }
            catch (Exception e) {

            }
        }
        return factors;
    }

    // Interrupts all threads
    // Thread can have the handler call this method when it finds the correct factors
    public void stopThreads() throws InterruptedException
    {
        for ( int i = 0; i < Testing.NUM_THREADS; i++)
        {
            threads[i].interrupt();
        }
    }
}
