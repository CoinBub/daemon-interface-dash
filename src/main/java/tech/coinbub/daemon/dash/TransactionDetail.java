package tech.coinbub.daemon.dash;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDetail {
    public String account;
    public String address;
    public Category category;
    public BigDecimal amount;
    public BigDecimal fee;
    public Boolean abandoned;
    public Long vout;
    
    public enum Category {
        immature,
        send
    }
}
