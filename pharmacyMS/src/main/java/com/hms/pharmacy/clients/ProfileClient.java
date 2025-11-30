package com.hms.pharmacy.clients;


import com.hms.pharmacy.config.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ProfileMS",configuration = FeignClientInterceptor.class)
public interface ProfileClient {

    @GetMapping("/profile/doctor/exists/{id}")
    Boolean doctorExists(@PathVariable Long id);

    @GetMapping("/profile/patient/exists/{id}")
    Boolean patientExists(@PathVariable Long id);


}
