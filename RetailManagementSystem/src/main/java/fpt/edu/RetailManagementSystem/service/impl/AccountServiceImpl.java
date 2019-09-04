package fpt.edu.RetailManagementSystem.service.impl;
import fpt.edu.RetailManagementSystem.persistent.entity.Account;
import fpt.edu.RetailManagementSystem.persistent.repository.AccountRepository;
import fpt.edu.RetailManagementSystem.service.AccountService;
import fpt.edu.RetailManagementSystem.service.dto.AccountDTO;
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
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public AccountDTO getByUsernameAndPassword(String username, String password) {
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        if(ObjectUtils.isEmpty(account)) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public List<AccountDTO> getAll() {
        List<Account> accounts = accountRepository.findAllByIsDelete();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Account acc : accounts ) {
            accountDTOS.add(modelMapper.map(acc, AccountDTO.class));

        }
        return accountDTOS;
    }

    @Override
    public Boolean delete(Integer id) {
        Optional.ofNullable(accountRepository.findById(id)).orElseThrow(() ->new EntityNotFoundException());
        accountRepository.deleteByID(id);
        return true;
    }

    @Override
    public AccountDTO insert(AccountDTO accountDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDTO, Account.class);
        account = accountRepository.save(account);
        AccountDTO dto = modelMapper.map(account, AccountDTO.class);
        return dto;
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        Optional.ofNullable(accountRepository.findById(accountDTO.getId())).orElseThrow(() -> new EntityNotFoundException());
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDTO, Account.class);
        account = accountRepository.saveAndFlush(account);
        AccountDTO dto = modelMapper.map(account, AccountDTO.class);
        return dto;
    }
}