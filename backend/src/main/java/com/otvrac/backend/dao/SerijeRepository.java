package com.otvrac.backend.dao;

import com.otvrac.backend.domain.Serije;
import com.otvrac.backend.specification.SerijeSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SerijeRepository extends JpaRepository<Serije, Integer>, JpaSpecificationExecutor<Serije> {
}
