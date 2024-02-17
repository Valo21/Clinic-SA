package com.app.clinic.repository;

import com.app.clinic.model.Appointment;
import com.app.clinic.model.Patient;
import com.app.clinic.model.PatientHistory;
import com.app.clinic.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query("SELECT p from Patient p where p.personalId = :personalId")
    Patient findOneByPersonalID(@Param("personalId") String personalId);

    @Query("SELECT a from Appointment a where a.patient = :patient")
    List<Appointment> getAppointmentsByPatient(@Param("patient") Patient patient);

    @Query("SELECT h from PatientHistory h where h.patient = :patient")
    List<PatientHistory> getHistoriesByPatient(@Param("patient") Patient patient);
}
