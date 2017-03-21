package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Test implements Serializable {

    private static final long serialVersionUID = -2446869033729866145L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Account owner;
    
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinStartTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinEndTime = new Date();

    private Integer timeLength = 60;

    private Integer attemptLimit = 1;

    private Boolean restricted = Boolean.FALSE;

    @OneToMany(mappedBy = "test", orphanRemoval = true)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @OrderBy("id")
    private Set<Attempt> attempts = new LinkedHashSet<>(0);
    
    @ManyToMany
    @JoinTable(name = "Test_Question", joinColumns = @JoinColumn(name = "testId"), inverseJoinColumns = @JoinColumn(name = "questionId"))
    @Cascade(CascadeType.SAVE_UPDATE)
    @OrderBy("id")
    private Set<Question> questions = new LinkedHashSet<>(0);

    @ManyToMany
    @JoinTable(name = "Test_Examinee", joinColumns = @JoinColumn(name = "testId"), inverseJoinColumns = @JoinColumn(name = "examinee"))
    @Cascade(CascadeType.SAVE_UPDATE)
    @OrderBy("username")
    private Set<Account> examinees = new LinkedHashSet<>(0);

    public Test() {
    }

    public Test(Account owner, String name, Date joinStartTime, Date joinEndTime, Integer timeLength, Integer attemptLimit, Boolean restricted, Course course) {
        this.owner = owner;
        this.name = name;
        this.joinStartTime = joinStartTime;
        this.joinEndTime = joinEndTime;
        this.timeLength = timeLength;
        this.attemptLimit = attemptLimit;
        this.restricted = restricted;
        this.course = course;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getOwner() {
        return this.owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getJoinStartTime() {
        return this.joinStartTime;
    }

    public void setJoinStartTime(Date joinStartTime) {
        this.joinStartTime = joinStartTime;
    }

    public Date getJoinEndTime() {
        return this.joinEndTime;
    }

    public void setJoinEndTime(Date joinEndTime) {
        this.joinEndTime = joinEndTime;
    }

    public Integer getTimeLength() {
        return this.timeLength;
    }

    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    public Integer getAttemptLimit() {
        return this.attemptLimit;
    }

    public void setAttemptLimit(Integer attemptLimit) {
        this.attemptLimit = attemptLimit;
    }

    public Boolean getRestricted() {
        return restricted;
    }

    public void setRestricted(Boolean restricted) {
        this.restricted = restricted;
    }

    public Set<Attempt> getAttempts() {
        return this.attempts;
    }

    public void addAttempt(Attempt attempt) {
        this.attempts.add(attempt);
        attempt.setTest(this);
    }

    public void removeAttempt(Attempt attempt) {
        this.attempts.remove(attempt);
        attempt.setTest(null);
    }

    public Set<Question> getQuestions() {
        return this.questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }

    public Set<Account> getExaminees() {
        return examinees;
    }

    public void addExaminee(Account examinee) {
        this.examinees.add(examinee);
    }

    public void removeExaminee(Account examinee) {
        this.examinees.remove(examinee);
    }

    public boolean isJoinable() {
        Date now = new Date();
        return (now.compareTo(joinStartTime) >= 0) && (now.compareTo(joinEndTime) <= 0);
    }
    
    public TestStatus getStatus() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.setTime(joinEndTime);
        calendar.roll(Calendar.MINUTE, timeLength);
        Date finishTime = calendar.getTime();
        
        if (now.compareTo(joinStartTime) < 0) {
            return TestStatus.PENDING;
        } else if (now.compareTo(finishTime) < 0) {
            return TestStatus.ONGOING;
        } else {
            return TestStatus.FINISHED;
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
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
        final Test other = (Test) obj;
        return (this.id != null) && Objects.equals(this.id, other.id);
    }

}
