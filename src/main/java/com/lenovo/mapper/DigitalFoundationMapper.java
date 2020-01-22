package com.lenovo.mapper;


import com.lenovo.pojo.DigitalFoundation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface DigitalFoundationMapper {

    //结构化sql
    @SelectProvider(type = DfDaoProvider.class, method = "findDfByQuery")
    List<DigitalFoundation> FindAllByQuery(String s,String source, String target);

    class DfDaoProvider {
        public String findDfByQuery(String s,String source, String target) {
            String sql = "select * from public.v_interface Where 1=1";
            if (source != "") {
                sql += " AND source_system LIKE '%" + source.toUpperCase() + "%'";
            }
            if (target != "") {
                sql += " AND target_system LIKE '%" + target.toUpperCase() + "%'";
            }
            if (s != "" && s.contains("system=")){
                int x = s.indexOf("system=");
        			String related = s.substring(x+8);
        			sql+=" AND source_system LIKE '%" + related + "%' OR target_system LIKE '%"+related+"%'";
        			return sql;
            }
            else if (s != "") {
                sql += " AND interface_name LIKE '%" + s + "%' OR interface_name LIKE '%" + s + "%' OR "
                        + "source_interface LIKE '%" + s + "%' OR source_interface_type LIKE '%" + s + "%' OR source_system LIKE '%" + s + "%'"
                        + "OR target_interface LIKE '%" + s + "%' OR target_interface_type LIKE '%" + s + "%' OR target_system LIKE '%" + s + "%' "
                        + "OR integration_platform LIKE '%" + s + "%'";
            }

            return sql;
        }
    }

}
