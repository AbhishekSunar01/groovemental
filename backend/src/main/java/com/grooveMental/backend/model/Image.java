package com.grooveMental.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "clothe_id")
    private Clothe clothe;
}