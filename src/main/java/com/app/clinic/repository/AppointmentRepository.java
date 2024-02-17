package com.app.clinic.repository;

import com.app.clinic.model.Appointment;
import com.app.clinic.model.Patient;
import com.app.clinic.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    @Query("SELECT a FROM Appointment a WHERE a.professional = :professional")
    List<Appointment> findManyByProfessional(@Param("professional") Professional professional);

    @Query("SELECT a FROM Appointment a WHERE a.patient = :patient")
    List<Appointment> findManyByPatient(@Param("patient") Patient patient);
}
