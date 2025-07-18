    package com.grooveMental.backend.model;

    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.Id;
    import jakarta.persistence.OneToMany;

    import java.util.List;

    @Entity
    public class PaymentStatus {
        @Id
        @GeneratedValue
        private Long id;

        private String status;

        @OneToMany(mappedBy = "paymentStatus")
        @JsonManagedReference
        private List<Cart> carts;

        @OneToMany(mappedBy = "paymentStatus")
        @JsonManagedReference
        private List<Order> orders;
    }

