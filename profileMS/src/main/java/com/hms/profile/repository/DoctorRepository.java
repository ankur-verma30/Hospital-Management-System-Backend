package com.hms.profile.repository;

import com.hms.profile.dto.DoctorDropDown;
import com.hms.profile.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email) ;
    Optional<Doctor> findByLicenseNo(String licenseNo);

    @Query("select d.id as id, d.name as name from Doctor d")
    List<DoctorDropDown> findAllDoctorDropdown();

    @Query("select d.id as id, d.name as name from Doctor d where d.id in ?1")
    List<DoctorDropDown> findAllDoctorsById(List<Long> ids);
}