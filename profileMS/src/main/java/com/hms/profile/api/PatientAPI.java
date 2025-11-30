package com.hms.profile.api;

import com.hms.profile.dto.PatientDTO;
import com.hms.profile.exception.HMSException;
import com.hms.profile.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/patient")
@Validated
@RequiredArgsConstructor
public class PatientAPI {
    private final PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<Long> addPatient(@RequestBody PatientDTO patientDTO) throws HMSException {
        Long id = patientService.addPatient(patientDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable String id) throws HMSException {
        Long fetchId=Long.parseLong(id);
        PatientDTO patientDTO = patientService.getPatientById(fetchId);
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) throws HMSException {
        PatientDTO updatedPatientDTO = patientService.updatePatient(patientDTO);
        return new ResponseEntity<>(updatedPatientDTO, HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> patientExists(@PathVariable Long id) throws HMSException{
        return new ResponseEntity<>(patientService.patientExists(id),HttpStatus.OK);
    }
}
