package com.pacheco.gestiontareas.tag.service;

import com.pacheco.gestiontareas.tag.model.Tag;
import com.pacheco.gestiontareas.tag.model.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de etiquetas.
 * Se encarga de buscar etiquetas existentes o crear nuevas si no existen.
 */
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    /**
     * Recibe una lista de textos de etiquetas y para cada uno:
     * - Si ya existe en la BD, lo recupera.
     * - Si no existe, lo crea y lo guarda.
     *
     * @param tags lista de textos de etiquetas
     * @return lista de objetos Tag (existentes o recién creados)
     */
    public List<Tag> saveOrGet(List<String> tags) {

        List<Tag> result = new ArrayList<>();

        tags.forEach(tag -> {
            Optional<Tag> val = tagRepository.findByText(tag);
            // Si lo encuentra, se añade; si no, se guarda primero y luego se añade
            result.add(val.orElseGet(() -> tagRepository.save(Tag.builder().text(tag).build())));
        });

        return result;

    }

}
