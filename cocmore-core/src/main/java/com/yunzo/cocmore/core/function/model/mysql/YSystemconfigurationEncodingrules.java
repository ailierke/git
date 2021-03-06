package com.yunzo.cocmore.core.function.model.mysql;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * YSystemconfigurationEncodingrules entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "y_systemconfiguration_encodingrules" )
@DynamicInsert(true)
@DynamicUpdate(true)
public class YSystemconfigurationEncodingrules implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private String fid;
	private YBasicOrganization YBasicOrganization;
	private String fnumber;
	private String finvoicesType;
	private Integer fbillState;
	private String ffixedValue;
	private String fdateValue;
	private String fserialNumber;
	private String fpreview;
	private String fcreaterId;
	private String fmodifiedId;
	private String flastModifiedId;
	private Date fcreateTime;
	private Date fmodifiedTime;
	private Date flastModifiedTime;
	private Integer flag;

	// Constructors

	/** default constructor */
	public YSystemconfigurationEncodingrules() {
	}

	/** minimal constructor */
	public YSystemconfigurationEncodingrules(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public YSystemconfigurationEncodingrules(String fid,
			YBasicOrganization YBasicOrganization, String fnumber,
			String finvoicesType, Integer fbillState, String ffixedValue,
			String fdateValue, String fserialNumber, String fpreview,
			String fcreaterId, String fmodifiedId, String flastModifiedId,
			Date fcreateTime, Date fmodifiedTime, Date flastModifiedTime,
			Integer flag) {
		this.fid = fid;
		this.YBasicOrganization = YBasicOrganization;
		this.fnumber = fnumber;
		this.finvoicesType = finvoicesType;
		this.fbillState = fbillState;
		this.ffixedValue = ffixedValue;
		this.fdateValue = fdateValue;
		this.fserialNumber = fserialNumber;
		this.fpreview = fpreview;
		this.fcreaterId = fcreaterId;
		this.fmodifiedId = fmodifiedId;
		this.flastModifiedId = flastModifiedId;
		this.fcreateTime = fcreateTime;
		this.fmodifiedTime = fmodifiedTime;
		this.flastModifiedTime = flastModifiedTime;
		this.flag = flag;
	}

	// Property accessors
	@Id
	@Column(name = "FID", unique = true, nullable = false, length = 36)
	public String getFid() {
		if (!StringUtils.isBlank(this.fid)) {
			return this.fid;
		}
		return UUID.randomUUID().toString();
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FBelongOrganizationID")
	public YBasicOrganization getYBasicOrganization() {
		return this.YBasicOrganization;
	}

	public void setYBasicOrganization(YBasicOrganization YBasicOrganization) {
		this.YBasicOrganization = YBasicOrganization;
	}

	@Column(name = "FNumber", length = 36)
	public String getFnumber() {
		return this.fnumber;
	}

	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}

	@Column(name = "FInvoicesType", length = 36)
	public String getFinvoicesType() {
		return this.finvoicesType;
	}

	public void setFinvoicesType(String finvoicesType) {
		this.finvoicesType = finvoicesType;
	}

	@Column(name = "FBillState")
	public Integer getFbillState() {
		return this.fbillState;
	}

	public void setFbillState(Integer fbillState) {
		this.fbillState = fbillState;
	}

	@Column(name = "FFixedValue", length = 36)
	public String getFfixedValue() {
		return this.ffixedValue;
	}

	public void setFfixedValue(String ffixedValue) {
		this.ffixedValue = ffixedValue;
	}

	@Column(name = "FDateValue", length = 36)
	public String getFdateValue() {
		return this.fdateValue;
	}

	public void setFdateValue(String fdateValue) {
		this.fdateValue = fdateValue;
	}

	@Column(name = "FSerialNumber", length = 36)
	public String getFserialNumber() {
		return this.fserialNumber;
	}

	public void setFserialNumber(String fserialNumber) {
		this.fserialNumber = fserialNumber;
	}

	@Column(name = "FPreview", length = 300)
	public String getFpreview() {
		return this.fpreview;
	}

	public void setFpreview(String fpreview) {
		this.fpreview = fpreview;
	}

	@Column(name = "FCreaterID", length = 36)
	public String getFcreaterId() {
		return this.fcreaterId;
	}

	public void setFcreaterId(String fcreaterId) {
		this.fcreaterId = fcreaterId;
	}

	@Column(name = "FModifiedID", length = 36)
	public String getFmodifiedId() {
		return this.fmodifiedId;
	}

	public void setFmodifiedId(String fmodifiedId) {
		this.fmodifiedId = fmodifiedId;
	}

	@Column(name = "FLastModifiedID", length = 36)
	public String getFlastModifiedId() {
		return this.flastModifiedId;
	}

	public void setFlastModifiedId(String flastModifiedId) {
		this.flastModifiedId = flastModifiedId;
	}

	@Column(name = "FCreateTime", length = 19)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "FModifiedTime", length = 19)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getFmodifiedTime() {
		return this.fmodifiedTime;
	}

	public void setFmodifiedTime(Date fmodifiedTime) {
		this.fmodifiedTime = fmodifiedTime;
	}

	@Column(name = "FLastModifiedTime", length = 19)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getFlastModifiedTime() {
		return this.flastModifiedTime;
	}

	public void setFlastModifiedTime(Date flastModifiedTime) {
		this.flastModifiedTime = flastModifiedTime;
	}

	@Column(name = "Flag")
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}