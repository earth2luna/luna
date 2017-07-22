/**
 * copyright@www.laulyl.com
 */
package com.luna.dao.po;

import java.util.Date;
import java.io.Serializable;
/**
 * @author laulyl
 * @time 2017-07-22 17:35:18
 * @description create by laulyl project
 */
public class User implements Serializable {

	private Long id;// 主键
	private String pin;// 唯一标识用户名
	private String password;// 密码
	private Integer gender;// 性别
	private String mobile;// 联系方式
	private String email;// 邮箱地址
	private String blogLink;// 个人网址
	private String thumbnail;// 头像缩略图
	private String profession;// 职业
	private String company;// 目前就职公司
	private String position;// 职位
	private String university;// 毕业院校
	private Date usStartTime;// 在校开始时间
	private Date usEndTime;// 在校结束时间
	private Integer educationalBackground;// 学历编码
	private Long scored;// 得分
	private String siteEval;// 网站评价
	private String selfEval;// 自我评价
	private String appeal;// 目前诉求
	private Integer serviceStatus;// 服务状态
	private Long userView;// uv
	private Long pageView;// pv
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private Date firstLoginTime;// 第一次登陆时间
	private Date lastLoginTime;// 最后一次登陆时间
	private static final long serialVersionUID = 1500716118889L;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setBlogLink(String blogLink) {
		this.blogLink = blogLink;
	}

	public String getBlogLink() {
		return this.blogLink;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getProfession() {
		return this.profession;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompany() {
		return this.company;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPosition() {
		return this.position;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getUniversity() {
		return this.university;
	}

	public void setUsStartTime(Date usStartTime) {
		this.usStartTime = usStartTime;
	}

	public Date getUsStartTime() {
		return this.usStartTime;
	}

	public void setUsEndTime(Date usEndTime) {
		this.usEndTime = usEndTime;
	}

	public Date getUsEndTime() {
		return this.usEndTime;
	}

	public void setEducationalBackground(Integer educationalBackground) {
		this.educationalBackground = educationalBackground;
	}

	public Integer getEducationalBackground() {
		return this.educationalBackground;
	}

	public void setScored(Long scored) {
		this.scored = scored;
	}

	public Long getScored() {
		return this.scored;
	}

	public void setSiteEval(String siteEval) {
		this.siteEval = siteEval;
	}

	public String getSiteEval() {
		return this.siteEval;
	}

	public void setSelfEval(String selfEval) {
		this.selfEval = selfEval;
	}

	public String getSelfEval() {
		return this.selfEval;
	}

	public void setAppeal(String appeal) {
		this.appeal = appeal;
	}

	public String getAppeal() {
		return this.appeal;
	}

	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Integer getServiceStatus() {
		return this.serviceStatus;
	}

	public void setUserView(Long userView) {
		this.userView = userView;
	}

	public Long getUserView() {
		return this.userView;
	}

	public void setPageView(Long pageView) {
		this.pageView = pageView;
	}

	public Long getPageView() {
		return this.pageView;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setFirstLoginTime(Date firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}

	public Date getFirstLoginTime() {
		return this.firstLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}
}
