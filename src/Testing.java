import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Testing {

    /* define constants */
    static int numberOfTrials = 10;
    private static final int MAXBITSIZE = 25;
    public static final int NUM_THREADS = 8;
    static String ResultsFolderPath = "/Users/elizabethwersal/IdeaProjects/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        // Run the whole experiment at least twice
        // Expect to throw away the data from the earlier runs, before java has fully optimized
        runFullExperiment("factorizationParallel-Exp1-ThrowAway.txt");
        //runFullExperiment("factorizationParallel-Exp2.txt");
        //runFullExperiment("factorizationParallel-Exp3.txt");
    }

    private static void runFullExperiment(String resultsFileName) {

        //  Open results file for data to be written to
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return;
        }

        //  Set up stopwatch and doubling ratio
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial

        //  Set up result file headers
        resultsWriter.println("#Bit size of p/q  AverageTime");
        resultsWriter.flush();

        //  Trial Variables
        Fermat_Parallel fermat = new Fermat_Parallel();
        BigInteger n;
        BigInteger p;
        BigInteger q;
        Factors f;

        for (int inputSize = 3; inputSize <= MAXBITSIZE; inputSize++) {
            System.out.println("Running test for bit size " + inputSize + " ... ");
            System.out.print("    Running trial batch...");
            System.gc();
            long batchElapsedTime = 0;
            for (long trial = 0; trial < numberOfTrials; trial++) {
                System.out.print("    Generating test data...");

                //Generate random p and q
                p = randomPrimeOfBitSize(inputSize);
                q = randomPrimeOfBitSize(inputSize);
                while(p.compareTo(q) == 0)
                    q = randomPrimeOfBitSize(inputSize);
                n = p.multiply(q);

                System.gc();
                System.out.println("...done.");
                System.out.println(p.toString() + " * " + q.toString());

                //Time and run the factorization algorithm
                TrialStopwatch.start();
                f = fermat.Fermat_Factorization(n);
                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime();
                //End Time

                f.printOutput();
            }
            // Calculate the average time per trial in this batch
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials;

            // Output time data to file
            resultsWriter.printf("%12d  %15.2f\n", inputSize, averageTimePerTrialInBatch);
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }

    //Returns a random prime of the specified bit size
    public static BigInteger randomPrimeOfBitSize(int size){
        BigDecimal minvalue = BigDecimal.valueOf(2).pow(size-1);
        BigDecimal maxvalue = BigDecimal.valueOf(2).pow(size);
        BigDecimal random = maxvalue.subtract(minvalue);
        random = random.multiply(BigDecimal.valueOf(Math.random()));
        random = random.add(minvalue);
        while(!isPrime(random.toBigInteger()))
        {
            random = maxvalue.subtract(minvalue);
            random = random.multiply(BigDecimal.valueOf(Math.random()));
            random = random.add(minvalue);
        }
        return random.toBigInteger();
    }

    public static boolean isPrime(BigInteger number) {
        //check via BigInteger.isProbablePrime(certainty)
        if (!number.isProbablePrime(5))
            return false;

        //check if even
        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        //find divisor if any from 3 to 'number'
        // Checking odd numbers
        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) {
            if (BigInteger.ZERO.equals(number.mod(i)))
                return false;
        }
        return true;
    }

}

