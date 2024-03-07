package com.example.leetCodeRepetition.Model;

import jakarta.persistence.*;

import java.sql.Date;


//enum Repetition{
//    LOW,
//    MEDIUM,
//    HIGH
//}
@Entity
@Table(name = "leetcode_question")
public class LeetCodeQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "deck_id", nullable = false)
    private Integer deck_id;

    @Column(name = "tags", nullable = true)
    private String[] tags;

    @Column(name = "date_created", nullable = false)
    private Date createdDate;

    @Column(name = "last_completion")
    private Date last_completion;

    @Column(name="difficulty", nullable = false)
    private String difficulty;

    @Column(name="next_review")
    private Date next_review;




    public LeetCodeQuestion() {
    }

    public LeetCodeQuestion(String name, String url, String tag, Integer deck_id,String difficulty) {
        this.name = name;
        this.url = url;
        this.category = category;
        this.deck_id = deck_id;
        this.difficulty = difficulty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getUrl() {
        return url;
    }

    public Date getLast_completion() {
        return last_completion;
    }

    public Date getNext_review() {
        return next_review;
    }

    public void setLast_completion(Date last_completion) {
        this.last_completion = last_completion;
    }

    public void setNext_review(Date next_review) {
        this.next_review = next_review;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

    public Integer getDeckId() {
        return deck_id;
    }

    public void setDeckId(Integer deckId) {
        this.deck_id = deckId;
    }
}
