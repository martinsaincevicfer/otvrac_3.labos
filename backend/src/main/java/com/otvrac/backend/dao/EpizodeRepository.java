package com.otvrac.backend.dao;

import com.otvrac.backend.domain.Epizode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EpizodeRepository extends JpaRepository<Epizode, Integer>, JpaSpecificationExecutor<Epizode> {
}
