package io.spring.boot.common.web.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class Student implements Serializable {

    private static final long serialVersionUID = 2120869894112984147L;

    @Id
    private int id;
    private String name;
    @Column(name = "SCORE_SUM")
    private String sumScore;
    @Column(name = "SCORE_AVG")
    private String avgScore;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSumScore() {
        return sumScore;
    }

    public void setSumScore(String sumScore) {
        this.sumScore = sumScore;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
