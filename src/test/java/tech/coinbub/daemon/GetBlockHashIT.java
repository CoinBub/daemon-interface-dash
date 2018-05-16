package tech.coinbub.daemon;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class GetBlockHashIT {
    public static final Long HEIGHT = 22L;

    @Test
    public void canGetBlockHash(final Dash dash) {
        final String best = dash.getblockhash(HEIGHT);
        assertThat(best, is(equalTo("6206c4d5af60d805f65468529c68cf0d11ef6d3e7930530da544c355eca83a09")));
    }
}
