package com.app.clinic.model;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String personalIdType;

    @Column(nullable = false)
    private String personalId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = true)
    private Date birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = true, name = "appointments")
    @JsonManagedReference()
    private List<Appointment> appointments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = true, name = "histories")
    @JsonManagedReference()
    private List<PatientHistory> histories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = true, name = "dental_chart")
    @JsonManagedReference
    private List<DentalChart> dentalCharts;

    @Column(nullable = false)
    @CreationTimestamp
    private String createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private String updatedAt;

}
