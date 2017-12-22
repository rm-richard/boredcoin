package learn.blockchain.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProofOfWorkCalculatorTest {

    @Test
    public void testMining() {
        final String previousHash = "testPrevHash";
        ProofOfWorkCalculator calculator = new ProofOfWorkCalculator("0000");
        String proofOfWork = calculator.mineNextProofOfWork("testPrevHash");
        boolean isPofValid = calculator.isProofOfWorkValid(proofOfWork, previousHash);

        assertTrue(isPofValid);
    }
}