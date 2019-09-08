package fpt.edu.RetailManagementSystem.api;
import fpt.edu.RetailManagementSystem.service.dto.BillInputDTO;
import fpt.edu.RetailManagementSystem.service.dto.BillInputDetailDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
@RequestMapping("/billsInput")
public interface BillInputApi {
    @ApiOperation(tags = {"Bill Input",}, notes = "", value = "Create new Bill")
    @PostMapping("{accountID}")
    ResponseEntity<Map<String, Boolean>> create(@RequestBody List<BillInputDetailDTO> billDetailDTO,
                                                @PathVariable Integer accountID,
                                                @PathVariable String code,
                                                @PathVariable float tax,
                                                @PathVariable Integer supplier);

    @ApiOperation(tags = {"Bill Input",}, notes = "", value = "Get All Bill")
    @GetMapping("")
    ResponseEntity<List<BillInputDTO>> getAllBill();

    @ApiOperation(tags = {"Bill Input",}, notes = "", value = "get all product in Bill")
    @GetMapping("{billID}")
    ResponseEntity<List<BillInputDetailDTO>> getAllProductOfBill(@PathVariable Integer billID);

    @ApiOperation(tags = {"Bill Input",}, notes = "", value = "Update status of bill")
    @PutMapping("/updateBillStatus/{id}")
    ResponseEntity<Map<String, Boolean>> updateBillStatus(@PathVariable Integer id);

    @ApiOperation(tags = {"Bill Input",}, notes = "", value = "Update isPaid of bill")
    @PutMapping("/updateIsPaid/{id}")
    ResponseEntity<Map<String, Boolean>> updateIsPaid(@PathVariable Integer id);
    //Boolean updateIsPaid( Integer id, Boolean isPaid);
}
