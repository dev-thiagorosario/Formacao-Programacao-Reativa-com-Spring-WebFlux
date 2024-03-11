package nogroup.campeonatobrasileiro.rest;

import io.swagger.v3.oas.annotations.Operation;
import nogroup.campeonatobrasileiro.DTO.ClassificacaoDTO;
import nogroup.campeonatobrasileiro.DTO.JogoDTO;
import nogroup.campeonatobrasileiro.services.JogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/jogos")
public class JogoRestController {

    private final JogoService jogoService;

    public JogoRestController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @Operation(summary = "Gera os jogos do campeonato")
    @PostMapping("/gerar")
    public ResponseEntity<Void> gerarJogos() {
        jogoService.gerarJogos(LocalDateTime.now(), Collections.emptyList());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Retorna um jogo específico")
    @GetMapping("/{id}")
    public ResponseEntity<JogoDTO> findById(@PathVariable Integer id) {
        JogoDTO jogo = jogoService.findById(id);
        return ResponseEntity.ok(jogo);
    }

    @Operation(summary = "Retorna todos os jogos")
    @GetMapping
    public ResponseEntity<List<JogoDTO>> findAll() {
        List<JogoDTO> jogos = jogoService.findAll();
        return ResponseEntity.ok(jogos);
    }

    @Operation(summary = "Retorna a classificação")
    @GetMapping("/classificacao")
    public ResponseEntity<ClassificacaoDTO> getClassificacao() {
        ClassificacaoDTO classificacao = jogoService.getClassificacao();
        return ResponseEntity.ok(classificacao);
    }

    @Operation(summary = "Finaliza um jogo")
    @PostMapping("/finalizar/{id}")
    public ResponseEntity<Void> finalizarJogo(@PathVariable Integer id, @RequestBody JogoDTO jogoDTO) {
        jogoService.finalizarJogo(id, jogoDTO);
        return ResponseEntity.ok().build();
    }
}

