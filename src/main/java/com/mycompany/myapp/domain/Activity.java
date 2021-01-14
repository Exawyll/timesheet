package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_spent")
    private Long timeSpent;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "activity_project",
               joinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private Set<Project> projects = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "activity_employee",
               joinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "activity_week",
               joinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "week_id", referencedColumnName = "id"))
    private Set<Week> weeks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public Activity timeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
        return this;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Activity projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Activity addProject(Project project) {
        this.projects.add(project);
        project.getActivities().add(this);
        return this;
    }

    public Activity removeProject(Project project) {
        this.projects.remove(project);
        project.getActivities().remove(this);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Activity employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Activity addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getActivities().add(this);
        return this;
    }

    public Activity removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getActivities().remove(this);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Week> getWeeks() {
        return weeks;
    }

    public Activity weeks(Set<Week> weeks) {
        this.weeks = weeks;
        return this;
    }

    public Activity addWeek(Week week) {
        this.weeks.add(week);
        week.getActivities().add(this);
        return this;
    }

    public Activity removeWeek(Week week) {
        this.weeks.remove(week);
        week.getActivities().remove(this);
        return this;
    }

    public void setWeeks(Set<Week> weeks) {
        this.weeks = weeks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", timeSpent=" + getTimeSpent() +
            "}";
    }
}
