package com.lenovo.mapper;


import com.lenovo.pojo.DigitalFoundation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface DigitalFoundationMapper {

    //结构化sql
    @SelectProvider(type = DfDaoProvider.class, method = "findDfByQuery")
    List<DigitalFoundation> FindAllByQuery(String s,String source, String target, String scenario);

    @SelectProvider(type = DfDaoProvider.class, method = "findSNByQuery")
    List<String> FindSNByQuery();

    @SelectProvider(type = DfDaoProvider.class, method = "findSystemByQuery")
    List<String> FindSystemByQuery();

    @SelectProvider(type = DfDaoProvider.class, method = "fn_affected_by_id")
    List<DigitalFoundation> FnAffectedById(String system_id, String interface_id);

    class DfDaoProvider {
        public String findDfByQuery(String s,String source, String target, String scenario) {
            String sql = "select * from public.v_interface_scenario Where 1=1";
            if (source != "") {
                sql += " AND source_system LIKE '%" + source.toUpperCase() + "%'";
            }
            if (target != "") {
                sql += " AND target_system LIKE '%" + target.toUpperCase() + "%'";
            }
            if (scenario != "") {
                sql += " AND scenario_name LIKE '%" + scenario +"%'";
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
                        + "OR integration_platform LIKE '%" + s + "%' OR scenario_name LIKE '%" + s + "%' OR remark LIKE '%" + s + "%'";
            }
            return sql;
        }
        public String findSNByQuery(){
            String sql = "select scenario_name from busness_scenario";
            return sql;
        }
        public String findSystemByQuery(){
            String sql = "select system_name from system";
            return sql;
        }
        public String fn_affected_by_id(String system_id, String interface_id){
//            String sql = "select * from fn_affected_by_systemid("+system_id+")";
//            String sql = "select * from fn_affected_by_systemid(271)";
              String sql = "select * from fn_affected_by_interfaceid(4852)";
//            String sql = "select * from public.v_interface_scenario Where 1=1";
            return sql;
        }

    }

}
