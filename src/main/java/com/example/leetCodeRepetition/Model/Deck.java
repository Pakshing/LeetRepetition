package com.example.leetCodeRepetition.Model;

import jakarta.persistence.*;

import java.sql.Date;

@Table(name = "deck")
@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "deck_owner_id", nullable = false)
    private Integer deck_owner_id;

    public Deck() {
        this.dateCreated = new Date(System.currentTimeMillis());
    }

    public Deck(String name, Integer deck_owner_id) {
        this.name = name;
        this.deck_owner_id = deck_owner_id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Integer getDeckOwnerId() {
        return deck_owner_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDeck_owner_id(Integer deck_owner_id) {
        this.deck_owner_id = deck_owner_id;
    }

    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                ", deck_owner_id=" + deck_owner_id +
                '}';
    }
}
