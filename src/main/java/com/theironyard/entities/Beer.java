package com.theironyard.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by stevenburris on 11/21/16.
 */
@Entity
@Table(name = "beers")
public class Beer {

    @Id
    String id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, length = 10000)
    String description;

    public Beer() {
    }

    public Beer(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
