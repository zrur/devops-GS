package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Irrigacao;
import br.com.fiap.aquamind.repository.IrrigacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para manipulação de Irrigacao (CRUD).
 */
@Service
public class IrrigacaoService {

    @Autowired
    private IrrigacaoRepository irrigacaoRepository;

    /**
     * Lista todas as irrigações.
     */
    public List<Irrigacao> listarTodas() {
        return irrigacaoRepository.findAll();
    }

    /**
     * Busca irrigação por ID. Se não existir, lança ResourceNotFoundException.
     */
    public Irrigacao buscarPorId(Long id) {
        return irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));
    }

    /**
     * Cria uma nova irrigação.
     */
    public Irrigacao criar(Irrigacao novaIrrigacao) {
        // Aqui você pode validar dataHoraFim > dataHoraInicio, volume >= 0 etc., antes do save
        return irrigacaoRepository.save(novaIrrigacao);
    }

    /**
     * Atualiza uma irrigação já existente. Se o ID não existir, lança ResourceNotFoundException.
     */
    public Irrigacao atualizar(Long id, Irrigacao dadosAtualizados) {
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));

        // Atualiza campos permitidos
        existente.setZona(dadosAtualizados.getZona());
        existente.setDataHoraInicio(dadosAtualizados.getDataHoraInicio());
        existente.setDataHoraFim(dadosAtualizados.getDataHoraFim());
        existente.setVolumeAgua(dadosAtualizados.getVolumeAgua());
        // Se tiver algum @PreUpdate, ele será preenchido automaticamente na entidade

        return irrigacaoRepository.save(existente);
    }

    /**
     * Deleta uma irrigação. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));
        irrigacaoRepository.delete(existente);
    }
}
