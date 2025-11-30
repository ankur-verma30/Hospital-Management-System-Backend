package com.hms.profile.api;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.dto.DoctorDropDown;
import com.hms.profile.exception.HMSException;
import com.hms.profile.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/doctor")
@Validated
@RequiredArgsConstructor
public class DoctorAPI {
    private final DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<Long> addDoctor(@RequestBody DoctorDTO doctorDTO) throws HMSException {
        Long id = doctorService.addDoctor(doctorDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable String id) throws HMSException {
        Long fetchId=Long.parseLong(id);
        DoctorDTO doctorDTO = doctorService.getDoctorById(fetchId);
        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) throws HMSException {
        DoctorDTO updatedDoctorDTO = doctorService.updateDoctor(doctorDTO);
        return new ResponseEntity<>(updatedDoctorDTO, HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> doctorExists(@PathVariable Long id) throws HMSException{
        return new ResponseEntity<>(doctorService.doctorExists(id),HttpStatus.OK);
    }

    @GetMapping("/dropdowns")
    public ResponseEntity<List<DoctorDropDown>> findAllDoctors() throws HMSException{
        return new ResponseEntity<>(doctorService.getDoctorDropDown(),HttpStatus.OK);
    }

    @GetMapping("/getDoctorsById")
    public ResponseEntity<List<DoctorDropDown>> getDoctorsById(@RequestParam List<Long> ids) throws HMSException{
        return new ResponseEntity<>(doctorService.getDoctorsById(ids),HttpStatus.OK);
    }
    
}
