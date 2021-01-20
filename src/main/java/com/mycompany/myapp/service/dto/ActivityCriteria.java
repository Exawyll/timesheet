package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Activity} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ActivityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActivityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter timeSpent;

    private LongFilter projectId;

    private LongFilter userId;

    private LongFilter weekId;

    public ActivityCriteria() {
    }

    public ActivityCriteria(ActivityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.timeSpent = other.timeSpent == null ? null : other.timeSpent.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.weekId = other.weekId == null ? null : other.weekId.copy();
    }

    @Override
    public ActivityCriteria copy() {
        return new ActivityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(FloatFilter timeSpent) {
        this.timeSpent = timeSpent;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getWeekId() {
        return weekId;
    }

    public void setWeekId(LongFilter weekId) {
        this.weekId = weekId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ActivityCriteria that = (ActivityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(timeSpent, that.timeSpent) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(weekId, that.weekId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        timeSpent,
        projectId,
        userId,
        weekId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (timeSpent != null ? "timeSpent=" + timeSpent + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (weekId != null ? "weekId=" + weekId + ", " : "") +
            "}";
    }

}
