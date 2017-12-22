package learn.blockchain.service;

import learn.blockchain.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionValidator {

    public static final String NETWORK_ADDRESS = "NETWORK";
    public static final int NETWORK_REWARD_MAX = 1;

    @Autowired
    private WalletSnapshotStore walletStore;

    public boolean isTransactionListValid(List<Transaction> transactions) {
        walletStore.refreshStore();
        int networkRewardCount = 0;

        for (Transaction tx : transactions) {
            if (NETWORK_ADDRESS.equals(tx.getFrom())) {
                networkRewardCount += 1;
                if (networkRewardCount > 1 || NETWORK_REWARD_MAX < tx.getAmount()) {
                    return false;
                }
            } else if (!isSingleTransactionValid(tx)) {
                return false;
            }
        }
        return true;
    }

    // TODO: coins can be double spent if included in one block
    public boolean isSingleTransactionValid(Transaction transaction) {
        int balance = walletStore.getWalletBalance(transaction.getFrom());
        return balance >= transaction.getAmount();
    }
}
