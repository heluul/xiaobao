package com.hqgj.xb.dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.hqgj.xb.bean.highcharts.Chart;
import com.hqgj.xb.bean.highcharts.Data;
import com.hqgj.xb.bean.highcharts.DiagramCharts;
import com.hqgj.xb.bean.highcharts.DiagramSeries;
import com.hqgj.xb.bean.highcharts.Options3d;
import com.hqgj.xb.bean.highcharts.Series;
import com.hqgj.xb.bean.highcharts.Title;
import com.hqgj.xb.bean.highcharts.Charts;
import com.hqgj.xb.bean.highcharts.mixedcharts.MixedCharts;
import com.hqgj.xb.bean.highcharts.mixedcharts.MixedSei;
import com.hqgj.xb.bean.highcharts.mixedcharts.MixedSeries;
import com.hqgj.xb.bean.highcharts.mixedcharts.MixedxAxis;
import com.hqgj.xb.dao.MarketStatisticsDAO;

/**
 * @author 鲁宗豪
 * @datetime 2015年8月17日 下午4:48:26
 */
@Repository
public class MarketStatisticsDAOImpl implements MarketStatisticsDAO {
	private static final Logger logger = Logger
			.getLogger(ResourceDaoImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	@Override
	public Charts getXiaoQuZiXunLiang(String starttime,String endtime) {
		String sql = "select School.schoolName schoolName,count(School.schoolName) countNum from Consult LEFT OUTER JOIN School on Consult.handleSchoolCode=School.schoolCode "
				+ "where Consult.consultDate between :starttime and :endtime group by School.schoolName";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );

		Charts charts = new Charts();
		//设置标题
		Title title=new Title();
		title.setText("校区咨询量统计");
		charts.setTitle(title);

		//设置Series
		Series series=new Series();
		series.setName("校区咨询量");
	
		//设置3D的显示
		Chart chart=new Chart();
		chart.setMargin(75);
		chart.setType("column");
		Options3d options3d=new Options3d();
		options3d.setAlpha(10);
		options3d.setBeta(25);
		options3d.setDepth(70);
		options3d.setEnabled(true);
		chart.setOptions3d(options3d);
		charts.setChart(chart);
		
		final List<Data> results = new ArrayList<Data>();
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Data data =new Data();
					
						data.setName(rs.getString("schoolName"));
						if(StringUtils.isNotBlank(rs.getString("countNum"))){
							data.setY(Float.parseFloat(rs.getString("countNum")));
						}else{
							data.setY(0);
						}
						results.add(data);
					}
				});
		logger.info(results );
		series.setData(results);
		charts.setSeries(series);

		return charts;
	}
	@Override
	public Charts getZiXunLaiYuan(String starttime,String endtime) {
		String sql = "select DSellSource.nameM nameM,count(Consult.sellSource) countNum from DSellSource left join Consult on DSellSource.id=Consult.sellSource "
				+ "where Consult.consultDate between :starttime and :endtime group by nameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );
		Charts charts = new Charts();
		//设置标题
		Title title=new Title();
		title.setText("咨询来源统计");
		charts.setTitle(title);

		//设置Series
		Series series=new Series();
		series.setName("咨询来源统计");
	
		final List<Data> results = new ArrayList<Data>();
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Data data =new Data();
					
						data.setName(rs.getString("nameM"));
						if(StringUtils.isNotBlank(rs.getString("countNum"))){
							data.setY(Float.parseFloat(rs.getString("countNum")));
						}else{
							data.setY(0);
						}
						results.add(data);
					}
				});
		logger.info(results );
		series.setData(results);
		charts.setSeries(series);

		return charts;
	}
	@Override
	public Charts getZiXunXiaoShouYuan(String starttime,String endtime) {
		String sql = "select DSeller.nameM nameM,count(Consult.seller) countNum from DSeller left join Consult on DSeller.id=Consult.seller "
				+ "where Consult.consultDate between :starttime and :endtime group by nameM ";
			
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );
		Charts charts = new Charts();
		//设置标题
		Title title=new Title();
		title.setText("咨询销售员");
		charts.setTitle(title);

		//设置Series
		Series series=new Series();
		series.setName("咨询销售员");
	
		final List<Data> results = new ArrayList<Data>();
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Data data =new Data();
					
						data.setName(rs.getString("nameM"));
						if(StringUtils.isNotBlank(rs.getString("countNum"))){
							data.setY(Float.parseFloat(rs.getString("countNum")));
						}else{
							data.setY(0);
						}
						results.add(data);
					}
				});
		logger.info(results );
		series.setData(results);
		charts.setSeries(series);

		return charts;
	}


	@Override
	public Charts getBaoMingLaiYuan(String starttime, String endtime) {
		String sql = "select DSellSource.nameM nameM,count(DSellSource.id) countNum from DSellSource left join StudentClass on DSellSource.id=StudentClass.sellSourceCode "
				+ "where StudentClass.enrollDate between :starttime and :endtime group by nameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );
		Charts charts = new Charts();
		//设置标题
		Title title=new Title();
		title.setText("报名来源");
		charts.setTitle(title);

		//设置Series
		Series series=new Series();
		series.setName("报名来源");
	
		final List<Data> results = new ArrayList<Data>();
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Data data =new Data();
					
						data.setName(rs.getString("nameM"));
						if(StringUtils.isNotBlank(rs.getString("countNum"))){
							data.setY(Float.parseFloat(rs.getString("countNum")));
						}else{
							data.setY(0);
						}
						results.add(data);
					}
				});
		logger.info(results );
		series.setData(results);
		charts.setSeries(series);

		return charts;
	}

	@Override
	public Charts getBaoMingXiaoShouYuan(String starttime, String endtime) {
		String sql = "select DSeller.nameM nameM,count(DSeller.id) countNum from DSeller left join StudentClass on DSeller.id=StudentClass.sellerCode "
				+ "where StudentClass.enrollDate between :starttime and :endtime group by nameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );
		Charts charts = new Charts();
		//设置标题
		Title title=new Title();
		title.setText("报名销售员");
		charts.setTitle(title);

		//设置Series
		Series series=new Series();
		series.setName("报名销售员");
	
		final List<Data> results = new ArrayList<Data>();
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Data data =new Data();
					
						data.setName(rs.getString("nameM"));
						if(StringUtils.isNotBlank(rs.getString("countNum"))){
							data.setY(Float.parseFloat(rs.getString("countNum")));
						}else{
							data.setY(0);
						}
						results.add(data);
					}
				});
		logger.info(results );
		series.setData(results);
		charts.setSeries(series);

		return charts;
	}


	@Override
	public Charts getGongLiXueXiao(String starttime, String endtime) {
		String sql = "select DCouncilSchool.nameM nameM,count(DCouncilSchool.id) countNum from DCouncilSchool left join Consult on DCouncilSchool.id=Consult.councilSchoolCode "
				+ "where Consult.consultDate between :starttime and :endtime group by nameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );
		Charts charts = new Charts();
		//设置标题
		Title title=new Title();
		title.setText("公立学校");
		charts.setTitle(title);

		//设置Series
		Series series=new Series();
		series.setName("公立学校");
	
		final List<Data> results = new ArrayList<Data>();
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Data data =new Data();
					
						data.setName(rs.getString("nameM"));
						if(StringUtils.isNotBlank(rs.getString("countNum"))){
							data.setY(Float.parseFloat(rs.getString("countNum")));
						}else{
							data.setY(0);
						}
						results.add(data);
					}
				});
		logger.info(results );
		series.setData(results);
		charts.setSeries(series);

		return charts;
	}


	@Override
	public Charts getJuZhuQuYu(String starttime, String endtime) {
		String sql = "select Consult.liveArea nameM,count(Consult.liveArea) countNum from Consult "
				+ "where Consult.consultDate between :starttime and :endtime group by nameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );
		Charts charts = new Charts();
		//设置标题
		Title title=new Title();
		title.setText("居住区域咨询量统计");
		charts.setTitle(title);

		//设置Series
		Series series=new Series();
		series.setName("居住区域咨询量统计");
	
		final List<Data> results = new ArrayList<Data>();
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Data data =new Data();
					
						data.setName(rs.getString("nameM"));
						if(StringUtils.isNotBlank(rs.getString("countNum"))){
							data.setY(Float.parseFloat(rs.getString("countNum")));
						}else{
							data.setY(0);
						}
						results.add(data);
					}
				});
		logger.info(results );
		series.setData(results);
		charts.setSeries(series);

		return charts;
	}


	@Override
	public Charts getXueShengNianLing(String starttime, String endtime) {
		String sql = "select (date_format(from_days(to_days(now()) - to_days(birthday)),'%Y') + 0) as age,count(Consult.birthday) countNum from Consult "
				+ "where Consult.consultDate between :starttime and :endtime group by age ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );
		Charts charts = new Charts();
		//设置标题
		Title title=new Title();
		title.setText("咨询学生年龄统计");
		charts.setTitle(title);

		//设置Series
		Series series=new Series();
		series.setName("咨询学生年龄统计");
	
		final List<Data> results = new ArrayList<Data>();
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Data data =new Data();
					
						data.setName(rs.getString("age"));
						if(StringUtils.isNotBlank(rs.getString("countNum"))){
							data.setY(Float.parseFloat(rs.getString("countNum")));
						}else{
							data.setY(0);
						}
						results.add(data);
					}
				});
	
		series.setData(results);
		charts.setSeries(series);

		return charts;
	}


	@Override
	public DiagramCharts getMeiYueXinSheng(String statisticalYear) {
		String consultSql = "select count((case when DATE_FORMAT(consultDate,'%m')=1 then 1 else null end)) '一月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=2 then 1 else  null  end)) '二月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=3 then  0  else  null  end)) '三月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=4 then 1  else  null end)) '四月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=5 then 1 else null  end)) '五月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=6 then 1 else  null  end)) '六月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=7 then 1 else  null  end)) '七月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=8 then 1 else  null  end))'八月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=9 then 1 else  null  end)) '九月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=10 then 1 else  null  end)) '十月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=11 then 1 else  null  end)) '十一月',"
				+ " count((case when DATE_FORMAT(consultDate,'%m')=12 then 1 else  null  end)) '十二月' "
				+ " from DSellSource left join Consult on DSellSource.id=Consult.sellSource where DATE_FORMAT(consultDate,'%Y')=:statisticalYear ";
		
		String enrollSql="select count((case when DATE_FORMAT(enrollDate,'%y%m')=1 then 1 else null end)) '一月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=2 then 1 else null end)) '二月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=3 then 1 else null end)) '三月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=4 then 1 else null end)) '四月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=5 then 1 else null end)) '五月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=6 then 1 else null end)) '六月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=7 then 1 else null end)) '七月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=8 then 1 else null end)) '八月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=9 then 1 else null end)) '九月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=10 then 1 else null end)) '十月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=11 then 1 else null end)) '十一月',"
				+ "count((case when DATE_FORMAT(enrollDate,'%m')=12 then 1 else null end)) '十二月' "
				+ " from DSellSource left join StudentClass on DSellSource.id=StudentClass.sellSourceCode where DATE_FORMAT(enrollDate,'%Y')=:statisticalYear ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("statisticalYear",  statisticalYear );
		DiagramCharts diagramCharts=new DiagramCharts();
		//设置标题
		Title title=new Title();
		title.setText("每月新生统计");
		diagramCharts.setTitle(title);

		//设置List<DiagramSeries>
		final List<DiagramSeries> results=new ArrayList<DiagramSeries>();
		this.npJdbcTemplate.query(consultSql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						int mydata1,mydata2,mydata3,mydata4,mydata5,mydata6,mydata7,mydata8,mydata9,mydata10,mydata11,mydata12;
						mydata1=Integer.parseInt(rs.getString("一月"));
						mydata2=Integer.parseInt(rs.getString("二月"));
						mydata3=Integer.parseInt(rs.getString("三月"));
						mydata4=Integer.parseInt(rs.getString("四月"));
						mydata5=Integer.parseInt(rs.getString("五月"));
						mydata6=Integer.parseInt(rs.getString("六月"));
						mydata7=Integer.parseInt(rs.getString("七月"));
						mydata8=Integer.parseInt(rs.getString("八月"));
						mydata9=Integer.parseInt(rs.getString("九月"));
						mydata10=Integer.parseInt(rs.getString("十月"));
						mydata11=Integer.parseInt(rs.getString("十一月"));
						mydata12=Integer.parseInt(rs.getString("十二月"));
						
						
						DiagramSeries diagramSeries=new DiagramSeries();
						List<Integer> LData= new ArrayList<Integer>();
						LData.add(mydata1);
						LData.add(mydata2);
						LData.add(mydata3);
						LData.add(mydata4);
						LData.add(mydata5);
						LData.add(mydata6);
						LData.add(mydata7);
						LData.add(mydata8);
						LData.add(mydata9);
						LData.add(mydata10);
						LData.add(mydata11);
						LData.add(mydata12);
						diagramSeries.setData(LData);
						diagramSeries.setName("每月学生咨询量");
						
						results.add(diagramSeries);
					}
				});
		
		this.npJdbcTemplate.query(enrollSql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						int mydata1,mydata2,mydata3,mydata4,mydata5,mydata6,mydata7,mydata8,mydata9,mydata10,mydata11,mydata12;
						mydata1=Integer.parseInt(rs.getString("一月"));
						mydata2=Integer.parseInt(rs.getString("二月"));
						mydata3=Integer.parseInt(rs.getString("三月"));
						mydata4=Integer.parseInt(rs.getString("四月"));
						mydata5=Integer.parseInt(rs.getString("五月"));
						mydata6=Integer.parseInt(rs.getString("六月"));
						mydata7=Integer.parseInt(rs.getString("七月"));
						mydata8=Integer.parseInt(rs.getString("八月"));
						mydata9=Integer.parseInt(rs.getString("九月"));
						mydata10=Integer.parseInt(rs.getString("十月"));
						mydata11=Integer.parseInt(rs.getString("十一月"));
						mydata12=Integer.parseInt(rs.getString("十二月"));
						
						
						DiagramSeries diagramSeries=new DiagramSeries();
						List<Integer> LData= new ArrayList<Integer>();
						LData.add(mydata1);
						LData.add(mydata2);
						LData.add(mydata3);
						LData.add(mydata4);
						LData.add(mydata5);
						LData.add(mydata6);
						LData.add(mydata7);
						LData.add(mydata8);
						LData.add(mydata9);
						LData.add(mydata10);
						LData.add(mydata11);
						LData.add(mydata12);
						diagramSeries.setData(LData);
						diagramSeries.setName("每月新生报名量");
						
						results.add(diagramSeries);
					}
				});
		diagramCharts.setDiagramseries(results);
		return diagramCharts;
	}


	@Override
	public DiagramCharts getZiXunLaiYuanQuShi(String statisticalYear) {
		String sql ="select (case when DATE_FORMAT(consultDate,'%m')=1 then 1 else 0 end) '一月',(case when DATE_FORMAT(consultDate,'%m')=2 then 1 else 0 end) '二月',(case when DATE_FORMAT(consultDate,'%m')=3 then 1 else 0 end) '三月',"
		+ "(case when DATE_FORMAT(consultDate,'%m')=4 then 1 else 0 end) '四月',(case when DATE_FORMAT(consultDate,'%m')=5 then 1 else 0 end) '五月',(case when DATE_FORMAT(consultDate,'%m')=6 then 1 else 0 end) '六月',"
		+ "(case when DATE_FORMAT(consultDate,'%m')=7 then 1 else 0 end) '七月',(case when DATE_FORMAT(consultDate,'%m')=8 then 1 else 0 end)'八月',(case when DATE_FORMAT(consultDate,'%m')=9 then 1 else 0 end) '九月',"
		+ "(case when DATE_FORMAT(consultDate,'%m')=10 then 1 else 0 end) '十月',(case when DATE_FORMAT(consultDate,'%m')=11 then 1 else 0 end) '十一月',(case when DATE_FORMAT(consultDate,'%m')=12 then 1 else 0 end) '十二月',"
		+ "DSellSource.nameM nameM from DSellSource left join Consult on DSellSource.id=Consult.sellSource where DATE_FORMAT(consultDate,'%Y')=:statisticalYear group by nameM ";
			
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("statisticalYear",  statisticalYear );
		DiagramCharts diagramCharts = new DiagramCharts();
		//设置标题
		Title title=new Title();
		title.setText("咨询来源趋势统计");
		diagramCharts.setTitle(title);
		//设置DiagramSeries
		final List<DiagramSeries> results=new ArrayList<DiagramSeries>();
		
		this.npJdbcTemplate.query(sql, paramMap,
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						int mydata1,mydata2,mydata3,mydata4,mydata5,mydata6,mydata7,mydata8,mydata9,mydata10,mydata11,mydata12;
						mydata1=Integer.parseInt(rs.getString("一月"));
						mydata2=Integer.parseInt(rs.getString("二月"));
						mydata3=Integer.parseInt(rs.getString("三月"));
						mydata4=Integer.parseInt(rs.getString("四月"));
						mydata5=Integer.parseInt(rs.getString("五月"));
						mydata6=Integer.parseInt(rs.getString("六月"));
						mydata7=Integer.parseInt(rs.getString("七月"));
						mydata8=Integer.parseInt(rs.getString("八月"));
						mydata9=Integer.parseInt(rs.getString("九月"));
						mydata10=Integer.parseInt(rs.getString("十月"));
						mydata11=Integer.parseInt(rs.getString("十一月"));
						mydata12=Integer.parseInt(rs.getString("十二月"));
						
						DiagramSeries diagramSeries=new DiagramSeries();
						List<Integer> LData= new ArrayList<Integer>();
						LData.add(mydata1);
						LData.add(mydata2);
						LData.add(mydata3);
						LData.add(mydata4);
						LData.add(mydata5);
						LData.add(mydata6);
						LData.add(mydata7);
						LData.add(mydata8);
						LData.add(mydata9);
						LData.add(mydata10);
						LData.add(mydata11);
						LData.add(mydata12);
						diagramSeries.setData(LData);
						diagramSeries.setName(rs.getString("nameM"));
						
						results.add(diagramSeries);
					}
				});
		
		logger.info("2" );
		
		diagramCharts.setDiagramseries(results);

		return diagramCharts;
	}


	@Override
	public MixedCharts getQianTaiBaoMingLiang(String starttime,String endtime, String studentType) {
		//传递参数解析
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("starttime",  starttime );
		paramMap.put("endtime", endtime );
		paramMap.put("studentType", studentType );
				
		
		MixedCharts mixedCharts=new MixedCharts();
		MixedxAxis xAxis=new MixedxAxis();
		//获得课程的名称列表。
		String coursesql="select nameM from Course ";
		final List<String> courseList = this.npJdbcTemplate.query(coursesql,
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int index)
							throws SQLException {
						return rs.getString("nameM");
					}
				});
		xAxis.setCategories(courseList);
		mixedCharts.setxAxis(xAxis);
		int courseLength=courseList.size();
		
		
		
		//获得销售员列表			
		String sellersql="select nameM from DSeller ";
		final List<String> sellerList = this.npJdbcTemplate.query(sellersql,
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int index)
							throws SQLException {
						return rs.getString("nameM");
					}
				});
		int sellerLength=sellerList.size();
		
		//开始查询，并封装数据
		MixedSeries series=new MixedSeries();	
		List<MixedSei> mixedSeiList=new ArrayList<MixedSei>();
		
		//获得柱状图的数据
		for (int row=0;row<sellerLength;row++)
		{
			
			MixedSei mixedSei=new MixedSei();
			mixedSei.setName(sellerList.get(row));
			mixedSei.setType("column");
			final List<Float> data=new ArrayList<Float>();
			for(int col=0;col<courseLength;col++)
			{
				String sql="select DSeller.nameM SellerName,Course.nameM CoursenameM,count(*) countNum "
						+ "from StudentClass left join DSeller on StudentClass.sellerCode=DSeller.id "
						+ "left join Class on StudentClass.classCode=Class.classCode "
						+ " left join Course on Class.courseCode=Course.courseCode "
						+ "where DSeller.nameM='"+sellerList.get(row)+"' and StudentClass.studentType=:studentType and StudentClass.enrollDate between :starttime and :endtime "
						+"and Course.nameM='"+courseList.get(col)+ "' group by SellerName ";
				//查询数据库
				this.npJdbcTemplate.query(sql, paramMap,
						new RowCallbackHandler() {
							@Override
							public void processRow(ResultSet rs) throws SQLException {
								if(rs.getRow()==1)
								{
									data.add(Float.parseFloat(rs.getString("countNum")));
								}
							}
						});
				//处理数据集为空的情况
				if(data.size()<col)
				{
					data.add((float) 0);
				}
			}
			mixedSei.setData(data);
			mixedSeiList.add(mixedSei);
		}
		
		//获得线状图的数据
		MixedSei splineSeries=new MixedSei();
		splineSeries.setName("平均值");
		splineSeries.setType("spline");
		courseLength = mixedSeiList.get(0).getData().size();
		List<Float> mixedsplinedata=new ArrayList<Float>();
		for (int i=0;i<courseLength;i++)
		{
			mixedsplinedata.add((float) 0);		
		}
		
		for(MixedSei mixedSei1 : mixedSeiList)
		{
			for(int col=0;col<courseLength;col++)
			{
				mixedsplinedata.set(col, (mixedsplinedata.get(col) + mixedSei1.getData().get(col))) ;
			}			
		}
		for (int col=0;col<courseLength;col++)
		{
			mixedsplinedata.set(col, (mixedsplinedata.get(col)/sellerLength)) ;
		}
		splineSeries.setData(mixedsplinedata);
		
		
		
		
		//获得饼状图的数据
		MixedSei pieSeries=new MixedSei();
		pieSeries.setName("总报名量");
		pieSeries.setType("pie");
		List<Integer> center=new ArrayList<Integer>();
		center.add(150);
		center.add(80);
		pieSeries.setCenter(center);
		
		
		List<Float> mixedpiedata=new ArrayList<Float>();
		for (MixedSei mixedSei1 : mixedSeiList) {
			float mysum=0;
			for (float data1 : mixedSei1.getData()) {
				mysum+=data1;
			}
			mixedpiedata.add(mysum);
		}
		pieSeries.setData(mixedpiedata);
		
	
		
		
		
		
		mixedSeiList.add(splineSeries);
		mixedSeiList.add(pieSeries);
		
		//附加数据系列
		series.setSeries(mixedSeiList);
		mixedCharts.setSeries(series);
		return mixedCharts;
	}


	@Override
	public DiagramCharts getBaoMingLaiYuanQuShi(String statisticalYear) {
		String sql ="select (case when DATE_FORMAT(enrollDate,'%m')=1 then 1 else 0 end) '一月',(case when DATE_FORMAT(enrollDate,'%m')=2 then 1 else 0 end) '二月',(case when DATE_FORMAT(enrollDate,'%m')=3 then 1 else 0 end) '三月',"
				+ "(case when DATE_FORMAT(enrollDate,'%m')=4 then 1 else 0 end) '四月',(case when DATE_FORMAT(enrollDate,'%m')=5 then 1 else 0 end) '五月',(case when DATE_FORMAT(enrollDate,'%m')=6 then 1 else 0 end) '六月',"
				+ "(case when DATE_FORMAT(enrollDate,'%m')=7 then 1 else 0 end) '七月',(case when DATE_FORMAT(enrollDate,'%m')=8 then 1 else 0 end)'八月',(case when DATE_FORMAT(enrollDate,'%m')=9 then 1 else 0 end) '九月',"
				+ "(case when DATE_FORMAT(enrollDate,'%m')=10 then 1 else 0 end) '十月',(case when DATE_FORMAT(enrollDate,'%m')=11 then 1 else 0 end) '十一月',(case when DATE_FORMAT(enrollDate,'%m')=12 then 1 else 0 end) '十二月',"
				+ "DSellSource.nameM nameM from DSellSource left join StudentClass on DSellSource.id=StudentClass.sellSourceCode where DATE_FORMAT(enrollDate,'%Y')=:statisticalYear group by nameM ";
					
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("statisticalYear",  statisticalYear );
				DiagramCharts diagramCharts = new DiagramCharts();
				//设置标题
				Title title=new Title();
				title.setText("报名来源趋势统计");
				diagramCharts.setTitle(title);
				//设置DiagramSeries
				final List<DiagramSeries> results=new ArrayList<DiagramSeries>();
				this.npJdbcTemplate.query(sql, paramMap,
						new RowCallbackHandler() {
							@Override
							public void processRow(ResultSet rs) throws SQLException {
								int mydata1,mydata2,mydata3,mydata4,mydata5,mydata6,mydata7,mydata8,mydata9,mydata10,mydata11,mydata12;
								mydata1=Integer.parseInt(rs.getString("一月"));
								mydata2=Integer.parseInt(rs.getString("二月"));
								mydata3=Integer.parseInt(rs.getString("三月"));
								mydata4=Integer.parseInt(rs.getString("四月"));
								mydata5=Integer.parseInt(rs.getString("五月"));
								mydata6=Integer.parseInt(rs.getString("六月"));
								mydata7=Integer.parseInt(rs.getString("七月"));
								mydata8=Integer.parseInt(rs.getString("八月"));
								mydata9=Integer.parseInt(rs.getString("九月"));
								mydata10=Integer.parseInt(rs.getString("十月"));
								mydata11=Integer.parseInt(rs.getString("十一月"));
								mydata12=Integer.parseInt(rs.getString("十二月"));
								
								DiagramSeries diagramSeries=new DiagramSeries();
								List<Integer> LData= new ArrayList<Integer>();
								LData.add(mydata1);
								LData.add(mydata2);
								LData.add(mydata3);
								LData.add(mydata4);
								LData.add(mydata5);
								LData.add(mydata6);
								LData.add(mydata7);
								LData.add(mydata8);
								LData.add(mydata9);
								LData.add(mydata10);
								LData.add(mydata11);
								LData.add(mydata12);
								diagramSeries.setData(LData);
								diagramSeries.setName(rs.getString("nameM"));
								
								results.add(diagramSeries);
							}
						});
				diagramCharts.setDiagramseries(results);

				return diagramCharts;
	}
}
