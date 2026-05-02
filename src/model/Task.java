/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author hp
 */
public class Task {
    private int id;
    private String title;
    private String status;//closed or open
    private String personName;
    private  LocalDate creationdate;//because using string datatype is difficulte for making operation

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public LocalDate getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(LocalDate creationdate) {
        this.creationdate = creationdate;
    }

    public Task(int id, String title, String status, String personName, LocalDate creationdate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.personName = personName;
        this.creationdate = creationdate;
    
    }
    @Override
public String toString() {
    return id + " - " + title + " (" + status + ") by " + personName + " [" + creationdate + "]";
}

}
