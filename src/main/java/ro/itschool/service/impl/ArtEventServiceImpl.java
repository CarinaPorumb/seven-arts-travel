package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.ArtEvent;
import ro.itschool.exception.ArtEventNotFound;
import ro.itschool.repository.ArtEventRepository;
import ro.itschool.service.ArtEventService;

import java.util.List;
import java.util.Optional;

@Service
public class ArtEventServiceImpl implements ArtEventService {
    @Autowired
    private ArtEventRepository artEventRepository;

    @Override
    public void deleteByName(String name) throws Exception {
        Optional.ofNullable(artEventRepository.findByName(name)).orElseThrow(ArtEventNotFound::new);
        artEventRepository.deleteByName(name);
    }

    @Override
    public void save(ArtEvent artEvent) throws Exception {
        Optional.of(artEventRepository.save(artEvent)).orElseThrow(ArtEventNotFound::new);
    }

    @Override
    public List<ArtEvent> getAll() throws Exception {
        return Optional.of(artEventRepository.findAll()).orElseThrow(ArtEventNotFound::new);
    }

    @Override
    public ArtEvent findByName(String name) throws Exception {
        return Optional.ofNullable(artEventRepository.findByName(name)).orElseThrow(ArtEventNotFound::new);
    }

    @Override
    public List<ArtEvent> getAllByUserId(Long userId) throws Exception {
        return Optional.ofNullable(artEventRepository.findByUserId(userId)).orElseThrow(ArtEventNotFound::new);
    }
}