package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TimesheetApp;
import com.mycompany.myapp.domain.Week;
import com.mycompany.myapp.repository.WeekRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WeekResource} REST controller.
 */
@SpringBootTest(classes = TimesheetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WeekResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_MONTH_NUM = 1;
    private static final Integer UPDATED_MONTH_NUM = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_WEEK_NUM = 1;
    private static final Integer UPDATED_WEEK_NUM = 2;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeekMockMvc;

    private Week week;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Week createEntity(EntityManager em) {
        Week week = new Week()
            .label(DEFAULT_LABEL)
            .monthNum(DEFAULT_MONTH_NUM)
            .year(DEFAULT_YEAR)
            .weekNum(DEFAULT_WEEK_NUM)
            .isActive(DEFAULT_IS_ACTIVE);
        return week;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Week createUpdatedEntity(EntityManager em) {
        Week week = new Week()
            .label(UPDATED_LABEL)
            .monthNum(UPDATED_MONTH_NUM)
            .year(UPDATED_YEAR)
            .weekNum(UPDATED_WEEK_NUM)
            .isActive(UPDATED_IS_ACTIVE);
        return week;
    }

    @BeforeEach
    public void initTest() {
        week = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeek() throws Exception {
        int databaseSizeBeforeCreate = weekRepository.findAll().size();
        // Create the Week
        restWeekMockMvc.perform(post("/api/weeks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(week)))
            .andExpect(status().isCreated());

        // Validate the Week in the database
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeCreate + 1);
        Week testWeek = weekList.get(weekList.size() - 1);
        assertThat(testWeek.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testWeek.getMonthNum()).isEqualTo(DEFAULT_MONTH_NUM);
        assertThat(testWeek.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testWeek.getWeekNum()).isEqualTo(DEFAULT_WEEK_NUM);
        assertThat(testWeek.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createWeekWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weekRepository.findAll().size();

        // Create the Week with an existing ID
        week.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeekMockMvc.perform(post("/api/weeks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(week)))
            .andExpect(status().isBadRequest());

        // Validate the Week in the database
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWeeks() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);

        // Get all the weekList
        restWeekMockMvc.perform(get("/api/weeks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(week.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].monthNum").value(hasItem(DEFAULT_MONTH_NUM)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].weekNum").value(hasItem(DEFAULT_WEEK_NUM)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);

        // Get the week
        restWeekMockMvc.perform(get("/api/weeks/{id}", week.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(week.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.monthNum").value(DEFAULT_MONTH_NUM))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.weekNum").value(DEFAULT_WEEK_NUM))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingWeek() throws Exception {
        // Get the week
        restWeekMockMvc.perform(get("/api/weeks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);

        int databaseSizeBeforeUpdate = weekRepository.findAll().size();

        // Update the week
        Week updatedWeek = weekRepository.findById(week.getId()).get();
        // Disconnect from session so that the updates on updatedWeek are not directly saved in db
        em.detach(updatedWeek);
        updatedWeek
            .label(UPDATED_LABEL)
            .monthNum(UPDATED_MONTH_NUM)
            .year(UPDATED_YEAR)
            .weekNum(UPDATED_WEEK_NUM)
            .isActive(UPDATED_IS_ACTIVE);

        restWeekMockMvc.perform(put("/api/weeks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeek)))
            .andExpect(status().isOk());

        // Validate the Week in the database
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeUpdate);
        Week testWeek = weekList.get(weekList.size() - 1);
        assertThat(testWeek.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testWeek.getMonthNum()).isEqualTo(UPDATED_MONTH_NUM);
        assertThat(testWeek.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testWeek.getWeekNum()).isEqualTo(UPDATED_WEEK_NUM);
        assertThat(testWeek.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingWeek() throws Exception {
        int databaseSizeBeforeUpdate = weekRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeekMockMvc.perform(put("/api/weeks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(week)))
            .andExpect(status().isBadRequest());

        // Validate the Week in the database
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);

        int databaseSizeBeforeDelete = weekRepository.findAll().size();

        // Delete the week
        restWeekMockMvc.perform(delete("/api/weeks/{id}", week.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
