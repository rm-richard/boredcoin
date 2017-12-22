package learn.blockchain.service;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
public class ProofOfWorkCalculator {

    private static final Logger logger = LoggerFactory.getLogger(ProofOfWorkCalculator.class);

    private final String difficulty;

    @Autowired
    public ProofOfWorkCalculator(@Value("${difficulty:000000}") String difficulty) {
        this.difficulty = difficulty;
    }

    public String mineNextProofOfWork(String previousBlockHash) {
        int seed = new Random().nextInt(100);
        int incrementor = 0;
        String pof = null;
        String pofHash = "";

        while (!pofHash.startsWith(difficulty)) {
            incrementor += 1;
            pof = seed + "" + incrementor;
            pofHash = toSha256(toHashFormat(pof, previousBlockHash));
        }

        logger.info("Found POF {} with hash {}", pof, pofHash);
        return pof;
    }

    public boolean isProofOfWorkValid(String proofOfWork, String previousBlockHash) {
        return toSha256(toHashFormat(proofOfWork, previousBlockHash)).startsWith(difficulty);
    }

    private String toHashFormat(String proofOfWork, String previousBlockHash) {
        return proofOfWork + "::" + previousBlockHash;
    }

    private String toSha256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            return Hex.encodeHexString(md.digest());
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Mining exception..");
        }
    }
}
