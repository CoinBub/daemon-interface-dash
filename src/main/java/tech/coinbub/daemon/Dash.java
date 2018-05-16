package tech.coinbub.daemon;

import java.math.BigDecimal;
import tech.coinbub.daemon.dash.Block;
import tech.coinbub.daemon.dash.Transaction;

/**
 * `dash-cli help` returns:
 * == Addressindex ==
 * getaddressbalance
 * getaddressdeltas
 * getaddressmempool
 * getaddresstxids
 * getaddressutxos
 * 
 * == Blockchain ==
 * getbestblockhash
 * getblock "hash" ( verbose )
 * getblockchaininfo
 * getblockcount
 * getblockhash index
 * getblockhashes timestamp
 * getblockheader "hash" ( verbose )
 * getblockheaders "hash" ( count verbose )
 * getchaintips ( count branchlen )
 * getdifficulty
 * getmempoolinfo
 * getrawmempool ( verbose )
 * getspentinfo
 * gettxout "txid" n ( includemempool )
 * gettxoutproof ["txid",...] ( blockhash )
 * gettxoutsetinfo
 * verifychain ( checklevel numblocks )
 * verifytxoutproof "proof"
 * 
 * == Control ==
 * debug ( 0|1|addrman|alert|bench|coindb|db|lock|rand|rpc|selectcoins|mempool|mempoolrej|net|proxy|prune|http|libevent|tor|zmq|dash|privatesend|instantsend|masternode|spork|keepass|mnpayments|gobject )
 * getinfo
 * help ( "command" )
 * stop
 * 
 * == Dash ==
 * getgovernanceinfo
 * getpoolinfo
 * getsuperblockbudget index
 * gobject "command"...
 * masternode "command"...
 * masternodebroadcast "command"...
 * masternodelist ( "mode" "filter" )
 * mnsync [status|next|reset]
 * privatesend "command"
 * sentinelping version
 * spork <name> [<value>]
 * voteraw <masternode-tx-hash> <masternode-tx-index> <governance-hash> <vote-signal> [yes|no|abstain] <time> <vote-sig>
 * 
 * == Generating ==
 * generate numblocks
 * getgenerate
 * setgenerate generate ( genproclimit )
 * 
 * == Mining ==
 * getblocktemplate ( "jsonrequestobject" )
 * getmininginfo
 * getnetworkhashps ( blocks height )
 * prioritisetransaction <txid> <priority delta> <fee delta>
 * submitblock "hexdata" ( "jsonparametersobject" )
 * 
 * == Network ==
 * addnode "node" "add|remove|onetry"
 * clearbanned
 * disconnectnode "node" 
 * getaddednodeinfo dummy ( "node" )
 * getconnectioncount
 * getnettotals
 * getnetworkinfo
 * getpeerinfo
 * listbanned
 * ping
 * setban "ip(/netmask)" "add|remove" (bantime) (absolute)
 * setnetworkactive true|false
 * 
 * == Rawtransactions ==
 * createrawtransaction [{"txid":"id","vout":n},...] {"address":amount,"data":"hex",...} ( locktime )
 * decoderawtransaction "hexstring"
 * decodescript "hex"
 * fundrawtransaction "hexstring" includeWatching
 * getrawtransaction "txid" ( verbose )
 * sendrawtransaction "hexstring" ( allowhighfees instantsend )
 * signrawtransaction "hexstring" ( [{"txid":"id","vout":n,"scriptPubKey":"hex","redeemScript":"hex"},...] ["privatekey1",...] sighashtype )
 * 
 * == Util ==
 * createmultisig nrequired ["key",...]
 * estimatefee nblocks
 * estimatepriority nblocks
 * estimatesmartfee nblocks
 * estimatesmartpriority nblocks
 * validateaddress "dashaddress"
 * verifymessage "dashaddress" "signature" "message"
 * 
 * == Wallet ==
 * abandontransaction "txid"
 * addmultisigaddress nrequired ["key",...] ( "account" )
 * backupwallet "destination"
 * dumphdinfo
 * dumpprivkey "dashaddress"
 * dumpwallet "filename"
 * encryptwallet "passphrase"
 * getaccount "dashaddress"
 * getaccountaddress "account"
 * getaddressesbyaccount "account"
 * getbalance ( "account" minconf addlockconf includeWatchonly )
 * getnewaddress ( "account" )
 * getrawchangeaddress
 * getreceivedbyaccount "account" ( minconf addlockconf )
 * getreceivedbyaddress "dashaddress" ( minconf addlockconf )
 * gettransaction "txid" ( includeWatchonly )
 * getunconfirmedbalance
 * getwalletinfo
 * importaddress "address" ( "label" rescan p2sh )
 * importelectrumwallet "filename" index
 * importprivkey "dashprivkey" ( "label" rescan )
 * importpubkey "pubkey" ( "label" rescan )
 * importwallet "filename"
 * instantsendtoaddress "dashaddress" amount ( "comment" "comment-to" subtractfeefromamount )
 * keepass <genkey|init|setpassphrase>
 * keypoolrefill ( newsize )
 * listaccounts ( minconf addlockconf includeWatchonly)
 * listaddressgroupings
 * listlockunspent
 * listreceivedbyaccount ( minconf addlockconf includeempty includeWatchonly)
 * listreceivedbyaddress ( minconf addlockconf includeempty includeWatchonly)
 * listsinceblock ( "blockhash" target-confirmations includeWatchonly)
 * listtransactions    ( "account" count from includeWatchonly)
 * listunspent ( minconf maxconf ["address",...] )
 * lockunspent unlock [{"txid":"txid","vout":n},...]
 * move "fromaccount" "toaccount" amount ( minconf "comment" )
 * sendfrom "fromaccount" "todashaddress" amount ( minconf addlockconf "comment" "comment-to" )
 * sendmany "fromaccount" {"address":amount,...} ( minconf addlockconf "comment" ["address",...] subtractfeefromamount use_is use_ps )
 * sendtoaddress "dashaddress" amount ( "comment" "comment-to" subtractfeefromamount use_is use_ps )
 * setaccount "dashaddress" "account"
 * settxfee amount
 * signmessage "dashaddress" "message"
 */
public interface Dash {
    /**
     * `getnewaddress ( "account" )`
     * 
     * Returns a new Dash address for receiving payments.
     * If 'account' is specified (DEPRECATED), it is added to the address book 
     * so payments received with the address will be credited to 'account'.
     * 
     * Arguments:
     * 1. "account"        (string, optional) DEPRECATED. The account name for the address to be linked to. If not provided, the default account "" is used. It can also be set to the empty string "" to represent the default account. The account does not need to exist, it will be created if there is no account by the given name.
     * 
     * Result:
     * "dashaddress"    (string) The new dash address
     * 
     * Examples:
     * > dash-cli getnewaddress 
     * > curl --user myusername --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getnewaddress", "params": [] }' -H 'content-type: text/plain;' http://127.0.0.1:9998/
     */
    String getnewaddress();
    String getnewaddress(String label);

    /**
     * `getbestblockhash`
     * 
     * Returns the hash of the best (tip) block in the longest block chain.
     * 
     * Result
     * "hex"      (string) the block hash hex encoded
     * 
     * Examples
     * > dash-cli getbestblockhash 
     * > curl --user myusername --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getbestblockhash", "params": [] }' -H 'content-type: text/plain;' http://127.0.0.1:9998/
     */
    String getbestblockhash();

    /**
     * `getblock "hash" ( verbose )`
     * 
     * If verbose is false, returns a string that is serialized, hex-encoded data for block 'hash'.
     * If verbose is true, returns an Object with information about block <hash>.
     * 
     * Arguments:
     * 1. "hash"          (string, required) The block hash
     * 2. verbose           (boolean, optional, default=true) true for a json object, false for the hex encoded data
     * 
     * Result (for verbose = true):
     * {
     *   "hash" : "hash",     (string) the block hash (same as provided)
     *   "confirmations" : n,   (numeric) The number of confirmations, or -1 if the block is not on the main chain
     *   "size" : n,            (numeric) The block size
     *   "height" : n,          (numeric) The block height or index
     *   "version" : n,         (numeric) The block version
     *   "merkleroot" : "xxxx", (string) The merkle root
     *   "tx" : [               (array of string) The transaction ids
     *      "transactionid"     (string) The transaction id
     *      ,...
     *   ],
     *   "time" : ttt,          (numeric) The block time in seconds since epoch (Jan 1 1970 GMT)
     *   "mediantime" : ttt,    (numeric) The median block time in seconds since epoch (Jan 1 1970 GMT)
     *   "nonce" : n,           (numeric) The nonce
     *   "bits" : "1d00ffff", (string) The bits
     *   "difficulty" : x.xxx,  (numeric) The difficulty
     *   "chainwork" : "xxxx",  (string) Expected number of hashes required to produce the chain up to this block (in hex)
     *   "previousblockhash" : "hash",  (string) The hash of the previous block
     *   "nextblockhash" : "hash"       (string) The hash of the next block
     * }
     * 
     * Result (for verbose=false):
     * "data"             (string) A string that is serialized, hex-encoded data for block 'hash'.
     * 
     * Examples:
     * > dash-cli getblock "00000000000fd08c2fb661d2fcb0d49abb3a91e5f27082ce64feed3b4dede2e2"
     * > curl --user myusername --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblock", "params": ["00000000000fd08c2fb661d2fcb0d49abb3a91e5f27082ce64feed3b4dede2e2"] }' -H 'content-type: text/plain;' http://127.0.0.1:9998/
     */
    Block getblock(String hash);
    Block getblock(String hash, boolean verbose);
    
    /**
     * `gettransaction "txid" ( includeWatchonly )`
     * 
     * Get detailed information about in-wallet transaction <txid>
     * 
     * Arguments:
     * 1. "txid"                (string, required) The transaction id
     * 2. "includeWatchonly"    (bool, optional, default=false) Whether to include watchonly addresses in balance calculation and details[]
     * 
     * Result:
     * {
     *   "amount" : x.xxx,        (numeric) The transaction amount in DASH
     *   "instantlock" : true|false, (bool) Current transaction lock state
     *   "confirmations" : n,     (numeric) The number of blockchain confirmations
     *   "blockhash" : "hash",    (string) The block hash
     *   "blockindex" : xx,       (numeric) The index of the transaction in the block that includes it
     *   "blocktime" : ttt,       (numeric) The time in seconds since epoch (1 Jan 1970 GMT)
     *   "txid" : "transactionid",   (string) The transaction id.
     *   "time" : ttt,            (numeric) The transaction time in seconds since epoch (1 Jan 1970 GMT)
     *   "timereceived" : ttt,    (numeric) The time received in seconds since epoch (1 Jan 1970 GMT)
     *   "bip125-replaceable": "yes|no|unknown"  (string) Whether this transaction could be replaced due to BIP125 (replace-by-fee);
     *                                                    may be unknown for unconfirmed transactions not in the mempool
     *   "details" : [
     *     {
     *       "account" : "accountname",      (string) DEPRECATED. The account name involved in the transaction, can be "" for the default account.
     *       "address" : "dashaddress",      (string) The dash address involved in the transaction
     *       "category" : "send|receive",    (string) The category, either 'send' or 'receive'
     *       "amount" : x.xxx,               (numeric) The amount in DASH
     *       "label" : "label",              (string) A comment for the address/transaction, if any
     *       "vout" : n,                     (numeric) the vout value
     *     }
     *     ,...
     *   ],
     *   "hex" : "data"                      (string) Raw data for transaction
     * }
     * 
     * Examples:
     * > dash-cli gettransaction "1075db55d416d3ca199f55b6084e2115b9345e16c5cf302fc80e9d5fbf5d48d"
     * > dash-cli gettransaction "1075db55d416d3ca199f55b6084e2115b9345e16c5cf302fc80e9d5fbf5d48d" true
     * > curl --user myusername --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "gettransaction", "params": ["1075db55d416d3ca199f55b6084e2115b9345e16c5cf302fc80e9d5fbf5d48d"] }' -H 'content-type: text/plain;' http://127.0.0.1:9998/
     */
    Transaction gettransaction(String txid);
    Transaction gettransaction(String txid, boolean includeWatchOnly);

    /**
     * sendtoaddress "dashaddress" amount ( "comment" "comment-to" subtractfeefromamount use_is use_ps )
     * 
     * Send an amount to a given address.
     * 
     * Arguments:
     * 1. "dashaddress" (string, required) The dash address to send to.
     * 2. "amount"      (numeric or string, required) The amount in DASH to send. eg 0.1
     * 3. "comment"     (string, optional) A comment used to store what the transaction is for. 
     *                              This is not part of the transaction, just kept in your wallet.
     * 4. "comment-to"  (string, optional) A comment to store the name of the person or organization 
     *                              to which you're sending the transaction. This is not part of the 
     *                              transaction, just kept in your wallet.
     * 5. subtractfeefromamount  (boolean, optional, default=false) The fee will be deducted from the amount being sent.
     *                              The recipient will receive less amount of Dash than you enter in the amount field.
     * 6. "use_is"      (bool, optional) Send this transaction as InstantSend (default: false)
     * 7. "use_ps"      (bool, optional) Use anonymized funds only (default: false)
     * 
     * Result:
     * "transactionid"  (string) The transaction id.
     * 
     * Examples:
     * > dash-cli sendtoaddress "XwnLY9Tf7Zsef8gMGL2fhWA9ZmMjt4KPwg" 0.1
     * > dash-cli sendtoaddress "XwnLY9Tf7Zsef8gMGL2fhWA9ZmMjt4KPwg" 0.1 "donation" "seans outpost"
     * > dash-cli sendtoaddress "XwnLY9Tf7Zsef8gMGL2fhWA9ZmMjt4KPwg" 0.1 "" "" true
     * > curl --user myusername --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "sendtoaddress", "params": ["XwnLY9Tf7Zsef8gMGL2fhWA9ZmMjt4KPwg", 0.1, "donation", "seans outpost"] }' -H 'content-type: text/plain;' http://127.0.0.1:9998/
     */
    String sendtoaddress(String address, BigDecimal amount);
    String sendtoaddress(String address, BigDecimal amount, String comment);
    String sendtoaddress(String address, BigDecimal amount, String comment, String commentTo);
    String sendtoaddress(String address, BigDecimal amount, String comment, String commentTo, boolean subtractfeefromamount);
    String sendtoaddress(String address, BigDecimal amount, String comment, String commentTo, boolean subtractfeefromamount, boolean use_is);
    String sendtoaddress(String address, BigDecimal amount, String comment, String commentTo, boolean subtractfeefromamount, boolean use_is, boolean use_ps);
}
