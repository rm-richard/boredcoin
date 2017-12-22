package learn.blockchain;

import learn.blockchain.model.Block;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BlockTest {

    @Test
    @Ignore
    public void testBlockCreation() {
        List<Block> chain = new ArrayList<Block>();
        Block prevBlock = Block.GENESIS_BLOCK;
        chain.add(prevBlock);

        for (int i = 1; i <= 20; ++i) {
            //Block newBlock = prevBlock.nextBlock();
            Block newBlock = null;
            chain.add(newBlock);
            prevBlock = newBlock;

            System.out.println("Block #" + i + " added to the chain");
            System.out.println("Hash: " + newBlock.getHash());
        }
    }
}