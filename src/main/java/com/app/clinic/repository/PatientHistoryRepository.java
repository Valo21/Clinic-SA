package com.app.clinic.repository;

import com.app.clinic.model.PatientHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientHistoryRepository extends JpaRepository<PatientHistory, String> {

}
