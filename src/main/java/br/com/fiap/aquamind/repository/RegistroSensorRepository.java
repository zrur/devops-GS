package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.RegistroSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroSensorRepository extends JpaRepository<RegistroSensor, Long> {
    // Aqui você poderia adicionar métodos customizados, por exemplo:
    // List<RegistroSensor> findByIdSensor(Long idSensor);

    // Mas, para o CRUD padrão, JpaRepository já é suficiente.
}
