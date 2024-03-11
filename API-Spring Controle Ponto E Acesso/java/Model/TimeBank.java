package Model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class TimeBank {

    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public class TimeBankId implements Serializable{
        private long idTimeBank;
        private long idMovement;
        private long idUser;
    }

    @EmbeddedId
    private TimeBankId id;
    private LocalDateTime workedDate;
    private BigDecimal hours;
    private BigDecimal hourBalance;
}
