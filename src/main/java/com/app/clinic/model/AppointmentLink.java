package com.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentLink {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JoinColumn(name = "professional_id")
    @OneToOne(targetEntity = Professional.class)
    @JsonManagedReference
    private Professional professional;

    @Column(nullable = false)
    private Integer duration;

    @CreationTimestamp
    private String createdAt;

    @UpdateTimestamp
    private String updatedAt;
}
