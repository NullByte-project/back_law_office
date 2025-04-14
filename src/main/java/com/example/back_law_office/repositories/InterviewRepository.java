package com.example.back_law_office.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.back_law_office.models.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    
}
