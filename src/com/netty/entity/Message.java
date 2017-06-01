package com.netty.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 发送消息实体
 * @author twjitm
 *
 */
public class Message implements Serializable {
	private Integer touserId;
	private Integer formuserId;
private String context;
private Integer id;
private Date sendtaime;


public Integer getTouserId() {
	return touserId;
}
public void setTouserId(Integer touserId) {
	this.touserId = touserId;
}
public Integer getFormuserId() {
	return formuserId;
}
public void setFormuserId(Integer formuserId) {
	this.formuserId = formuserId;
}
public String getContext() {
	return context;
}
public void setContext(String context) {
	this.context = context;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Date getSendtaime() {
	return sendtaime;
}
public void setSendtaime(Date sendtaime) {
	this.sendtaime = sendtaime;
}



}
