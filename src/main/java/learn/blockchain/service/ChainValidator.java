package learn.blockchain.service;

import learn.blockchain.model.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChainValidator {

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    private ProofOfWorkCalculator proofOfWorkCalculator;

    // TODO: sign and verify transactions so we dont spend others money
    public boolean isChainValid(List<Block> blocks) {
        String prevHash = Block.GENESIS_BLOCK.getPrevHash();
        for (Block block: blocks) {
            String blockHash = block.hashBlock();

            if (!prevHash.equals(block.getPrevHash()) || !blockHash.equals(block.getHash())) {
                return false;
            }

            proofOfWorkCalculator.isProofOfWorkValid(block.getProofOfWork(), prevHash);
            prevHash = blockHash;

            if (!transactionValidator.isTransactionListValid(block.getData().getTransactions())) {
                return false;
            }
        }
        return true;
    }
}
