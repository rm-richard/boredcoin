package learn.blockchain.controller;

import learn.blockchain.service.ProofOfWorkCalculator;
import learn.blockchain.service.Store;
import learn.blockchain.model.Block;
import learn.blockchain.model.Data;
import learn.blockchain.model.Transaction;
import learn.blockchain.service.WalletSnapshotStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class MinerController {

    private static final Logger logger = LoggerFactory.getLogger(MinerController.class);

    @Autowired
    private Store store;

    @Autowired
    private ProofOfWorkCalculator proofOfWorkCalculator;

    @Autowired
    private WalletSnapshotStore walletSnapshotStore;

    @RequestMapping(value = "/mine", method = RequestMethod.POST)
    public Block mine() {
        Block lastBlock = store.getBlocks().get(store.getBlocks().size() - 1);
        String proof = proofOfWorkCalculator.mineNextProofOfWork(lastBlock.getHash());

        store.getTransactions().add(new Transaction("NETWORK", Store.MINER_ADDRESS, 1));

        Data newData = new Data(store.getTransactions());
        Block minedBlock = new Block(lastBlock.getIdx() + 1, new Date(), newData, lastBlock.getHash(), proof);
        store.setTransactions(new ArrayList<>());

        logger.info("Block mined with hash {}", minedBlock.getHash());
        logger.debug(minedBlock.toString());

        store.getBlocks().add(minedBlock);
        walletSnapshotStore.refreshStore(); // TODO: invalidate instead
        return minedBlock;
    }
}
