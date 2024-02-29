package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionsDTO;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotiticationService notiticationService;

    public List<TransactionsDTO> findTransactionUser() throws Exception {
        return this.transactionRepository.findTransactionUser();
    }
    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById((transactionDTO.senderId()));
        User receiver = this.userService.findUserById((transactionDTO.receiverId()));

        userService.validateTransaction(sender, receiver, transactionDTO.value());

//        if(!authorizeTransaction(sender, transactionDTO.value())){
//             throw new Exception("Transação não autorizado");
//        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.transactionRepository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notiticationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notiticationService.sendNotification(receiver, "Transferencia recebida com sucesso");

        return transaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizeResponse = restTemplate.getForEntity("http://localhost.com/test", Map.class);

        if (authorizeResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) Objects.requireNonNull(authorizeResponse.getBody()).get("message");
            return "Authorized".equalsIgnoreCase(message);
        } else return false;
    }
}