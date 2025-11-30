package com.hms.pharmacy.service;

import com.hms.pharmacy.dto.SaleDTO;
import com.hms.pharmacy.entity.Sale;
import com.hms.pharmacy.exception.HMSException;
import com.hms.pharmacy.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleServiceImpl implements SaleService{

    private final SaleRepository saleRepository;
    @Override
    public Long createSale(SaleDTO saleDTO) throws HMSException {
        if(saleRepository.existsByPrescriptionId(saleDTO.getPrescriptionId())){
            throw new HMSException("SALE_ALREADY_EXISTS");
        }
        saleDTO.setSaleDate(LocalDateTime.now());
        return saleRepository.save(saleDTO.toEntity()).getId();
     }

    @Override
    public void updateSale(SaleDTO saleDTO) throws HMSException {
        Sale sale = saleRepository.findById(saleDTO.getId())
                .orElseThrow(
                () -> new HMSException("SALE_NOT_FOUND"));
        sale.setSaleDate(saleDTO.getSaleDate());
        sale.setTotalAmount(saleDTO.getTotalAmount());
        saleRepository.save(sale);
    }

    @Override
    public SaleDTO getSale(Long id) throws HMSException {
        return saleRepository.findById(id).orElseThrow(()->new HMSException("SALE_NOT_FOUND")).toDTO();
    }

    @Override
    public SaleDTO getSaleByPrescriptionId(Long prescriptionId) throws HMSException {
        return saleRepository.findByPrescriptionId(prescriptionId).orElseThrow(()->new HMSException("SALE_NOT_FOUND")).toDTO();
    }
}
