<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支出按校区</title>
<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
/* 财务中的controller都在controller下的FinancialStatisticsController来进行后台逻辑的处理 */
	var grid;
	function init() {
		$('#starttime').datebox({
			required : true,
			value : getPreOneMonths()
		});
		$('#endtime').datebox({
			required : true,
			value : getCurrentDate()
		});

		grid = $('#grid').datagrid({
			url : 'getZhiChuAnXiaoQu',
			striped : true,
			pagination : true,
			rownumbers : true,
			nowrap : false,
			idField : 'userId',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				field : 'expenditurenameM',
				title : '支出校区名称',
				width : "33%",
				align : 'center'

			}, {
				field : 'numberofaccounts',
				title : '条数',
				width : "33%",
				align : 'center'

			},

			{
				field : 'summoneyAmount',
				title : '总金额',
				width : "33%",
				align : 'center',

			} ] ],
			toolbar : '#toolbar'
		});
	}
	$(function() {
		init();
	});
</script>
</head>

<body style="margin: 0px; padding: 0px">
	<div id="toolbar"
		style="padding-top: 10px; padding-bottom: 10px; text-align: center">
		<form method="post" class="form">
			<div>
				统计开始日期：<input id="starttime" type="text" name="starttime"
					class="easyui-datebox" style="width: 200px;" required="required">
				统计截止日期：<input id="endtime" type="text" name="endtime"
					class="easyui-datebox" style="width: 200px;" required="required">
				<a href="javascript:void(0);" class="easyui-linkbutton"
					data-options="iconCls:'ext-icon-zoom',plain:true"
					onclick="grid.datagrid('load',cxw.serializeObject($('form')));">查询</a>
			</div>

		</form>
	</div>
	<table id="grid" data-options="border:false,fit:true"></table>
</body>
</html>