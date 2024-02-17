package com.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date date;


    @JoinColumn(nullable = false, name = "patient_id")
    @ManyToOne(targetEntity = Patient.class)
    @JsonBackReference()
    private Patient patient;

    @JoinColumn(nullable = false, name = "professional_id")
    @ManyToOne(targetEntity = Professional.class)
    @JsonBackReference
    private Professional professional;

    @CreationTimestamp
    private String createdAt;

    @UpdateTimestamp
    private String updatedAt;
}
