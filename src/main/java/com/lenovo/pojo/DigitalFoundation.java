package com.lenovo.pojo;

public class DigitalFoundation {
    private String interface_id;
    private String interface_name;
    private String s_interface_obj_id;
    private String t_interface_obj_id;
    private String f_interface_id;
    private String source_interface;
    private String source_interface_type;
    private String source_system_id;
    private String source_system;
    private String target_interface;
    private String target_interface_type;
    private String target_system_id;
    private String target_system;
    private String integration_platform;
    private String scenario_id;
    private String scenario_name;
    private String remark;

    public DigitalFoundation(String interface_id, String interface_name, String s_interface_obj_id,
                             String t_interface_obj_id, String f_interface_id, String source_interface,
                             String source_interface_type, String source_system_id, String source_system,
                             String target_interface, String target_interface_type, String target_system_id,
                             String target_system, String integration_platform, String scenario_id,
                             String scenario_name, String remark) {
        this.interface_id = interface_id;
        this.interface_name = interface_name;
        this.s_interface_obj_id = s_interface_obj_id;
        this.t_interface_obj_id = t_interface_obj_id;
        this.f_interface_id = f_interface_id;
        this.source_interface = source_interface;
        this.source_interface_type = source_interface_type;
        this.source_system_id = source_system_id;
        this.source_system = source_system;
        this.target_interface = target_interface;
        this.target_interface_type = target_interface_type;
        this.target_system_id = target_system_id;
        this.target_system = target_system;
        this.integration_platform = integration_platform;
        this.scenario_id = scenario_id;
        this.scenario_name = scenario_name;
        this.remark = remark;
    }
    public DigitalFoundation(){

    }

    public String getS_interface_obj_id() {
        return s_interface_obj_id;
    }

    public void setS_interface_obj_id(String s_interface_obj_id) {
        this.s_interface_obj_id = s_interface_obj_id;
    }


    public String getT_interface_obj_id() {
        return t_interface_obj_id;
    }

    public void setT_interface_obj_id(String t_interface_obj_id) {
        this.t_interface_obj_id = t_interface_obj_id;
    }

    public String getF_interface_id() {
        return f_interface_id;
    }

    public void setF_interface_id(String f_interface_id) {
        this.f_interface_id = f_interface_id;
    }

    public String getSource_system_id() {
        return source_system_id;
    }

    public void setSource_system_id(String source_system_id) {
        this.source_system_id = source_system_id;
    }

    public String getTarget_system_id() {
        return target_system_id;
    }

    public void setTarget_system_id(String target_system_id) {
        this.target_system_id = target_system_id;
    }

    public String getScenario_id() {
        return scenario_id;
    }

    public void setScenario_id(String scenario_id) {
        this.scenario_id = scenario_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScenario_name() {
        return scenario_name;
    }

    public void setScenario_name(String scenario_name) {
        this.scenario_name = scenario_name;
    }

    public String getIntegration_platform() {
        return integration_platform;
    }

    public void setIntegration_platform(String integration_platform) {
        this.integration_platform = integration_platform;
    }

    public String getInterface_id() {
        return interface_id;
    }

    public void setInterface_id(String interface_id) {
        this.interface_id = interface_id;
    }

    public String getInterface_name() {
        return interface_name;
    }

    public void setInterface_name(String interface_name) {
        this.interface_name = interface_name;
    }

    public String getSource_interface() {
        return source_interface;
    }

    public void setSource_interface(String source_interface) {
        this.source_interface = source_interface;
    }

    public String getSource_interface_type() {
        return source_interface_type;
    }

    public void setSource_interface_type(String source_interface_type) {
        this.source_interface_type = source_interface_type;
    }

    public String getSource_system() {
        return source_system;
    }

    public void setSource_system(String source_system) {
        this.source_system = source_system;
    }

    public String getTarget_interface() {
        return target_interface;
    }

    public void setTarget_interface(String target_interface) {
        this.target_interface = target_interface;
    }

    public String getTarget_interface_type() {
        return target_interface_type;
    }

    public void setTarget_interface_type(String target_interface_type) {
        this.target_interface_type = target_interface_type;
    }

    public String getTarget_system() {
        return target_system;
    }

    public void setTarget_system(String target_system) {
        this.target_system = target_system;
    }
}
