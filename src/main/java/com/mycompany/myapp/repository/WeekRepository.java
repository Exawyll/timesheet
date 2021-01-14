package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Week;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Week entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
}
