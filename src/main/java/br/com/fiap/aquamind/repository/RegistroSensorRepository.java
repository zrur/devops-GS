package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.RegistroSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroSensorRepository extends JpaRepository<RegistroSensor, Long> {

}
