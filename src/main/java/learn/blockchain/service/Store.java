package learn.blockchain.service;

import learn.blockchain.model.Block;
import learn.blockchain.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Store {

    // TODO: move to an external wallet class
    public static String MINER_ADDRESS = "random_miner_address_q435t45z4";

    private List<Transaction> transactions = new ArrayList<Transaction>();
    private List<Block> blocks = new ArrayList<>();

    public Store() {
        blocks.add(Block.GENESIS_BLOCK);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
