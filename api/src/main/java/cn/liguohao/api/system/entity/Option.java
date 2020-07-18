package cn.liguohao.api.system.entity;

import javax.persistence.*;

/**
 * @ClassName: Option
 * @Description: 设置信息
 * @author: li-guohao
 * @date: 2020-7-18 1:08:21
 */
@Entity
@Table(name = "system_option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Long oid;                //主键ID，自增

    @Column(name = "option_name")
    private String optionName;      // 设置名称

    @Column(name = "option_value", columnDefinition="mediumtext")
    private String optionValue;     // 设置值
    
    @Column(name = "option_desc")
    private String optionDesc;     // 设置描述

    @Column(name = "option_category")
    private String optionCategory;  // 设置分类


    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionCategory() {
        return optionCategory;
    }

    public void setOptionCategory(String optionCategory) {
        this.optionCategory = optionCategory;
    }

	public String getOptionDesc() {
		return optionDesc;
	}

	public void setOptionDesc(String optionDesc) {
		this.optionDesc = optionDesc;
	}
    
}
