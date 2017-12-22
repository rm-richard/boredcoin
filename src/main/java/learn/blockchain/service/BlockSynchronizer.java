package learn.blockchain.service;

import learn.blockchain.model.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BlockSynchronizer {

    private static final Logger logger = LoggerFactory.getLogger(BlockSynchronizer.class);

    @Value("${peerNodes}")
    private String[] peerNodes;

    @Autowired
    private Store store;

    @Autowired
    private ChainValidator chainValidator;

    // TODO: pending transactions also need to be synched
    @Scheduled(fixedRate = 20000, initialDelay = 10000)
    public void synchronizeBlocks() {
        List<List<Block>> otherChains = findOtherChains();
        List<Block> longestChain = store.getBlocks();
        for (List<Block> chain : otherChains) {
            if (chain.size() > longestChain.size() && chainValidator.isChainValid(chain)) {
                longestChain = chain;
            }
        }

        if (longestChain != store.getBlocks()) {
            logger.info("Replacing blockchain with a longer one");
            store.setBlocks(new ArrayList<>(longestChain));
        }
    }

    public List<List<Block>> findOtherChains() {
        List<List<Block>> otherChains = new ArrayList<>();

        for (String peer : peerNodes) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Block[]> chain = restTemplate.getForEntity("http://" + peer + "/blocks", Block[].class);
                List<Block> receivedChain = Arrays.asList(chain.getBody());
                otherChains.add(receivedChain);
                logger.debug("Peer {} responded with a chain of length {}", peer, receivedChain.size());
            } catch (Exception ex) {
                logger.debug("Peer {} cannot be reached", peer);
            }
        }

        return otherChains;
    }
}
