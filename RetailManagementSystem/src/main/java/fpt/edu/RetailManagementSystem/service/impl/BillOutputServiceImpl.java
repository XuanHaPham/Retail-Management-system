package fpt.edu.RetailManagementSystem.service.impl;

import fpt.edu.RetailManagementSystem.persistent.entity.BillOutput;
import fpt.edu.RetailManagementSystem.persistent.entity.BillOutputDetail;
import fpt.edu.RetailManagementSystem.persistent.entity.Product;
import fpt.edu.RetailManagementSystem.persistent.repository.*;
import fpt.edu.RetailManagementSystem.service.BillOutputService;
import fpt.edu.RetailManagementSystem.service.dto.BillOutputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillOutputDetailDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillOutputServiceImpl implements BillOutputService {
    private final BillOutputRepository billOutputRepository;
    private final BillOutputDetailRepository billOutputDetailRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    public BillOutputServiceImpl(BillOutputRepository billOutputRepository,
                                 BillOutputDetailRepository billOutputDetailRepository,
                                 ProductRepository productRepository,
                                 AccountRepository accountRepository) {
        this.billOutputRepository = billOutputRepository;
        this.billOutputDetailRepository = billOutputDetailRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Boolean create(List<BillOutputDetailDTO> billInputDetailDTOS, String code, float tax, Integer seller, Integer customerID){
        float total = 0;
        for (BillOutputDetailDTO b : billInputDetailDTOS) {
            float sum = productRepository.findByID(b.getProductID()).getPrice()*b.getQuantity();
            total += sum;
        }
        BillOutput bill = new BillOutput();
        bill.setTimeCreated(new Date());
        bill.setTotal(total*tax);
        bill.setStatus(false);
        bill.setCode(code);
        bill.setTax(tax);
        bill.setSellerId(seller);
        bill.setCustomerId(customerID);
        billOutputRepository.save(bill);
        for (BillOutputDetailDTO b : billInputDetailDTOS) {
            int newQuantiTY = productRepository.findByID(b.getProductID()).getQuantity() + b.getQuantity();
            productRepository.updateQuantity(newQuantiTY, b.getProductID());
            BillOutputDetail billDetail = new BillOutputDetail();
            billDetail.setStatus(true);
            billDetail.setQuantity(b.getQuantity());
            billDetail.setProductID(b.getProductID());
            billDetail.setBillID(bill.getId());
            billDetail.setUnit(b.getUnit());
            billOutputDetailRepository.save(billDetail);
        }
        return true;
    }

    @Override
    public List<BillOutputDTO> getAllBill(){
        List<BillOutput> bills = billOutputRepository.findAllByIsDelete();
        List<BillOutputDTO> billDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (BillOutput b : bills ) {
            BillOutputDTO billDTO = modelMapper.map(b, BillOutputDTO.class);
            billDTOS.add(billDTO);
        }
        return billDTOS;
    }
    @Override
    public List<BillOutputDetailDTO> getAllProductOfBill(Integer billID){
        List<BillOutputDetail> billDetails = billOutputDetailRepository.findAllByIsDelete(billID);
        List<BillOutputDetailDTO> billDetailDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (BillOutputDetail b : billDetails ) {
            Product product = productRepository.findByID(b.getProductID());
            BillOutputDetailDTO billDetailDTO = modelMapper.map(b, BillOutputDetailDTO.class);
            billDetailDTO.setCode(product.getCode());
            billDetailDTO.setPrice(product.getPrice());
            billDetailDTO.setProductName(product.getName());
            billDetailDTO.setQuantity(b.getQuantity());
            billDetailDTO.setUnit(b.getUnit());
            billDetailDTOS.add(billDetailDTO);
        }
        return billDetailDTOS;
    }

    @Override
    public Boolean updateStatus(Integer id){
        Optional.ofNullable(billOutputRepository.findById(id)).orElseThrow(() ->new EntityNotFoundException());
        if(billOutputRepository.findBillByID(id).getStatus())
            billOutputRepository.deleteByID(id, false);
        else
            billOutputRepository.deleteByID(id, true);
        return true;
    }

    @Override
    public Boolean updateIsPaid( Integer id){
        Optional.ofNullable(billOutputRepository.findById(id)).orElseThrow(() ->new EntityNotFoundException());
        if(billOutputRepository.findBillByID(id).getIsPaid())
            billOutputRepository.updateIsPaid(id, false);
        else
            billOutputRepository.updateIsPaid(id, true);
        return true;
    }
}
