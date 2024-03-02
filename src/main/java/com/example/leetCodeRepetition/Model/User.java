package com.example.leetCodeRepetition.Model;

import java.util.Map;

public class User {
    private String name;
    private Map<String, Object> attributes;

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
