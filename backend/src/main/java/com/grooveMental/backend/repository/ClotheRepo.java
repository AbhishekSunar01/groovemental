package com.grooveMental.backend.repository;

import com.grooveMental.backend.model.Clothe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClotheRepo extends JpaRepository<Clothe, Long> {
    List<Clothe> findByCategory_Id(Long categoryId);
}
