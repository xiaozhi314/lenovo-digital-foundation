package com.lenovo.mapper;


import com.lenovo.pojo.Job;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface JobsMapper {
	
	 @Select("SELECT job_name,source_table,target_table,start_time,end_time,source_row_number,target_row_number "
	 		+ "FROM talend_logs_db_to_ludp_tst")
	    public List<Job> getJobsList();
	
}
