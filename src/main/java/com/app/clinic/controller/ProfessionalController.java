package com.app.clinic.controller;

import com.app.clinic.dto.ResponseDTO;
import com.app.clinic.model.Appointment;
import com.app.clinic.model.Professional;
import com.app.clinic.repository.ProfessionalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/professionals")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfessionalController {

    private final ProfessionalRepository professionalRepository;

    ProfessionalController(ProfessionalRepository professionalRepository){
        this.professionalRepository = professionalRepository;
    }

    @GetMapping
    public ResponseEntity<List<Professional>> getProfessionals(){
        List<Professional> professionals = professionalRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professionals);
    }

    @GetMapping("{professionalId}/appointments")
    public ResponseEntity<ResponseDTO> getAppointments(@PathVariable("professionalId") String professionalId){
        List<Appointment> appointments = professionalRepository.findAppointmentsByProfessionalId(professionalId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO
                        .builder()
                        .data(appointments)
                        .build());
    }
}
