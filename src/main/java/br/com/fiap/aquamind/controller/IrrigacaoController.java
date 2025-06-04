package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.IrrigacaoDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Irrigacao;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.IrrigacaoRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de Irrigacao, usando DTO.
 */
@RestController
@RequestMapping("/api/irrigacoes")
public class IrrigacaoController {

    @Autowired
    private IrrigacaoRepository irrigacaoRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * GET /api/irrigacoes
     * Lista todas as irrigações (como DTOs).
     */
    @GetMapping
    public List<IrrigacaoDTO> listarTodas() {
        List<Irrigacao> listaEntidades = irrigacaoRepository.findAll();
        return listaEntidades.stream()
                .map(IrrigacaoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/irrigacoes/{id}
     * Busca uma irrigação por ID. Se não achar, retorna 404.
     */
    @GetMapping("/{id}")
    public IrrigacaoDTO buscarPorId(@PathVariable Long id) {
        Irrigacao irrigacao = irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));
        return IrrigacaoDTO.fromEntity(irrigacao);
    }

    /**
     * POST /api/irrigacoes
     * Cria uma nova Irrigacao a partir de DTO. Retorna DTO com 201 Created.
     */
    @PostMapping
    public ResponseEntity<IrrigacaoDTO> criar(@Valid @RequestBody IrrigacaoDTO novoDTO) {
        // 1) Validar se existe a Zona com id = novoDTO.getIdZona()
        Zona zona = zonaRepository.findById(novoDTO.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Zona não encontrada com id = " + novoDTO.getIdZona()
                ));

        // 2) Mapeia DTO → entidade (sem zona)
        Irrigacao irrigacao = novoDTO.toEntity();
        irrigacao.setZona(zona);

        // 3) Salva no banco
        Irrigacao salvo = irrigacaoRepository.save(irrigacao);

        // 4) Converte entidade salva → DTO e retorna 201
        IrrigacaoDTO respostaDTO = IrrigacaoDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    /**
     * PUT /api/irrigacoes/{id}
     * Atualiza uma Irrigacao existente. Se não achar, retorna 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IrrigacaoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody IrrigacaoDTO dtoAtualizado
    ) {
        // 1) Busca a entidade existente
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));

        // 2) Valida existência de Zona (passada no DTO)
        Zona zona = zonaRepository.findById(dtoAtualizado.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Zona não encontrada com id = " + dtoAtualizado.getIdZona()
                ));

        // 3) Atualiza campos mutáveis via DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setZona(zona);

        // 4) Salva no banco e converte para DTO
        Irrigacao atualizado = irrigacaoRepository.save(existente);
        IrrigacaoDTO respostaDTO = IrrigacaoDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    /**
     * DELETE /api/irrigacoes/{id}
     * Deleta uma Irrigacao existente. Se não achar, retorna 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));
        irrigacaoRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
