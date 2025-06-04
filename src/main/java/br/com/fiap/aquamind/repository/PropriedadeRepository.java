package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {
    // JpaRepository já fornece todos os métodos CRUD necessários.
}
