package tech.coinbub.daemon.dash;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.List;
import tech.coinbub.daemon.serialization.YesNoBooleanDeserializer;
import tech.coinbub.daemon.serialization.YesNoBooleanSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    public String txid;
    public BigDecimal amount;
    public BigDecimal fee;
    public Long confirmations;
    public Boolean instantlock;
    public Boolean generated;
    public Boolean trusted;
    public String blockhash;
    public Long blockindex;
    public Long blocktime;
    public List<String> walletconflicts;
    public Long time;
    public Long timereceived;
    @JsonProperty("bip125-replaceable")
    @JsonDeserialize(using = YesNoBooleanDeserializer.class)
    @JsonSerialize(using = YesNoBooleanSerializer.class)
    public Boolean bip125_replaceable;
    public List<TransactionDetail> details;
    public String hex;
    public String comment;
    public String to;
}

//dash@test:~$ dash-cli gettransaction ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9
//{
//  "amount": 0.00000000,
//  "confirmations": 1,
//  "instantlock": false,
//  "generated": true,
//  "blockhash": "5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098",
//  "blockindex": 0,
//  "blocktime": 1526427059,
//  "txid": "ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9",
//  "walletconflicts": [
//  ],
//  "time": 1526427051,
//  "timereceived": 1526427051,
//  "bip125-replaceable": "no",
//  "details": [
//    {
//      "account": "",
//      "address": "yMQcwprKzfs4XYVouPoeYQyvHWZFzdfJR5",
//      "category": "immature",
//      "amount": 500.00000000,
//      "vout": 0
//    }
//  ],
//  "hex": "01000000010000000000000000000000000000000000000000000000000000000000000000ffffffff0401780101ffffffff0100743ba40b000000232103aa149e05747f78bdcafecfca069aaa791c2ca16867083b4455911fc211e273daac00000000"
//}
