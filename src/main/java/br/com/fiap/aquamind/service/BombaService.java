package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.BombaDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Bomba;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.BombaRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para manipular Bomba.
 * Todas as regras de negócio / validações ficam aqui.
 */
@Service
public class BombaService {

    @Autowired
    private BombaRepository bombaRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * Retorna a lista completa de bombas como DTO.
     */
    @Transactional(readOnly = true)
    public List<BombaDTO> listarTodas() {
        List<Bomba> entidades = bombaRepository.findAll();
        return entidades.stream()
                .map(BombaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma única bomba por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public BombaDTO buscarPorId(Long id) {
        Bomba bomba = bombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + id));
        return BombaDTO.fromEntity(bomba);
    }

    /**
     * Cria uma nova bomba a partir do DTO. Se a Zona não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public BombaDTO criar(BombaDTO novaBombaDTO) {
        // 1) Verifica se a zona existe
        Zona zona = zonaRepository.findById(novaBombaDTO.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Zona não encontrada com id = " + novaBombaDTO.getIdZona()
                ));

        // 2) Monta a entidade Bomba
        Bomba bomba = new Bomba();
        bomba.setZona(zona);
        bomba.setModelo(novaBombaDTO.getModelo());
        bomba.setStatus(novaBombaDTO.getStatus());
        bomba.setAtivo(novaBombaDTO.getAtivo());
        bomba.setDataInstalacao(novaBombaDTO.getDataInstalacao());

        // 3) Salva
        Bomba salvo = bombaRepository.save(bomba);

        // 4) Converte para DTO e retorna
        return BombaDTO.fromEntity(salvo);
    }

    /**
     * Atualiza uma bomba existente. Se não encontrar a bomba ou a zona, lança ResourceNotFoundException.
     */
    @Transactional
    public BombaDTO atualizar(Long id, BombaDTO dadosAtualizadosDTO) {
        // 1) Verifica se a bomba existe
        Bomba existente = bombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + id));

        // 2) Verifica se a zona indicada existe
        Zona zona = zonaRepository.findById(dadosAtualizadosDTO.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Zona não encontrada com id = " + dadosAtualizadosDTO.getIdZona()
                ));

        // 3) Atualiza campos
        existente.setZona(zona);
        existente.setModelo(dadosAtualizadosDTO.getModelo());
        existente.setStatus(dadosAtualizadosDTO.getStatus());
        existente.setAtivo(dadosAtualizadosDTO.getAtivo());
        existente.setDataInstalacao(dadosAtualizadosDTO.getDataInstalacao());

        // 4) Salva e devolve DTO
        Bomba atualizado = bombaRepository.save(existente);
        return BombaDTO.fromEntity(atualizado);
    }

    /**
     * Deleta a bomba que tiver o ID dado. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        Bomba existente = bombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + id));

        bombaRepository.delete(existente);
    }
}
