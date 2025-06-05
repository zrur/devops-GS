package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.ConfiguracaoZona;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.ConfiguracaoZonaRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfiguracaoZonaService {

    @Autowired
    private ConfiguracaoZonaRepository configuracaoZonaRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * Lista todas as configurações de zona.
     */
    public List<ConfiguracaoZona> listarTodas() {
        return configuracaoZonaRepository.findAll();
    }

    /**
     * Busca uma configuração de zona pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    public ConfiguracaoZona buscarPorId(Long id) {
        return configuracaoZonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ConfiguracaoZona não encontrada com id = " + id));
    }

    /**
     * Cria uma nova configuração de zona.
     */
    public ConfiguracaoZona criar(ConfiguracaoZona novaConfig) {
        Long zonaId = (novaConfig.getZona() != null ? novaConfig.getZona().getId() : null);
        Zona zona = zonaRepository.findById(zonaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + zonaId));
        novaConfig.setZona(zona);
        return configuracaoZonaRepository.save(novaConfig);
    }

    /**
     * Atualiza uma configuração de zona. Se o ID não existir, lança ResourceNotFoundException.
     */
    public ConfiguracaoZona atualizar(Long id, ConfiguracaoZona dadosAtualizados) {
        ConfiguracaoZona existente = configuracaoZonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ConfiguracaoZona não encontrada com id = " + id));

        Long zonaId = (dadosAtualizados.getZona() != null ? dadosAtualizados.getZona().getId() : null);
        Zona zona = zonaRepository.findById(zonaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + zonaId));

        existente.setLimiteUmidadeMin(dadosAtualizados.getLimiteUmidadeMin());
        existente.setHorarioInicioIrriga(dadosAtualizados.getHorarioInicioIrriga());
        existente.setHorarioFimIrriga(dadosAtualizados.getHorarioFimIrriga());
        existente.setAtivo(dadosAtualizados.getAtivo());
        existente.setZona(zona);
        // @PreUpdate ajusta dataAtualizacao

        return configuracaoZonaRepository.save(existente);
    }

    /**
     * Deleta uma configuração de zona. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        ConfiguracaoZona existente = configuracaoZonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ConfiguracaoZona não encontrada com id = " + id));
        configuracaoZonaRepository.delete(existente);
    }
}
