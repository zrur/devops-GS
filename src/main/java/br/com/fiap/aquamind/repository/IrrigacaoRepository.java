package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.Irrigacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigacaoRepository extends JpaRepository<Irrigacao, Long> {
}
