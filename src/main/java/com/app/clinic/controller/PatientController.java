package com.app.clinic.controller;

import com.app.clinic.dto.ResponseDTO;
import com.app.clinic.model.Appointment;
import com.app.clinic.model.Patient;
import com.app.clinic.model.PatientHistory;
import com.app.clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/patients")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;

    @GetMapping()
    public ResponseEntity<ResponseDTO> getPatient(@RequestParam("id") String id){
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResponseDTO
                    .builder()
                            .message("Patient not found")
                    .build());
        };

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO
                        .builder()
                        .data(patient)
                        .build());
    }
}
