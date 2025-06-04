// src/main/java/br/com/fiap/aquamind/repository/LogAcaoBombaRepository.java
package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.LogAcaoBomba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAcaoBombaRepository extends JpaRepository<LogAcaoBomba, Long> {
    // Métodos básicos providos pelo JpaRepository
}
