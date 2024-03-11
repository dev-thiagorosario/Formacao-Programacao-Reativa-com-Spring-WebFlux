package nogroup.campeonatobrasileiro.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TimeDTO {
    private Integer id;
    private String nome;
    @NotNull
    private String sigla;
    private String uf;
    private String estadio;
}
