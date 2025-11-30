package com.hms.profile.service;

import com.hms.profile.dto.PatientDTO;
import com.hms.profile.entity.Patient;
import com.hms.profile.exception.HMSException;
import com.hms.profile.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Long addPatient(PatientDTO patientDTO) throws HMSException {
        if(patientDTO.getEmail()!=null && patientRepository.findByEmail(patientDTO.getEmail()).isPresent()){
            throw new HMSException("PATIENT_ALREADY_EXISTS");
        }
        if(patientDTO.getAadharNo()!=null && patientRepository.findByAadharNo(patientDTO.getAadharNo()).isPresent()){
            throw new HMSException("PATIENT_ALREADY_EXISTS");
        }
        Patient savedPatient = patientRepository.save(patientDTO.toPatientEntity());
        return savedPatient.getId();
    }

    @Override
    public PatientDTO getPatientById(Long id) throws HMSException {
        return patientRepository.findById(id).orElseThrow(()->new HMSException("PATIENT_NOT_FOUND")).toPatientDTO();
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO) throws HMSException {
        patientRepository.findById(patientDTO.getId()).orElseThrow(()->new HMSException("PATIENT_NOT_FOUND"));
        return patientRepository.save(patientDTO.toPatientEntity()).toPatientDTO();
    }

    @Override
    public Boolean patientExists(Long id) throws HMSException {
        return patientRepository.existsById(id);
    }
}
