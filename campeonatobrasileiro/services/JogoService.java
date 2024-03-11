package nogroup.campeonatobrasileiro.services;

import nogroup.campeonatobrasileiro.DTO.ClassificacaoDTO;
import nogroup.campeonatobrasileiro.DTO.ClassificacaoTimeDTO;
import nogroup.campeonatobrasileiro.DTO.JogoDTO;
import nogroup.campeonatobrasileiro.empity.Jogo;
import nogroup.campeonatobrasileiro.repository.JogoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JogoService {

    private final JogoRepository jogoRepository;
    private final TimeService timeService;

    public JogoService(JogoRepository jogoRepository, TimeService timeService) {
        this.jogoRepository = jogoRepository;
        this.timeService = timeService;
    }

    public void gerarJogos(LocalDateTime primeiraRodada, List<LocalDate> datasInvalidas) {
        List<nogroup.campeonatobrasileiro.empity.Time> times = timeService.findAll();
        List<nogroup.campeonatobrasileiro.empity.Time> all1 = new ArrayList<>(times);
        List<nogroup.campeonatobrasileiro.empity.Time> all2 = new ArrayList<>(times);

        jogoRepository.deleteAll();

        List<Jogo> jogos = new ArrayList<>();
        int t = times.size();
        int m = t / 2;
        LocalDateTime dataJogo = primeiraRodada;
        int rodada;
        for (int i = 0; i < t - 1; i++) {
            rodada = i + 1;
            for (int j = 0; j < m; j++) {
                nogroup.campeonatobrasileiro.empity.Time time1;
                nogroup.campeonatobrasileiro.empity.Time time2;
                if (j % 2 == 1 || i % 2 == 1 && j == 0) {
                    time1 = times.get(t - j - 1);
                    time2 = times.get(j);
                } else {
                    time1 = times.get(j);
                    time2 = times.get(t - j - 1);
                }
                jogos.add(gerarJogo(dataJogo, rodada, time1, time2));
                dataJogo = dataJogo.plusDays(7);
            }
            times.add(1, times.remove(times.size() - 1));
        }

        jogos.forEach(System.out::println);
        jogoRepository.saveAll(jogos);

        List<Jogo> jogos2 = new ArrayList<>();
        jogos.forEach(jogo -> {
            nogroup.campeonatobrasileiro.empity.Time time1 = jogo.getTime2();
            nogroup.campeonatobrasileiro.empity.Time time2 = jogo.getTime1();
            jogos2.add(gerarJogo(jogo.getData().plusDays(7 * jogos.size()), jogo.getRodada() + jogos.size(), time1, time2));
        });
        jogoRepository.saveAll(jogos2);
    }

    private Jogo gerarJogo(LocalDateTime dataJogo, Integer rodada, nogroup.campeonatobrasileiro.empity.Time time1, nogroup.campeonatobrasileiro.empity.Time time2) {
        Jogo jogo = new Jogo();
        jogo.setTime1(time1);
        jogo.setTime2(time2);
        jogo.setRodada(rodada);
        jogo.setData(dataJogo);
        jogo.setEncerrado(false);
        jogo.setGolsTime1(0);
        jogo.setGolsTime2(0);
        jogo.setPublicoPagante(0);
        return jogo;
    }

    public JogoDTO findById(Integer id) {
        return jogoRepository.findById(id)
                .map(this::empityToDTO)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
    }

    public List<JogoDTO> findAll() {
        return jogoRepository.findAll().stream().map(this::empityToDTO).collect(Collectors.toList());
    }

    private JogoDTO entityToDTO(Jogo entity) {
        JogoDTO dto = new JogoDTO();
        dto.setId(entity.getId());
        dto.setData(entity.getData());
        dto.setEncerrado(entity.getEncerrado());
        dto.setGolsTime1(entity.getGolsTime1());
        dto.setGolsTime2(entity.getGolsTime2());
        dto.setPublicoPagante(entity.getPublicoPagante());
        dto.setRodada(entity.getRodada());
        dto.setTime1(timeService.entityToDto(entity.getTime1()));
        dto.setTime2(timeService.entityToDto(entity.getTime2()));
        return dto;
    }

    public ClassificacaoDTO getClassificacao() {
        ClassificacaoDTO dto = new ClassificacaoDTO();
        List<nogroup.campeonatobrasileiro.empity.Time> times = timeService.findAll();
        times.forEach(time -> {
            List<Jogo> jogosTimeMandante = jogoRepository.findByTime1AndEncerrado(time, true);
            List<Jogo> jogosTimeVisitante = jogoRepository.findByTime2AndEncerrado(time, true);
            int vitorias = 0;
            int empates = 0;
            int derrotas = 0;
            int golsMarcados = 0;
            int golsSofridos = 0;

            for (Jogo jogo : jogosTimeMandante) {
                if (jogo.getGolsTime1() > jogo.getGolsTime2()) {
                    vitorias++;
                } else if (jogo.getGolsTime1() == jogo.getGolsTime2()) {
                    empates++;
                } else {
                    derrotas++;
                }
                golsMarcados += jogo.getGolsTime1();
                golsSofridos += jogo.getGolsTime2();
            }
            for (Jogo jogo : jogosTimeVisitante) {
                if (jogo.getGolsTime2() > jogo.getGolsTime1()) {
                    vitorias++;
                } else if (jogo.getGolsTime2() == jogo.getGolsTime1()) {
                    empates++;
                } else {
                    derrotas++;
                }
                golsMarcados += jogo.getGolsTime2();
                golsSofridos += jogo.getGolsTime1();
            }

            ClassificacaoTimeDTO classificacaoTimeDto = new ClassificacaoTimeDTO();
            classificacaoTimeDto.setIdTime(time.getId());
            classificacaoTimeDto.setTime(time.getNome());
            classificacaoTimeDto.setPontos((vitorias * 3) + empates);
            classificacaoTimeDto.setDerrotas(derrotas);
            classificacaoTimeDto.setEmpates(empates);
            classificacaoTimeDto.setVitorias(vitorias);
            classificacaoTimeDto.setGolsMarcados(golsMarcados);
            classificacaoTimeDto.setGolsSofridos(golsSofridos);
            classificacaoTimeDto.setJogos(derrotas + empates + vitorias);
            dto.getTimes().add(classificacaoTimeDto);
        });
        Collections.sort(dto.getTimes(), Collections.reverseOrder());
        int posicao = 1;
        for (ClassificacaoTimeDTO time : dto.getTimes()) {
            time.setPosicao(posicao++);
        }
        return dto;
    }

    public void finalizarJogo(Integer id, JogoDTO jogoDTO) throws Throwable {
        Jogo jogo = (Jogo) jogoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
        if (jogo.getEncerrado()) {
            throw new IllegalStateException("Jogo já foi encerrado");
        }
        jogo.setGolsTime1(jogoDTO.getGolsTime1());
        jogo.setGolsTime2(jogoDTO.getGolsTime2());
        jogo.setEncerrado(true);
        jogo.setPublicoPagante(jogoDTO.getPublicoPagante());
        jogoRepository.save(jogo);
    }
}
