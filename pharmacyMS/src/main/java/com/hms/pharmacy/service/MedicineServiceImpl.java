package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.MedicineDTO;
import com.hms.pharmacy.entity.Medicine;
import com.hms.pharmacy.exception.HMSException;
import com.hms.pharmacy.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService{

    private final MedicineRepository medicineRepository;

    @Override
    public Long addMedicine(MedicineDTO medicineDTO) throws HMSException {
        Optional<Medicine> medicine = medicineRepository.findByNameIgnoreCaseAndDosageIgnoreCase(medicineDTO.getName(), medicineDTO.getDosage());
        if(medicine.isPresent()){
            throw new HMSException("MEDICINE_ALREADY_EXISTS");
        }
        medicineDTO.setCreatedAt(LocalDateTime.now());
        medicineDTO.setStock(0);
        return medicineRepository.save(medicineDTO.toEntity()).getId();
    }

    @Override
    public MedicineDTO getMedicineById(Long id) throws HMSException {
        return medicineRepository.findById(id).orElseThrow(() -> new HMSException("MEDICINE_NOT_FOUND")).toDTO();

    }

    @Override
    public void updateMedicine(MedicineDTO medicineDTO) throws HMSException {
       Medicine existingMedicine=
               medicineRepository.findById(medicineDTO.getId()).orElseThrow(() -> new HMSException("MEDICINE_NOT_FOUND"));

       if(!(medicineDTO.getName().equalsIgnoreCase(existingMedicine.getName()) && medicineDTO.getDosage().equalsIgnoreCase(existingMedicine.getDosage()))){
           Optional<Medicine> medicine=medicineRepository.findByNameIgnoreCaseAndDosageIgnoreCase(medicineDTO.getName(), medicineDTO.getDosage());
           if(medicine.isPresent()){
               throw new HMSException("MEDICINE_ALREADY_EXISTS");
           }
       }
       existingMedicine.setName(medicineDTO.getName());
       existingMedicine.setDosage(medicineDTO.getDosage());
       existingMedicine.setCategory(medicineDTO.getCategory());
       existingMedicine.setType(medicineDTO.getType());
       existingMedicine.setManufacturer(medicineDTO.getManufacturer());
       existingMedicine.setUnitPrice(medicineDTO.getUnitPrice());

        medicineRepository.save(medicineDTO.toEntity());

    }

    @Override
    public List<MedicineDTO> getAllMedicines() throws HMSException {
        return ((List<Medicine>)medicineRepository.findAll()).stream().map(Medicine::toDTO).toList();
    }

    @Override
    public Integer getStockById(Long id) throws HMSException {
        return medicineRepository.findStockById(id).orElseThrow(() -> new HMSException("MEDICINE_NOT_FOUND"));
    }

    @Override
    public Integer addStock(Long id, Integer quantity) throws HMSException {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new HMSException("MEDICINE_NOT_FOUND"));
        medicine.setStock((medicine.getStock() != null ? medicine.getStock() : 0) + quantity);
        medicineRepository.save(medicine);
        return medicine.getStock();
    }

    @Override
    public Integer removeStock(Long id, Integer quantity) throws HMSException {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new HMSException("MEDICINE_NOT_FOUND"));
        if(medicine.getStock()==null) throw new HMSException("NOT_ENOUGH_STOCK");
        if(medicine.getStock()<quantity) throw new HMSException("NOT_ENOUGH_STOCK");
        medicine.setStock(medicine.getStock() - quantity);
        medicineRepository.save(medicine);
        return medicine.getStock();
    }
}
