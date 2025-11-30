package com.hms.profile.service;

import com.hms.profile.dto.DoctorDTO;
import com.hms.profile.dto.DoctorDropDown;
import com.hms.profile.exception.HMSException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DoctorService {
    public Long addDoctor(DoctorDTO doctorDTO) throws HMSException;
    public DoctorDTO getDoctorById(Long id) throws HMSException;

    DoctorDTO updateDoctor(DoctorDTO doctorDTO) throws HMSException;

    public Boolean doctorExists(Long id) throws HMSException;

    public List<DoctorDropDown> getDoctorDropDown() throws HMSException;

    public List<DoctorDropDown> getDoctorsById(List<Long> ids) throws HMSException;
}
