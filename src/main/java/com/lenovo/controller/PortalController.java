package com.lenovo.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.mapper.JobsMapper;
import com.lenovo.pojo.Job;
import com.lenovo.pojo.RtPageInfo;



@Controller
@RequestMapping("portal")
public class PortalController {
	@Resource
    private JobsMapper jobsMapper;
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/logs_db_to_ludp_tst")
	public String getJobs() {
		return "job";
	}
	
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public RtPageInfo query(
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            @RequestParam(required = false, name = "startIndex") Integer startIndex,
            @RequestParam(required = false, name = "pageIndex") Integer pageIndex,
            HttpServletRequest req){
    	
    	String[] s = {"job_name","source_table","target_table","start_time","end_time","source_row_number",
    			"target_row_number"};
        String OrderBy= s[Integer.parseInt(req.getParameter("order[column]"))]+" "+req.getParameter("order[dir]");
        
    	PageHelper.startPage(startIndex,pageSize,OrderBy);
        List<Job> jobList= jobsMapper.getJobsList();
        
        System.out.println("start:"+ startIndex + "size:"+ pageSize + "index:"+pageIndex);
        
        PageInfo<Job> page = new PageInfo<>(jobList);
        //返回DataTable使用
        RtPageInfo pageInfo = new RtPageInfo();
        pageInfo.setData(page.getList());//这里是数据内容 List
        pageInfo.setPageNum(startIndex/pageSize);//Integer
        pageInfo.setPageSize(pageSize);//pageSize
        pageInfo.setTotalCount(page.getTotal());//BigInteger
        
        System.out.println("------------------success------------------");
        return pageInfo ;
    }
	
}
