package com.app.clinic.repository;


import com.app.clinic.model.Appointment;
import com.app.clinic.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional, String> {
    @Query("SELECT p from Professional p where p.email = :email")
    Optional<Professional> findOneByEmail(@Param("email") String email);

    @Query("SELECT p.appointments FROM Professional p WHERE p.id = :professionalId")
    List<Appointment> findAppointmentsByProfessionalId(@Param("professionalId") String professionalId);

    @Query("SELECT p.appointments FROM Professional p WHERE p.id = :professionalId")
    List<Appointment> findPatientsByProfessionalId(@Param("professionalId") String professionalId);
}
