package com.pacheco.gestiontareas.tag.service;

import com.pacheco.gestiontareas.tag.model.Tag;
import com.pacheco.gestiontareas.tag.model.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    // Si el tag ya existe lo usamos, si no lo creamos nuevo
    public List<Tag> saveOrGet(List<String> tags) {

        List<Tag> result = new ArrayList<>();

        tags.forEach(tag -> {
            Optional<Tag> val = tagRepository.findByText(tag);
            result.add(val.orElseGet(() -> tagRepository.save(Tag.builder().text(tag).build())));
        });

        return result;
    }

}
