package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.BombaDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Bomba;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.BombaRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bombas")
public class BombaController {

    @Autowired
    private BombaRepository bombaRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * GET /api/bombas
     * Retorna todas as bombas em formato de DTO.
     */
    @GetMapping
    public List<BombaDTO> listarTodas() {
        List<Bomba> listaEntidades = bombaRepository.findAll();
        return listaEntidades
                .stream()
                .map(BombaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/bombas/{id}
     * Busca uma bomba por ID. Se não encontrar, lança ResourceNotFoundException.
     */
    @GetMapping("/{id}")
    public BombaDTO buscarPorId(@PathVariable Long id) {
        Bomba bomba = bombaRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Bomba não encontrada com id = " + id));
        return BombaDTO.fromEntity(bomba);
    }

    /**
     * POST /api/bombas
     * Cria uma nova bomba a partir de dados recebidos em JSON (BombaDTO).
     */
    @PostMapping
    public ResponseEntity<BombaDTO> criar(@Valid @RequestBody BombaDTO novaBombaDTO) {
        // 1) Valida se a Zona existe
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

        // 3) Salva no repositório
        Bomba salva = bombaRepository.save(bomba);

        // 4) Converte para DTO e retorna 201 Created
        BombaDTO respostaDTO = BombaDTO.fromEntity(salva);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    /**
     * PUT /api/bombas/{id}
     * Atualiza uma bomba existente (buscando pelo id). Se não encontrar, lança ResourceNotFoundException.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BombaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody BombaDTO dadosAtualizadosDTO
    ) {
        // 1) Verifica se a bomba existe
        Bomba existente = bombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + id));

        // 2) Verifica se a zona informada no DTO existe
        Zona zona = zonaRepository.findById(dadosAtualizadosDTO.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Zona não encontrada com id = " + dadosAtualizadosDTO.getIdZona()
                ));

        // 3) Atualiza os campos da entidade
        existente.setZona(zona);
        existente.setModelo(dadosAtualizadosDTO.getModelo());
        existente.setStatus(dadosAtualizadosDTO.getStatus());
        existente.setAtivo(dadosAtualizadosDTO.getAtivo());
        existente.setDataInstalacao(dadosAtualizadosDTO.getDataInstalacao());

        // 4) Salva e converte para DTO
        Bomba atualizada = bombaRepository.save(existente);
        BombaDTO respostaDTO = BombaDTO.fromEntity(atualizada);
        return ResponseEntity.ok(respostaDTO);
    }

    /**
     * DELETE /api/bombas/{id}
     * Deleta a bomba especificada por id. Se não encontrar, lança ResourceNotFoundException.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Bomba existente = bombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + id));

        bombaRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
