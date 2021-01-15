package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Activity entity.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query(value = "select distinct activity from Activity activity left join fetch activity.projects left join fetch activity.employees left join fetch activity.weeks",
        countQuery = "select count(distinct activity) from Activity activity")
    Page<Activity> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct activity from Activity activity left join fetch activity.projects left join fetch activity.employees left join fetch activity.weeks")
    List<Activity> findAllWithEagerRelationships();

    @Query("select activity from Activity activity left join fetch activity.projects left join fetch activity.employees left join fetch activity.weeks where activity.id =:id")
    Optional<Activity> findOneWithEagerRelationships(@Param("id") Long id);
}