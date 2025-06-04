// src/main/java/br/com/fiap/aquamind/repository/AlertaUmidadeRepository.java
package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.AlertaUmidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaUmidadeRepository extends JpaRepository<AlertaUmidade, Long> {
    // Métodos básicos providos pelo JpaRepository
}
