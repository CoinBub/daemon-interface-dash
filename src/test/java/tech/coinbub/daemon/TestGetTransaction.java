package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.dash.Transaction;
import tech.coinbub.daemon.dash.TransactionDetail;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class TestGetTransaction {
    @Test
    public void canGetTransaction(final Dash dash) {
        final Transaction tx = dash.gettransaction("ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9");
        assertThat(tx, hasOnly(
                property("txid", is(equalTo("ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9"))),
                property("amount", is(equalTo(new BigDecimal("0.0")))),
                property("confirmations", is(equalTo(2L))),
                property("instantlock", is(equalTo(false))),
                property("generated", is(equalTo(true))),
                property("blockhash", is(equalTo("5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098"))),
                property("blockindex", is(equalTo(0L))),
                property("blocktime", is(equalTo(1526427059L))),
                property("walletconflicts", hasSize(0)),
                property("time", is(equalTo(1526427051L))),
                property("timereceived", is(equalTo(1526427051L))),
                property("bip125_replaceable", is(equalTo(false))),
                property("details", hasSize(1)),
                property("hex", is(equalTo("01000000010000000000000000000000000000000000000000000000000000000000000000ffffffff0401780101ffffffff0100743ba40b000000232103aa149e05747f78bdcafecfca069aaa791c2ca16867083b4455911fc211e273daac00000000")))
        ));

        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo("yMQcwprKzfs4XYVouPoeYQyvHWZFzdfJR5"))),
                property("category", is(equalTo(TransactionDetail.Category.immature))),
                property("amount", is(equalTo(new BigDecimal("500.0")))),
                property("vout", is(equalTo(0L)))
        ));
    }
}
