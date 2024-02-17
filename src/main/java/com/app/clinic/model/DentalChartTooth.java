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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentalChartTooth {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String string;

    @Column(nullable = true)
    private String diagnostic;

    @Column(nullable = true)
    private String treatment;

    @Column(nullable = false)
    private Number thoothNumber;

    @Column(nullable = false)
    private Number quadrantNumber;

    @JoinColumn(nullable = false, name = "quadrant_id")
    @ManyToOne(targetEntity = DentalChartQuadrant.class)
    @JsonBackReference
    private DentalChartQuadrant quadrant;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
