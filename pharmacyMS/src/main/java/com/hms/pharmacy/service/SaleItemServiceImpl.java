package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.SaleItemDTO;
import com.hms.pharmacy.entity.SaleItem;
import com.hms.pharmacy.exception.HMSException;
import com.hms.pharmacy.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleItemServiceImpl implements SaleItemService{

    private  final SaleItemRepository saleItemRepository;

    @Override
    public Long createSaleItem(SaleItemDTO saleItemDTO) throws HMSException {
        return saleItemRepository.save(saleItemDTO.toEntity()).getId();
    }

    @Override
    public void createMultipleSaleItems(Long saleId, Long medicineId, List<SaleItemDTO> saleItemDTOs) throws HMSException {
    saleItemDTOs.stream().map((x) ->{
        x.setSaleId(saleId);
        x.setMedicineId(medicineId);
        return x.toEntity();
    }).forEach(saleItemRepository::save);
    }

    @Override
    public void updateSaleItem(SaleItemDTO saleItemDTO) throws HMSException {
        SaleItem saleItem = saleItemRepository.findById(saleItemDTO.getId()).orElseThrow(() -> new HMSException("SALE_ITEM_NOT_FOUND"));

        saleItem.setQuantity(saleItemDTO.getQuantity());
        saleItem.setUnitPrice(saleItemDTO.getUnitPrice());
        saleItemRepository.save(saleItem);
    }

    @Override
    public List<SaleItemDTO> getSaleItemsBySaleId(Long saleId) throws HMSException {
        return saleItemRepository.findBySaleId(saleId).stream().map(SaleItem::toDTO).toList();
    }

    @Override
    public SaleItemDTO getSaleItem(Long id) throws HMSException {
        return saleItemRepository.findById(id).orElseThrow(() -> new HMSException("SALE_ITEM_NOT_FOUND")).toDTO();
    }
}
