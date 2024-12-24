package com.otvrac.backend.service;

import com.otvrac.backend.dao.EpizodeRepository;
import com.otvrac.backend.dao.SerijeRepository;
import com.otvrac.backend.domain.Epizode;
import com.otvrac.backend.domain.Serije;
import com.otvrac.backend.specification.SerijeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerijeService {
    @Autowired
    private final SerijeRepository serijeRepository;

    @Autowired
    private  final EpizodeRepository epizodeRepository;

    public SerijeService(SerijeRepository serijeRepository, EpizodeRepository epizodeRepository) {
        this.serijeRepository = serijeRepository;
        this.epizodeRepository = epizodeRepository;
    }

    public List<Serije> getAllSerije() {
        return serijeRepository.findAll();
    }

    public List<Serije> getFilteredSerije(Specification<Serije> spec) {
        return serijeRepository.findAll(spec);
    }

    public List<Serije> getSerijeWithFilteredAttributes(String attribute, String value) {
        Specification<Serije> spec = SerijeSpecification.hasCombinedAttribute(attribute, value);

        return serijeRepository.findAll(spec).stream()
                .peek(serija -> {
                    if (attribute.equals("sve")) {
                        // Provjeri prolazi li serija po atributu "sve"
                        boolean matchesSerija = serijaMatchesFilterForSve(serija, value);

                        if (matchesSerija) {
                            // Ako serija zadovoljava uvjet, ostavi sve epizode
                            return;
                        }

                        // Ako serija ne zadovoljava uvjet, filtriraj epizode
                        List<Epizode> filteredEpizode = serija.getEpizode().stream()
                                .filter(epizoda -> epizodaMatchesFilterForSve(epizoda, value))
                                .toList();

                        serija.setEpizode(filteredEpizode);
                    } else if (attribute.startsWith("epizode.")) {
                        // Filtriraj epizode samo ako je atribut povezan s epizodama
                        List<Epizode> filteredEpizode = serija.getEpizode().stream()
                                .filter(epizoda -> epizodaMatchesFilter(epizoda, attribute, value))
                                .toList();
                        serija.setEpizode(filteredEpizode);
                    }
                    // Inače ostavi sve epizode netaknute
                })
                // Zadrži samo serije koje imaju filtrirane epizode ili koje zadovoljavaju filter serije
                .filter(serija -> !serija.getEpizode().isEmpty() || attribute.equals("sve") || !attribute.startsWith("epizode."))
                .toList();
    }

    private boolean serijaMatchesFilterForSve(Serije serija, String value) {
        return serija.getNaslov().contains(value) ||
                (serija.getZanr() != null && serija.getZanr().contains(value)) ||
                (serija.getJezik() != null && serija.getJezik().contains(value)) ||
                (serija.getAutor() != null && serija.getAutor().contains(value)) ||
                (serija.getMreza() != null && serija.getMreza().contains(value)) ||
                (serija.getGodinaIzlaska() != null && serija.getGodinaIzlaska().toString().contains(value)) ||
                (serija.getOcjena() != null && serija.getOcjena().toString().contains(value)) ||
                (serija.getBrojSezona() != null && serija.getBrojSezona().toString().contains(value));
    }

    private boolean epizodaMatchesFilterForSve(Epizode epizoda, String value) {
        return epizoda.getNazivEpizode().contains(value) ||
                (epizoda.getScenarist() != null && epizoda.getScenarist().contains(value)) ||
                (epizoda.getRedatelj() != null && epizoda.getRedatelj().contains(value)) ||
                (epizoda.getSezona() != null && epizoda.getSezona().toString().contains(value)) ||
                (epizoda.getBrojEpizode() != null && epizoda.getBrojEpizode().toString().contains(value)) ||
                (epizoda.getDatumEmitiranja() != null && epizoda.getDatumEmitiranja().toString().contains(value)) ||
                (epizoda.getTrajanje() != null && epizoda.getTrajanje().toString().contains(value)) ||
                (epizoda.getOcjena() != null && epizoda.getOcjena().toString().contains(value));
    }

    private boolean epizodaMatchesFilter(Epizode epizoda, String attribute, String value) {
        return switch (attribute) {
            case "epizode.nazivEpizode" -> epizoda.getNazivEpizode().contains(value);
            case "epizode.scenarist" -> epizoda.getScenarist().contains(value);
            case "epizode.redatelj" -> epizoda.getRedatelj().contains(value);
            // Dodajte ostale atribute epizoda prema potrebi
            default -> false;
        };
    }
}
