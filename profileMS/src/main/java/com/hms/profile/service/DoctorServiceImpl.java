package com.hms.profile.service;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.dto.DoctorDropDown;
import com.hms.profile.entity.Doctor;
import com.hms.profile.exception.HMSException;
import com.hms.profile.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public Long addDoctor(DoctorDTO doctorDTO) throws HMSException {
       if(doctorDTO.getEmail()!=null && doctorRepository.findByEmail(doctorDTO.getEmail()).isPresent()){
           throw new HMSException("DOCTOR_ALREADY_EXISTS");
       }

       if(doctorDTO.getLicenseNo()!=null && doctorRepository.findByLicenseNo(doctorDTO.getLicenseNo()).isPresent()){
           throw new HMSException("DOCTOR_ALREADY_EXISTS");
       }
        // Save doctor and return generated ID
        Doctor savedDoctor = doctorRepository.save(doctorDTO.toDoctorEntity());
        return savedDoctor.getId();
    }

    @Override
    public DoctorDTO getDoctorById(Long id) throws HMSException {
        return doctorRepository.findById(id).orElseThrow(()->new HMSException("DOCTOR_NOT_FOUND")).toDoctorDTO();
    }

    @Override
    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) throws HMSException {
        doctorRepository.findById(doctorDTO.getId()).orElseThrow(()->new HMSException("DOCTOR_NOT_FOUND"));
        return doctorRepository.save(doctorDTO.toDoctorEntity()).toDoctorDTO();
    }

    @Override
    public Boolean doctorExists(Long id) throws HMSException {
       return doctorRepository.existsById(id);
    }

    @Override
    public List<DoctorDropDown> getDoctorDropDown() throws HMSException {
        return doctorRepository.findAllDoctorDropdown();
    }

    @Override
    public List<DoctorDropDown> getDoctorsById(List<Long> ids) throws HMSException {
       return doctorRepository.findAllDoctorsById(ids);
    }
}
