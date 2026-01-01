package com.hms.pharmacy.repository;

import com.hms.pharmacy.entity.MedicineInventory;
import com.hms.pharmacy.entity.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicineInventoryRepository extends JpaRepository<MedicineInventory, Long> {

 List<MedicineInventory> findByExpiryDateBefore(LocalDate date);

 List<MedicineInventory> findByMedicineIdAndExpiryDateAfterAndQuantityGreaterThanAndStatusOrderByExpiryDateAsc(Long medicineId
         , LocalDate expiryDate, Integer quantity, StockStatus stockStatus);
}