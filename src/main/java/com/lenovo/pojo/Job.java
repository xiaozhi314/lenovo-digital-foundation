package com.lenovo.pojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Job {
	
	private String job_name;
	private String source_table;
	private String target_table;
	private Date start_time;
	private Date end_time;
	private String source_row_number;
	private String target_row_number;
	
	SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getSource_table() {
		return source_table;
	}
	public void setSource_table(String source_table) {
		this.source_table = source_table;
	}
	public String getTarget_table() {
		return target_table;
	}
	public void setTarget_table(String target_table) {
		this.target_table = target_table;
	}

//	public Date getStart_time() {
//		return start_time;
//	}
//	public Date getEnd_time() {
//		return end_time;
//	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public String getStart_time() {
		if(start_time!=null)
			return bartDateFormat.format(start_time);
		else
			return null;
	}
	public String getEnd_time() {
		if(end_time!=null)
			return bartDateFormat.format(end_time);
		else
			return null;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getSource_row_number() {
		return source_row_number;
	}
	public void setSource_row_number(String source_row_number) {
		this.source_row_number = source_row_number;
	}
	public String getTarget_row_number() {
		return target_row_number;
	}
	public void setTarget_row_number(String target_row_number) {
		this.target_row_number = target_row_number;
	}
	
	

}
