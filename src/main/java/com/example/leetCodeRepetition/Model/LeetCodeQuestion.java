package com.example.leetCodeRepetition.Model;

import jakarta.persistence.*;


//enum Repetition{
//    LOW,
//    MEDIUM,
//    HIGH
//}
@Entity
@Table(name = "leet_code_question")
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

    @Column(name = "deck_id")
    private Integer deckId;

    public LeetCodeQuestion() {
    }

    public LeetCodeQuestion(String name, String url, String tag, Integer deckId) {
        this.name = name;
        this.url = url;
        this.tag = tag;
        this.deckId = deckId;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }
}
