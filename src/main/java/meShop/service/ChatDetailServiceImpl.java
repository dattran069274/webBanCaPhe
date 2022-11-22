package meShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meShop.model.ChatDetailModel;
import meShop.repository.ChatDetailRepository;
@Service
public class ChatDetailServiceImpl implements ChatDetailService{
	@Autowired ChatDetailRepository chatDetailRepository;
	@Override
	public List<ChatDetailModel> getAllChatDetails() {
		
		return null;
	}

	@Override
	public void saveChatDetail(ChatDetailModel chatDetailMWodel) {
		chatDetailRepository.save(chatDetailMWodel);
		
	}

	@Override
	public ChatDetailModel getChatDetailModelById(long id) {
		
		Optional<ChatDetailModel> optional= chatDetailRepository.findById(id);
		if(optional.isPresent()) return optional.get();
		return null;
	}

	@Override
	public void deleteChatDetailById(long id) {
		chatDetailRepository.deleteById(id);
		
	}

	@Override
	public List<ChatDetailModel> getAllChatDetailsBySenderId(long senderId) {
		return chatDetailRepository.getAllChatDetailsBySenderId(senderId);
	}

}
