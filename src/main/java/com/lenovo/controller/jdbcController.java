package com.lenovo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lenovo.entity.Job;

@Controller
@RequestMapping("/portal")
public class jdbcController {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/logs_db_to_ludp_tst")
	public String getJobList(ModelMap map) {
		String sql = "SELECT job_name,source_table,target_table,start_time,end_time,source_row_number,target_row_number "
				+ "FROM talend_logs_db_to_ludp_tst";
		List<Job> jobList = jdbcTemplate.query(sql, new RowMapper<Job>() {
			Job job = null;
			@Override
			public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
				job = new Job();
				job.setJob_name(rs.getString("job_name"));
				job.setSource_table(rs.getString("source_table"));
				job.setTarget_table(rs.getString("target_table"));
				job.setStart_time(rs.getDate("start_time"));
				job.setEnd_time(rs.getDate("end_time"));
				job.setSource_row_number(rs.getString("source_row_number"));
				job.setTarget_row_number(rs.getString("target_row_number"));
				
				return job;
			}});
//		for(Job job:jobList) {
//			System.out.println(job.getJob_name());
//		}
		map.addAttribute("jobs",jobList);
		return "job"; 
	}

}
