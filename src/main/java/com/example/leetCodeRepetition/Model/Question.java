package com.example.leetCodeRepetition.Model;

import jakarta.persistence.*;

import java.sql.Date;


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
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "owner_id", nullable = false)
    private Integer owner_id;

    @Column(name = "tags", nullable = false)
    private String[] tags;

    @Column(name = "date_created" , nullable = false)
    private Date date_created;

    @Column(name = "last_completion" , nullable = false)
    private Date last_completion;

    @Column(name="difficulty", nullable = false)
    private String difficulty;

    @Column(name="next_review")
    private Date next_review;




    public Question() {
        this.date_created = new Date(System.currentTimeMillis());
        this.last_completion = new Date(System.currentTimeMillis());
        this.tags = new String[0];
    }

    public Question(String name, String url, String category, Integer owner_id,String difficulty) {
        this.name = name;
        this.url = url;
        this.category = category;
        this.owner_id = owner_id;
        this.difficulty = difficulty;
    }

    public Question(String name, String url, String category, Integer owner_id,String difficulty, String[] tags) {
        this.name = name;
        this.url = url;
        this.category = category;
        this.owner_id = owner_id;
        this.difficulty = difficulty;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date createdDate) {
        this.date_created = createdDate;
    }

    public Date getLast_completion() {
        return last_completion;
    }

    public void setLast_completion(Date last_completion) {
        this.last_completion = last_completion;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Date getNext_review() {
        return next_review;
    }

    public void setNext_review(Date next_review) {
        this.next_review = next_review;
    }
}
