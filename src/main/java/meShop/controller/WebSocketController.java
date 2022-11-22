package meShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import meShop.model.ChatDetailModel;
import meShop.model.ChatMessage;
import meShop.service.ChatDetailService;

@Controller
public class WebSocketController {

	@Autowired ChatDetailService chatDetailService;

    @MessageMapping("/real/chat.sendMessage")
    @SendTo("/real/topic/publicChatRoom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    	System.out.println("controller sendMessage");
    	//luu message vao database
        return chatMessage;
    }

    @MessageMapping("/real/chat.addUser")
    @SendTo("/real/topic/publicChatRoom")
    public List<ChatDetailModel> addUser(@Payload ChatDetailModel chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
    	System.out.println("controller addUser");
    	//get lich su tro chuyen
        headerAccessor.getSessionAttributes().put("username", chatMessage.getName());
        List<ChatDetailModel> ChatDetailModels=chatDetailService.getAllChatDetailsBySenderId(chatMessage.getSenderId());
        return ChatDetailModels;
    }

}
