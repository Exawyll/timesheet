package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Week;
import com.mycompany.myapp.repository.WeekRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Week}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WeekResource {

    private final Logger log = LoggerFactory.getLogger(WeekResource.class);

    private static final String ENTITY_NAME = "week";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeekRepository weekRepository;

    public WeekResource(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    /**
     * {@code POST  /weeks} : Create a new week.
     *
     * @param week the week to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new week, or with status {@code 400 (Bad Request)} if the week has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/weeks")
    public ResponseEntity<Week> createWeek(@RequestBody Week week) throws URISyntaxException {
        log.debug("REST request to save Week : {}", week);
        if (week.getId() != null) {
            throw new BadRequestAlertException("A new week cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Week result = weekRepository.save(week);
        return ResponseEntity.created(new URI("/api/weeks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weeks} : Updates an existing week.
     *
     * @param week the week to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated week,
     * or with status {@code 400 (Bad Request)} if the week is not valid,
     * or with status {@code 500 (Internal Server Error)} if the week couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/weeks")
    public ResponseEntity<Week> updateWeek(@RequestBody Week week) throws URISyntaxException {
        log.debug("REST request to update Week : {}", week);
        if (week.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Week result = weekRepository.save(week);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, week.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /weeks} : get all the weeks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weeks in body.
     */
    @GetMapping("/weeks")
    public List<Week> getAllWeeks() {
        log.debug("REST request to get all Weeks");
        return weekRepository.findAll();
    }

    /**
     * {@code GET  /weeks/:id} : get the "id" week.
     *
     * @param id the id of the week to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the week, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/weeks/{id}")
    public ResponseEntity<Week> getWeek(@PathVariable Long id) {
        log.debug("REST request to get Week : {}", id);
        Optional<Week> week = weekRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(week);
    }

    /**
     * {@code DELETE  /weeks/:id} : delete the "id" week.
     *
     * @param id the id of the week to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/weeks/{id}")
    public ResponseEntity<Void> deleteWeek(@PathVariable Long id) {
        log.debug("REST request to delete Week : {}", id);
        weekRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
