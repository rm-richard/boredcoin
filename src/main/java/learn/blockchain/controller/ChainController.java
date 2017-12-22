package learn.blockchain.controller;

import com.google.common.collect.ImmutableMap;
import learn.blockchain.service.Store;
import learn.blockchain.model.Block;
import learn.blockchain.model.Transaction;
import learn.blockchain.service.TransactionValidator;
import learn.blockchain.service.WalletSnapshotStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ChainController {

    private static final Logger logger = LoggerFactory.getLogger(ChainController.class);

    @Autowired
    private Store store;

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    private WalletSnapshotStore walletStore;

    @RequestMapping("/transactions")
    public List<Transaction> getTransactions() {
        return store.getTransactions();
    }

    @RequestMapping("/blocks")
    public List<Block> getBlockchain() {
        return store.getBlocks();
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity createTransaction(@RequestBody Transaction transaction) {
        if (transactionValidator.isSingleTransactionValid(transaction)) {
            store.getTransactions().add(transaction);
            logger.debug("New transaction requested: " + transaction);
            return ResponseEntity.status(200).body(null);
        } else {
            logger.warn("Invalid transaction: " + transaction);
            return ResponseEntity.status(400).body("Invalid transaction");
        }
    }

    @RequestMapping(value = "/coins/{id}")
    public Map<String, String> getCurrentCoins(@PathVariable("id") String id) {
        walletStore.refreshStore();

        return ImmutableMap.of("minerId", id,"value", String.valueOf(walletStore.getWalletBalance(id)));
    }
}
