package com.lenovo.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Interface {
	private String interface_id;
	private String interface_name;
	private String integration_platform;
	private String source_system_id;
	private String source_system_name;
	private String source_interface;
	private String source_interface_type;
	private String source_technical_interface;
	private String source_channel;
	private String target_system_id;
	private String target_system_name;
	private String target_interface;
	private String target_interface_type;
	private String target_technical_interface;
	private String target_channel;
	private String mapping_id;
	private String mapping_name;
	private String last_modified_by;
	private Date last_modifyied_date;
	private String source_channel_detail;
	private String target_channel_detail;
	private String receiver_condition;
	private String mapping_condition;
	
	
	public String getSource_channel_detail() {
		return source_channel_detail;
	}
	public void setSource_channel_detail(String source_channel_detail) {
		this.source_channel_detail = source_channel_detail;
	}
	public String getTarget_channel_detail() {
		return target_channel_detail;
	}
	public void setTarget_channel_detail(String target_channel_detail) {
		this.target_channel_detail = target_channel_detail;
	}
	public String getReceiver_condition() {
		return receiver_condition;
	}
	public void setReceiver_condition(String receiver_condition) {
		this.receiver_condition = receiver_condition;
	}
	public String getMapping_condition() {
		return mapping_condition;
	}
	public void setMapping_condition(String mapping_condition) {
		this.mapping_condition = mapping_condition;
	}
	SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getInterface_id() {
		return interface_id;
	}
	public void setInterface_id(String interface_id) {
		this.interface_id = interface_id;
	}
	public String getInterface_name() {
		return interface_name;
	}
	public void setInterface_name(String interface_name) {
		this.interface_name = interface_name;
	}
	public String getIntegration_platform() {
		return integration_platform;
	}
	public void setIntegration_platform(String integration_platform) {
		this.integration_platform = integration_platform;
	}
	public String getSource_system_id() {
		return source_system_id;
	}
	public void setSource_system_id(String source_system_id) {
		this.source_system_id = source_system_id;
	}
	public String getSource_system_name() {
		return source_system_name;
	}
	public void setSource_system_name(String source_system_name) {
		this.source_system_name = source_system_name;
	}
	public String getSource_interface() {
		return source_interface;
	}
	public void setSource_interface(String source_interface) {
		this.source_interface = source_interface;
	}
	public String getSource_interface_type() {
		return source_interface_type;
	}
	public void setSource_interface_type(String source_interface_type) {
		this.source_interface_type = source_interface_type;
	}
	public String getSource_technical_interface() {
		return source_technical_interface;
	}
	public void setSource_technical_interface(String source_technical_interface) {
		this.source_technical_interface = source_technical_interface;
	}
	public String getSource_channel() {
		return source_channel;
	}
	public void setSource_channel(String source_channel) {
		this.source_channel = source_channel;
	}
	public String getTarget_system_id() {
		return target_system_id;
	}
	public void setTarget_system_id(String target_system_id) {
		this.target_system_id = target_system_id;
	}
	public String getTarget_system_name() {
		return target_system_name;
	}
	public void setTarget_system_name(String target_system_name) {
		this.target_system_name = target_system_name;
	}
	public String getTarget_interface() {
		return target_interface;
	}
	public void setTarget_interface(String target_interface) {
		this.target_interface = target_interface;
	}
	public String getTarget_interface_type() {
		return target_interface_type;
	}
	public void setTarget_interface_type(String target_interface_type) {
		this.target_interface_type = target_interface_type;
	}
	public String getTarget_technical_interface() {
		return target_technical_interface;
	}
	public void setTarget_technical_interface(String target_technical_interface) {
		this.target_technical_interface = target_technical_interface;
	}
	public String getTarget_channel() {
		return target_channel;
	}
	public void setTarget_channel(String target_channel) {
		this.target_channel = target_channel;
	}
	public String getMapping_id() {
		return mapping_id;
	}
	public void setMapping_id(String mapping_id) {
		this.mapping_id = mapping_id;
	}
	public String getMapping_name() {
		return mapping_name;
	}
	public void setMapping_name(String mapping_name) {
		this.mapping_name = mapping_name;
	}
	public String getLast_modified_by() {
		return last_modified_by;
	}
	public void setLast_modified_by(String last_modified_by) {
		this.last_modified_by = last_modified_by;
	}
	public String getLast_modifyied_date() {
		if(last_modifyied_date!=null)
			return bartDateFormat.format(last_modifyied_date);
		else
			return null;
	}
	public void setLast_modifyied_date(Date last_modifyied_date) {
		this.last_modifyied_date = last_modifyied_date;
	}
	
	
}
