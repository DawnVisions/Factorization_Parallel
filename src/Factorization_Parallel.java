import java.io.Console;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.NoSuchElementException;
import java.util.OptionalLong;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.function.LongConsumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Factorization_Parallel {

    public static Factors Fermat_Factorization(BigInteger n)
    {

        OptionalLong foundK = LongStream.range(0, Long.parseLong(n.sqrt().toString()))
                .parallel()
                .filter(k -> isPerfectSquare(n.add(BigInteger.valueOf((long)Math.pow(k, 2))), k))
                .findFirst();
        BigInteger k = BigInteger.valueOf(foundK.getAsLong());
        BigInteger s = squareRoot(n.add(k.pow(2))).toBigInteger();

        return new Factors(s.add(k), s.subtract(k), k );
    }

    static boolean isPerfectSquare(BigInteger s, long k)
    {
        System.out.println("Thread : " + Thread.currentThread().getName() + ", value: " + k);
        return squareRoot(s).stripTrailingZeros().scale() <= 0;
    }

    static BigDecimal squareRoot(BigInteger n)
    {
        BigDecimal x = new BigDecimal(n.toString());
        MathContext mc = new MathContext(20);
        return x.sqrt(mc);
    }
}
