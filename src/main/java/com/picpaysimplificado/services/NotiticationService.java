package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotiticationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        System.out.println("Serviço de notificação está fora do ar");
//        String email = user.getEmail();
//        NotificationDTO notificationRequest = new NotificationDTO(email, message);
//
//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("", notificationRequest, String.class);
//
//        if(!(notificationResponse.getStatusCode()== HttpStatus.OK)){
//            throw new Exception("Serviço de notificação está fora do ar");
//        }
    }
}
