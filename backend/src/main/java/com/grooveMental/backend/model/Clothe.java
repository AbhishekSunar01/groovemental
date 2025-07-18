    package com.grooveMental.backend.model;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.Data;

    import java.math.BigDecimal;
    import java.util.List;

    @Entity
    @Data
    public class Clothe {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private BigDecimal price;

        @Column(nullable = false)
        private String description;

        @ManyToOne
        @JsonBackReference
        private Category category;

        @ManyToOne
        private User seller;

        @OneToMany(mappedBy = "clothe")
        @JsonManagedReference
        private List<CartItem> cartItems;
    }

