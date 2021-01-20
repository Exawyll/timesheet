package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Week.
 */
@Entity
@Table(name = "week")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Week implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "month_num")
    private Integer monthNum;

    @Column(name = "year")
    private Integer year;

    @Column(name = "week_num")
    private Integer weekNum;

    @Column(name = "is_active")
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Week label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getMonthNum() {
        return monthNum;
    }

    public Week monthNum(Integer monthNum) {
        this.monthNum = monthNum;
        return this;
    }

    public void setMonthNum(Integer monthNum) {
        this.monthNum = monthNum;
    }

    public Integer getYear() {
        return year;
    }

    public Week year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public Week weekNum(Integer weekNum) {
        this.weekNum = weekNum;
        return this;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Week isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Week)) {
            return false;
        }
        return id != null && id.equals(((Week) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Week{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", monthNum=" + getMonthNum() +
            ", year=" + getYear() +
            ", weekNum=" + getWeekNum() +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
