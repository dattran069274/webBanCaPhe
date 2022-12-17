package meShop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "chatDetail")
public class ChatDetailModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id; 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "content", nullable = false)
	String content;
	@Column(name = "name", nullable = false)
	String name;
	@Column(name = "createdAt", nullable = false)
	Date createdAt;
	@Column(name = "senderId", nullable = false)
	long senderId;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public ChatDetailModel(String content, String name, Date date, long senderId) {
		super();
		this.content = content;
		this.name = name;
		this.createdAt = date;
		this.senderId = senderId;
	}
	public ChatDetailModel() {
		super();
	}
	
}
