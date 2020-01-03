package com.lenovo.mapper;


import com.lenovo.pojo.Interface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface InterfacesMapper {

    //结构化sql
    @SelectProvider(type = InterfaceDaoProvider.class, method = "findInterfaceByQuery")
    List<Interface> FindAllByQuery(String s, String source, String target, String platform);

    class InterfaceDaoProvider {
        public String findInterfaceByQuery(String s, String source, String target, String platform) {
            String sql = "select * from public.interface Where 1=1";
            //	String temp = s.toLowerCase().replaceAll(" ", "");
            System.out.println("query: " + s + " source: " + source + " target: " + target + " platform: " + platform);
//        	System.out.println(platform.equals(""));
            if (source != "") {
                sql += " AND source_system_name LIKE '%" + source + "%'";
            }
            if (target != "") {
                sql += " AND target_system_name LIKE '%" + target + "%'";
            }
            if (platform != "") {
                sql += " AND integration_platform LIKE '%" + platform + "%'";
                return sql;
            }
            if (s != "") {
                sql += " AND interface_name LIKE '%" + s + "%' OR integration_platform LIKE '%" + s + "%' OR "
                        + "source_system_name LIKE '%" + s + "%' OR source_interface LIKE '%" + s + "%' OR source_interface_type LIKE '%" + s + "%'"
                        + "OR source_technical_interface LIKE '%" + s + "%' OR source_channel LIKE '%" + s + "%' OR target_system_name LIKE '%" + s + "%' "
                        + "OR target_interface LIKE '%" + s + "%' OR target_interface_type LIKE '%" + s + "%' OR target_technical_interface LIKE '%" + s + "%' "
                        + "OR target_channel LIKE '%" + s + "%' OR mapping_name LIKE '%" + s + "%' OR last_modified_by LIKE '%" + s + "%' OR source_channel_detail LIKE '%" + s + "%' "
                        + "OR target_channel_detail LIKE '%" + s + "%' OR receiver_condition LIKE '%" + s + "%' OR mapping_condition LIKE '%" + s + "%'";
//    					+ "OR last_modifyied_date LIKE '%"+s+"%'";
            }
            return sql;

//        	if(s != null) {
//        		if(temp.contains("source=")&&temp.contains("target=")) {
//        			System.out.println("进入");
////        			判断source和target位置 
//        			int x = temp.indexOf("source=");
//        			int y = temp.indexOf("target=");
//        			String source = x<y?temp.substring(x+7, y):temp.substring(x+7);
//        			String target = x<y?temp.substring(y+7):temp.substring(y+7, x);
//        			source = source.toUpperCase();
//        			target = target.toUpperCase();
//        			System.out.println("source="+source);
//        			System.out.println("target="+target);
//        			sql+= " WHERE source_system_name LIKE '%"+source+"%' AND target_system_name LIKE '%"+target+"%'";
//        			return sql;
//        		}
//        		else if(temp.contains("source=")) {
//        			int x = temp.indexOf("source=");
//        			String source = temp.substring(x+7).toUpperCase();
//        			System.out.println("source="+source);
//        			sql+=" WHERE source_system_name LIKE '%"+source+"%'";
//        			return sql;
//        		}
//        		else if(temp.contains("target=")) {
//        			int y = temp.indexOf("target=");
//        			String target = temp.substring(y+7).toUpperCase();
//        			System.out.println(target);
//        			sql+=" WHERE target_system_name LIKE '%"+target+"%'";
//        			return sql;
//        		}
//        }
        }
    }

}
