package tech.coinbub.daemon;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.dash.Transaction;
import tech.coinbub.daemon.dash.TransactionDetail;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class SendToAddressIT {
    public static final int LISTENER_PORT = 20010;
    public static final String VALID_ADDRESS = "yN813x8DrZh5aJXZfaja9xm1kZv7fGsSWw";

    private NotificationListener listener = null;
    private Object result = null;
    private final CountDownLatch latch = new CountDownLatch(1);

    @AfterEach
    public void teardown() throws IOException {
        if (listener != null) {
            listener.stop();
        }
    }

    @Test
    public void throwsErrorOnInvalidAddress(final Dash dash) {
        final JsonRpcClientException ex = Assertions.assertThrows(JsonRpcClientException.class, () -> {
            dash.sendtoaddress("abc", BigDecimal.ONE);
        });
        assertThat(ex.getMessage(), is(equalTo("Invalid Dash address")));
    }

    @Test
    public void supportsNoComments(final Dash dash, final NormalizedDash normalized) throws InterruptedException, IOException {
        setup(normalized);
        final String txid = dash.sendtoaddress(VALID_ADDRESS, BigDecimal.ONE);
        final Transaction tx = dash.gettransaction(txid);
        assertThat(tx.amount, is(equalTo(new BigDecimal("-1.0"))));

        latch.await(500, TimeUnit.MILLISECONDS);
        assertThat(result, is(not(nullValue())));
        assertThat(((tech.coinbub.daemon.normalization.model.Transaction) result).id.length(), is(equalTo(64)));
    }

    @Test
    public void supportsSourceComment(final Dash dash, final NormalizedDash normalized) throws InterruptedException, IOException {
        setup(normalized);
        final String txid = dash.sendtoaddress(VALID_ADDRESS, BigDecimal.ONE, "test transaction!");
        final Transaction tx = dash.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("txid", not(isEmptyString())),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001")))),
                property("confirmations", is(equalTo(0L))),
                property("instantlock", is(equalTo(false))),
                property("trusted", is(equalTo(true))),
                property("walletconflicts", hasSize(0)),
                property("time", is(not(nullValue()))),
                property("timereceived", is(not(nullValue()))),
                property("bip125_replaceable", is(equalTo(false))),
                property("details", hasSize(1)),
                property("comment", is(equalTo("test transaction!"))),
                property("hex", not(isEmptyString()))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo(VALID_ADDRESS))),
                property("category", is(equalTo(TransactionDetail.Category.send))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001")))),
                property("abandoned", is(equalTo(false))),
                property("vout", is(equalTo(0L)))
        ));

        latch.await(500, TimeUnit.MILLISECONDS);
        assertThat(result, is(not(nullValue())));
        assertThat(((tech.coinbub.daemon.normalization.model.Transaction) result).id.length(), is(equalTo(64)));
    }

    @Test
    public void supportsDestinationComment(final Dash dash, final NormalizedDash normalized) throws InterruptedException, IOException {
        setup(normalized);
        final String txid = dash.sendtoaddress(VALID_ADDRESS, BigDecimal.ONE, "test transaction!", "receiving test!");
        final Transaction tx = dash.gettransaction(txid);
        assertThat(tx, hasOnly(
                property("txid", not(isEmptyString())),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001")))),
                property("confirmations", is(equalTo(0L))),
                property("instantlock", is(equalTo(false))),
                property("trusted", is(equalTo(true))),
                property("walletconflicts", hasSize(0)),
                property("time", is(not(nullValue()))),
                property("timereceived", is(not(nullValue()))),
                property("bip125_replaceable", is(equalTo(false))),
                property("details", hasSize(1)),
                property("comment", is(equalTo("test transaction!"))),
                property("to", is(equalTo("receiving test!"))),
                property("hex", not(isEmptyString()))
        ));
        
        final TransactionDetail detail = tx.details.get(0);
        assertThat(detail, hasOnly(
                property("account", isEmptyString()),
                property("address", is(equalTo(VALID_ADDRESS))),
                property("category", is(equalTo(TransactionDetail.Category.send))),
                property("amount", is(equalTo(new BigDecimal("-1.0")))),
                property("fee", is(closeTo(new BigDecimal("-0.00000192"), new BigDecimal("0.00000001")))),
                property("abandoned", is(equalTo(false))),
                property("vout", is(equalTo(0L)))
        ));

        latch.await(500, TimeUnit.MILLISECONDS);
        assertThat(result, is(not(nullValue())));
        assertThat(((tech.coinbub.daemon.normalization.model.Transaction) result).id.length(), is(equalTo(64)));
    }

    private void setup(final NormalizedDash normalized) throws IOException {
        result = null;

        listener = new NotificationListener(LISTENER_PORT);
        listener.setTransformer(new NotificationListener.TransactionTransformer(normalized));
        listener.addObserver((Observable o, Object o1) -> {
            System.out.println("RESULT: " + o1);
            result = o1;
            latch.countDown();
        });
    }
}
