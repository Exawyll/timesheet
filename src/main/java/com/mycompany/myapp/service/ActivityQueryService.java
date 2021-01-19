package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Activity;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ActivityRepository;
import com.mycompany.myapp.service.dto.ActivityCriteria;

/**
 * Service for executing complex queries for {@link Activity} entities in the database.
 * The main input is a {@link ActivityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Activity} or a {@link Page} of {@link Activity} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivityQueryService extends QueryService<Activity> {

    private final Logger log = LoggerFactory.getLogger(ActivityQueryService.class);

    private final ActivityRepository activityRepository;

    public ActivityQueryService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Return a {@link List} of {@link Activity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Activity> findByCriteria(ActivityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Activity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Activity> findByCriteria(ActivityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActivityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.count(specification);
    }

    /**
     * Function to convert {@link ActivityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Activity> createSpecification(ActivityCriteria criteria) {
        Specification<Activity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Activity_.id));
            }
            if (criteria.getTimeSpent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeSpent(), Activity_.timeSpent));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectId(),
                    root -> root.join(Activity_.projects, JoinType.LEFT).get(Project_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Activity_.users, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getWeekId() != null) {
                specification = specification.and(buildSpecification(criteria.getWeekId(),
                    root -> root.join(Activity_.weeks, JoinType.LEFT).get(Week_.id)));
            }
        }
        return specification;
    }
}
