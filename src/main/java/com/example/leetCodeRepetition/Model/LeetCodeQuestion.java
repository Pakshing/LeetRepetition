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

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "deck_id", nullable = false)
    private Integer deck_id;

    @Column(name = "tags")
    private String[] tags;

    @Column(name = "date_created", nullable = false)
    private Date createdDate;


    public LeetCodeQuestion() {
    }

    public LeetCodeQuestion(String name, String url, String tag, Integer deck_id) {
        this.name = name;
        this.url = url;
        this.tag = tag;
        this.deck_id = deck_id;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getDeckId() {
        return deck_id;
    }

    public void setDeckId(Integer deckId) {
        this.deck_id = deckId;
    }
}
