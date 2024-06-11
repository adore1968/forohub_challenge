package com.forohub.forohub.controller;

import com.forohub.forohub.domain.topicos.DatosDetallesTopico;
import com.forohub.forohub.domain.topicos.DatosRegistroTopico;
import com.forohub.forohub.domain.topicos.Topico;
import com.forohub.forohub.domain.topicos.TopicoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetallesTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        var topico = topicoRepository.save(new Topico(datosRegistroTopico));
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetallesTopico>> listarTopicos(@PageableDefault(size = 10, sort = "titulo") Pageable pageable) {
        var topicos = topicoRepository.findAll(pageable)
                .map(DatosDetallesTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetallesTopico> detallarTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetallesTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetallesTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            var topico = topicoOptional.get();
            topico.actualizar(datosRegistroTopico);
            return ResponseEntity.ok(new DatosDetallesTopico(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
