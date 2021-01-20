package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Activity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Activity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

    @Query("select activity from Activity activity where activity.user.login = ?#{principal.username}")
    List<Activity> findByUserIsCurrentUser();
}
