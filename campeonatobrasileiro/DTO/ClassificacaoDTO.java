package nogroup.campeonatobrasileiro.DTO;

import java.util.ArrayList;
import java.util.List;

public class ClassificacaoDTO {
    private List<ClassificacaoTimeDTO> times = new ArrayList<>();

    public List<ClassificacaoTimeDTO> getTimes() {
        return times;
    }

    // Considerando a possibilidade de adicionar setters ou outros métodos, se necessário
    public void setTimes(List<ClassificacaoTimeDTO> times) {
        this.times = times;
    }
}

