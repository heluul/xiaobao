package com.hqgj.xb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.hqgj.xb.bean.Staff;
import com.hqgj.xb.bean.SystemLog;
import com.hqgj.xb.bean.easyui.Grid;
import com.hqgj.xb.bean.easyui.Parameter;
import com.hqgj.xb.dao.SystemLogDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年8月4日 下午4:12:50
 */
@Repository
public class SystemLogDAOImpl implements SystemLogDAO {
	private static final Logger logger = Logger
			.getLogger(SystemLogDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public int writeLog(SystemLog log) {
		log.setId(UUID.randomUUID().toString());
		String sql = "insert into SystemLog(id,operateTime,username,operateType,message) values (:id,:operateTime,:username,:operateType,:message)";
		SqlParameterSource logParameterSource = new BeanPropertySqlParameterSource(
				log);
		return this.npJdbcTemplate.update(sql, logParameterSource);
	}

	@Override
	public Grid readLog(SystemLog systemLog, Parameter parameter) {
		logger.info(systemLog.getOperateType());
		String sql = "SELECT syslog.id id,syslog.operateTime operateTime,syslog.username username,syslog.operateType operateType,syslog.message message,opT.nameM operateName from SystemLog syslog "
				+ " LEFT OUTER JOIN OperateType opT on syslog.operateType=opT.code"
				+ " where DATE_FORMAT(syslog.operateTime,'%Y-%m-%d')=:startTime";

		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", systemLog.getOperateTime());

		if (!StringUtils.equals("qb", systemLog.getOperateType())) {
			sql += " and syslog.operateType=:opType";
			map.put("opType", systemLog.getOperateType());
		}

		final List<SystemLog> results = new ArrayList<SystemLog>();
		this.npJdbcTemplate.query(sql, map, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				SystemLog systemLog = new SystemLog();
				systemLog.setId("id");
				systemLog.setOperateTime(rs.getString("operateTime"));
				systemLog.setMessage(rs.getString("message"));
				systemLog.setOperateType(rs.getString("operateType"));
				systemLog.setUsername(rs.getString("username"));
				systemLog.setOperateName(rs.getString("operateName"));
				results.add(systemLog);
			}
		});

		logger.info("一共有" + results.size() + "条数据");
		logger.info("page:" + parameter.getPage() + ";rows:"
				+ parameter.getRows());
		Grid grid = new Grid();
		if ((int) parameter.getPage() > 0) {
			int page = (int) parameter.getPage();
			int rows = (int) parameter.getRows();
			int fromIndex = (page - 1) * rows;
			int toIndex = (results.size() <= page * rows && results.size() >= (page - 1)
					* rows) ? results.size() : page * rows;
			grid.setRows(results.subList(fromIndex, toIndex));
			grid.setTotal(results.size());
		} else {
			grid.setRows(results);
		}
		return grid;
	}

	// /读取登录类型数据字典
	public List<SystemLog> readOperateType() {
		String sql = "select code,nameM from OperateType";
		List<SystemLog> results = this.npJdbcTemplate.query(sql,
				new RowMapper<SystemLog>() {
					@Override
					public SystemLog mapRow(ResultSet rs, int index)
							throws SQLException {
						SystemLog systemLog = new SystemLog();
						systemLog.setOperateType(rs.getString("code"));
						systemLog.setOperateName(rs.getString("nameM"));
						return systemLog;
					}
				});

		SystemLog temp = new SystemLog();
		temp.setOperateType("qb");
		temp.setOperateName("--全部--");
		results.add(0, temp);
		return results;
	}
}
