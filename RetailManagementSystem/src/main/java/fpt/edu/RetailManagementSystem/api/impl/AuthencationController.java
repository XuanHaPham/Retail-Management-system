package com.example.demo.api.impl;

import com.example.demo.api.AnthencationApi;
import com.example.demo.service.AccountService;
import com.example.demo.service.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthencationController implements AnthencationApi {

    @Autowired
    AccountService accountService;

    @Override
    public ResponseEntity<AccountDTO> checkLogin(@RequestParam("username") String username,
                                                 @RequestParam("password") String password) {
        AccountDTO accountDTO = accountService.getByUsernameAndPassword(username, password);
        return ResponseEntity.ok(accountDTO);
    }

}
