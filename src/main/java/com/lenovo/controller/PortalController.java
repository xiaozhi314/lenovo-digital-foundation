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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;


@Controller
@RequestMapping("portal")
public class PortalController {
    private static final Logger log = LoggerFactory.getLogger(PortalController.class);
    @Resource
    private InterfacesMapper interfacesMapper;
    @Resource
    private DigitalFoundationMapper digitalfoundationMapper;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pw;


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

    @RequestMapping("/influence")
    public String getInfluence () {
        return "influence";
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
            String query, String source, String target, String scenario,
            HttpServletRequest req) {
        String[] s = {"interface_id", "interface_name", "source_interface", "source_interface_type", "source_system", "target_interface",
                "target_interface_type", "target_system","integration_platform","scenario_name","remark"};

        String OrderBy = "interface_id asc";
        if (req.getParameter("order[column]") != null && req.getParameter("order[dir]") != null)
            OrderBy = s[Integer.parseInt(req.getParameter("order[column]"))] + " " + req.getParameter("order[dir]");

        PageHelper.startPage(pageIndex, pageSize,OrderBy);
        List<DigitalFoundation> dfList = digitalfoundationMapper.FindAllByQuery(query.trim(),source.trim(), target.trim(),scenario.trim());
        PageInfo<DigitalFoundation> page = new PageInfo<>(dfList);
        //返回DataTable使用
        RtPageInfo pageInfo = new RtPageInfo();
        pageInfo.setData(page.getList());//这里是数据内容 List
        pageInfo.setPageNum(startIndex / pageSize);//Integer
        pageInfo.setPageSize(pageSize);//pageSize
        pageInfo.setTotalCount(page.getTotal());//BigInteger

        generateGexf(query,source,target,scenario);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/influencequery", method = RequestMethod.GET)
    public RtPageInfo influencequery(
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            @RequestParam(required = false, name = "startIndex") Integer startIndex,
            @RequestParam(required = false, name = "pageIndex") Integer pageIndex,
            String system_id, String interface_id, HttpServletRequest req) {

        List<DigitalFoundation> fnAffectedList = new ArrayList<>() ;
        try (Connection conn = DriverManager.getConnection(dbUrl,username,pw);
             Statement statement = conn.createStatement();
             CallableStatement proc = conn.prepareCall("{ ? = call fn_affected_by_systemid( ? ) }")) {
            conn.setAutoCommit(false);
            // create or replace stored function
//            proc.execute();
            //----------------------------------
            // output
            proc.registerOutParameter(1, Types.REF_CURSOR);
            log.warn("LMAO))))System ID: "+system_id);
            // input
            proc.setInt(2,204);
            proc.execute();
            // Get result
            ResultSet rs = (ResultSet) proc.getObject(1);

            ResultSetMetaData md = rs.getMetaData();//获取键名
            int columnCount = md.getColumnCount();//获取行的数量
            while (rs.next()) {

                for (int i = 1; i <= columnCount/17; i++) {
                    for(int j=1;j<=17;j++){
                        DigitalFoundation df = new DigitalFoundation();
                        df.setInterface_id(rs.getString(j));
                    }
                    String columnValue = rs.getString(i);

//                    if("target_interface".equals(md.getColumnName(i))){
//
//                        DigitalFoundation DF = new DigitalFoundation();
//                        DF.setTarget_interface(rs.getObject(i)+"");
////                        System.out.println(rs.getObject(i)+"");
//                        fnAffectedList.add(DF);
//                    }
//                    if("target_interface".equals(md.getColumnName(i))){
//                        DigitalFoundation DF = new DigitalFoundation();
//                        DF.setTarget_interface(rs.getObject(i)+"");
//                    }
//                    if("integration_platform".equals(md.getColumnName(i))){
//                        DigitalFoundation DF = new DigitalFoundation();
//                        DF.setIntegration_platform(rs.getObject(i)+"");
//                        fnAffectedList.add(DF);
//                    }
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        CallableStatement proc = null;
//        try {
//            sql4StoresProc = "{ ? = call fn_affected_by_systemid("+"337"+") }";
//            proc = conn.prepareCall(sql4StoresProc);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            proc.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
////            ResultSet rs = (ResultSet) proc.getObject(1);
//            ResultSet rs = proc.executeQuery();
//            while(rs.next()){
//                String id = rs.getString(1);
//                System.out.println("id"+id);
//            }
//            ResultSetMetaData md = rs.getMetaData();
//            int columnCount = md.getColumnCount();
//            System.out.println("columnCount:"+ columnCount);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        PageHelper.startPage(pageIndex, pageSize);
//        List<DigitalFoundation> fnAffectedList = digitalfoundationMapper.FnAffectedById(system_id,interface_id);
        PageInfo<DigitalFoundation> page = new PageInfo<>(fnAffectedList);
        //返回DataTable使用
        RtPageInfo pageInfo = new RtPageInfo();
        pageInfo.setData(page.getList());//这里是数据内容 List
        pageInfo.setPageNum(startIndex / pageSize);//Integer
        pageInfo.setPageSize(pageSize);//pageSize
        pageInfo.setTotalCount(page.getTotal());//BigInteger

        return pageInfo;
    }


    public void generateGexf(String query,String source,String target,String scenario){
        List<DigitalFoundation> echartDF = digitalfoundationMapper.FindAllByQuery(query.trim(),source.trim(),target.trim(),scenario.trim());
        System.out.println("echart行数共有:" + echartDF.size());
        Map<String, Integer> map = new HashMap<>();
        for(int i=0;i<echartDF.size();i++){
            DigitalFoundation row = echartDF.get(i);
            if(map.get(row.getSource_system())==null)
                map.put(row.getSource_system(),1);
            else
                map.put(row.getSource_system(), map.get(row.getSource_system())+1);
            if(map.get(row.getTarget_system())==null)
                map.put(row.getTarget_system(),1);
            else
                map.put(row.getTarget_system(), map.get(row.getTarget_system())+1);
        }

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

        Iterator iter = map.entrySet().iterator();
        Map<Integer, Node> nodeID = new HashMap();//存Node和ID
        Map<Integer, String > nodeName = new HashMap();//存Node名字和ID

        for(int i = 0;i<map.size();i++){
            Node[] node = new Node[map.size()];
            node[i] = graph.createNode(String.valueOf(i));
            if(i==map.size()-1){
                    System.out.println("节点个数共有:"+ i);
            }
            Map.Entry entry = (Map.Entry) iter.next();
            node[i]
                    .setLabel((String)entry.getKey())
                    .getAttributeValues()
                    .addValue(attUrl,"1");
            Color color = new ColorImpl();
            Random r = new Random();
            color.setR(r.nextInt(255));
            color.setG(r.nextInt(255));
            color.setB(r.nextInt(255));
            Position position = new PositionImpl();
            position.setX(r.nextInt(3500));
            position.setY(r.nextInt(1300));
            position.setZ(0);
            node[i].setColor(color);
            node[i].setSize( (float) Math.sqrt(map.get((String) entry.getKey()))+7);
//            node[i].setSize(map.get((String) entry.getKey()));

            if ("LUDP".equals(entry.getKey())){
                System.out.println("LUDP "+entry.getKey());
                Position positionLUDP = new PositionImpl();
                positionLUDP.setX(450);
                positionLUDP.setY(250);
                positionLUDP.setZ(0);
                node[i].setPosition(positionLUDP);
            }else{
                node[i].setPosition(position);
            }
            nodeID.put(i,node[i]);
            nodeName.put(i, (String)entry.getKey());
        }

        //Talend的节点，平台节点需不需要？
//        Node nodeTalend = graph.createNode(String.valueOf(map.size()));
//        nodeTalend
//                .setLabel("Talend")
//                .getAttributeValues()
//                .addValue(attUrl,"1");
//        nodeTalend.setColor(new ColorImpl(255,255,255));
//        nodeTalend.setSize(50);
//        nodeTalend.setPosition(new PositionImpl(700,250,0));
        int counterLines = 0;
        for (int i = 0; i < nodeID.size(); i++) {
            for (int j = 0; j < echartDF.size(); j++) {
                DigitalFoundation row = echartDF.get(j);
                if (row.getSource_system().equals(nodeName.get(i))) {
                    for (int k = 0; k < nodeID.size(); k++) {
                        if(nodeName.get(k).equals(row.getTarget_system())){
                            try {
                                //nodeID.get(k).connectTo(String.valueOf(i), nodeID.get(i)).setWeight(0.8f);
                                //target connect to source
                                counterLines++;
                                nodeID.get(i).connectTo(String.valueOf(k), nodeID.get(k)).setThickness(0.3f);
                                //source connect to target
//                                nodeID.get(i).connectTo(String.valueOf(map.size()), nodeTalend).setWeight(0.8f).setThickness(0.5f);
//                                nodeTalend.connectTo(String.valueOf(k), nodeID.get(k)).setThickness(4f);
                            } catch (IllegalArgumentException e) {
                                //System.out.println(e);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("counterLines: "+ counterLines);
        //写Gexf文件
        StaxGraphWriter graphWriter = new StaxGraphWriter();
        try {
            String uploadDir = ResourceUtils.getURL("classpath:").getPath()+"/static/data/";
//            File gexfFile = new File(uploadDir+"demo.gexf");
            File gexfFile = new File("D://demo.gexf");
            Writer out;
            out =  new FileWriter(gexfFile, false);
            graphWriter.writeToStream(gexf, out, "UTF-8");
            System.out.println(gexfFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryScenario", method = RequestMethod.POST)
    public List<String> selectScenario(HttpServletRequest request, HttpServletResponse response){
        List<String> scenario = digitalfoundationMapper.FindSNByQuery();
        scenario.add(0,"");
        return scenario;
    }

    @ResponseBody
    @RequestMapping(value = "/querySystem", method = RequestMethod.POST)
    public List<String> selectSystem(HttpServletRequest request, HttpServletResponse response){
        List<String> system = digitalfoundationMapper.FindSystemByQuery();
        system.add(0,"");
        return system;
    }


}
