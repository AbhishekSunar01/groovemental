package com.grooveMental.backend.repository;

import com.grooveMental.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {}