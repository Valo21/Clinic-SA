package com.app.clinic.controller;

import com.app.clinic.dto.AppointmentLinkConfigDTO;
import com.app.clinic.dto.ResponseDTO;
import com.app.clinic.model.AppointmentLink;
import com.app.clinic.model.Professional;
import com.app.clinic.repository.AppointmentLinkRepository;
import com.app.clinic.repository.ProfessionalRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/appointment-link")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentLinkController {

    private final AppointmentLinkRepository appointmentLinkRepository;
    private final ProfessionalRepository professionalRepository;

    AppointmentLinkController(AppointmentLinkRepository appointmentLinkRepository, ProfessionalRepository professionalRepository){
        this.appointmentLinkRepository = appointmentLinkRepository;
        this.professionalRepository = professionalRepository;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAppointmentLinkInfo(@RequestParam("id") String id){
        final Optional<AppointmentLink> appointmentLink = appointmentLinkRepository.findById(id);
        if (appointmentLink.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ResponseDTO
                            .builder()
                            .message("Wrong id")
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO
                        .builder()
                        .data(appointmentLink)
                        .build());
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> postLink(@RequestBody @Valid AppointmentLinkConfigDTO config) {
        final Optional<Professional> professional = professionalRepository.findById(config.getProfessionalId());
        if (professional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ResponseDTO
                            .builder()
                            .message("Wrong professional id")
                            .build());
        }
        Optional<AppointmentLink> link = appointmentLinkRepository.getAppointmentLinkByProfessional(professional.get());
        if (link.isEmpty()){
            appointmentLinkRepository
                    .save(AppointmentLink
                            .builder()
                            .duration(config.getDuration())
                            .professional(professional.get())
                            .build());
        } else {
            appointmentLinkRepository
                    .save(AppointmentLink
                            .builder()
                            .id(link.get().getId())
                            .duration(config.getDuration())
                            .professional(professional.get())
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseDTO
                        .builder()
                        .data(link)
                        .build());

    }
}
