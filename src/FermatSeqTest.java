import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FermatSeqTest {

    @Test
    void fermat_test1() {
        Factors f = Fermat_Sequential.Fermat_Factorization(new BigInteger("4019989"));
        assertEquals("2011" , f.p.toString());
    }

    @Test
    void fermat_test2() {
        Factors f = Fermat_Sequential.Fermat_Factorization(new BigInteger("1"));
        assertEquals("1", f.p.toString());
    }

    @Test
    void fermat_test3() {
        Factors f = Fermat_Sequential.Fermat_Factorization(new BigInteger("57306427"));
        assertEquals("7817", f.p.toString());
    }

    @Test
    void fermat_test4() {
        Factors f = Fermat_Sequential.Fermat_Factorization(BigInteger.valueOf(13009*12889));
        assertEquals("13009", f.p.toString());
    }

    @Test
    void fermat_test5() {
        Factors f = Fermat_Sequential.Fermat_Factorization(new BigInteger("999057799"));
        assertEquals("98809", f.p.toString());
    }

    @Test
    void fermat_test6() {
        Factors f = Fermat_Sequential.Fermat_Factorization(new BigInteger("4695728683"));
        assertEquals("70051", f.p.toString());
    }

    @Test
    void fermat_test7() {
        Factors f = Fermat_Sequential.Fermat_Factorization(BigInteger.valueOf(45007*41621));
        assertEquals("45007", f.p.toString());
    }
}