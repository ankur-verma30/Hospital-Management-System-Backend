package com.hms.profile.dto;

import com.hms.profile.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String licenseNo;
    private String specialization;
    private String department;
    private Integer totalExp;
    private String dob;

    public Doctor toDoctorEntity(){
        return new Doctor(this.id, this.name, this.email, this.phone, this.address, this.licenseNo, this.specialization, this.department, this.totalExp, this.dob);
    }
}
