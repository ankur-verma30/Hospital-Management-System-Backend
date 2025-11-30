package com.hms.appointment.api;

import com.hms.appointment.dto.AppointmentDTO;
import com.hms.appointment.dto.AppointmentDetails;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@Validated
@RequiredArgsConstructor
public class AppointmentAPI {

    private final AppointmentService appointmentService;

    @PostMapping("/scheduled")
    public ResponseEntity<Long> scheduleAppointment(@RequestBody AppointmentDTO appointmentDTO) throws HMSException {
        Long id = appointmentService.scheduleAppointment(appointmentDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> rescheduleAppointment(@PathVariable Long appointmentId) throws HMSException {
        appointmentService.cancelAppointment(appointmentId);
        return new ResponseEntity<>("Appointment cancelled successfully",HttpStatus.OK);
    }

    @GetMapping("/get/{appointmentId}")
    public ResponseEntity<AppointmentDTO> getAppointmentDetails(@PathVariable Long appointmentId) throws HMSException {
        AppointmentDTO appointmentDTO = appointmentService.getAppointmentDetails(appointmentId);
        return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Test", HttpStatus.OK);
    }

    @GetMapping("/get/details/{appointmentId}")
    public ResponseEntity<AppointmentDetails> getAppointmentDetailsWithName(@PathVariable Long appointmentId) throws HMSException {
        AppointmentDetails appointmentDetails = appointmentService.getAppointmentDetailsWithName(appointmentId);
        return new ResponseEntity<>(appointmentDetails, HttpStatus.OK);
    }

    @GetMapping("/getAllByPatient/{patientId}")
    public ResponseEntity<List<AppointmentDetails>> getAppointmentDetailsByPatientId(@PathVariable Long patientId) throws HMSException {
        List<AppointmentDetails> appointmentDetails = appointmentService.getAppointmentDetailsByPatientId(patientId);
        return new ResponseEntity<>(appointmentDetails, HttpStatus.OK);
    }

    @GetMapping("/getAllByDoctor/{doctorId}")
    public ResponseEntity<List<AppointmentDetails>> getAppointmentDetailsByDoctorId(@PathVariable Long doctorId) throws HMSException {
        List<AppointmentDetails> appointmentDetails = appointmentService.getAppointmentDetailsByDoctorId(doctorId);
        return new ResponseEntity<>(appointmentDetails, HttpStatus.OK);
    }
}
