package com.lenovo.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.mapper.DigitalFoundationMapper;
import com.lenovo.mapper.InterfacesMapper;
import com.lenovo.pojo.DigitalFoundation;
import com.lenovo.pojo.Interface;
import com.lenovo.pojo.RtPageInfo;
import it.uniroma1.dis.wsngroup.gexf4j.core.*;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.Attribute;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.viz.ColorImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.viz.PositionImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.viz.Color;
import it.uniroma1.dis.wsngroup.gexf4j.core.viz.Position;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Calendar;
import java.util.List;


@Controller
@RequestMapping("portal")
public class PortalController {
    @Resource
    private InterfacesMapper interfacesMapper;
    @Resource
    private DigitalFoundationMapper digitalfoundationMapper;


    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/interface")
    public String getInterfaces() {
        return "interface";
    }

    @RequestMapping("/df")
    public String getDf() {
        return "df";
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public RtPageInfo query(
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            @RequestParam(required = false, name = "startIndex") Integer startIndex,
            @RequestParam(required = false, name = "pageIndex") Integer pageIndex,
            String query, String source, String target, String platform,
            HttpServletRequest req) {

        String[] s = {"interface_id", "interface_name", "integration_platform", "source_system_id", "source_system_name", "source_interface",
                "source_interface_type", "source_technical_interface", "source_channel", "target_system_id", "target_system_name"
                , "target_interface", "target_interface_type", "target_technical_interface", "target_channel", "mapping_id", "mapping_name", "last_modified_by"
                , "last_modifyied_date"};

        String OrderBy = "interface_id asc";
        if (req.getParameter("order[column]") != null && req.getParameter("order[dir]") != null)
            OrderBy = s[Integer.parseInt(req.getParameter("order[column]"))] + " " + req.getParameter("order[dir]");

        PageHelper.startPage(pageIndex, pageSize, OrderBy);
        System.out.println("start:" + startIndex + "size:" + pageSize + "index:" + pageIndex);
        List<Interface> interfaceList = interfacesMapper.FindAllByQuery(query.trim(), source.trim(), target.trim(), platform.trim());
        PageInfo<Interface> page = new PageInfo<>(interfaceList);
        //返回DataTable使用
        RtPageInfo pageInfo = new RtPageInfo();
        pageInfo.setData(page.getList());//这里是数据内容 List
        pageInfo.setPageNum(startIndex / pageSize);//Integer
        pageInfo.setPageSize(pageSize);//pageSize
        pageInfo.setTotalCount(page.getTotal());//BigInteger

        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/dfquery", method = RequestMethod.GET)
    public RtPageInfo dfquery(
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            @RequestParam(required = false, name = "startIndex") Integer startIndex,
            @RequestParam(required = false, name = "pageIndex") Integer pageIndex,
            String query, String source, String target,
            HttpServletRequest req) {
        String[] s = {"interface_id", "interface_name", "source_interface", "source_interface_type", "source_system", "target_interface",
                "target_interface_type", "target_system"};

        String OrderBy = "interface_id asc";
        if (req.getParameter("order[column]") != null && req.getParameter("order[dir]") != null)
            OrderBy = s[Integer.parseInt(req.getParameter("order[column]"))] + " " + req.getParameter("order[dir]");

        PageHelper.startPage(pageIndex, pageSize,OrderBy);
        List<DigitalFoundation> dfList = digitalfoundationMapper.FindAllByQuery(query.trim(),source.trim(), target.trim());
        PageInfo<DigitalFoundation> page = new PageInfo<>(dfList);
        //返回DataTable使用
        RtPageInfo pageInfo = new RtPageInfo();
        pageInfo.setData(page.getList());//这里是数据内容 List
        pageInfo.setPageNum(startIndex / pageSize);//Integer
        pageInfo.setPageSize(pageSize);//pageSize
        pageInfo.setTotalCount(page.getTotal());//BigInteger

        generateGexf();
        System.out.println("GEXF generated");
        return pageInfo;
    }

    public void generateGexf(){
        Gexf gexf = new GexfImpl();
        Calendar date = Calendar.getInstance();
        gexf.getMetadata()
                .setLastModified(date.getTime())
                .setCreator("Gephi.org")
                .setDescription("A Web network");
        gexf.setVisualization(true);

        Graph graph = gexf.getGraph();
        graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);

        AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
        graph.getAttributeLists().add(attrList);

        Attribute attUrl = attrList.createAttribute("modularity_class", AttributeType.INTEGER, "Modularity Class");
        Node sys1 = graph.createNode("0");
        sys1
                .setLabel("ECC")
                .getAttributeValues()
                .addValue(attUrl, "3");
        Color color1 = new ColorImpl();
        color1.setR(235);
        color1.setG(81);
        color1.setB(72);
        Position position1 = new PositionImpl();
        position1.setX(266);
        position1.setY(300);
        position1.setZ(0);
        sys1.setColor(color1);
        sys1.setSize(30);
        sys1.setPosition(position1);

        Node sys2 = graph.createNode("1");
        sys2
                .setLabel("LUDP")
                .getAttributeValues()
                .addValue(attUrl, "3");
        Color color2 = new ColorImpl();
        color2.setR(100);
        color2.setG(81);
        color2.setB(72);
        Position position2 = new PositionImpl();
        position2.setX(189);
        position2.setY(-346);
        position2.setZ(0);
        sys2.setColor(color2);
        sys2.setSize(20);
        sys2.setPosition(position2);

        sys1.connectTo("0", sys2).setWeight(0.8f);

        StaxGraphWriter graphWriter = new StaxGraphWriter();

        try {
            String uploadDir= ResourceUtils.getURL("classpath:").getPath()+"/static/data/";
            File f = new File(uploadDir+"demo.gexf");
            Writer out;
            out =  new FileWriter(f, false);
            graphWriter.writeToStream(gexf, out, "UTF-8");
            System.out.println(f.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
