package model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Question implements java.io.Serializable {

    private static final long serialVersionUID = 3561971390326822863L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @Column(columnDefinition = "nvarchar(max)")
    private String content;

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @OrderBy("id")
    private Set<Choice> choices = new LinkedHashSet<>(0);

    public Question() {
    }

    public Question(String content) {
        this.content = content;
    }

    public Question(Account owner, Course course, String content) {
        this.owner = owner;
        this.course = course;
        this.content = content;
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
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Choice> getChoices() {
        return this.choices;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
        choice.setQuestion(this);
    }

    public void removeChoice(Choice choice) {
        this.choices.remove(choice);
        choice.setQuestion(null);
    }
    
    public int getTotalCorrectChoices() {
        int correctChoices = 0;
        for (Choice c : choices) {
            if (c.isCorrect()) {
                correctChoices++;
            }
        }
        
        return correctChoices;
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
        final Question other = (Question) obj;
        return (this.id != null) && Objects.equals(this.id, other.id);
    }

}
