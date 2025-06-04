// src/main/java/br/com/fiap/aquamind/repository/HistoricoAcaoUsuarioRepository.java
package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.HistoricoAcaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoAcaoUsuarioRepository extends JpaRepository<HistoricoAcaoUsuario, Long> {
    // Métodos básicos providos pelo JpaRepository
}
