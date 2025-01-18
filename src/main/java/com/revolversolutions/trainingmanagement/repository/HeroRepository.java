package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, String> {
}
