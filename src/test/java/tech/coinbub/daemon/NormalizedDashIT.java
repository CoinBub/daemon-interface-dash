package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static tech.coinbub.daemon.GetBlockHashIT.HEIGHT;
import tech.coinbub.daemon.normalization.model.Block;
import tech.coinbub.daemon.normalization.model.Transaction;
import tech.coinbub.daemon.normalization.model.TransactionDetail;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class NormalizedDashIT {
    @Test
    public void testGetblockhash(final NormalizedDash normalized) {
        assertThat(normalized.getblockhash(HEIGHT), is(equalTo("6206c4d5af60d805f65468529c68cf0d11ef6d3e7930530da544c355eca83a09")));
    }

    @Test
    public void testGetblock(final NormalizedDash normalized) {
        final Block block = normalized.getblock("5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098");
        assertThat(block, hasOnly(
                property("hash", is(equalTo("5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098"))),
                property("confirmations", is(equalTo(2L))),
                property("size", is(equalTo(180L))),
                property("height", is(equalTo(120L))),
                property("tx", hasSize(1)),
                property("time", is(equalTo(1526427059L))),
                property("previousblockhash", is(equalTo("04ba8ee85b10ca00d191a7b9bc4c11bbda9f7c5a75a2e974c443d592d5de705d"))),
                property("nextblockhash", is(equalTo("2cfa16f56e115b62447f81a992c999f1f212ff6beaae40523c44590b6269aec3")))
        ));
        
        assertThat(block.tx.get(0), is(equalTo("ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9")));
    }

    @Test
    public void testGettransaction(final NormalizedDash normalized) {
        final Transaction tx = normalized.gettransaction("ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9");
        assertThat(tx, hasOnly(
                property("id", is(equalTo("ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9"))),
                property("amount", is(equalTo(new BigDecimal("0.0")))),
                property("confirmations", is(equalTo(2L))),
                property("blockhash", is(equalTo("5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098"))),
                property("time", is(equalTo(1526427051L))),
                property("details", hasSize(1))
        ));

        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("address", is(equalTo("yMQcwprKzfs4XYVouPoeYQyvHWZFzdfJR5"))),
                property("amount", is(equalTo(new BigDecimal("500.0"))))
        ));
    }

    @Test
    public void testGetnewaddress(final NormalizedDash normalized) {
        assertThat(normalized.getnewaddress().length(), is(equalTo(34)));
    }

    @Test
    public void testSendToAddressNoComments(final NormalizedDash normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE);
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", is(equalTo(txid))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001")))),
                property("confirmations", is(equalTo(0L))),
                property("time", is(not(nullValue()))),
                property("details", hasSize(1))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001"))))
        ));
    }

    @Test
    public void testSendToAddressSourceComment(final NormalizedDash normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE, "test transaction!");
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", is(equalTo(txid))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001")))),
                property("confirmations", is(equalTo(0L))),
                property("time", is(not(nullValue()))),
                property("details", hasSize(1)),
                property("comment_from", is(equalTo("test transaction!")))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001"))))
        ));
    }

    @Test
    public void testSendToAddressDestComment(final NormalizedDash normalized) {
        final String txid = normalized.sendtoaddress(SendToAddressIT.VALID_ADDRESS, BigDecimal.ONE, "test transaction!", "receiving test!");
        final Transaction tx = normalized.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("id", is(equalTo(txid))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001")))),
                property("confirmations", is(equalTo(0L))),
                property("time", is(not(nullValue()))),
                property("details", hasSize(1)),
                property("comment_from", is(equalTo("test transaction!"))),
                property("comment_to", is(equalTo("receiving test!")))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("address", is(equalTo(SendToAddressIT.VALID_ADDRESS))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001"))))
        ));
    }

}
