package com.codewithsm.webscrapingusingrpa.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="IMDB_Informations")
public class ImdbModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Title;
    private String Year;

//    @Column(length = 8000) //it's use to define varchar length
    @Column(columnDefinition = "TEXT") //use for define varchar max length
    private  String Description;
    private String Duration;
    private String Guidanc_Rating;
    private String Star_Rating;

    public ImdbModel(String title, String year, String description, String duration, String guidanc_Rating, String star_Rating) {
        Title = title;
        Year = year;
        Description = description;
        Duration = duration;
        Guidanc_Rating = guidanc_Rating;
        Star_Rating = star_Rating;
    }

    public ImdbModel() {

    }
}
