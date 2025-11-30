package com.hms.profile.entity;

import com.hms.profile.dto.BloodGroup;
import com.hms.profile.dto.PatientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private LocalDate dob;
    private String phone;
    private String address;

    @Column(unique = true)
    private String aadharNo;

    @Enumerated(EnumType.ORDINAL)
    private BloodGroup bloodGroup;

    private String allergies;
    private String chronicDisease;

    public PatientDTO toPatientDTO(){
        return new PatientDTO(this.id, this.name, this.email, this.dob,this.phone,this.address, this.aadharNo,
                this.bloodGroup,this.allergies,this.chronicDisease);
    }

}
