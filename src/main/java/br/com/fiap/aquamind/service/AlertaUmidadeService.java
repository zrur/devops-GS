package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.AlertaUmidade;
import br.com.fiap.aquamind.repository.AlertaUmidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaUmidadeService {

    @Autowired
    private AlertaUmidadeRepository alertaUmidadeRepository;

    /**
     * Lista todos os alertas de umidade.
     */
    public List<AlertaUmidade> listarTodas() {
        return alertaUmidadeRepository.findAll();
    }

    /**
     * Busca um alerta de umidade pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    public AlertaUmidade buscarPorId(Long id) {
        return alertaUmidadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AlertaUmidade não encontrado com id = " + id));
    }

    /**
     * Cria um novo alerta de umidade.
     */
    public AlertaUmidade criar(AlertaUmidade novoAlerta) {
        // Aqui você pode validar, por exemplo, valor de umidade >= 0, etc.
        return alertaUmidadeRepository.save(novoAlerta);
    }

    /**
     * Atualiza um alerta de umidade existente. Se o ID não existir, lança ResourceNotFoundException.
     */
    public AlertaUmidade atualizar(Long id, AlertaUmidade dadosAtualizados) {
        AlertaUmidade existente = alertaUmidadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AlertaUmidade não encontrado com id = " + id));

        existente.setDataHora(dadosAtualizados.getDataHora());
        existente.setUmidadeAtual(dadosAtualizados.getUmidadeAtual());
        existente.setDescricao(dadosAtualizados.getDescricao());
        existente.setResolvido(dadosAtualizados.getResolvido());
        existente.setZona(dadosAtualizados.getZona());
        // O @PreUpdate na entidade irá ajustar dataAtualizacao automaticamente

        return alertaUmidadeRepository.save(existente);
    }

    /**
     * Deleta um alerta de umidade. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        AlertaUmidade existente = alertaUmidadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AlertaUmidade não encontrado com id = " + id));
        alertaUmidadeRepository.delete(existente);
    }
}
