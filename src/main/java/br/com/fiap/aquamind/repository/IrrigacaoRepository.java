package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.Irrigacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigacaoRepository extends JpaRepository<Irrigacao, Long> {
    // Se futuramente precisar, por exemplo:
    // List<Irrigacao> findByIdZona(Long idZona);

    // Mas, para operações CRUD básicas, JpaRepository já basta.
}
