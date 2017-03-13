package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Attempt implements Serializable {

    private static final long serialVersionUID = 2461061552176176279L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "examinee")
    private Account examinee;

    @ManyToOne
    @JoinColumn(name = "testId")
    private Test test;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime = new Date();

    private Double score = 0.0;

    @ManyToMany
    @JoinTable(name = "Attempt_Choice", joinColumns = @JoinColumn(name = "attemptId"), inverseJoinColumns = @JoinColumn(name = "choiceId"))
    @Cascade(CascadeType.SAVE_UPDATE)
    private Set<Choice> choices = new HashSet<>(0);

    public Attempt() {
    }

    public Attempt(Account examinee, Test test) {
        this.examinee = examinee;
        this.test = test;
    }

    public Attempt(Account examinee, Test test, Date startTime, Date endTime, Double score) {
        this.examinee = examinee;
        this.test = test;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getExaminee() {
        return examinee;
    }

    public void setExaminee(Account examinee) {
        this.examinee = examinee;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Set<Choice> getChoices() {
        return choices;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }

    public void removeChoice(Choice choice) {
        this.choices.remove(choice);
    }

    @Override
    public int hashCode() {
        return 7;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attempt other = (Attempt) obj;
        return (this.id != null) && Objects.equals(this.id, other.id);
    }
}
