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
public class Movement {

    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public class MovementId implements Serializable{
        private long idMoviment;
        private long idUser;
    }


    @EmbeddedId
    private MovementId id;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private BigDecimal period;
    private Occurrence occurrence;
    private Calendar calendar;
}
