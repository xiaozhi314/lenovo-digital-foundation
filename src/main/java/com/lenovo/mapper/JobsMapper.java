package com.lenovo.mapper;


import com.lenovo.pojo.Job;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

@Mapper
public interface JobsMapper {
	
//	 @Select("SELECT job_name,source_table,target_table,start_time,end_time,source_row_number,target_row_number "
//	 		+ "FROM talend_logs_db_to_ludp_tst")
//	    public List<Job> getJobsList();
	
	//结构化sql
    @SelectProvider(type = JobDaoProvider.class, method = "findJobByQuery")
    List<Job> FindAllByQuery(String s);

    class JobDaoProvider {
        public String findJobByQuery(String s) {
        	String sql = "SELECT job_name,source_table,target_table,start_time,end_time,source_row_number,"
            		+ "target_row_number FROM talend_logs_db_to_ludp_tst";
            if(s!=null){
                sql += " WHERE job_name LIKE '%"+s+"%' OR source_table LIKE '%"+s+"%' OR "
                		+ "target_table LIKE '%"+s+"%' OR start_time LIKE '%"+s+"%' OR end_time LIKE '%"+s+"%'"
                		+ "OR source_row_number LIKE '%"+s+"%' OR target_row_number LIKE '%"+s+"%'";
            }
            return sql;
        }
    }
	
}
