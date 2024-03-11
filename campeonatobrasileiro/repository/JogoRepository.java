package nogroup.campeonatobrasileiro.repository;
import nogroup.campeonatobrasileiro.empity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import nogroup.campeonatobrasileiro.empity.Time;

import java.util.List;

@Repository
public interface JogoRepository<Jogo> extends JpaRepository<Jogo, Integer> {
    List<Jogo> findByTime1AndEncerrado(Time time, Boolean encerrado);
    List<Jogo> findByTime2AndEncerrado(Time time, Boolean encerrado);
}