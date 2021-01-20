package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TimesheetApp;
import com.mycompany.myapp.domain.Activity;
import com.mycompany.myapp.domain.Project;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Week;
import com.mycompany.myapp.repository.ActivityRepository;
import com.mycompany.myapp.service.ActivityService;
import com.mycompany.myapp.service.dto.ActivityCriteria;
import com.mycompany.myapp.service.ActivityQueryService;

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
 * Integration tests for the {@link ActivityResource} REST controller.
 */
@SpringBootTest(classes = TimesheetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActivityResourceIT {

    private static final Float DEFAULT_TIME_SPENT = 1F;
    private static final Float UPDATED_TIME_SPENT = 2F;
    private static final Float SMALLER_TIME_SPENT = 1F - 1F;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityQueryService activityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityMockMvc;

    private Activity activity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .timeSpent(DEFAULT_TIME_SPENT);
        return activity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createUpdatedEntity(EntityManager em) {
        Activity activity = new Activity()
            .timeSpent(UPDATED_TIME_SPENT);
        return activity;
    }

    @BeforeEach
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();
        // Create the Activity
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getTimeSpent()).isEqualTo(DEFAULT_TIME_SPENT);
    }

    @Test
    @Transactional
    public void createActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity with an existing ID
        activity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeSpent").value(hasItem(DEFAULT_TIME_SPENT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.timeSpent").value(DEFAULT_TIME_SPENT.doubleValue()));
    }


    @Test
    @Transactional
    public void getActivitiesByIdFiltering() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        Long id = activity.getId();

        defaultActivityShouldBeFound("id.equals=" + id);
        defaultActivityShouldNotBeFound("id.notEquals=" + id);

        defaultActivityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActivityShouldNotBeFound("id.greaterThan=" + id);

        defaultActivityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActivityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllActivitiesByTimeSpentIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where timeSpent equals to DEFAULT_TIME_SPENT
        defaultActivityShouldBeFound("timeSpent.equals=" + DEFAULT_TIME_SPENT);

        // Get all the activityList where timeSpent equals to UPDATED_TIME_SPENT
        defaultActivityShouldNotBeFound("timeSpent.equals=" + UPDATED_TIME_SPENT);
    }

    @Test
    @Transactional
    public void getAllActivitiesByTimeSpentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where timeSpent not equals to DEFAULT_TIME_SPENT
        defaultActivityShouldNotBeFound("timeSpent.notEquals=" + DEFAULT_TIME_SPENT);

        // Get all the activityList where timeSpent not equals to UPDATED_TIME_SPENT
        defaultActivityShouldBeFound("timeSpent.notEquals=" + UPDATED_TIME_SPENT);
    }

    @Test
    @Transactional
    public void getAllActivitiesByTimeSpentIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where timeSpent in DEFAULT_TIME_SPENT or UPDATED_TIME_SPENT
        defaultActivityShouldBeFound("timeSpent.in=" + DEFAULT_TIME_SPENT + "," + UPDATED_TIME_SPENT);

        // Get all the activityList where timeSpent equals to UPDATED_TIME_SPENT
        defaultActivityShouldNotBeFound("timeSpent.in=" + UPDATED_TIME_SPENT);
    }

    @Test
    @Transactional
    public void getAllActivitiesByTimeSpentIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where timeSpent is not null
        defaultActivityShouldBeFound("timeSpent.specified=true");

        // Get all the activityList where timeSpent is null
        defaultActivityShouldNotBeFound("timeSpent.specified=false");
    }

    @Test
    @Transactional
    public void getAllActivitiesByTimeSpentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where timeSpent is greater than or equal to DEFAULT_TIME_SPENT
        defaultActivityShouldBeFound("timeSpent.greaterThanOrEqual=" + DEFAULT_TIME_SPENT);

        // Get all the activityList where timeSpent is greater than or equal to UPDATED_TIME_SPENT
        defaultActivityShouldNotBeFound("timeSpent.greaterThanOrEqual=" + UPDATED_TIME_SPENT);
    }

    @Test
    @Transactional
    public void getAllActivitiesByTimeSpentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where timeSpent is less than or equal to DEFAULT_TIME_SPENT
        defaultActivityShouldBeFound("timeSpent.lessThanOrEqual=" + DEFAULT_TIME_SPENT);

        // Get all the activityList where timeSpent is less than or equal to SMALLER_TIME_SPENT
        defaultActivityShouldNotBeFound("timeSpent.lessThanOrEqual=" + SMALLER_TIME_SPENT);
    }

    @Test
    @Transactional
    public void getAllActivitiesByTimeSpentIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where timeSpent is less than DEFAULT_TIME_SPENT
        defaultActivityShouldNotBeFound("timeSpent.lessThan=" + DEFAULT_TIME_SPENT);

        // Get all the activityList where timeSpent is less than UPDATED_TIME_SPENT
        defaultActivityShouldBeFound("timeSpent.lessThan=" + UPDATED_TIME_SPENT);
    }

    @Test
    @Transactional
    public void getAllActivitiesByTimeSpentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where timeSpent is greater than DEFAULT_TIME_SPENT
        defaultActivityShouldNotBeFound("timeSpent.greaterThan=" + DEFAULT_TIME_SPENT);

        // Get all the activityList where timeSpent is greater than SMALLER_TIME_SPENT
        defaultActivityShouldBeFound("timeSpent.greaterThan=" + SMALLER_TIME_SPENT);
    }


    @Test
    @Transactional
    public void getAllActivitiesByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        Project project = ProjectResourceIT.createEntity(em);
        em.persist(project);
        em.flush();
        activity.setProject(project);
        activityRepository.saveAndFlush(activity);
        Long projectId = project.getId();

        // Get all the activityList where project equals to projectId
        defaultActivityShouldBeFound("projectId.equals=" + projectId);

        // Get all the activityList where project equals to projectId + 1
        defaultActivityShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }


    @Test
    @Transactional
    public void getAllActivitiesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        activity.setUser(user);
        activityRepository.saveAndFlush(activity);
        Long userId = user.getId();

        // Get all the activityList where user equals to userId
        defaultActivityShouldBeFound("userId.equals=" + userId);

        // Get all the activityList where user equals to userId + 1
        defaultActivityShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllActivitiesByWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        Week week = WeekResourceIT.createEntity(em);
        em.persist(week);
        em.flush();
        activity.setWeek(week);
        activityRepository.saveAndFlush(activity);
        Long weekId = week.getId();

        // Get all the activityList where week equals to weekId
        defaultActivityShouldBeFound("weekId.equals=" + weekId);

        // Get all the activityList where week equals to weekId + 1
        defaultActivityShouldNotBeFound("weekId.equals=" + (weekId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivityShouldBeFound(String filter) throws Exception {
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeSpent").value(hasItem(DEFAULT_TIME_SPENT.doubleValue())));

        // Check, that the count call also returns 1
        restActivityMockMvc.perform(get("/api/activities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivityShouldNotBeFound(String filter) throws Exception {
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivityMockMvc.perform(get("/api/activities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivity() throws Exception {
        // Initialize the database
        activityService.save(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findById(activity.getId()).get();
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .timeSpent(UPDATED_TIME_SPENT);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivity)))
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getTimeSpent()).isEqualTo(UPDATED_TIME_SPENT);
    }

    @Test
    @Transactional
    public void updateNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc.perform(put("/api/activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivity() throws Exception {
        // Initialize the database
        activityService.save(activity);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Delete the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
