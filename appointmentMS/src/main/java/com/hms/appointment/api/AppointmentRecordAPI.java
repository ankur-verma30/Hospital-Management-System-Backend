package com.hms.appointment.api;

import com.hms.appointment.dto.AppointmentRecordDTO;
import com.hms.appointment.dto.PrescriptionDTO;
import com.hms.appointment.dto.PrescriptionDetails;
import com.hms.appointment.dto.RecordDetails;
import com.hms.appointment.exception.HMSException;
import com.hms.appointment.service.AppointmentRecordService;
import com.hms.appointment.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment/report")
@Validated
@RequiredArgsConstructor
public class AppointmentRecordAPI {

    private final AppointmentRecordService appointmentRecordService;
    private final PrescriptionService prescriptionService;

    @PostMapping("/create")
    public ResponseEntity<Long> createAppointmentReport(@RequestBody AppointmentRecordDTO appointmentRecordDTO) throws HMSException {
        Long appointmentRecord = appointmentRecordService.createAppointmentRecord(appointmentRecordDTO);
        return new ResponseEntity<>(appointmentRecord, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAppointmentReport(@RequestBody AppointmentRecordDTO appointmentRecordDTO) throws  HMSException{
        appointmentRecordService.updateAppointmentRecord(appointmentRecordDTO);
        return new ResponseEntity<>("Appointment Record Updated", HttpStatus.OK);
    }

    @GetMapping("/getAppointmentId/{appointmentId}")
    public ResponseEntity<AppointmentRecordDTO> getAppointmentReportByAppointmentId(@PathVariable Long appointmentId) throws  HMSException{
        AppointmentRecordDTO appointmentRecord = appointmentRecordService.getAppointmentRecordByAppointmentId(appointmentId);
        return new ResponseEntity<>(appointmentRecord, HttpStatus.OK);
    }

    @GetMapping("/getDetailsAppointmentId/{appointmentId}")
    public ResponseEntity<AppointmentRecordDTO> getAppointmentRecordDetailsByAppointmentId(@PathVariable Long appointmentId) throws HMSException{
        AppointmentRecordDTO appointmentRecord = appointmentRecordService.getAppointmentRecordDetailsByAppointmentId(appointmentId);
        return new ResponseEntity<>(appointmentRecord,HttpStatus.OK);
    }

    @GetMapping("/getById/{recordId}")
    public ResponseEntity<AppointmentRecordDTO> getAppointmentReportById(@PathVariable Long recordId) throws  HMSException{
        AppointmentRecordDTO appointmentRecord = appointmentRecordService.getAppointmentRecordById(recordId);
        return new ResponseEntity<>(appointmentRecord,HttpStatus.OK);
    }

    @GetMapping("/getRecordsByPatientId/{patientId}")
    public ResponseEntity<List<RecordDetails>> getAppointmentRecordsByPatientId(@PathVariable Long patientId) throws  HMSException{
        List<RecordDetails> appointmentRecords = appointmentRecordService.getAppointmentRecordByPatientId(patientId);
        return new ResponseEntity<>(appointmentRecords,HttpStatus.OK);
    }

    @GetMapping("/isRecordExists/{appointmentId}")
    public ResponseEntity<Boolean> isRecordExists(@PathVariable Long appointmentId) throws  HMSException{
        Boolean recordExists = appointmentRecordService.appointmentRecordExists(appointmentId);
        return new ResponseEntity<>(recordExists,HttpStatus.OK);
    }

    @GetMapping("/getPrescriptionByPatientId/{patientId}")
    public ResponseEntity<List<PrescriptionDetails>> getPrescriptionByPatientId(@PathVariable Long patientId) throws  HMSException{
        List<PrescriptionDetails> prescriptions = prescriptionService.getPrescriptionsByPatientId(patientId);
        return new ResponseEntity<>(prescriptions,HttpStatus.OK);
    }
}
