package fpt.edu.RetailManagementSystem.api;

import fpt.edu.RetailManagementSystem.service.dto.PaymentBillDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
@RequestMapping("/paymentBill")
public interface PaymentBillApi {
    @ApiOperation(tags = {"PaymentBill",}, notes = "", value = "Get all PaymentBill")
    @GetMapping("/getAllPaymentBill")
    ResponseEntity<List<PaymentBillDTO>> getAllPaymentBill();

    @ApiOperation(tags = {"PaymentBill",}, notes = "", value = "Delete PaymentBill by ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Integer id);

    @ApiOperation(tags = {"PaymentBill",}, notes = "", value = "Get PaymentBill by ID")
    @GetMapping("/findByID")
    ResponseEntity<PaymentBillDTO> findByID(@RequestParam("id") Integer id);

    @ApiOperation(tags = {"PaymentBill",}, notes = "", value = "insert PaymentBill")
    @PostMapping("")
    ResponseEntity<PaymentBillDTO> insert(@RequestBody PaymentBillDTO paymentBillDTO);

    @ApiOperation(tags = {"PaymentBill",}, notes = "", value = "update PaymentBill")
    @PutMapping("")
    ResponseEntity<PaymentBillDTO> update(@RequestBody PaymentBillDTO paymentBillDTO);
}
