package fpt.edu.RetailManagementSystem.service;

import fpt.edu.RetailManagementSystem.service.dto.BillOutputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillOutputDetailDTO;

import java.util.List;

public interface BillOutputService {
    Boolean create(List<BillOutputDetailDTO> billInputDetailDTOS, String code, float tax, Integer seller, Integer customerID);
    List<BillOutputDTO> getAllBill();
    List<BillOutputDetailDTO> getAllProductOfBill(Integer billID);
    Boolean updateStatus(Integer id);
    Boolean updateIsPaid( Integer id);
}
