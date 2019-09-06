package fpt.edu.RetailManagementSystem.service.impl;

import fpt.edu.RetailManagementSystem.persistent.entity.PaymentBill;
import fpt.edu.RetailManagementSystem.persistent.entity.Product;
import fpt.edu.RetailManagementSystem.persistent.repository.PaymentBillRepository;
import fpt.edu.RetailManagementSystem.persistent.repository.ProductRepository;
import fpt.edu.RetailManagementSystem.service.PaymentBillService;
import fpt.edu.RetailManagementSystem.service.ProductService;
import fpt.edu.RetailManagementSystem.service.dto.PaymentBillDTO;
import fpt.edu.RetailManagementSystem.service.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentBillServiceImpl implements PaymentBillService {

    private final PaymentBillRepository paymentBillRepository;

    public PaymentBillServiceImpl(PaymentBillRepository paymentBillRepository) {
        this.paymentBillRepository = paymentBillRepository;
    }

    @Override
    public List<PaymentBillDTO> getAll(){
        List<PaymentBill> paymentBills = paymentBillRepository.findAllByIsDelete();
        List<PaymentBillDTO> paymentBillDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (PaymentBill pro : paymentBills ) {
            PaymentBillDTO paymentBillDTO = modelMapper.map(pro, PaymentBillDTO.class);
            paymentBillDTOS.add(paymentBillDTO);
        }
        return paymentBillDTOS;
    }

    @Override
    public Boolean deleteByID(Integer id){
        Optional.ofNullable(paymentBillRepository.findById(id)).orElseThrow(() ->new EntityNotFoundException());
        paymentBillRepository.deleteByID(id, true);
        return true;
    }

    @Override
    public PaymentBillDTO findByID(Integer id){
        PaymentBill paymentBill = paymentBillRepository.findBillByID(id);
        if(ObjectUtils.isEmpty(paymentBill)) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        PaymentBillDTO paymentBillDTO = modelMapper.map(paymentBill, PaymentBillDTO.class);
        return paymentBillDTO;
    }
    @Override
    public PaymentBillDTO insert(PaymentBillDTO paymentBillDTO){
        ModelMapper modelMapper = new ModelMapper();
        PaymentBill paymentBill = modelMapper.map(paymentBillDTO, PaymentBill.class);
        paymentBill = paymentBillRepository.save(paymentBill);
        PaymentBillDTO dto = modelMapper.map(paymentBill, PaymentBillDTO.class);
        return dto;
    }

    @Override
    public PaymentBillDTO update(PaymentBillDTO paymentBillDTO){
        Optional.ofNullable(paymentBillRepository.findById(paymentBillDTO.getId())).orElseThrow(() -> new EntityNotFoundException());
        ModelMapper modelMapper = new ModelMapper();
        PaymentBill paymentBill = modelMapper.map(paymentBillDTO, PaymentBill.class);
        paymentBill = paymentBillRepository.saveAndFlush(paymentBill);
        PaymentBillDTO dto = modelMapper.map(paymentBill, PaymentBillDTO.class);
        return dto;
    }

}
