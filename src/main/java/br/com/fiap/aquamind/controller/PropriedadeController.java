package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.PropriedadeDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Propriedade;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.model.Estado;
import br.com.fiap.aquamind.repository.PropriedadeRepository;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import br.com.fiap.aquamind.repository.EstadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de Propriedade, usando DTO.
 */
@RestController
@RequestMapping("/api/propriedades")
public class PropriedadeController {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    /**
     * GET /api/propriedades
     * Lista todas as propriedades como DTOs.
     */
    @GetMapping
    public List<PropriedadeDTO> listarTodas() {
        List<Propriedade> lista = propriedadeRepository.findAll();
        return lista.stream()
                .map(PropriedadeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/propriedades/{id}
     * Busca uma propriedade por ID; retorna 404 se não encontrar.
     */
    @GetMapping("/{id}")
    public PropriedadeDTO buscarPorId(@PathVariable Long id) {
        Propriedade p = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));
        return PropriedadeDTO.fromEntity(p);
    }

    /**
     * POST /api/propriedades
     * Cria uma nova propriedade. Valida DTO, busca Usuario e Estado, salva e retorna DTO com 201.
     */
    @PostMapping
    public ResponseEntity<PropriedadeDTO> criar(@Valid @RequestBody PropriedadeDTO novaDTO) {
        // 1) Validar existência de Usuario
        Usuario usuario = usuarioRepository.findById(novaDTO.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com id = " + novaDTO.getIdUsuario()
                ));

        // 2) Validar existência de Estado
        Estado estado = estadoRepository.findById(novaDTO.getIdEstado())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estado não encontrado com id = " + novaDTO.getIdEstado()
                ));

        // 3) Montar entidade a partir do DTO
        Propriedade p = novaDTO.toEntity();
        p.setUsuario(usuario);
        p.setEstado(estado);

        // 4) Salvar no repositório
        Propriedade salva = propriedadeRepository.save(p);

        // 5) Converter para DTO e retornar 201 Created
        PropriedadeDTO respostaDTO = PropriedadeDTO.fromEntity(salva);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    /**
     * PUT /api/propriedades/{id}
     * Atualiza uma propriedade existente. Se não existir, retorna 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PropriedadeDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PropriedadeDTO dtoAtualizado
    ) {
        // 1) Busca a propriedade existente
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));

        // 2) Verifica se o Usuario informado existe
        Usuario usuario = usuarioRepository.findById(dtoAtualizado.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com id = " + dtoAtualizado.getIdUsuario()
                ));

        // 3) Verifica se o Estado informado existe
        Estado estado = estadoRepository.findById(dtoAtualizado.getIdEstado())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estado não encontrado com id = " + dtoAtualizado.getIdEstado()
                ));

        // 4) Atualiza campos na entidade existente via DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setUsuario(usuario);
        existente.setEstado(estado);

        // 5) Salva no repositório
        Propriedade atualizada = propriedadeRepository.save(existente);

        // 6) Converte para DTO e retorna 200 OK
        PropriedadeDTO respostaDTO = PropriedadeDTO.fromEntity(atualizada);
        return ResponseEntity.ok(respostaDTO);
    }

    /**
     * DELETE /api/propriedades/{id}
     * Deleta uma propriedade existente; se não existir, retorna 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));

        propriedadeRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
