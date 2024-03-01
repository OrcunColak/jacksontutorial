package customdeserialization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
@ToString
class Transaction {
    private BigDecimal amount;
    private Currency currency;
    private LocalDateTime date;
}
