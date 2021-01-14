package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "team")
    private String team;

    @ManyToMany(mappedBy = "employees")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Employee isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getTeam() {
        return team;
    }

    public Employee team(String team) {
        this.team = team;
        return this;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Employee activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Employee addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getEmployees().add(this);
        return this;
    }

    public Employee removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getEmployees().remove(this);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Employee projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Employee addProject(Project project) {
        this.projects.add(project);
        project.getEmployees().add(this);
        return this;
    }

    public Employee removeProject(Project project) {
        this.projects.remove(project);
        project.getEmployees().remove(this);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", team='" + getTeam() + "'" +
            "}";
    }
}
