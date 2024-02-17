package com.app.clinic.controller;

import com.app.clinic.dto.PatientHistoryCreationDTO;
import com.app.clinic.dto.ResponseDTO;
import com.app.clinic.model.Patient;
import com.app.clinic.model.PatientHistory;
import com.app.clinic.model.Professional;
import com.app.clinic.repository.PatientHistoryRepository;

import com.app.clinic.repository.PatientRepository;
import com.app.clinic.repository.ProfessionalRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/patient-history")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PatientHistoryController {
    final PatientHistoryRepository patientHistoryRepository;
    final ProfessionalRepository professionalRepository;
    final PatientRepository patientRepository;

    @PostMapping
    public ResponseEntity<ResponseDTO> postPatientHistory(@Valid @RequestBody PatientHistoryCreationDTO body){
        Optional<Patient> patient = patientRepository.findById(body.getPatientId());
        if (patient.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ResponseDTO
                            .builder()
                            .message("Patient not found")
                            .build());
        }
        Optional<Professional> professional = professionalRepository.findById(body.getProfessionalId());
        if (professional.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ResponseDTO
                            .builder()
                            .message("Professional not found")
                            .build());
        }

        PatientHistory patientHistory = PatientHistory
                .builder()
                .professional(professional.get())
                .patient(patient.get())
                .name(body.getName())
                .age(body.getAge())
                .sex(body.getSex())
                .nationality(body.getOccupation())
                .occupation(body.getOccupation())
                .motivation(body.getMotivation())
                .currentDisease(body.getCurrentDisease())
                .personalBackground(body.getPersonalBackground())
                .currentDiseaseBackground(body.getCurrentDiseaseBackground())
                .familyBackground(body.getFamilyBackground())
                .hereditaryBackground(body.getHereditaryBackground())
                .build();

        patientHistory = patientHistoryRepository.save(patientHistory);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO
                        .builder()
                        .data(patientHistory)
                        .build());
    }
}
