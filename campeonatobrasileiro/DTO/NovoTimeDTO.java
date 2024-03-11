package nogroup.campeonatobrasileiro.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class NovoTimeDTO {

    @NotEmpty(message = "Nome é obrigatório")
    private String nome;
    @NotEmpty(message = "Sigla é obrigatória")
    private String sigla;
    @NotEmpty(message = "Estado é obrigatório")
    private String estado;

}
