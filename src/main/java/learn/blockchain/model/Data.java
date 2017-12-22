package learn.blockchain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Data {
    private List<Transaction> transactions;

    @JsonCreator
    public Data(@JsonProperty("transactions") List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Data{transactions=" + transactions + '}';
    }
}
