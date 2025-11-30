package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.MedicineInventoryDTO;
import com.hms.pharmacy.entity.MedicineInventory;
import com.hms.pharmacy.entity.StockStatus;
import com.hms.pharmacy.exception.HMSException;
import com.hms.pharmacy.repository.MedicineInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineInventoryServiceImpl implements MedicineInventoryService{

    private final MedicineInventoryRepository medicineInventoryRepository;

    private final MedicineService medicineService;


    @Override
    public List<MedicineInventoryDTO> getAllMedicines() throws HMSException {
        return medicineInventoryRepository.findAll().stream().map(MedicineInventory::toDTO).toList();
    }

    @Override
    public MedicineInventoryDTO getMedicineById(Long id) throws HMSException {
        return medicineInventoryRepository.findById(id).orElseThrow(() -> new HMSException("INVENTORY_NOT_FOUND")).toDTO();

    }

    @Override
    public MedicineInventoryDTO addMedicine(MedicineInventoryDTO medicineInventoryDTO) throws HMSException {
        medicineInventoryDTO.setAddedDate(LocalDate.now());
        medicineService.addStock(medicineInventoryDTO.getMedicineId(), medicineInventoryDTO.getQuantity());

        medicineInventoryDTO.setInitialQuantity(medicineInventoryDTO.getQuantity());
        medicineInventoryDTO.setStatus(StockStatus.ACTIVE);

        MedicineInventory medicineInventory = medicineInventoryDTO.toEntity();

        MedicineInventory savedInventory = medicineInventoryRepository.save(medicineInventory);

        // Map the saved entity (with ID) back to DTO
        return savedInventory.toDTO();
    }


    @Override
    public MedicineInventoryDTO updateMedicine(MedicineInventoryDTO medicineInventoryDTO) throws HMSException {
        MedicineInventory existingMedicineInventory = medicineInventoryRepository.findById(medicineInventoryDTO.getId()).orElseThrow(() -> new HMSException("INVENTORY_NOT_FOUND"));

        existingMedicineInventory.setBatchNo(medicineInventoryDTO.getBatchNo());

        if(existingMedicineInventory.getInitialQuantity()<medicineInventoryDTO.getQuantity()){
            medicineService.addStock(medicineInventoryDTO.getMedicineId(),
                    medicineInventoryDTO.getQuantity()-existingMedicineInventory.getInitialQuantity());
        }
        else if(existingMedicineInventory.getInitialQuantity()>medicineInventoryDTO.getQuantity()){
            medicineService.removeStock(medicineInventoryDTO.getMedicineId(),
                    existingMedicineInventory.getInitialQuantity()-medicineInventoryDTO.getQuantity());
        }

        existingMedicineInventory.setQuantity(medicineInventoryDTO.getQuantity());
        existingMedicineInventory.setInitialQuantity(medicineInventoryDTO.getQuantity());
        existingMedicineInventory.setExpiryDate(medicineInventoryDTO.getExpiryDate());


        return medicineInventoryRepository.save(existingMedicineInventory).toDTO();
    }

    @Override
    public void deleteMedicine(Long id) throws HMSException {
        medicineInventoryRepository.deleteById(id);
    }

    private void markExpiredStatus(List<MedicineInventory> medicineInventories ) throws HMSException {
        for(MedicineInventory medicine:medicineInventories){
            medicine.setStatus(StockStatus.EXPIRED);
        }
        medicineInventoryRepository.saveAll(medicineInventories);
    }

    @Override
    @Scheduled(cron = "0 41 17 * * ?") // 17:31:00
    public void deleteExpiredMedicine() throws HMSException {
        List<MedicineInventory> expiredMedicine = medicineInventoryRepository.findByExpiryDateBefore(LocalDate.now());
        for(MedicineInventory medicine:expiredMedicine){
            medicineService.removeStock(medicine.getMedicine().getId(),medicine.getQuantity());
        }

        this.markExpiredStatus(expiredMedicine);
    }




    // 0 30 14 * * ?"
 // seconds minutes hours dayofMonth month dayofWeek
}
