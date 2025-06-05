package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Estado;
import br.com.fiap.aquamind.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    /**
     * Lista todos os estados.
     */
    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }

    /**
     * Busca um estado pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    public Estado buscarPorId(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado não encontrado com id = " + id));
    }

    /**
     * Cria um novo estado.
     */
    public Estado criar(Estado novoEstado) {
        // Aqui você pode validar nome e sigla não vazios, por exemplo.
        return estadoRepository.save(novoEstado);
    }

    /**
     * Atualiza um estado existente. Se o ID não existir, lança ResourceNotFoundException.
     */
    public Estado atualizar(Long id, Estado dadosAtualizados) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado não encontrado com id = " + id));

        existente.setNome(dadosAtualizados.getNome());
        existente.setSigla(dadosAtualizados.getSigla());
        // Não alteramos o ID, pois o PathVariable já indica qual recusar.

        return estadoRepository.save(existente);
    }

    /**
     * Deleta um estado. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado não encontrado com id = " + id));
        estadoRepository.delete(existente);
    }
}
