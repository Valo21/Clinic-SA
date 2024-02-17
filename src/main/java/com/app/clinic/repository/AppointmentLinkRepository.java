package com.app.clinic.repository;

import com.app.clinic.model.AppointmentLink;
import com.app.clinic.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppointmentLinkRepository extends JpaRepository<AppointmentLink, String> {

    @Query("SELECT l FROM AppointmentLink l WHERE l.professional = :professional")
    Optional<AppointmentLink> getAppointmentLinkByProfessional(@Param("professional") Professional professional);
}
