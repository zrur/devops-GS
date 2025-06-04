package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {
    // JpaRepository já contém findAll(), findById(), save(), delete() etc.
}
