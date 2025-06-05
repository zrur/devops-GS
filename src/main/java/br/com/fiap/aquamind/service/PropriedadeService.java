package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Propriedade;
import br.com.fiap.aquamind.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para manipulação de Propriedade (CRUD).
 */
@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    /**
     * Lista todas as propriedades.
     */
    public List<Propriedade> listarTodas() {
        return propriedadeRepository.findAll();
    }

    /**
     * Busca uma propriedade por ID. Se não existir, lança ResourceNotFoundException.
     */
    public Propriedade buscarPorId(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));
    }

    /**
     * Cria uma nova propriedade.
     */
    public Propriedade criar(Propriedade novaPropriedade) {
        // validações: nome não nulo, idUsuario e idEstado válidos, areaHectares > 0, etc.
        return propriedadeRepository.save(novaPropriedade);
    }

    /**
     * Atualiza uma propriedade existente. Se o ID não existir, lança ResourceNotFoundException.
     */
    public Propriedade atualizar(Long id, Propriedade dadosAtualizados) {
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));

        existente.setNome(dadosAtualizados.getNome());
        existente.setUsuario(dadosAtualizados.getUsuario());
        existente.setEstado(dadosAtualizados.getEstado());
        existente.setAreaHectares(dadosAtualizados.getAreaHectares());
        existente.setAtivo(dadosAtualizados.getAtivo());
        // @PreUpdate cuidará de dataAtualizacao

        return propriedadeRepository.save(existente);
    }

    /**
     * Deleta uma propriedade. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));
        propriedadeRepository.delete(existente);
    }
}
