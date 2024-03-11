package nogroup.campeonatobrasileiro.rest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import nogroup.campeonatobrasileiro.DTO.NovoTimeDTO;
import nogroup.campeonatobrasileiro.DTO.TimeDTO;
import nogroup.campeonatobrasileiro.empity.Time;
import nogroup.campeonatobrasileiro.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/times")
class TimeRestController {

    @Autowired
    private TimeService timeService;

    @GetMapping
    public ResponseEntity<List<TimeDTO>> getTimes() {
        return ResponseEntity.ok().body(timeService.getTimes());
    }

    @PostMapping
    public ResponseEntity<TimeDTO> adicionarTime(@Valid @RequestBody NovoTimeDTO novoTimeDTO) throws Exception {
        return ResponseEntity.ok().body(timeService.adicionarTime(novoTimeDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TimeDTO> atualizarTime(@RequestParam(value = "id", required = true) Integer id, @Valid @RequestBody NovoTimeDTO novoTimeDTO) throws Exception {
        return ResponseEntity.ok().body(timeService.atualizarTime(id, novoTimeDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarTime(@PathVariable Integer id) throws Exception {
        timeService.deletarTime(id);
        return ResponseEntity.ok().build();
    }

}

