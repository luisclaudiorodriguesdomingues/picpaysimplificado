package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    private static final String USER_NOT_FOUND = "Usuário não encontrado";
    private static final String USER_NOT_AUTHORIZED = "Tipo de usuário não autorizado a realizar transação";
    private static final String INSUFFICIENT_FUNDS = "Saldo insuficiente";
    private static final String DOCUMENT_EXIST = "Um usuário já cadastrado com esse documento";
    private static final String EMAIL_EXIST = "Um usuário já cadastrado com esse email";
    private static final String REDUNDANT_TRANSFER = "Transaferencia redundante";

    public  void validateTransaction(User sender, User receiver, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MARCHANT){
            throw new Exception(USER_NOT_AUTHORIZED);
        }
        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception(INSUFFICIENT_FUNDS);
        }
        if (sender.getId().equals(receiver.getId())){
            throw new Exception(REDUNDANT_TRANSFER);
        }

    }
    public  void validateCreateUser(UserDTO userDTO) throws Exception {
        Optional<User> userByDocument = this.repository.findUserByDocument(userDTO.document());
        if(userByDocument.isPresent()){
            throw new Exception(DOCUMENT_EXIST);
        }
        Optional<User> userByEmail = this.repository.findUserByEmail(userDTO.email());
        if(userByEmail.isPresent()){
            throw new Exception(EMAIL_EXIST);
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception((USER_NOT_FOUND)));
    }
    public User createUser(UserDTO userDTO) throws Exception {
        validateCreateUser(userDTO);
        User user = new User(userDTO);
        this.saveUser(user);
        return user;
    }
    public List<User> getAllUsers(){
        return this.repository.findAll();
    }
    public void  saveUser(User user){
        this.repository.save(user);
    }

}
