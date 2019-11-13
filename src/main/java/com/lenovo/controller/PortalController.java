package com.lenovo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.mapper.JobsMapper;
import com.lenovo.pojo.Job;


@Controller
@RequestMapping("portal")
public class PortalController {
	@Resource
    private JobsMapper jobsMapper;
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	//遍历
    @RequestMapping({"/","logs_db_to_ludp_tst"})
    public String listJob(@RequestParam(value = "name",defaultValue = "") String name,
                           Model model, @RequestParam(value = "start", defaultValue = "1") int start,
                           @RequestParam(value = "size", defaultValue = "100") int size) throws Exception {
        PageHelper.startPage(start, size, "job_name asc");
        List<Job> jobList = jobsMapper.getJobsList(name);
        PageInfo<Job> page = new PageInfo<>(jobList);
        model.addAttribute("pages", page);
        model.addAttribute("name", name);
        return "job";
    }
}
