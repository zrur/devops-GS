package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.LogAcaoBomba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAcaoBombaRepository extends JpaRepository<LogAcaoBomba, Long> {

}
