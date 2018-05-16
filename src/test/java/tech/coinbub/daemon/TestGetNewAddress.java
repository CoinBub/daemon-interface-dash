package tech.coinbub.daemon;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class TestGetNewAddress {
    @Test
    public void canGetAddressForDefaultAccount(final Dash dash) {
        final String address = dash.getnewaddress();
        assertThat(address.length(), is(equalTo(34)));
    }
}
