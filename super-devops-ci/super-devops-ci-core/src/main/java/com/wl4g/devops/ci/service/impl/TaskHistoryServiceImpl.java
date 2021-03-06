/*
 * Copyright 2017 ~ 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wl4g.devops.ci.service.impl;

import com.wl4g.devops.ci.service.TaskHistoryService;
import com.wl4g.devops.common.bean.ci.Project;
import com.wl4g.devops.common.bean.ci.TaskHistory;
import com.wl4g.devops.common.bean.ci.TaskHistoryDetail;
import com.wl4g.devops.common.bean.share.AppCluster;
import com.wl4g.devops.common.bean.share.AppInstance;
import com.wl4g.devops.common.constants.CiDevOpsConstants;
import com.wl4g.devops.dao.ci.ProjectDao;
import com.wl4g.devops.dao.ci.TaskHistoryDao;
import com.wl4g.devops.dao.ci.TaskHistoryDetailDao;
import com.wl4g.devops.dao.scm.AppClusterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author vjay
 * @date 2019-05-17 11:44:00
 */
@Service
public class TaskHistoryServiceImpl implements TaskHistoryService {

	@Autowired
	private TaskHistoryDao taskHistoryDao;
	@Autowired
	private TaskHistoryDetailDao taskHistoryDetailDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private AppClusterDao appClusterDao;

	@Override
	public List<TaskHistory> list(String groupName, String projectName, String branchName) {
		return taskHistoryDao.list(groupName, projectName, branchName);
	}

	@Override
	public List<TaskHistoryDetail> getDetailByTaskId(Integer id) {
		return taskHistoryDetailDao.getDetailByTaskId(id);
	}

	@Override
	public TaskHistory getById(Integer id) {
		TaskHistory taskHistory = taskHistoryDao.selectByPrimaryKey(id);
		Project project = projectDao.selectByPrimaryKey(taskHistory.getProjectId());
		if (null != project && null != project.getAppClusterId()) {
			AppCluster appCluster = appClusterDao.getAppGroup(project.getAppClusterId());
			if (null != appCluster) {
				taskHistory.setGroupName(appCluster.getName());
			}
		}
		return taskHistory;

	}

	@Override
	@Transactional
	public TaskHistory createTaskHistory(Project project, List<AppInstance> instances, int type, int status, String branchName,
			String sha, Integer refId, String preCommand, String postCommand, Integer tarType,Integer contactGroupId) {
		Assert.notNull(project, "not found project,please check che project config");
		TaskHistory taskHistory = new TaskHistory();
		taskHistory.preInsert();
		taskHistory.setType(type);
		taskHistory.setProjectId(project.getId());
		taskHistory.setStatus(status);
		taskHistory.setBranchName(branchName);
		taskHistory.setShaGit(sha);
		taskHistory.setRefId(refId);
		taskHistory.setPreCommand(preCommand);
		taskHistory.setPostCommand(postCommand);
		taskHistory.setTarType(tarType);
		taskHistory.setEnable(CiDevOpsConstants.TASK_ENABLE_STATUS);
		taskHistory.setContactGroupId(contactGroupId);
		taskHistoryDao.insertSelective(taskHistory);
		for (AppInstance instance : instances) {
			TaskHistoryDetail taskHistoryDetail = new TaskHistoryDetail();
			taskHistoryDetail.preInsert();
			taskHistoryDetail.setTaskId(taskHistory.getId());
			taskHistoryDetail.setInstanceId(instance.getId());
			taskHistoryDetail.setStatus(CiDevOpsConstants.TASK_STATUS_CREATE);
			taskHistoryDetailDao.insertSelective(taskHistoryDetail);
		}
		return taskHistory;
	}

	@Override
	public void updateStatus(int taskId, int status) {
		TaskHistory taskHistory = new TaskHistory();
		taskHistory.preUpdate();
		taskHistory.setId(taskId);
		taskHistory.setStatus(status);
		taskHistoryDao.updateByPrimaryKeySelective(taskHistory);
	}

	@Override
	public void updateStatusAndResult(int taskId, int status, String result) {
		TaskHistory taskHistory = new TaskHistory();
		taskHistory.preUpdate();
		taskHistory.setId(taskId);
		taskHistory.setStatus(status);
		taskHistory.setResult(result);
		taskHistoryDao.updateByPrimaryKeySelective(taskHistory);
	}

	@Override
	public void updateStatusAndResultAndSha(int taskId, int status, String result, String sha, String md5) {
		TaskHistory taskHistory = new TaskHistory();
		taskHistory.preUpdate();
		taskHistory.setId(taskId);
		taskHistory.setStatus(status);
		taskHistory.setResult(result);
		taskHistory.setShaGit(sha);
		taskHistory.setShaLocal(md5);
		taskHistoryDao.updateByPrimaryKeySelective(taskHistory);
	}

	@Override
	public void updateDetailStatusAndResult(int taskDetailId, int status, String result) {
		TaskHistoryDetail taskHistoryDetail = new TaskHistoryDetail();
		taskHistoryDetail.preUpdate();
		taskHistoryDetail.setId(taskDetailId);
		taskHistoryDetail.setStatus(status);
		taskHistoryDetail.setResult(result);
		taskHistoryDetailDao.updateByPrimaryKeySelective(taskHistoryDetail);
	}

}