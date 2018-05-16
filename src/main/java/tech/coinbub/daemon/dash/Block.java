package tech.coinbub.daemon.dash;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Block {
    public String hash;
    public Long confirmations;
    public Long size;
    public Long height;
    public Long version;
    public String merkleroot;
    public List<String> tx;
    public Long time;
    public Long mediantime;
    public Long nonce;
    public String bits;
    public BigDecimal difficulty;
    public String chainwork;
    public String previousblockhash;
    public String nextblockhash;
}

//dash@test:~$ dash-cli getblock 5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098
//{
//  "hash": "5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098",
//  "confirmations": 1,
//  "size": 180,
//  "height": 120,
//  "version": 536870912,
//  "merkleroot": "ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9",
//  "tx": [
//    "ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9"
//  ],
//  "time": 1526427059,
//  "mediantime": 1526427058,
//  "nonce": 2,
//  "bits": "207fffff",
//  "difficulty": 4.656542373906925e-10,
//  "chainwork": "00000000000000000000000000000000000000000000000000000000000000f2",
//  "previousblockhash": "04ba8ee85b10ca00d191a7b9bc4c11bbda9f7c5a75a2e974c443d592d5de705d"
//}