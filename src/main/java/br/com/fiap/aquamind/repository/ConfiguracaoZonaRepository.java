package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.ConfiguracaoZona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoZonaRepository extends JpaRepository<ConfiguracaoZona, Long> {

}
