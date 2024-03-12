package com.example.leetCodeRepetition.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Arrays;


//enum Repetition{
//    LOW,
//    MEDIUM,
//    HIGH
//}
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "owner_id", nullable = false)
    private Integer owner_id;

    @Column(name = "tags", nullable = false)
    private String[] tags;

    @Column(name = "date_created" , nullable = false)
    private Timestamp date_created;

    @Column(name = "last_completion" , nullable = false)
    private Timestamp last_completion;

    @Column(name="difficulty", nullable = false)
    private String difficulty;

    @Column(name="next_review")
    private Timestamp next_review;
    @Transient
    private Long next_review_long;




    public Question() {
        this.date_created = new Timestamp(System.currentTimeMillis());
        this.last_completion = new Timestamp(System.currentTimeMillis());
        this.tags = new String[0];
    }

    public Question(String title, String url, String category, Integer owner_id,String difficulty) {
        this.title = title;
        this.url = url;
        this.category = category;
        this.owner_id = owner_id;
        this.difficulty = difficulty;
    }

    public Question(String title, String url, String category, Integer owner_id,String difficulty, String[] tags) {
        this.title = title;
        this.url = url;
        this.category = category;
        this.owner_id = owner_id;
        this.difficulty = difficulty;
        this.tags = tags;
    }

    public Question(String title, String url, String category, Integer owner_id,String difficulty, Long next_review_long) {
        this.title = title;
        this.url = url;
        this.category = category;
        this.owner_id = owner_id;
        this.difficulty = difficulty;
        this.next_review = new Timestamp(next_review_long);;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp createdDate) {
        this.date_created = createdDate;
    }

    public Timestamp getLast_completion() {
        return last_completion;
    }

    public void setLast_completion(Timestamp last_completion) {
        this.last_completion = last_completion;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Timestamp getNext_review() {
        return next_review;
    }

    public Long getNext_review_long() {
        return next_review_long;
    }

    public void setNext_review_long(Long next_review_long) {
        this.next_review_long = next_review_long;
    }

    public void setNext_review(Timestamp timestamp) {
        this.next_review = timestamp;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", owner_id=" + owner_id +
                ", tags=" + Arrays.toString(tags) +
                ", date_created=" + date_created +
                ", last_completion=" + last_completion +
                ", difficulty='" + difficulty + '\'' +
                ", next_review=" + next_review +
                ", next_review_long=" + next_review_long +
                '}';
    }


}
