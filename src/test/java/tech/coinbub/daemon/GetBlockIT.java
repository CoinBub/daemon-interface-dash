package tech.coinbub.daemon;

import java.math.BigDecimal;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.coinbub.daemon.dash.Block;
import static tech.coinbub.daemon.testutils.BeanMatcher.hasOnly;
import static tech.coinbub.daemon.testutils.BeanPropertyMatcher.property;
import tech.coinbub.daemon.testutils.Dockerized;

@ExtendWith(Dockerized.class)
public class GetBlockIT {
    @Test
    public void canGetBlock(final Dash dash) {
        final Block block = dash.getblock("5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098");
        assertThat(block, hasOnly(
                property("hash", is(equalTo("5cb4c105b64565280ff8ab64fc5fa126f45eea06954e4efa105bb912e3a7d098"))),
                property("confirmations", is(equalTo(2L))),
                property("size", is(equalTo(180L))),
                property("height", is(equalTo(120L))),
                property("version", is(equalTo(536870912L))),
                property("merkleroot", is(equalTo("ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9"))),
                property("tx", hasSize(1)),
                property("time", is(equalTo(1526427059L))),
                property("mediantime", is(equalTo(1526427058L))),
                property("nonce", is(equalTo(2L))),
                property("bits", is(equalTo("207fffff"))),
                property("difficulty", is(equalTo(new BigDecimal("4.656542373906925e-10")))),
                property("chainwork", is(equalTo("00000000000000000000000000000000000000000000000000000000000000f2"))),
                property("previousblockhash", is(equalTo("04ba8ee85b10ca00d191a7b9bc4c11bbda9f7c5a75a2e974c443d592d5de705d"))),
                property("nextblockhash", is(equalTo("2cfa16f56e115b62447f81a992c999f1f212ff6beaae40523c44590b6269aec3")))
        ));
        
        assertThat(block.tx.get(0), is(equalTo("ac829a3ad158bc88e17ff123291ea0e91ff35e462446cc424f9a1b858bd8dab9")));
    }
    
}
