package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.Bomba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BombaRepository extends JpaRepository<Bomba, Long> {
    // CRUD completo e métodos de consulta básicos já vêm com JpaRepository.
}
