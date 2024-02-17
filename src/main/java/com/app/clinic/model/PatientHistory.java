package com.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PatientHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JoinColumn(nullable = false, name = "patient_id")
    @ManyToOne()
    @JsonBackReference()
    private Patient patient;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Professional professional;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false)
    private String motivation;

    @Column(nullable = false)
    private String currentDisease;

    @Column(nullable = false)
    private String currentDiseaseBackground;

    @Column(nullable = false)
    private String personalBackground;

    @Column(nullable = false)
    private String familyBackground;

    @Column(nullable = false)
    private String hereditaryBackground;

    @Column(nullable = false)
    @CreationTimestamp
    private String createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private String updatedAt;

}
