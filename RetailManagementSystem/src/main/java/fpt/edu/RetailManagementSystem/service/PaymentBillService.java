package fpt.edu.RetailManagementSystem.service;

import fpt.edu.RetailManagementSystem.service.dto.PaymentBillDTO;

import java.util.List;

public interface PaymentBillService {
    List<PaymentBillDTO> getAll();

    List<PaymentBillDTO> findByCategoryID(Integer id);

    Boolean deleteByID(Integer id);

    PaymentBillDTO findByID(Integer id);

    PaymentBillDTO insert(PaymentBillDTO paymentBillDTO);

    PaymentBillDTO update(PaymentBillDTO paymentBillDTO);
}
