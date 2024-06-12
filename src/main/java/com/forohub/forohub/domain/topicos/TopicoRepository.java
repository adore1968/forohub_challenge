package com.forohub.forohub.domain.topicos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
