package com.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentalChart {

    @Id
    private String id;

    @Column(nullable = true)
    private String diagnostic;

    @Column(nullable = true)
    private String treatment;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = true, name = "quadrants")
    @JsonManagedReference
    private List<DentalChartQuadrant> quadrants;

    @JoinColumn(nullable = false, name = "professional_id")
    @ManyToOne(targetEntity = Professional.class)
    @JsonBackReference
    private Professional professional;

    @JoinColumn(nullable = false, name = "patient_id")
    @ManyToOne(targetEntity = Patient.class)
    @JsonBackReference
    private Patient patient;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
    
}
