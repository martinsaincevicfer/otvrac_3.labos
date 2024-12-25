package com.otvrac.backend.service;

import com.otvrac.backend.dao.EpizodeRepository;
import com.otvrac.backend.domain.Epizode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpizodeService {

    @Autowired
    private final EpizodeRepository epizodeRepository;

    public EpizodeService(EpizodeRepository epizodeRepository) {
        this.epizodeRepository = epizodeRepository;
    }

    public List<Epizode> getAllEpizode() {
        return epizodeRepository.findAll();
    }

    public Optional<Epizode> getEpizodaById(Integer id) {
        return epizodeRepository.findById(id);
    }

    public List<Epizode> getEpizodeBySerijaId(Integer serijaId) {
        return epizodeRepository.findBySerijaId(serijaId);
    }
}
