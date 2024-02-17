package com.app.clinic.controller;


import com.app.clinic.dto.AppointmentCreationDTO;
import com.app.clinic.dto.ResponseDTO;
import com.app.clinic.model.Appointment;
import com.app.clinic.model.AppointmentLink;
import com.app.clinic.model.Patient;
import com.app.clinic.model.Professional;
import com.app.clinic.repository.AppointmentLinkRepository;
import com.app.clinic.repository.AppointmentRepository;
import com.app.clinic.repository.PatientRepository;
import com.app.clinic.repository.ProfessionalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentLinkRepository appointmentLinkRepository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;

    AppointmentController(
            AppointmentRepository appointmentRepository,
            AppointmentLinkRepository appointmentLinkRepository,
            PatientRepository patientRepository,
            ProfessionalRepository professionalRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentLinkRepository = appointmentLinkRepository;
        this.patientRepository = patientRepository;
        this.professionalRepository = professionalRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Appointment>> getAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(appointments);
    };

    @GetMapping("professional")
    public ResponseEntity<ResponseDTO> getProfessionalAppointments(@RequestParam(name = "id") String id) {
        Optional<Professional> professional = professionalRepository.findById(id);
        if (professional.isEmpty()){
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDTO
                            .builder()
                            .message("Not professional")
                            .build());
        }
        String appointmentLinkId = appointmentLinkRepository.getAppointmentLinkByProfessional(professional.get()).get().getId();
        List<Appointment> appointments = appointmentRepository.findManyByProfessional(professional.get());
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO
                        .builder()
                        .message(appointmentLinkId)
                        .data(appointments)
                        .build());
    };

    @GetMapping("patient")
    public ResponseEntity<ResponseDTO> getPatientAppointments(@RequestParam(name = "id") String id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()){
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDTO
                            .builder()
                            .message("Not professional")
                            .build());
        }
        List<Appointment> appointments = appointmentRepository.findManyByPatient(patient.get());
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO
                        .builder()
                        .data(appointments)
                        .build());
    };

    @PostMapping()
    public ResponseEntity<ResponseDTO> postAppointment(@RequestBody AppointmentCreationDTO body) {
        Optional<AppointmentLink> appointmentLink = appointmentLinkRepository.findById(body.getLinkId());
        if (appointmentLink.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseDTO
                    .builder()
                    .message("Wrong appointment link")
                    .build());
        }
        Professional professional = appointmentLink.get().getProfessional();
        if (professional == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseDTO
                    .builder()
                            .message("Professional doesn't exists")
                    .build());
        }
        Patient patient = patientRepository.findOneByPersonalID(body.getPersonalID());
        if (patient == null) {
            patient = patientRepository.save(Patient
                    .builder()
                            .personalIdType(body.getPersonalIDType())
                            .personalId(body.getPersonalID())
                            .firstName(body.getFirstName())
                            .lastName(body.getLastName())
                    .build());
        }
        Appointment appointment = Appointment
                .builder()
                .description(body.getDescription())
                .date(body.getDate())
                .patient(patient)
                .professional(professional)
                .build();
        appointmentRepository.save(appointment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO
                        .builder()
                        .data(appointment.getId())
                        .build());
    };
}
