package com.example.capSProj.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="notification")
public class Notification {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int serviceid;
	
	private String orgName;
	
	private String notification;
	
	private String serviceType;
	
	private String createdDate;
	
	private boolean isPending;
	
	private boolean isAcknowledged;
	
	private boolean isInProgress;
	
	private boolean isClose;
	
	private boolean Decline;
	
	private boolean Rfe;
	
	private String RfeMsg;
	
	private String serviceName;
	
	private String clientName;
	
	private String acknowledgeDate;
	
	private String inProgressDate;
	
	private String declineDate;
	
	private String rfeDate;
	
	private String pendingDate;

	private String closeDate;
	
	private String statusMsg;
//	private String approveMsg;

	private String referralOrg;

	public int getServiceid() {
		return serviceid;
	}

	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	
	


	public String getServiceName() {
		return serviceName;
	}

	


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setServiceid(int serviceid) {
		this.serviceid = serviceid;
	}

	public int getId() {
		return id;
	}

	

	public boolean isAcknowledged() {
		return isAcknowledged;
	}


	public void setAcknowledged(boolean isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}


	public boolean isInProgress() {
		return isInProgress;
	}


	public void setInProgress(boolean isInProgress) {
		this.isInProgress = isInProgress;
	}


	public boolean isDecline() {
		return Decline;
	}


	public void setDecline(boolean decline) {
		Decline = decline;
	}


	public boolean isRfe() {
		return Rfe;
	}


	public void setRfe(boolean rfe) {
		Rfe = rfe;
	}


	public String getRfeMsg() {
		return RfeMsg;
	}


	public void setRfeMsg(String rfeMsg) {
		RfeMsg = rfeMsg;
	}


	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}


	public boolean isPending() {
		return isPending;
	}


	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}


	public String getStatusMsg() {
		return statusMsg;
	}


	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}


	public String getAcknowledgeDate() {
		return acknowledgeDate;
	}


	public void setAcknowledgeDate(String acknowledgeDate) {
		this.acknowledgeDate = acknowledgeDate;
	}


	public String getInProgressDate() {
		return inProgressDate;
	}


	public void setInProgressDate(String inProgressDate) {
		this.inProgressDate = inProgressDate;
	}


	public String getDeclineDate() {
		return declineDate;
	}


	public void setDeclineDate(String declineDate) {
		this.declineDate = declineDate;
	}


	public String getRfeDate() {
		return rfeDate;
	}


	public void setRfeDate(String rfeDate) {
		this.rfeDate = rfeDate;
	}


	public String getPendingDate() {
		return pendingDate;
	}


	public void setPendingDate(String pendingDate) {
		this.pendingDate = pendingDate;
	}


	public String getCloseDate() {
		return closeDate;
	}


	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}


	public boolean isClose() {
		return isClose;
	}


	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}


	public String getReferralOrg() {
		return referralOrg;
	}


	public void setReferralOrg(String referralOrg) {
		this.referralOrg = referralOrg;
	}


	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	
}
