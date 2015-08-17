package com.hqgj.xb.service;

import com.hqgj.xb.bean.ClassS;
import com.hqgj.xb.bean.ClassTimePlan;
import com.hqgj.xb.bean.easyui.Grid;
import com.hqgj.xb.bean.easyui.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年8月13日 下午2:33:34
 */
public interface ClassSService {
	/**
	 * 添加班级
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年8月13日 下午2:39:41
	 * @return
	 */
	public int addClass(ClassS cla, ClassTimePlan classTimePlan);

	/**
	 * 根据条件查询班级信息
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年8月15日 下午2:18:04
	 * @param classS
	 * @return
	 */
	public Grid getClass(ClassS classS, Parameter parameter);

	/**
	 * 根据班级编码查询指定班级信息
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年8月17日 上午8:26:14
	 * @param classCode
	 * @return
	 */
	public ClassS getClassSByClassCode(String classCode);

	/**
	 * 根据班级编码删除班级
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年8月16日 上午9:30:00
	 * @param classCode
	 * @return
	 */
	public int deleteClass(String classCode);

	/**
	 * 修改指定班级的信息
	 * 
	 * @author 崔兴伟
	 * @datetime 2015年8月17日 上午11:55:26
	 * @param classS
	 * @return
	 */
	public int updateClass(ClassS cla, ClassTimePlan classTimePlan);
}
