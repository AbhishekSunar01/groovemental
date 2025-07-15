    package com.grooveMental.backend.model;

    import jakarta.persistence.*;

    import java.math.BigDecimal;
    import java.util.List;

    @Entity
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
        private Category category;

        @ManyToOne
        private User seller;

        @OneToMany(mappedBy = "clothe")
        private List<CartItem> cartItems;
    }

