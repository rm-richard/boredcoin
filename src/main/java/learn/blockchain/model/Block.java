package learn.blockchain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Date;

public class Block {
    public static final Block GENESIS_BLOCK = new Block(0, new Date(),
            new Data(Collections.<Transaction>emptyList()), "0", "1992");

    private Integer idx;
    private Date timestamp;
    private Data data;
    private String prevHash;
    private String proofOfWork;
    private String hash;

    @JsonCreator
    public Block(@JsonProperty("idx") Integer idx, @JsonProperty("timestamp") Date timestamp,
                 @JsonProperty("data") Data data, @JsonProperty("prevHash") String prevHash,
                 @JsonProperty("proofOfWork") String proofOfWork, @JsonProperty("hash") String hash) {
        this.idx = idx;
        this.timestamp = timestamp;
        this.data = data;
        this.prevHash = prevHash;
        this.proofOfWork = proofOfWork;
        this.hash = hash;
    }

    public Block(Integer idx, Date timestamp, Data data, String prevHash, String proofOfWork) {
        this.idx = idx;
        this.timestamp = timestamp;
        this.data = data;
        this.prevHash = prevHash;
        this.proofOfWork = proofOfWork;
        this.hash = hashBlock();
    }

    public String hashBlock() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(idx);
            baos.write(timestamp.toString().getBytes());
            baos.write(data.toString().getBytes());
            baos.write(proofOfWork.toString().getBytes());
            baos.write(prevHash.getBytes());
            return Hex.encodeHexString(digest.digest(baos.toByteArray()));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to hash block", ex);
        }
    }

    public Integer getIdx() {
        return idx;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Data getData() {
        return data;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getProofOfWork() {
        return proofOfWork;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "idx=" + idx +
                ", timestamp=" + timestamp +
                ", data='" + data + '\'' +
                ", proofOfWork='" + proofOfWork + '\'' +
                ", prevHash='" + prevHash + '\'' +
                '}';
    }
}
