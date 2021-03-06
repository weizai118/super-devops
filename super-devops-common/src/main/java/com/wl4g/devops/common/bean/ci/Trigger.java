package com.wl4g.devops.common.bean.ci;

import com.wl4g.devops.common.bean.BaseBean;

import java.io.Serializable;

public class Trigger extends BaseBean implements Serializable {

	private static final long serialVersionUID = 381411777614066880L;

	private String name;

	private Integer appClusterId;

	private Integer taskId;

	private Integer type;

	private String cron;

	private String sha;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getAppClusterId() {
		return appClusterId;
	}

	public void setAppClusterId(Integer appClusterId) {
		this.appClusterId = appClusterId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron == null ? null : cron.trim();
	}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha == null ? null : sha.trim();
	}

	@Override
	public String toString() {
		return "Trigger{" + "name='" + name + '\'' + ", appClusterId=" + appClusterId + ", taskId=" + taskId + ", type=" + type
				+ ", cron='" + cron + '\'' + ", sha='" + sha + '\'' + '}';
	}
}