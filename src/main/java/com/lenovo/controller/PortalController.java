package com.lenovo.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.mapper.InterfacesMapper;
import com.lenovo.pojo.Interface;
import com.lenovo.pojo.RtPageInfo;



@Controller
@RequestMapping("portal")
public class PortalController {
	@Resource
    private InterfacesMapper interfacesMapper;
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/interface")
	public String getInterfaces() {
		return "interface";
	}
	
	@ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public RtPageInfo query(
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            @RequestParam(required = false, name = "startIndex") Integer startIndex,
            @RequestParam(required = false, name = "pageIndex") Integer pageIndex,
            String query, String source, String target, String platform,
            HttpServletRequest req){
    	
    	String[] s = {"interface_id","interface_name","integration_platform","source_system_id","source_system_name","source_interface",
    			"source_interface_type","source_technical_interface","source_channel","target_system_id","target_system_name"
    			,"target_interface","target_interface_type","target_technical_interface","target_channel","mapping_id","mapping_name","last_modified_by"
    			,"last_modifyied_date"};
    	
        String OrderBy = "interface_id asc";
    	if(req.getParameter("order[column]")!=null&&req.getParameter("order[dir]")!=null)
        	 OrderBy= s[Integer.parseInt(req.getParameter("order[column]"))]+" "+req.getParameter("order[dir]");
    	
    	PageHelper.startPage(startIndex,pageSize,OrderBy);
        System.out.println("start:"+ startIndex + "size:"+ pageSize + "index:"+pageIndex);
        List<Interface> interfaceList = interfacesMapper.FindAllByQuery(query.trim(),source.trim(),target.trim(),platform.trim());
        PageInfo<Interface> page = new PageInfo<>(interfaceList);
        //返回DataTable使用
        RtPageInfo pageInfo = new RtPageInfo();
        pageInfo.setData(page.getList());//这里是数据内容 List
        pageInfo.setPageNum(startIndex/pageSize);//Integer
        pageInfo.setPageSize(pageSize);//pageSize
        pageInfo.setTotalCount(page.getTotal());//BigInteger
        
        return pageInfo ;
    }
	
}
