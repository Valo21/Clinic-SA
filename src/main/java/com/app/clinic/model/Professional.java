package com.app.clinic.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String givenName;

    @Column(nullable = false)
    private String familyName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean emailVerified;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String picture;

    @Column()
    private String specialty;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "appointments")
    @JsonManagedReference
    private List<Appointment> appointments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "dental_charts")
    @JsonManagedReference
    private List<DentalChart> dentalCharts;

    @JoinColumn()
    @ManyToOne
    @JsonManagedReference
    private Institution institution;

    @JoinColumn(name = "appointment_link_id")
    @OneToOne(targetEntity = AppointmentLink.class)
    @JsonBackReference
    private AppointmentLink appointmentLink;

    @Column()
    private String provider;

    @Column()
    private String providerId;

    @Column(nullable = false)
    @CreationTimestamp
    private String createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private String updatedAt;

}
