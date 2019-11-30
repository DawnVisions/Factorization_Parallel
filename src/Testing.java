import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Testing {

    /* define constants */
    static int numberOfTrials = 3;
    private static final int MAXBITSIZE = 4;
    static String ResultsFolderPath = "/Users/elizabethwersal/IdeaProjects/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        Factors f = Factorization_Parallel.Fermat_Factorization(new BigInteger("4019989"));
        f.printOutput();

        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
        runFullExperiment("factorizationParallel-Exp1-ThrowAway.txt");
        //runFullExperiment("factorizationParallel-Exp2.txt");
        //runFullExperiment("factorizationParallel-Exp3.txt");
    }

    private static void runFullExperiment(String resultsFileName) {

        //Open results file for data to be written to
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return;
        }

        //Set up stopwatch and doubling ratio
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial
        double lastAverageTime = -1;
        double doublingRatio = 0;

        //Set up result file headers
        resultsWriter.println("#Bit size of p/q  AverageTime  Doubling Ratio");
        resultsWriter.flush();

        //Trial Variables
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
                //Generate p and q
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
                f = Factorization_Parallel.Fermat_Factorization(n);
                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime();
                //End Time

                f.printOutput();
            }
            ///calculate the average time per trial in this batch
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials;
            if (lastAverageTime != -1) {
                doublingRatio = averageTimePerTrialInBatch / lastAverageTime;
            }
            lastAverageTime = averageTimePerTrialInBatch;

            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f  %15f\n", inputSize, averageTimePerTrialInBatch, doublingRatio);
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
        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
            if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
                return false;
        }
        return true;
    }

}

