package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para manipulação de Zona (CRUD).
 */
@Service
public class ZonaService {

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * Lista todas as zonas.
     */
    public List<Zona> listarTodas() {
        return zonaRepository.findAll();
    }

    /**
     * Busca uma zona por ID. Se não existir, lança ResourceNotFoundException.
     */
    public Zona buscarPorId(Long id) {
        return zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));
    }

    /**
     * Cria uma nova zona.
     */
    public Zona criar(Zona novaZona) {
        // validações: nome não nulo, areaHectares > 0, idPropriedade válido, etc.
        return zonaRepository.save(novaZona);
    }

    /**
     * Atualiza uma zona existente. Se o ID não existir, lança ResourceNotFoundException.
     */
    public Zona atualizar(Long id, Zona dadosAtualizados) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));

        existente.setNome(dadosAtualizados.getNome());
        existente.setPropriedade(dadosAtualizados.getPropriedade());
        existente.setAreaHectares(dadosAtualizados.getAreaHectares());
        existente.setAtivo(dadosAtualizados.getAtivo());
        // @PreUpdate cuidará de dataAtualizacao

        return zonaRepository.save(existente);
    }

    /**
     * Deleta uma zona. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));
        zonaRepository.delete(existente);
    }
}
