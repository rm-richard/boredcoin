package learn.blockchain.service;

import learn.blockchain.model.Block;
import learn.blockchain.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WalletSnapshotStore {
    private Map<String, Integer> walletBalances = new HashMap<>();

    @Autowired
    private Store store;

    // TODO: introduce an invalidator method, calculate as needed in the getter
    public void refreshStore() {
        walletBalances = new HashMap<>();
        List<Block> blocks = store.getBlocks();

        for (Block block: blocks) {
            for (Transaction tx: block.getData().getTransactions()) {
                increaseWalletBalance(tx.getFrom(), tx.getAmount() * (-1));
                increaseWalletBalance(tx.getTo(), tx.getAmount());
            }
        }
    }

    public Integer getWalletBalance(String address) {
        Integer val = walletBalances.get(address);
        return val == null ? 0 : val;
    }

    private void increaseWalletBalance(String address, Integer value) {
        if (walletBalances.containsKey(address)) {
            walletBalances.put(address, walletBalances.get(address) + value);
        } else {
            walletBalances.put(address, value);
        }
    }
}
