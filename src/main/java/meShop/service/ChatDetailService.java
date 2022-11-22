package meShop.service;

import java.util.List;

import meShop.model.ChatDetailModel;

public interface ChatDetailService {
	List<ChatDetailModel> getAllChatDetails();
	List<ChatDetailModel> getAllChatDetailsBySenderId(long senderId);
	void saveChatDetail(ChatDetailModel chatDetailMWodel);
	ChatDetailModel getChatDetailModelById(long id); 
	void deleteChatDetailById(long id);
}	
