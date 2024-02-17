package com.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentalChartQuadrant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Number quadrantNumber;

    @JoinColumn(nullable = false, name = "dental_chart_id")
    @ManyToOne(targetEntity = DentalChart.class)
    @JsonBackReference
    private DentalChart dentalChart;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "tooth_list")
    @JsonManagedReference
    private List<DentalChartTooth> toothList;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
