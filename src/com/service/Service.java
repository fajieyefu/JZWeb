package com.service;

/**
 * 
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.*;

import com.db.DBManager;
import com.db.DBManager_old;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;

public class Service {

	public String login(String username, String password) {

		// 登录查询sql
		String logSql = "select * from sam_pro_users where user_name ='"
				+ username + "' and pass_word ='" + password + "'";
		String departCodeSql = "select * from Employees where ecode='"
				+ username + "'";

		// 创建DBManager
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		// 锟斤拷锟斤拷DB锟斤拷锟斤拷
		try {
			ResultSet rs = sql.executeQuery(logSql);
			if (rs.next()) {
				String user_man = rs.getString("user_name");
				String user_man_name = rs.getString("user_desc");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("user_man_name", user_man_name);
				jsonObject.put("user_man", user_man);
				ResultSet rs1 = sql.executeQuery(departCodeSql);
				if (rs1.next()) {
					String depart_code = rs1.getString("depart_code");
					jsonObject.put("depart_code", depart_code);
					String departSql = "select * from department where depart_code='"
							+ depart_code + "'";
					ResultSet rs2 = sql.executeQuery(departSql);
					if (rs2.next()) {
						String depart_name = rs2.getString("depart_name");
						jsonObject.put("depart_name", depart_name);
					}
				}
				System.out.println(jsonObject.toString());
				sql.closeDB();
				return jsonObject.toString();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeDB();
		}

		return null;
	}

	/*
	 * 注册
	 */
	public Boolean register(String username, String password) {

		// 锟斤拷取sql锟斤拷询锟斤拷锟�
		String regSql = "insert into [user] values('" + username + "','"
				+ password + "') ";

		// 锟斤拷取DB锟斤拷锟斤拷
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		int ret = sql.executeUpdate(regSql);
		if (ret != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();

		return false;
	}

	public String getNewsInfo(int pages_num) {
		int pages_start = (pages_num - 1) * 10 + 1;
		int pages_end = pages_num * 10;
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String newsSql;
		if (pages_num == 1) {
			newsSql = "select top 10 * from sam_affiche  order by id desc";
		} else {
			newsSql = "select top 10 * from sam_affiche where id<(select Min(id) from (select top "
					+ pages_end
					+ " id from sam_affiche order by id desc)AS T) order by id asc";
		}
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ResultSet rs = sql.executeQuery(newsSql);
		int count = 0;
		try {
			while (rs.next()) {
				jsonObject = new JSONObject();
				String title = rs.getString("HEADER");
				String content = rs.getString("CONTENT");
				String fb_date = rs.getString("FB_DATE");
				String fb_man = rs.getString("FB_Man_name");
				jsonObject.put("title", title);
				jsonObject.put("content", content);
				jsonObject.put("fb_date", fb_date);
				jsonObject.put("fb_man", fb_man);
				jsonArray.add(jsonObject);
				count++;
			}
			if (count != 0) {
				jsonObject = new JSONObject();
				jsonObject.put("count", count);
				jsonArray.add(jsonObject);
				sql.closeDB();
				return jsonArray.toString();
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public String getAuthorityable(String account) {
		HashMap<String, String> map = new HashMap<String, String>();
		String work_journal_String = "frmASA103";// 工作日志
		String applyToLeave_String = "frmOAS204";// 出差
		String testReslut_input_String = "frmTQM201";// 检验结果
		String apply_fee_String = "frmOAS205";// 用款
		String contract_input_String = "frmPUR201";// 合同（订单录入）
		String Reimbursement_String = "frmOAS204"; // 报销
		int num = 1;
		String authoritySql_1 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ work_journal_String + "' and User_ID ='" + account + "'";
		String authoritySql_2 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ applyToLeave_String + "' and User_ID ='" + account + "'";
		String authoritySql_3 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ testReslut_input_String + "' and User_ID ='" + account + "'";
		String authoritySql_4 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ apply_fee_String + "' and User_ID ='" + account + "'";
		String authoritySql_5 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ contract_input_String + "' and User_ID ='" + account + "'";
		String authoritySql_6 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ Reimbursement_String + "' and User_ID ='" + account + "'";
		String[] authoritySql = { authoritySql_1, authoritySql_2,
				authoritySql_3, authoritySql_4, authoritySql_5, authoritySql_6 };
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		while (num <= 6) {
			ResultSet rs = sql.executeQuery(authoritySql[num - 1]);
			try {
				if (rs.next()) {
					map.put("temp" + num, "success");
				} else
					map.put("temp" + num, "fail");
				num++;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.closeDB();
		JSONArray jsonArray = JSONArray.fromObject(map);
		return jsonArray.toString();

	}

	public String getShenPiAuthorityable(String account) {
		HashMap<String, String> map = new HashMap<String, String>();
		String applyToLeave_shenPi_String = "frmOAS503";// 锟斤拷锟斤拷锟斤拷锟斤拷
		String testReslut_shenPi_String = "frmTQM202";// 锟斤拷锟斤拷锟斤拷锟斤拷
		String apply_fee_shenPi_String = "frmOAS206";// 锟矫匡拷锟斤拷锟诫（业锟斤拷锟斤拷?
		String contract_shenPi_String = "frmOAS206";// 锟斤拷同录锟诫（业锟斤拷锟斤拷?
		String reimbursement_String = "frmOAS503";// 锟斤拷锟斤拷锟斤拷锟斤拷
		int num = 1;
		String authoritySql_1 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ applyToLeave_shenPi_String
				+ "' and User_ID ='"
				+ account
				+ "'";
		String authoritySql_2 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ testReslut_shenPi_String + "' and User_ID ='" + account + "'";
		String authoritySql_3 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ apply_fee_shenPi_String + "' and User_ID ='" + account + "'";
		String authoritySql_4 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ contract_shenPi_String + "' and User_ID ='" + account + "'";
		String authoritySql_5 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ reimbursement_String + "' and User_ID ='" + account + "'";
		String[] authoritySql = { authoritySql_1, authoritySql_2,
				authoritySql_3, authoritySql_4, authoritySql_5 };
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		while (num <= 5) {
			ResultSet rs = sql.executeQuery(authoritySql[num - 1]);
			try {
				if (rs.next()) {
					map.put("temp" + num, "success");
				} else
					map.put("temp" + num, "fail");
				num++;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.closeDB();
		JSONArray jsonArray = JSONArray.fromObject(map);
		return jsonArray.toString();

	}

	public String getAuditType(String account) {
		// TODO Auto-generated method stub
		String auditTypeSql = "select * from [] ";// 1
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(auditTypeSql);
		JSONArray jsonArray = new JSONArray();
		try {
			while (rs.next()) {
				JSONObject jsonObject = new JSONObject();
				String value = rs.getString("");// 2
				jsonObject.put("auditType", value);
				jsonArray.add(jsonObject);
			}
			db.closeDB();
			return jsonArray.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			db.closeDB();
			return null;
		}
	}

	public String getCheckMan(String account) {
		String checkManSql = "select * from []";// 1
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(checkManSql);
		JSONArray jsonArray = new JSONArray();
		try {
			while (rs.next()) {
				JSONObject jsonObject = new JSONObject();
				String value = rs.getString("");// 2
				jsonObject.put("checkMan", value);
				jsonArray.add(jsonObject);
			}
			db.closeDB();
			return jsonArray.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			db.closeDB();
			return null;
		}

	}

	/**
	 * 锟斤拷锟接猴拷同锟斤拷锟斤拷
	 * 
	 * @param applyBill
	 * @param account
	 * @param time
	 * @param audit_type
	 * @param title
	 * @param content
	 * @param check_man
	 * @return
	 */
	public Boolean makeContractInput(String applyBill, String account,
			String time, String audit_type, String title, String content,
			String check_man) {
		String contractInputSql = "insert into [User] (UserId,Name,LoginName,Pwd)values('"
				+ applyBill
				+ "','"
				+ account
				+ "','"
				+ time
				+ "','"
				+ audit_type
				+ "','"
				+ "','"
				+ title
				+ "','"
				+ content
				+ "','"
				+ check_man + "')";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		int ret = db.executeUpdate(contractInputSql);
		if (ret != 0) {
			db.closeDB();
			return true;
		}
		db.closeDB();

		return false;

	}

	/**
	 * 锟斤拷取锟矫匡拷锟斤拷锟斤拷模锟斤拷
	 * 
	 * @return
	 */

	public String getApplyFeeType(String account) {
		// TODO Auto-generated method stub
		String sql = "select * from Employees where ecode='" + account + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(sql);
		String depart_code = "";
		try {
			if (rs.next())
				depart_code = rs.getString("depart_code");
			else {
				db.closeDB();
				return "fail";
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONArray jsonArray = new JSONArray();
		String flower_type = "用款";
		JSONObject jsonObject;
		String type;
		String sql1 = "select * from oas_flower_depart where depart_code='"
				+ depart_code + "'and flower_type='" + flower_type + "'";
		rs = db.executeQuery(sql1);
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					jsonObject = new JSONObject();
					type = rs.getString("flower_name");
					jsonObject.put("type", type);
					jsonArray.add(jsonObject);
				}
				if (db != null) {
					db.closeDB();
				}
				return jsonArray.toString();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (db != null) {
			db.closeDB();
		}
		return "fail";
	}

	public String makeApplyFee(String account, String departname,
			String applyType, String time, String expect_time,
			String amount_string, String title, String content,
			String receiveMan_string, String bankCardCode_string,
			String receiveTypeString, String files) {
		// TODO Auto-generated method stub
		int num = 0;
		int code = 0;
		String msg = "OK";
		String flower_type = "用款申请";
		String depart_code = "";
		String load_man_name = "";
		String bill_flag = "A";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dateFprmat_1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		long times = System.currentTimeMillis();
		String timeString = dateFprmat_1.format(times);
		String temp = "YKSQ" + account + dateFormat.format(times);
		String sql = "select * from oas_flower_loan where Loan_code like '"
				+ temp + "%' order by Loan_code desc ";
		String sql2 = "select * from Employees where ecode='" + account + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs2 = db.executeQuery(sql2);
		try {
			if (rs2.next()) {
				depart_code = rs2.getString("depart_code");
				load_man_name = rs2.getString("name");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			code = 1;
			msg = e1.getMessage();
			// e1.printStackTrace();
		}
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				String ts = rs.getString("Loan_code");
				String t = ts.substring(ts.length() - 2, ts.length());
				int n = Integer.parseInt(t);
				num = n + 1;
			} else {
				num = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			code = 1;
			msg = e.getMessage();
		}
		String temp2 = "0" + num;
		temp2 = temp2.substring(temp2.length() - 2, temp2.length());
		String loan_code = temp + temp2;
		// 锟斤拷取flower_name锟斤拷应锟斤拷锟斤拷顺锟斤拷锟�
		String DetailSql = "select * from oas_flower_depart_detail where flower_name='"
				+ applyType
				+ "'and depart_code='"
				+ depart_code
				+ "' order by audit_order desc";
		String audit_name = "", audit_man_code = "", audit_man_name = "", quanxian_flag = "", tuihui_flag = "";
		String start_time = "", audit_flag = "";
		int audit_order = 0;
		ResultSet rs4 = db.executeQuery(DetailSql);
		try {

			while (rs4.next()) {
				audit_name = rs4.getString("audit_name");
				audit_order = rs4.getInt("audit_order");
				audit_man_code = rs4.getString("audit_man_code");
				audit_man_name = rs4.getString("audit_man_name");
				quanxian_flag = rs4.getString("quanxian_flag");
				tuihui_flag = rs4.getString("tuihui_flag");
				DBManager db_temp = DBManager.createInstance();
				db_temp.connectDB();
				System.out.println("audit_order:" + audit_order);
				if (audit_order == 1) {
					start_time = timeString;
					audit_flag = "A";
					String insertDetailSql = "insert into oas_flower_loan_detail (loan_code,audit_name,audit_order,audit_man_code"
							+ ",audit_man_name,quanxian_flag,tuihui_flag,audit_flag,start_time)values('"
							+ loan_code
							+ "','"
							+ audit_name
							+ "','"
							+ audit_order
							+ "','"
							+ audit_man_code
							+ "','"
							+ audit_man_name
							+ "','"
							+ quanxian_flag
							+ "','"
							+ tuihui_flag
							+ "','"
							+ audit_flag
							+ "','"
							+ start_time + "')";
					int ret_1 = db_temp.executeUpdate(insertDetailSql);
				} else {
					String insertDetailSql_2 = "insert into oas_flower_loan_detail(loan_code,audit_name,audit_order,audit_man_code"
							+ ",audit_man_name,quanxian_flag,tuihui_flag,audit_flag)values('"
							+ loan_code
							+ "','"
							+ audit_name
							+ "','"
							+ audit_order
							+ "','"
							+ audit_man_code
							+ "','"
							+ audit_man_name
							+ "','"
							+ quanxian_flag
							+ "','"
							+ tuihui_flag + "','" + audit_flag + "')";
					int ret_2 = db_temp.executeUpdate(insertDetailSql_2);
				}
				db_temp.closeDB();
			}

		} catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		// 锟斤拷锟斤拷锟斤拷锟斤拷械锟斤拷锟斤拷锟斤拷

		String insertSql = "insert into oas_flower_loan (loan_code,depart_name,flower_name,"
				+ "flower_type,Loan_use_date,loan_sum,loan_name,loan_desc,depart_code,loan_man,"
				+ "loan_man_name,getpay_man,pay_type,loan_date,bill_flag,files"
				+ ")values('"
				+ loan_code
				+ "','"
				+ departname
				+ "','"
				+ applyType
				+ "','"
				+ flower_type
				+ "','"
				+ expect_time
				+ "','"
				+ amount_string
				+ "','"
				+ title
				+ "','"
				+ content
				+ "','"
				+ depart_code
				+ "','"
				+ account
				+ "','"
				+ load_man_name
				+ "','"
				+ receiveMan_string
				+ "','"
				+ receiveTypeString
				+ "','"
				+ time
				+ "','"
				+ bill_flag
				+ "','" + files + "')";
		db.connectDB();
		int ret = db.executeUpdate(insertSql);
		if (ret == 0) {
			code = 1;

		}
		db.closeDB();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		jsonObject.put("num", num);
		return jsonObject.toString();

	}

	public String getReimbursementType(String departcode) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		String type;
		String flower_type = "报销";
		String sql = "select * from oas_flower_depart where depart_code='"
				+ departcode + "' and flower_type ='" + flower_type + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					jsonObject = new JSONObject();
					type = rs.getString("flower_name");
					jsonObject.put("type", type);
					jsonArray.add(jsonObject);
				}
				if (db != null) {
					db.closeDB();
				}
				return jsonArray.toString();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (db != null) {
			db.closeDB();
		}
		return "fail";
	}

	/**
	 * 锟斤拷锟接憋拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param account
	 * @param time
	 * @param departCode
	 * @param applyFeeTypeString
	 * @param amount_string
	 * @param title
	 * @param content
	 * @return
	 */

	public String makeReimbursement(String account, String time,
			String departname, String departCode, String applyFeeTypeString,
			String amount_string, String title, String content, String files) {
		int code = 0;
		String msg = "success";
		int num = 0;
		String flower_type = "报销申请";
		String load_man_name = "";
		String bill_flag = "A";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dateFprmat_1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		long times = System.currentTimeMillis();
		String temp = "BXSQ" + account + dateFormat.format(times);
		String timeString = dateFprmat_1.format(times);
		String sql = "select * from oas_flower_loan where Loan_code like '"
				+ temp + "%' order by Loan_code desc ";
		String sql2 = "select * from Employees where ecode='" + account + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs2 = db.executeQuery(sql2);
		try {
			if (rs2.next()) {
				load_man_name = rs2.getString("name");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			code = 1;
			msg = e1.getMessage();
		}
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				String ts = rs.getString("loan_code");
				String t = ts.substring(ts.length() - 2, ts.length());
				int n = Integer.parseInt(t);
				num = n + 1;
			} else {
				num = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			code = 1;
			msg = e.getMessage();
		}

		String temp2 = "0" + num;
		temp2 = temp2.substring(temp2.length() - 2, temp2.length());
		String loan_code = temp + temp2;
		// 锟斤拷取flower_name锟斤拷锟斤拷顺锟斤拷锟�
		String DetailSql = "select * from oas_flower_depart_detail where flower_name='"
				+ applyFeeTypeString
				+ "'and depart_code='"
				+ departCode
				+ "' order by audit_order desc";
		String audit_name = "", audit_man_code = "", audit_man_name = "", quanxian_flag = "", tuihui_flag = "";
		String start_time = "", audit_flag = "";
		int audit_order = 0;
		ResultSet rs4 = db.executeQuery(DetailSql);
		try {

			while (rs4.next()) {
				audit_name = rs4.getString("audit_name");
				audit_order = rs4.getInt("audit_order");
				audit_man_code = rs4.getString("audit_man_code");
				audit_man_name = rs4.getString("audit_man_name");
				quanxian_flag = rs4.getString("quanxian_flag");
				tuihui_flag = rs4.getString("tuihui_flag");
				DBManager db_temp = DBManager.createInstance();
				db_temp.connectDB();
				System.out.println("audit_order:" + audit_order);
				if (audit_order == 1) {
					start_time = timeString;
					audit_flag = "A";
					String insertDetailSql = "insert into oas_flower_loan_detail (loan_code,audit_name,audit_order,audit_man_code"
							+ ",audit_man_name,quanxian_flag,tuihui_flag,audit_flag,start_time)values('"
							+ loan_code
							+ "','"
							+ audit_name
							+ "','"
							+ audit_order
							+ "','"
							+ audit_man_code
							+ "','"
							+ audit_man_name
							+ "','"
							+ quanxian_flag
							+ "','"
							+ tuihui_flag
							+ "','"
							+ audit_flag
							+ "','"
							+ start_time + "')";
					int ret_1 = db_temp.executeUpdate(insertDetailSql);
				} else {
					String insertDetailSql_2 = "insert into oas_flower_loan_detail(loan_code,audit_name,audit_order,audit_man_code"
							+ ",audit_man_name,quanxian_flag,tuihui_flag,audit_flag,audit_txt)values('"
							+ loan_code
							+ "','"
							+ audit_name
							+ "','"
							+ audit_order
							+ "','"
							+ audit_man_code
							+ "','"
							+ audit_man_name
							+ "','"
							+ quanxian_flag
							+ "','"
							+ tuihui_flag + "','" + audit_flag + "')";
					int ret_2 = db_temp.executeUpdate(insertDetailSql_2);
				}
				db_temp.closeDB();
			}

		} catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}

		// 锟斤拷锟斤拷锟斤拷锟斤拷械锟斤拷锟斤拷锟斤拷

		String insertSql = "insert into oas_flower_loan (loan_code,flower_name,"
				+ "flower_type,loan_sum,loan_name,loan_desc,depart_name,depart_code,loan_man,"
				+ "loan_man_name,loan_date,bill_flag,files)values('"
				+ loan_code
				+ "','"
				+ applyFeeTypeString
				+ "','"
				+ flower_type
				+ "','"
				+ amount_string
				+ "','"
				+ title
				+ "','"
				+ content
				+ "','"
				+ departname
				+ "','"
				+ departCode
				+ "','"
				+ account
				+ "','"
				+ load_man_name
				+ "','"
				+ time
				+ "','"
				+ bill_flag
				+ "','" + files + "')";
		DBManager db3 = DBManager.createInstance();
		db3.connectDB();
		int ret = db3.executeUpdate(insertSql);
		if (ret == 0) {
			code = 1;
			msg = "fail";
		}
		db3.closeDB();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		jsonObject.put("num", num);
		return jsonObject.toString();

	}

	/**
	 * 获取申请的模板类型
	 * 
	 * @param departcode
	 * @param type
	 * @return
	 */
	public String getType(String departcode, String type) {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		String sql = "select * from oas_flower_depart where depart_code='"
				+ departcode + "' and flower_type ='" + type + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					jsonObject = new JSONObject();
					type = rs.getString("flower_name");
					jsonObject.put("type", type);
					jsonArray.add(jsonObject);
				}
				if (db != null) {
					db.closeDB();
				}
				return jsonArray.toString();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (db != null) {
			db.closeDB();
		}
		return "fail";
	}

	/**
	 * 锟斤拷锟接筹拷锟斤拷锟斤拷锟诫单
	 * 
	 * @param account
	 * @param departcode
	 * @param time
	 * @param travelType
	 * @param title
	 * @param content
	 * @param travel_date
	 * @param travelDays
	 */
	public String makeTravel(String account, String departname,
			String departcode, String time, String travelType, String title,
			String content, String travel_date, String travelDays) {
		int num = 0;
		String flower_type = "出差申请";
		String load_man_name = "";
		String bill_flag = "A";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dateFprmat_1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		long times = System.currentTimeMillis();
		String temp = "CCSQ" + account + dateFormat.format(times);
		String timeString = dateFprmat_1.format(times);
		String sql = "select * from oas_flower_loan where Loan_code like '"
				+ temp + "%' order by Loan_code desc ";
		String sql2 = "select * from Employees where ecode='" + account + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs2 = db.executeQuery(sql2);
		try {
			if (rs2.next()) {
				load_man_name = rs2.getString("name");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				String ts = rs.getString("loan_code");
				String t = ts.substring(ts.length() - 2, ts.length());
				int n = Integer.parseInt(t);
				num = n + 1;
			} else {
				num = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String temp2 = "0" + num;
		temp2 = temp2.substring(temp2.length() - 2, temp2.length());
		String loan_code = temp + temp2;
		String DetailSql = "select * from oas_flower_depart_detail where flower_name='"
				+ travelType
				+ "'and depart_code='"
				+ departcode
				+ "' order by audit_order desc";
		String audit_name = "", audit_man_code = "", audit_man_name = "", quanxian_flag = "", tuihui_flag = "";
		String start_time = "", audit_flag = "";
		int audit_order = 0;
		ResultSet rs4 = db.executeQuery(DetailSql);
		try {
			while (rs4.next()) {
				audit_name = rs4.getString("audit_name");
				audit_order = rs4.getInt("audit_order");
				audit_man_code = rs4.getString("audit_man_code");
				audit_man_name = rs4.getString("audit_man_name");
				quanxian_flag = rs4.getString("quanxian_flag");
				tuihui_flag = rs4.getString("tuihui_flag");
				System.out.println("audit_order:" + audit_order);
				DBManager db2 = DBManager.createInstance();
				db2.connectDB();
				if (audit_order == 1) {
					start_time = timeString;
					audit_flag = "A";
					String insertDetailSql = "insert into oas_flower_loan_detail (loan_code,audit_name,audit_order,audit_man_code"
							+ ",audit_man_name,quanxian_flag,tuihui_flag,audit_flag,start_time)values('"
							+ loan_code
							+ "','"
							+ audit_name
							+ "','"
							+ audit_order
							+ "','"
							+ audit_man_code
							+ "','"
							+ audit_man_name
							+ "','"
							+ quanxian_flag
							+ "','"
							+ tuihui_flag
							+ "','"
							+ audit_flag
							+ "','"
							+ start_time + "')";
					int ret_1 = db2.executeUpdate(insertDetailSql);
				} else {
					String insertDetailSql_2 = "insert into oas_flower_loan_detail(loan_code,audit_name,audit_order,audit_man_code"
							+ ",audit_man_name,quanxian_flag,tuihui_flag,audit_flag)values('"
							+ loan_code
							+ "','"
							+ audit_name
							+ "','"
							+ audit_order
							+ "','"
							+ audit_man_code
							+ "','"
							+ audit_man_name
							+ "','"
							+ quanxian_flag
							+ "','"
							+ tuihui_flag + "','" + audit_flag + "')";
					int ret_2 = db2.executeUpdate(insertDetailSql_2);
				}
				db2.closeDB();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String insertSql = "insert into oas_flower_loan (loan_code,depart_name,flower_name,"
				+ "flower_type,loan_sum,loan_name,loan_desc,depart_code,loan_man,"
				+ "loan_man_name,loan_date,bill_flag" + ")values('"
				+ loan_code
				+ "','"
				+ departname
				+ "','"
				+ travelType
				+ "','"
				+ flower_type
				+ "','"
				+ travelDays
				+ "','"
				+ title
				+ "','"
				+ content
				+ "','"
				+ departcode
				+ "','"
				+ account
				+ "','"
				+ load_man_name + "','" + time + "','" + bill_flag + "')";
		db.connectDB();
		int ret = db.executeUpdate(insertSql);
		if (ret != 0) {
			db.closeDB();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("num", num);
			return jsonObject.toString();
		}
		db.closeDB();
		return "fail";

	}

	/**
	 * 
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷息锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟芥。
	 * 
	 * @param account
	 * @param type
	 * @return
	 */
	public String getApplyInfo(String account, String type) {
		// TODO Auto-generated method stub
		String sql = "select from [] where ";

		return null;
	}

	/**
	 * 获取查询模块的权限
	 * 
	 * @param account
	 * @param departcode
	 * @param count
	 * @param type
	 * @return
	 */
	public String getQueryAuthority(String account, String departcode) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		String contract_shenPi_String = "frmOAS503";// 合同
		String testReslut_shenPi_String = "frmTQM202";// 检验结果
		String apply_fee_shenPi_String = "frmOAS206";// 用款申请
		String applyToLeave_shenPi_String = "frmOAS206";// 出差申请
		String reimbursement_String = "frmOAS503";// 报销
		String workJournal_String = "frmOAS503";// 日志
		String completeCar_String = "frmASA816";// 整车月度统计查询
		int num = 1;
		String authoritySql_1 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ contract_shenPi_String + "' and User_ID ='" + account + "'";
		String authoritySql_2 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ testReslut_shenPi_String + "' and User_ID ='" + account + "'";
		String authoritySql_3 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ apply_fee_shenPi_String + "' and User_ID ='" + account + "'";
		String authoritySql_4 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ applyToLeave_shenPi_String
				+ "' and User_ID ='"
				+ account
				+ "'";
		String authoritySql_5 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ reimbursement_String + "' and User_ID ='" + account + "'";
		String authoritySql_6 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ workJournal_String + "' and User_ID ='" + account + "'";
		String authoritySql_7 = "select * from sam_user_popup_menu where Tool_Name ='"
				+ completeCar_String + "' and User_ID ='" + account + "'";

		String[] authoritySql = { authoritySql_1, authoritySql_2,
				authoritySql_3, authoritySql_4, authoritySql_5, authoritySql_6,
				authoritySql_7 };
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		while (num <= 7) {
			ResultSet rs = sql.executeQuery(authoritySql[num - 1]);
			try {
				if (rs.next()) {
					map.put("temp" + num, "success");
				} else
					map.put("temp" + num, "fail");
				num++;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql.closeDB();
		JSONArray jsonArray = JSONArray.fromObject(map);
		System.out.println(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 锟斤拷取锟斤拷询锟斤拷锟斤拷细锟斤拷息
	 * 
	 * @param account
	 * @param departcode
	 * @param count
	 * @param type
	 * @return
	 */
	public String getQueryInfo(String account, String departcode, String count,
			String type) {
		// TODO Auto-generated method stub
		int pages_num = Integer.parseInt(count);
		int pages_start = (pages_num - 1) * 10 + 1;
		int pages_end = pages_num * 10;
		JSONObject jsonObject;
		JSONArray jsonArray = new JSONArray();
		String newsSql;
		String typeSql = "";
		if (type.equals("全部")) {
			typeSql = " ";
		} else {
			typeSql = " and flower_type='" + type + "'";
		}
		System.out.println(pages_num);
		if (pages_num == 1) {
			newsSql = "select top 10 * from oas_flower_loan  where loan_man='"
					+ account + "'" + typeSql + " order by id desc";
		} else {
			newsSql = "select top 10 * from oas_flower_loan where id<(select Min(id) id from"
					+ " (select top "
					+ pages_end
					+ " id from oas_flower_loan order by id desc)AS "
					+ "T) and loan_man='"
					+ account
					+ "'"
					+ typeSql
					+ "order by id desc";
		}
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ResultSet rs = sql.executeQuery(newsSql);
		String loan_code = "", loan_name = "", loan_date = "", bill_flag = "";
		int num = 0;
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					jsonObject = new JSONObject();
					loan_code = rs.getString("loan_code");
					loan_name = rs.getString("loan_name");
					loan_date = rs.getString("loan_date");
					bill_flag = rs.getString("bill_flag");
					if (loan_name == null) {
						loan_name = "";
					}
					if (loan_date == null) {
						loan_date = "";
					}
					if (bill_flag == null) {
						bill_flag = "";
					}
					if (bill_flag.equals("A")) {
						bill_flag = "待提交";
					} else if (bill_flag.equals("Y")) {
						bill_flag = "审核通过";
					} else if (bill_flag.equals("N")) {
						bill_flag = "未通过";
					}
					jsonObject.put("loan_code", loan_code);
					jsonObject.put("loan_name", loan_name);
					jsonObject.put("loan_date", loan_date);
					jsonObject.put("bill_flag", bill_flag);
					jsonArray.add(jsonObject);
					num++;
				}
			}
			System.out.println(num);
			System.out.println(jsonArray.toString());
			if (num != 0) {
				sql.closeDB();
				return jsonArray.toString();
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql.closeDB();
		return null;
	}

	public String getLoanDeatil(String Loan_code) {
		int code = 0;
		String msg = "OK";
		JSONObject loanDetail = new JSONObject();
		String sql = "select  * from oas_flower_loan  where loan_code='"
				+ Loan_code + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				String loan_code = rs.getString("loan_code");
				String flower_name = rs.getString("flower_name");
				String flower_type = rs.getString("flower_type");
				String loan_use_date = rs.getString("loan_use_date");
				String loan_sum = rs.getString("loan_sum");
				String loan_name = rs.getString("loan_name");
				String loan_desc = rs.getString("loan_desc");
				String depart_code = rs.getString("depart_code");
				String depart_name = rs.getString("depart_name");
				String loan_man = rs.getString("loan_man");
				String loan_man_name = rs.getString("loan_man_name");
				String getpay_man = rs.getString("getpay_man");
				String pay_type = rs.getString("pay_type");
				String get_account = rs.getString("get_account");
				String loan_date = rs.getString("loan_date");
				String bill_flag = rs.getString("bill_flag");
				String files = rs.getString("files");
				if (bill_flag.equals("Y")) {
					bill_flag = "审核通过";
				} else if (bill_flag.equals("N")) {
					bill_flag = "未通过";
				} else if (bill_flag.equals("A")) {
					bill_flag = "待提交";
				}
				String receive_flag = rs.getString("receive_flag");

				loanDetail.put("loan_code", loan_code);
				loanDetail.put("flower_name", flower_name);
				loanDetail.put("flower_type", flower_type);
				loanDetail.put("loan_use_date", loan_use_date);
				loanDetail.put("loan_sum", loan_sum);
				loanDetail.put("loan_name", loan_name);
				loanDetail.put("loan_desc", loan_desc);
				loanDetail.put("depart_code", depart_code);
				loanDetail.put("depart_name", depart_name);
				loanDetail.put("loan_man", loan_man);
				loanDetail.put("loan_man_name", loan_man_name);
				loanDetail.put("getpay_man", getpay_man);
				loanDetail.put("pay_type", pay_type);
				loanDetail.put("get_account", get_account);
				loanDetail.put("loan_date", loan_date);
				loanDetail.put("bill_flag", bill_flag);
				loanDetail.put("receive_flag", receive_flag);
				loanDetail.put("files", files);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			code = 1;
			msg = e.getMessage();
		} finally {
			db.closeDB();
		}
		JSONObject returnObject = new JSONObject();
		returnObject.put("code", code);
		returnObject.put("msg", msg);
		returnObject.put("loanDetail", loanDetail.toString());

		return returnObject.toString();

	}

	public String getCheckInfo(String account, String departcode, String count,
			String type) {
		// TODO Auto-generated method stub
		int code = 0;
		String msg = "success";
		JSONArray jsonArray = new JSONArray();
		String newsSql;
		String typeSql = "";
		if (type.equals("全部")) {
			typeSql = " and audit_flag='A' ";
		} else {
			if (!type.equals("合同")) {
				type = type + "申请";
			}
			typeSql = " and audit_flag='A' and flower_type='" + type + "'";
		}
		newsSql = "select  * from oas_flower_loan_detail_v  where audit_man_code='"
				+ account + "'" + typeSql + " order by id desc";
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ResultSet rs = sql.executeQuery(newsSql);
		String loan_code = "", loan_name = "", loan_date = "", bill_flag = "", audit_code = "";
		int id = 0;
		int num = 0;
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					JSONObject jsonObject = new JSONObject();
					id = rs.getInt("id");
					loan_code = rs.getString("loan_code");
					loan_name = rs.getString("loan_name");
					loan_date = rs.getString("loan_date");
					bill_flag = rs.getString("bill_flag");
					audit_code = rs.getString("audit_order");
					if (loan_name == null) {
						loan_name = "";
					}
					if (loan_date == null) {
						loan_date = "";
					}
					if (bill_flag == null) {
						bill_flag = "";
					}
					jsonObject.put("loan_code", loan_code);
					jsonObject.put("loan_name", loan_name);
					jsonObject.put("loan_date", loan_date);
					jsonObject.put("bill_flag", bill_flag);
					jsonObject.put("audit_code", audit_code);
					jsonObject.put("id", id);
					jsonArray.add(jsonObject);
					num++;
				}
			}
			System.out.println(num);
			System.out.println(jsonArray.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql.closeDB();
		}

		return jsonArray.toString();
	}

	/**
	 * 获取审批信息
	 * 
	 * @param loan_code
	 * @param audit_code
	 * @return
	 */
	public String getCheckDetail(String loan_code, String audit_order) {
		int code = 0;
		String msg = "success";
		JSONObject jsonObject = new JSONObject();
		String sql = "select * from oas_flower_loan_detail_v where loan_code='"
				+ loan_code + "' and audit_order='" + audit_order + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(sql);
		JSONObject dataJsonObject = new JSONObject();
		try {
			if (rs.next()) {
				dataJsonObject.put("loan_use_date",
						rs.getString("loan_use_date"));
				dataJsonObject.put("loan_sum", rs.getString("loan_sum"));
				dataJsonObject.put("flower_name", rs.getString("flower_name"));
				dataJsonObject.put("depart_code", rs.getString("depart_code"));
				dataJsonObject.put("depart_name", rs.getString("depart_name"));
				dataJsonObject.put("bill_flag", rs.getString("bill_flag"));
				dataJsonObject.put("loan_date", rs.getString("Loan_date"));
				dataJsonObject.put("loan_name", rs.getString("Loan_name"));
				dataJsonObject.put("loan_desc", rs.getString("Loan_desc"));
				dataJsonObject.put("loan_man_name",
						rs.getString("loan_man_name"));
				dataJsonObject.put("files", rs.getString("files"));
			}
		} catch (SQLException e) {
			code = 1;
			msg = e.getMessage();
			// TODO Auto-generated catch block
		} finally {
			db.closeDB();
		}
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		jsonObject.put("loanDetail", dataJsonObject.toString());
		// TODO Auto-generated method stub
		return jsonObject.toString();
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷私锟斤拷谋锟斤拷锟叫的憋拷恰锟�
	 * 
	 * @param loan_code
	 * @param audit_code
	 * @param flag_num
	 * @return
	 */
	public String changeFlagType(String loan_code, String audit_order,
			String flag_num) {
		// TODO Auto-generated method stub
		String flag = "N";
		if (flag_num.equals("1")) {
			flag = "Y";
		}
		String next_audit_code = (Integer.parseInt(audit_order) + 1) + "";
		String sql = "update oas_flower_loan_detail_v set audit_flag ='" + flag
				+ "' where loan_code='" + loan_code + "' and audit_order='"
				+ audit_order + "'";
		String nextSql = "select * from oas_flower_loan_detail_v where loan_code='"
				+ loan_code + "' and audit_order='" + next_audit_code + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		int ret = db.executeUpdate(sql);
		if (flag.equals("Y")) {
			ResultSet rs = db.executeQuery(nextSql);
			try {
				if (rs.next()) {
					String updateNextSql = "update oas_flower_loan_detail_v set audit_flag ='A' where loan_code='"
							+ loan_code
							+ "' and audit_order='"
							+ next_audit_code + "'";
					db.executeUpdate(updateNextSql);
				} else {
					String updateBillFlag = "update oas_flower_loan set bill_flag='Y' where loan_code='"
							+ loan_code + "'";
					db.executeUpdate(updateBillFlag);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			}

		} else {
			String updateBillFlag = "update oas_flower_loan set bill_flag='N' where loan_code='"
					+ loan_code + "'";
			db.executeUpdate(updateBillFlag);
		}
		db.closeDB();
		return "success";
	}

	public String getSpinnerInfo() {
		// TODO Auto-generated method stub
		String spinnerSql = "select * from sal_contract_item";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(spinnerSql);
		JSONObject allJsonObject = new JSONObject();
		JSONObject jsonObject = null;
		try {
			while (rs.next()) {
				DBManager db_temp = DBManager.createInstance();
				db_temp.connectDB();
				jsonObject = new JSONObject();
				String item_code = rs.getString("item_code");
				String spinnerinfoSql = "select * from sal_contract_item_detail where item_code='"
						+ item_code + "' order by order_list ";
				ResultSet info_rs = db_temp.executeQuery(spinnerinfoSql);
				if (info_rs.next()) {
					info_rs.previous();
					int order_list = 1;
					while (info_rs.next()) {
						String item_text = info_rs.getString("item_text");
						jsonObject.put(order_list, item_text);
						order_list++;
					}
					allJsonObject.put(item_code, jsonObject);
				}

			}
			String proListSql = "select * from  PLN_prod_list_v where prod_type=1";
			ResultSet proList_rs = db.executeQuery(proListSql);

			if (proList_rs.next()) {
				proList_rs.previous();
				int count = 1;
				jsonObject = new JSONObject();
				while (proList_rs.next()) {
					String product_code = proList_rs.getString("product_code");
					String product_name = proList_rs.getString("item_name");
					jsonObject.put(count, product_code + " " + product_name);
					count++;
				}
				allJsonObject.put("vehicle_type", jsonObject);
			}
			db.closeDB();
			System.out.println(allJsonObject.toString());
			return allJsonObject.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.closeDB();
		return null;
	}

	public String makeContractInput(Map<String, String> map) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		long times = System.currentTimeMillis();
		String date = dateFormat.format(times);
		String sql = "select * from sal_contract where contract_no like '"
				+ date + "%' order by contract_no desc ";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(sql);
		int count = 0;
		try {
			if (rs.next()) {
				String ts = rs.getString("contract_no");
				String t = ts.substring(ts.length() - 3, ts.length());
				int n = Integer.parseInt(t);
				count = n + 1;
			} else {
				count = 1;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.closeDB();
		String temp2 = "00" + count;
		String record_date = date;
		temp2 = temp2.substring(temp2.length() - 3, temp2.length());
		String contract_no = date + temp2;
		String buyer_name = map.get("buyer_name");
		String user_man = map.get("user_man");
		String user_man_name = map.get("user_man_name");
		String sign_date = map.get("sign_date");
		String sign_address = map.get("sign_address");
		String tractor = map.get("tractor");
		String power_type = map.get("power_type");
		String traction_pin = map.get("traction_pin");
		String traction_seat = map.get("traction_seat");
		String heightfromground = map.get("heightfromground");
		String vehicle_type = map.get("vehicle_type");
		String product_code = vehicle_type.split(" ")[0];
		String product_name = vehicle_type.split(" ")[1];
		String type_name = map.get("type_name");
		String plan = map.get("plan");
		String length_out = map.get("length_out");
		String measure_length_out = map.get("measure_length_out");
		String width_out = map.get("width_out");
		String measure_width_out = map.get("measure_width_out");
		String height_out = map.get("height_out");
		String measure_height_out = map.get("measure_height_out");
		String length_in = map.get("length_in");
		String measure_length_in = map.get("measure_length_in");
		String width_in = map.get("width_in");
		String measure_width_in = map.get("measure_width_in");
		String height_in = map.get("height_in");
		String measure_height_in = map.get("measure_height_in");
		String upper_wing_plate = map.get("upper_wing_plate");
		String lower_wing_plate = map.get("lower_wing_plate");
		String vertical_plate = map.get("vertical_plate");
		String stringer_height = map.get("stringer_height");
		String lower_bending = map.get("lower_bending");
		String punch = map.get("punch");
		String box_bottom_thickness = map.get("box_bottom_thickness");
		String box_bottom_style = map.get("box_bottom_style");
		String waterproof_tank = map.get("waterproof_tank");
		String middle_beam_material = map.get("middle_beam_material");
		String middle_beam_Style = map.get("middle_beam_Style");
		String middle_beam_num = map.get("middle_beam_num");
		String middle_beam_punch = map.get("middle_beam_punch");
		String side_beam_material = map.get("side_beam_material");
		String xiangban_style = map.get("xiangban_style");
		String xiangban_side = map.get("xiangban_side");
		String xiangban_num = map.get("xiangban_num");
		String box_size = map.get("box_size");
		String box_style = map.get("box_style");
		String hualan_side = map.get("hualan_side");
		String hualan_side_num = map.get("hualan_side_num");
		String tube_style = map.get("tube_style");
		String zhanzhu_style = map.get("zhanzhu_style");
		String suogan = map.get("suogan");
		String backdoor_style = map.get("backdoor_style");
		String booster = map.get("booster");
		String longmenjia_style = map.get("longmenjia_style");
		String pengbukuang_num = map.get("pengbukuang_num");
		String pengbu_size = map.get("pengbu_size");
		String ladder = map.get("ladder");
		String ba_tai = map.get("ba_tai");
		String la_cheng = map.get("la_cheng");
		String peng_gan_num = map.get("peng_gan_num");
		String anzhuang_style = map.get("anzhuang_style");
		String kit_left = map.get("kit_left");
		String kit_right = map.get("kit_right");
		String rope_device_num = map.get("rope_device_num");
		String rope_device_position = map.get("rope_device_position");
		String spare_tire_num = map.get("spare_tire_num");
		String spare_tire_position = map.get("spare_tire_position");
		String elevator_num = map.get("elevator_num");
		String water_bag = map.get("water_bag");
		String water_bag_space = map.get("water_bag_space");
		String axle = map.get("axle");
		String steel_plate = map.get("steel_plate");
		String wheelbase = map.get("wheelbase");
		String brake_air_chamber = map.get("brake_air_chamber");
		String tyre = map.get("tyre");
		String steel_ring = map.get("steel_ring");
		String abs = map.get("abs");
		String body_color_up = map.get("body_color_up");
		String body_color_bottom = map.get("body_color_bottom");
		String suspension = map.get("suspension");
		String another_quest = map.get("another_quest");
		String model = map.get("model");
		String tonnage = map.get("tonnage");
		String plate_num = map.get("plate_num");
		String axis_number = map.get("axis_number");
		String color = map.get("color");
		String steel = map.get("steel");
		String num = map.get("num");
		String price = map.get("price");
		String total_amount = map.get("total_amount");
		String shoufu = map.get("shoufu");
		String extra_days = map.get("extra_days");
		String delivery_mode = map.get("delivery_mode");
		String audit_flag = "B";
		String pur_name = "山东九州汽车制造有限公司";
		String values = "values('" + contract_no + "','" + buyer_name + "','"
				+ sign_date + "','" + sign_address + "','" + tractor + "','"
				+ power_type + "','" + traction_pin + "','" + traction_seat
				+ "','" + heightfromground + "','" + product_code + "','"
				+ product_name + "','" + type_name + "','" + plan + "','"
				+ length_out + "','" + measure_length_out + "','" + width_out
				+ "','" + measure_width_out + "','" + height_out + "','"
				+ measure_height_out + "','" + length_in + "','"
				+ measure_length_in + "','" + width_in + "','"
				+ measure_width_in + "','" + height_in + "','"
				+ measure_height_in + "','" + upper_wing_plate + "','"
				+ lower_wing_plate + "','" + vertical_plate + "','"
				+ stringer_height + "','" + lower_bending + "','" + punch
				+ "','" + box_bottom_thickness + "','" + box_bottom_style
				+ "','" + waterproof_tank + "','" + middle_beam_material
				+ "','" + middle_beam_Style + "','" + middle_beam_num + "','"
				+ middle_beam_punch + "','" + side_beam_material + "','"
				+ xiangban_style + "','" + xiangban_side + "','" + xiangban_num
				+ "','" + box_size + "','" + box_style + "','" + hualan_side
				+ "','" + hualan_side_num + "','" + tube_style + "','"
				+ zhanzhu_style + "','" + suogan + "','" + backdoor_style
				+ "','" + booster + "','" + longmenjia_style + "','"
				+ pengbukuang_num + "','" + pengbu_size + "','" + ba_tai
				+ "','" + la_cheng + "','" + peng_gan_num + "','"
				+ anzhuang_style + "','" + kit_left + "','" + kit_right + "','"
				+ rope_device_num + "','" + rope_device_position + "','"
				+ spare_tire_num + "','" + spare_tire_position + "','"
				+ elevator_num + "','" + water_bag + "','" + water_bag_space
				+ "','" + axle + "','" + steel_plate + "','" + wheelbase
				+ "','" + brake_air_chamber + "','" + tyre + "','" + steel_ring
				+ "','" + abs + "','" + body_color_up + "','"
				+ body_color_bottom + "','" + suspension + "','"
				+ another_quest + "','" + model + "','" + tonnage + "','"
				+ plate_num + "','" + axis_number + "','" + color + "','"
				+ steel + "','" + num + "','" + price + "','" + total_amount
				+ "','" + shoufu + "','" + extra_days + "','" + delivery_mode
				+ "','" + audit_flag + "','" + user_man + "','" + user_man_name
				+ "','" + record_date + "','" + pur_name + "')";
		String ziduan = "(contract_no,cusm_name,plan_date,contract_area,p_chetou,p_dongli,p_qyx,p_qyz,"
				+ "p_ldgd,product_code,product_name,product_name_a,D1_1,D2_1,D2_2,D2_3,D2_4,"
				+ "D2_5,D2_6,D2_7,D2_8,D2_9,D2_10,D2_11,D2_12,D3_3,D3_4,D3_5,D3_6,"
				+ "D3_1,D3_2,D4_1,D4_2,D4_3,D5_1,D5_2,D5_3,D5_4,D6_1,D7_1,D7_2,D7_3,D8_1,"
				+ "D8_2,D8_3,D8_4,D8_5,D9_1,D9_2,D10_1,D10_2,D11_1,D11_2,D11_3,D12_1,D12_2,D12_3,"
				+ "D12_4,D13_1,D13_3,D13_2,D13_4,D14_1,D14_2,D14_3,D15_1,D15_2,D16_1,D16_2,"
				+ "D17_1,D17_2,D18_1,D18_2,D19_1,D19_2,D19_3,D20_1,remark,y_xinghao,y_dunwei,"
				+ "y_gangban,y_zhoushu,y_yanse,y_luntai,p_num,p_price,totol_amt,first_amt,"
				+ "delivery_date,tihuofangshi,audit_flag,record_man,record_man_name,"
				+ "record_date,pur_name)";
		String insertContract = "insert into sal_contract" + ziduan + values;
		DBManager insertDB = DBManager.createInstance();
		insertDB.connectDB();
		int ret = insertDB.executeUpdate(insertContract);
		if (ret != 0) {
			insertDB.closeDB();
			return contract_no;
		} else {
			insertDB.closeDB();
			return "fail";
		}
	}

	/**
	 * 
	 * 该方法为获取报表查询里面的合同列表，需要排除没有提交的数据，去除只保存的数据
	 * 
	 * @param account
	 * @param count
	 * @return
	 */
	public String getContractList(String account, String count) {
		// TODO Auto-generated method stub
		int pages_num = Integer.parseInt(count);
		int pages_start = (pages_num - 1) * 10 + 1;
		int pages_end = pages_num * 10;
		JSONObject jsonObject;
		JSONArray jsonArray = new JSONArray();
		String newsSql;
		String flag = "A";
		System.out.println(pages_num);
		if (pages_num == 1) {
			newsSql = "select top 10 * from sal_contract  where record_man='"
					+ account + "'and audit_flag<>'" + flag
					+ "'order  by id desc";
		} else {
			newsSql = "select top 10 * from sal_contract where id<(select Min(id) id from"
					+ " (select top "
					+ pages_end
					+ " id from sal_contract  where record_man='"
					+ account
					+ "'and audit_flag<>'"
					+ flag
					+ "' order by id desc)AS "
					+ "T) and record_man='"
					+ account
					+ "'and audit_flag<>'"
					+ flag + "'order by id desc";
		}
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(newsSql);
		String loan_code = "", loan_name = "", loan_date = "", bill_flag = "";
		int num = 0;
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					jsonObject = new JSONObject();
					loan_code = rs.getString("contract_no");
					loan_name = rs.getString("cusm_name");
					loan_date = rs.getString("record_date");
					bill_flag = rs.getString("audit_flag");
					if (loan_name == null) {
						loan_name = "";
					}
					if (loan_date == null) {
						loan_date = "";
					}
					if (bill_flag == null) {
						bill_flag = "";
					}
					jsonObject.put("loan_code", loan_code);
					jsonObject.put("loan_name", loan_name);
					jsonObject.put("loan_date", loan_date);
					jsonObject.put("bill_flag", bill_flag);
					jsonArray.add(jsonObject);
					num++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (num != 0) {
			db.closeDB();
			return jsonArray.toString();
		} else {
			return null;
		}
	}

	public String getContractInfo(String loan_code) {
		// TODO Auto-generated method stub
		String contractInfoSql = "select * from sal_contract where contract_no='"
				+ loan_code + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(contractInfoSql);
		try {
			if (rs.next()) {
				JSONObject jsonObject = new JSONObject();
				String contract_no = rs.getString("contract_no");
				jsonObject.put("contract_no", contract_no);
				String buyer_name = rs.getString("cusm_name");
				jsonObject.put("buyer_name", buyer_name);
				String sign_date = rs.getString("plan_date");
				jsonObject.put("sign_date", sign_date);
				String sign_address = rs.getString("contract_area");
				jsonObject.put("sign_address", sign_address);
				String tractor = rs.getString("p_chetou");
				jsonObject.put("tractor", tractor);
				String power_type = rs.getString("p_dongli");
				jsonObject.put("power_type", power_type);
				String traction_pin = rs.getString("p_qyx");
				jsonObject.put("traction_pin", traction_pin);
				String traction_seat = rs.getString("p_qyz");
				jsonObject.put("traction_seat", traction_seat);
				String heightfromground = rs.getString("p_ldgd");
				jsonObject.put("heightfromground", heightfromground);
				String product_code = rs.getString("product_code");
				jsonObject.put("product_code", product_code);
				String product_name = rs.getString("product_name");
				jsonObject.put("product_name", product_name);
				String type_name = rs.getString("product_name_a");
				jsonObject.put("type_name", type_name);
				String measure_length_out = rs.getString("D2_2");
				jsonObject.put("measure_length_out", measure_length_out);
				String width_out = rs.getString("D2_3");
				jsonObject.put("width_out", width_out);
				String measure_width_out = rs.getString("D2_4");
				jsonObject.put("measure_width_out", measure_width_out);
				String height_out = rs.getString("D2_5");
				jsonObject.put("height_out", height_out);
				String measure_height_out = rs.getString("D2_6");
				jsonObject.put("measure_height_out", measure_height_out);
				String length_in = rs.getString("D2_7");
				jsonObject.put("length_in", length_in);
				String measure_length_in = rs.getString("D2_8");
				jsonObject.put("measure_length_in", measure_length_in);
				String width_in = rs.getString("D2_9");
				jsonObject.put("width_in", width_in);
				String measure_width_in = rs.getString("D2_10");
				jsonObject.put("measure_width_in", measure_width_in);
				String height_in = rs.getString("D2_11");
				jsonObject.put("height_in", height_in);
				String measure_height_in = rs.getString("D2_12");
				jsonObject.put("measure_height_in", measure_height_in);
				String upper_wing_plate = rs.getString("D3_3");
				jsonObject.put("upper_wing_plate", upper_wing_plate);
				String lower_wing_plate = rs.getString("D3_4");
				jsonObject.put("lower_wing_plate", lower_wing_plate);
				String vertical_plate = rs.getString("D3_5");
				jsonObject.put("vertical_plate", vertical_plate);
				String stringer_height = rs.getString("D3_6");
				jsonObject.put("stringer_height", stringer_height);
				String lower_bending = rs.getString("D3_1");
				jsonObject.put("lower_bending", lower_bending);
				String punch = rs.getString("D3_2");
				jsonObject.put("punch", punch);
				String box_bottom_thickness = rs.getString("D4_1");
				jsonObject.put("box_bottom_thickness", box_bottom_thickness);
				String box_bottom_style = rs.getString("D4_2");
				jsonObject.put("box_bottom_style", box_bottom_style);
				String waterproof_tank = rs.getString("D4_3");
				jsonObject.put("waterproof_tank", waterproof_tank);
				String middle_beam_material = rs.getString("D5_1");
				jsonObject.put("middle_beam_material", middle_beam_material);
				String middle_beam_Style = rs.getString("D5_2");
				jsonObject.put("middle_beam_Style", middle_beam_Style);
				String middle_beam_num = rs.getString("D5_3");
				jsonObject.put("middle_beam_num", middle_beam_num);
				String middle_beam_punch = rs.getString("D5_4");
				jsonObject.put("middle_beam_punch", middle_beam_punch);
				String side_beam_material = rs.getString("D6_1");
				jsonObject.put("side_beam_material", side_beam_material);
				String xiangban_style = rs.getString("D7_1");
				jsonObject.put("xiangban_style", xiangban_style);
				String xiangban_side = rs.getString("D7_2");
				jsonObject.put("xiangban_side", xiangban_side);
				String xiangban_num = rs.getString("D7_3");
				jsonObject.put("xiangban_num", xiangban_num);
				String box_size = rs.getString("D8_1");
				jsonObject.put("box_size", box_size);
				String box_style = rs.getString("D8_2");
				jsonObject.put("box_style", box_style);
				String hualan_side = rs.getString("D8_3");
				jsonObject.put("hualan_side", hualan_side);
				String hualan_side_num = rs.getString("D8_4");
				jsonObject.put("hualan_side_num", hualan_side_num);
				String tube_style = rs.getString("D8_5");
				jsonObject.put("tube_style", tube_style);
				String zhanzhu_style = rs.getString("D9_1");
				jsonObject.put("zhanzhu_style", zhanzhu_style);
				String suogan = rs.getString("D9_2");
				jsonObject.put("suogan", suogan);
				String backdoor_style = rs.getString("D10_1");
				jsonObject.put("backdoor_style", backdoor_style);
				String booster = rs.getString("D10_2");
				jsonObject.put("booster", booster);
				String longmenjia_style = rs.getString("D11_1");
				jsonObject.put("longmenjia_style", longmenjia_style);
				String pengbukuang_num = rs.getString("D11_2");
				jsonObject.put("pengbukuang_num", pengbukuang_num);
				String pengbu_size = rs.getString("D11_3");
				jsonObject.put("pengbu_size", pengbu_size);
				String ba_tai = rs.getString("D12_1");
				jsonObject.put("ba_tai", ba_tai);
				String la_cheng = rs.getString("D12_2");
				jsonObject.put("la_cheng", la_cheng);
				String peng_gan_num = rs.getString("D12_3");
				jsonObject.put("peng_gan_num", peng_gan_num);
				String anzhuang_style = rs.getString("D12_4");
				jsonObject.put("anzhuang_style", anzhuang_style);
				String kit_left = rs.getString("D13_1");
				jsonObject.put("kit_left", kit_left);
				String kit_right = rs.getString("D13_3");
				jsonObject.put("kit_right", kit_right);
				String rope_device_num = rs.getString("D13_2");
				jsonObject.put("rope_device_num", rope_device_num);
				String rope_device_position = rs.getString("D13_4");
				jsonObject.put("rope_device_position", rope_device_position);
				String spare_tire_num = rs.getString("D14_1");
				jsonObject.put("spare_tire_num", spare_tire_num);
				String spare_tire_position = rs.getString("D14_2");
				jsonObject.put("spare_tire_position", spare_tire_position);
				String elevator_num = rs.getString("D14_3");
				jsonObject.put("elevator_num", elevator_num);
				String water_bag = rs.getString("D15_1");
				jsonObject.put("water_bag", water_bag);
				String water_bag_space = rs.getString("D15_2");
				jsonObject.put("water_bag_space", water_bag_space);
				String axle = rs.getString("D16_1");
				jsonObject.put("axle", axle);
				String steel_plate = rs.getString("D16_2");
				jsonObject.put("steel_plate", steel_plate);
				String wheelbase = rs.getString("D17_1");
				jsonObject.put("wheelbase", wheelbase);
				String brake_air_chamber = rs.getString("D17_2");
				jsonObject.put("brake_air_chamber", brake_air_chamber);
				String tyre = rs.getString("D18_1");
				jsonObject.put("tyre", tyre);
				String steel_ring = rs.getString("D18_2");
				jsonObject.put("steel_ring", steel_ring);
				String abs = rs.getString("D19_1");
				jsonObject.put("abs", abs);
				String body_color_up = rs.getString("D19_2");
				jsonObject.put("body_color_up", body_color_up);
				String body_color_bottom = rs.getString("D19_3");
				jsonObject.put("body_color_bottom", body_color_bottom);
				String suspension = rs.getString("D20_1");
				jsonObject.put("suspension", suspension);
				String another_quest = rs.getString("remark");
				jsonObject.put("another_quest", another_quest);
				String model = rs.getString("y_xinghao");
				jsonObject.put("model", model);
				String tonnage = rs.getString("y_dunwei");
				jsonObject.put("tonnage", tonnage);
				String plate_num = rs.getString("y_gangban");
				jsonObject.put("plate_num", plate_num);
				String axis_number = rs.getString("y_zhoushu");
				jsonObject.put("axis_number", axis_number);
				String color = rs.getString("y_yanse");
				jsonObject.put("color", color);
				String steel = rs.getString("y_luntai");
				jsonObject.put("steel", steel);
				String num = rs.getString("p_num");
				jsonObject.put("num", num);
				String price = rs.getString("p_price");
				jsonObject.put("price", price);
				String total_amount = rs.getString("totol_amt");
				jsonObject.put("total_amount", total_amount);
				String shoufu = rs.getString("first_amt");
				jsonObject.put("shoufu", shoufu);
				String extra_days = rs.getString("delivery_date");
				jsonObject.put("extra_days", extra_days);
				String delivery_mode = rs.getString("tihuofangshi");
				jsonObject.put("delivery_mode", delivery_mode);
				db.closeDB();
				System.out.println(jsonObject.toString());
				return jsonObject.toString();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.closeDB();
		return null;
	}

	public String getQualitySpinnerInfo() {
		// TODO Auto-generated method stub
		String tempName = "班组长";
		String temDepartcode_1 = "D003";
		String tempDeapartcode_2 = "D006";
		String bomSql = "select * from TQM_product_bom ";// 锟斤拷取bom模锟斤拷
		String gongXuSql = "select * from TQM_product_bom_process";// 锟斤拷取锟斤拷楣わ拷锟�
		String monitorSql = "select * from Employees where post_name='"
				+ tempName + "'";// 锟斤拷取锟斤拷锟介长
		String inspectorSql = "select * from Employees where depart_code='"
				+ temDepartcode_1 + "' or depart_code= '" + tempDeapartcode_2
				+ "'";// 锟斤拷取锟绞硷拷员
		JSONObject jsonObject;
		JSONObject jsonObjectAll = new JSONObject();
		DBManager db = DBManager.createInstance();
		db.connectDB();

		ResultSet rs = db.executeQuery(bomSql);
		try {
			if (rs.next()) {
				rs.previous();
				int num = 1;
				jsonObject = new JSONObject();
				while (rs.next()) {
					String bomString = rs.getString("product_type");
					jsonObject.put(num, bomString);
					num++;
				}
				jsonObjectAll.put("bom", jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs2 = db.executeQuery(gongXuSql);
		try {
			if (rs2.next()) {
				rs2.previous();
				int num = 1;
				jsonObject = new JSONObject();
				while (rs2.next()) {
					String gongXuString = rs2.getString("process_name");
					String gongxu_code = rs2.getString("process_code");
					jsonObject.put(num, gongxu_code + ":" + gongXuString);
					num++;
				}
				jsonObjectAll.put("gongXu", jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs3 = db.executeQuery(monitorSql);
		try {
			if (rs3.next()) {
				rs3.previous();
				int num = 1;
				jsonObject = new JSONObject();
				while (rs3.next()) {
					String monitorName = rs3.getString("name");
					String e_code = rs3.getString("ecode");
					jsonObject.put(num, e_code + ":" + monitorName);
					num++;
				}
				jsonObjectAll.put("monitor", jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs4 = db.executeQuery(inspectorSql);
		try {
			if (rs4.next()) {
				rs4.previous();
				int num = 1;
				jsonObject = new JSONObject();
				while (rs4.next()) {
					String inspectorName = rs4.getString("name");
					String e_code = rs4.getString("ecode");
					jsonObject.put(num, e_code + ":" + inspectorName);
					num++;
				}
				jsonObjectAll.put("inspector", jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.closeDB();
		System.out.println(jsonObjectAll.toString());
		return jsonObjectAll.toString();
	}

	public String makeQualityInfo(Map<String, String> map) {
		// TODO Auto-generated method stub
		String bom_model = map.get("bom_model");
		String process_code = map.get("check_gongxu");
		String monitor = map.get("monitor");
		String inspector = map.get("inspector");
		String off_date = map.get("off_date");
		String check_date = map.get("check_date");
		String produce_date = map.get("produce_date");
		String product_no = map.get("production_no");
		String process_name = map.get("check_gongxu_name");
		String monitor_man_name = map.get("monitor_name");
		String inspector_man_name = map.get("inspector_name");
		String account = map.get("account");
		String userName = map.get("userName");
		String warehouse_in_flag = "N";
		String audit_flag = "A";
		long times = System.currentTimeMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(times);
		String product_no_Sql = "select * from sal_contract where sc_bill_no='"
				+ product_no + "'";
		String product_code = "", product_name = "", product_model = "";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(product_no_Sql);
		try {
			if (rs.next()) {
				product_code = rs.getString("product_code");
				product_name = rs.getString("product_name");
				product_model = rs.getString("product_model");
				if (product_model == null) {
					product_model = "";
				}
			} else {
				db.closeDB();
				return "0";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String isExistSql = "select * from TQM_product_no where product_no='"
				+ product_no + "' and process_code='" + process_code + "'";
		ResultSet isExistRs = db.executeQuery(isExistSql);
		try {
			if (isExistRs.next()) {
				db.closeDB();
				return "1";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String insertSql = "insert into TQM_product_no (product_no,product_code,product_name,product_model,"
				+ "product_type,process_code,process_name,check_man,check_man_name,make_man,make_man_name,"
				+ "check_date,start_date,finish_date,record_date,record_man,record_man_name,warehouse_in_flag,"
				+ "audit_flag)values('"
				+ product_no
				+ "','"
				+ product_code
				+ "','"
				+ product_name
				+ "','"
				+ product_model
				+ "','"
				+ bom_model
				+ "','"
				+ process_code
				+ "','"
				+ process_name
				+ "','"
				+ inspector
				+ "','"
				+ inspector_man_name
				+ "','"
				+ monitor
				+ "','"
				+ monitor_man_name
				+ "','"
				+ off_date
				+ "','"
				+ check_date
				+ "','"
				+ produce_date
				+ "','"
				+ date
				+ "','"
				+ account
				+ "','"
				+ userName
				+ "','"
				+ warehouse_in_flag
				+ "','"
				+ audit_flag
				+ "')";

		int ret = db.executeUpdate(insertSql);
		if (ret != 0) {
			String getItemDescSql = "select * from TQM_product_bom_process_projetc where "
					+ "product_type='"
					+ bom_model
					+ "' and process_code='"
					+ process_code + "' order by paixu";
			ResultSet rs3 = db.executeQuery(getItemDescSql);
			try {
				if (rs3.next()) {
					rs3.previous();
					while (rs3.next()) {
						String processName = rs3.getString("process_name");
						if (processName == null) {
							processName = "";
						}
						String item_desc = rs3.getString("item_desc");
						String item_ok = rs3.getString("item_ok");
						String item_a = rs3.getString("item_a");
						String item_b = rs3.getString("item_b");
						String product_type = rs3.getString("product_type");
						String paixu = rs3.getString("paixu");
						String insertItemSql = "insert into TQM_product_no_project(product_no,product_code,"
								+ "process_code,item_desc,item_ok,item_a,item_b,paixu,product_type,process_name)values('"
								+ product_no
								+ "','"
								+ product_code
								+ "','"
								+ process_code
								+ "','"
								+ item_desc
								+ "','"
								+ item_ok
								+ "','"
								+ item_a
								+ "','"
								+ item_b
								+ "','"
								+ paixu
								+ "','"
								+ product_type
								+ "','"
								+ processName + "')";
						DBManager db_2 = DBManager.createInstance();
						db_2.connectDB();
						db_2.executeUpdate(insertItemSql);
						int num = 0;
						System.out.println(num++);
						db_2.closeDB();
					}
					db.closeDB();
					return "3";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		db.closeDB();
		return "4";
		// String
		// itemSql="select * from TQM_product_no_project where product_no='"+product_no+
		// "'and process_code='"+process_code+"'order by paixu" ;
		// DBManager db_itemSql= DBManager.createInstance();
		// db_itemSql.connectDB();
		// ResultSet itemRs= db_itemSql.executeQuery(itemSql);
		// JSONObject jsonObject;
		// JSONArray jsonArray= new JSONArray();
		// try {
		// while(itemRs.next()){
		// jsonObject= new JSONObject();
		// String productCode=itemRs.getString("product_code");
		// String item_desc=itemRs.getString("item_desc");
		// String item_ok=itemRs.getString("item_ok");
		// String item_a=itemRs.getString("item_a");
		// String item_b=itemRs.getString("item_b");
		// String paixu=itemRs.getString("paixu");
		// jsonObject.put("product_code", productCode);
		// jsonObject.put("item_desc", item_desc);
		// jsonObject.put("item_ok", item_ok);
		// jsonObject.put("item_a", item_a);
		// jsonObject.put("item_b", item_b);
		// jsonObject.put("paixu", paixu);
		// jsonArray.add(jsonObject);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// db_itemSql.closeDB();
		// db.closeDB();
		// System.out.println(jsonArray.toString());
		// return jsonArray.toString();

	}

	public String getQualityProcess(String product_no, String process_code) {
		String itemSql = "select * from TQM_product_no_project where product_no='"
				+ product_no
				+ "'and process_code='"
				+ process_code
				+ "'order by paixu";
		DBManager db_itemSql = DBManager.createInstance();
		db_itemSql.connectDB();
		ResultSet itemRs = db_itemSql.executeQuery(itemSql);
		JSONObject jsonObject;
		JSONArray jsonArray = new JSONArray();
		try {
			while (itemRs.next()) {
				jsonObject = new JSONObject();
				String productCode = itemRs.getString("product_code");
				String item_desc = itemRs.getString("item_desc");
				String item_ok = itemRs.getString("item_ok");
				String item_a = itemRs.getString("item_a");
				String item_b = itemRs.getString("item_b");
				String paixu = itemRs.getString("paixu");
				jsonObject.put("product_code", productCode);
				jsonObject.put("item_desc", item_desc);
				jsonObject.put("item_ok", item_ok);
				jsonObject.put("item_a", item_a);
				jsonObject.put("item_b", item_b);
				jsonObject.put("paixu", paixu);
				jsonArray.add(jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db_itemSql.closeDB();
		System.out.println(jsonArray.toString());
		return jsonArray.toString();
	}

	public String makeQualityDetail(String product_no, String process_code,
			String paixu, String remark, String item_result) {
		// TODO Auto-generated method stub
		String return_msg = "fail";
		String audit_flag = "Y";
		String updateSql = "update TQM_product_no_project set item_result='"
				+ item_result + "', item_remark='" + remark
				+ "' where product_no='" + product_no + "'and process_code='"
				+ process_code + "' and paixu='" + paixu + "'";
		String updateSql2 = "update TQM_product_no set audit_flag='"
				+ audit_flag + "' where product_no='" + product_no + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();

		int ret = db.executeUpdate(updateSql);
		if (ret != 0) {
			int ret2 = db.executeUpdate(updateSql2);
			if (ret2 != 0) {
				return_msg = "success";
			}
		}
		db.closeDB();
		return return_msg;

	}

	public String makeWorkJournal(String user_man, String user_man_name,
			String depart_code, String depart_name, String main_work,
			String work_detail) {
		// TODO Auto-generated method stub
		String post_name = null;
		String getPostName = "select * from Employees where ecode='" + user_man
				+ "'";
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		long times = System.currentTimeMillis();
		String date = dateFormat.format(times);
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(getPostName);
		try {
			if (rs.next()) {
				post_name = rs.getString("post_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String workJournalSql = "insert into oas_gzrz (journal_title,journal_content,user_code,"
				+ "user_name,post_name,depart_code,depart_name,record_date)values('"
				+ main_work
				+ "','"
				+ work_detail
				+ "','"
				+ user_man
				+ "','"
				+ user_man_name
				+ "','"
				+ post_name
				+ "','"
				+ depart_code
				+ "','" + depart_name + "','" + date + "')";

		int ret = db.executeUpdate(workJournalSql);
		if (ret != 0) {
			db.closeDB();
			return "1";
		}
		db.closeDB();
		return "0";
	}

	public String getWorkJournalInfo(String account, String count) {
		// TODO Auto-generated method stub
		int pages_num = Integer.parseInt(count);
		int pages_end = pages_num * 10;
		String workJournalInfo;
		if (pages_num == 1) {
			workJournalInfo = "select top 10 * from oas_gzrz  where user_code='"
					+ account + "' order by id desc";
		} else {
			workJournalInfo = "select top 10 * from oas_gzrz where id<(select Min(id) id from"
					+ " (select top "
					+ pages_end
					+ " id from oas_gzrz order by id desc)AS "
					+ "T) and user_code='" + account + "'order by id desc";
		}
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		ResultSet rs = sql.executeQuery(workJournalInfo);
		JSONObject jsonObject;
		JSONArray jsonArray = new JSONArray();
		int num = 0;
		try {
			if (rs.next()) {
				rs.previous();
				{
					while (rs.next()) {
						jsonObject = new JSONObject();
						String journal_title = rs.getString("journal_title");
						String journal_content = rs
								.getString("journal_content");
						String record_date = rs.getString("record_date");
						String post_name = rs.getString("post_name");
						jsonObject.put("journal_title", journal_title);
						jsonObject.put("journal_content", journal_content);
						jsonObject.put("record_date", record_date);
						jsonObject.put("post_name", post_name);
						jsonArray.add(jsonObject);
						num++;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(num);
		System.out.println(jsonArray.toString());
		if (num > 0) {
			sql.closeDB();
			return jsonArray.toString();
		} else {
			return "0";
		}
	}

	public String getContractDetailList() {
		// TODO Auto-generated method stub
		DBManager db = DBManager.createInstance();
		db.connectDB();
		DBManager db_class = null, db_model = null;
		String sql_process_code = "select * from PLN_item_process order by process_code";
		String sql_class_code;
		String sql_model_code;
		JSONArray jsonArray_basic_model = new JSONArray();
		JSONObject jsonObjectAll = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;//
		JSONObject jsonObject_model;
		JSONArray jsonArray_model;
		JSONObject jsonObject_class;
		JSONArray jsonArray_class = null;
		int code = 0;
		String msg = "OK";
		ResultSet rs = db.executeQuery(sql_process_code);
		db_class = DBManager.createInstance();
		db_class.connectDB();
		try {
			while (rs.next()) {
				String process_code = rs.getString("process_code");
				String process_name = rs.getString("process_name");
				// 鑾峰彇process_code瀵瑰簲鐨刢lass_code
				sql_class_code = "select * from PLN_item_process_class where process_code='"
						+ process_code + "' order by class_code";
				ResultSet rs_class_code = db_class.executeQuery(sql_class_code);
				jsonArray_class = new JSONArray();
				while (rs_class_code.next()) {
					String class_code = rs_class_code.getString("class_code");
					String class_name = rs_class_code.getString("class_name");
					// 鑾峰彇
					sql_model_code = "select * from PLN_item_process_class_model where class_code='"
							+ class_code + "'order by model_code";
					db_model = DBManager.createInstance();
					db_model.connectDB();
					ResultSet rs_model_code = db_model
							.executeQuery(sql_model_code);
					jsonArray_model = new JSONArray();
					while (rs_model_code.next()) {
						jsonObject_model = new JSONObject();
						String model_name = rs_model_code
								.getString("model_name");
						String model_code = rs_model_code
								.getString("model_code");
						jsonObject_model.put("model_name", model_name);
						jsonObject_model.put("model_code", model_code);
						jsonArray_model.add(jsonObject_model);
					}
					jsonObject_class = new JSONObject();
					jsonObject_class.put("class_code", class_code);
					jsonObject_class.put("class_name", class_name);
					jsonObject_class.put("model_data", jsonArray_model);

					jsonArray_class.add(jsonObject_class);

				}
				jsonObject = new JSONObject();
				jsonObject.put("process_code", process_code);
				jsonObject.put("process_name", process_name);
				jsonObject.put("class_data", jsonArray_class);
				jsonArray.add(jsonObject);

			}
			String basic_model_type = "select product_code,item_name from PLN_prod_list_v";
			ResultSet rs_basic_model = db.executeQuery(basic_model_type);
			while (rs_basic_model.next()) {
				JSONObject jsonObject_basic_model = new JSONObject();
				String product_code = rs_basic_model.getString("product_code");
				String item_name = rs_basic_model.getString("item_name");
				jsonObject_basic_model.put("product_code", product_code);
				jsonObject_basic_model.put("item_name", item_name);
				jsonArray_basic_model.add(jsonObject_basic_model);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code = 110;
			msg = e.getMessage();
		} finally {

			db.closeDB();
			if (db_class != null) {
				db_class.closeDB();
			}
			if (db_model != null) {
				db_model.closeDB();
			}

		}

		jsonObjectAll.put("code", code);
		jsonObjectAll.put("msg", msg);
		jsonObjectAll.put("data", jsonArray);
		jsonObjectAll.put("basic_model", jsonArray_basic_model);

		return jsonObjectAll.toString();
	}

	public String makeContractInput2(String jsonData) {
		int code = 0;
		String msg = "OK";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		long times = System.currentTimeMillis();
		String date = dateFormat.format(times);
		String record_date = date;
		String sql = "select * from sal_contract where contract_no like '"
				+ date + "%' order by contract_no desc ";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(sql);
		int count = 0;
		try {
			if (rs.next()) {
				String ts = rs.getString("contract_no");
				String t = ts.substring(ts.length() - 3, ts.length());
				int n = Integer.parseInt(t);
				count = n + 1;
			} else {
				count = 1;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(jsonData);

		String account = jsonObject.getString("account");
		String userman_name = jsonObject.getString("userman_name");
		JSONObject model_typeJSONObject = jsonObject
				.getJSONObject("model_type");
		String basic_product_code = model_typeJSONObject
				.getString("product_code");
		String basic_item_name = model_typeJSONObject.getString("item_name");
		JSONObject basic_infoJSONObject = jsonObject
				.getJSONObject("basic_info");
		JSONArray list_infoJSONArray = jsonObject.getJSONArray("list_info");
		String contract_no = basic_infoJSONObject.getString("contract_no");
		String buyer_name = basic_infoJSONObject.getString("buyer_name");
		String sign_date = basic_infoJSONObject.getString("sign_date");
		String sign_address = basic_infoJSONObject.getString("sign_address");
		String tractor = basic_infoJSONObject.getString("tractor");
		String traction_pin = basic_infoJSONObject.getString("traction_pin");
		String traction_seat = basic_infoJSONObject.getString("traction_seat");
		String heightfromground = basic_infoJSONObject
				.getString("heightfromground");
		String power_type = basic_infoJSONObject.getString("power_type");
		String model = basic_infoJSONObject.getString("model");
		String tonnage = basic_infoJSONObject.getString("tonnage");
		String plate_num = basic_infoJSONObject.getString("plate_num");
		String axis_number = basic_infoJSONObject.getString("axis_number");
		String color = basic_infoJSONObject.getString("color");
		String steel = basic_infoJSONObject.getString("steel");
		String sum = basic_infoJSONObject.getString("sum");
		String price = basic_infoJSONObject.getString("price");
		String total_price = basic_infoJSONObject.getString("total_price");
		String shoufu = basic_infoJSONObject.getString("shoufu");
		String extra_days = basic_infoJSONObject.getString("extra_days");
		String delivery_model = basic_infoJSONObject
				.getString("delivery_model");
		String wai_chang = basic_infoJSONObject.getString("wai_chang");
		String wai_kuan = basic_infoJSONObject.getString("wai_kuan");
		String wai_gao = basic_infoJSONObject.getString("wai_gao");
		String nei_chang = basic_infoJSONObject.getString("nei_chang");
		String nei_kuan = basic_infoJSONObject.getString("nei_kuan");
		String nei_gao = basic_infoJSONObject.getString("nei_gao");
		String cexiangban = basic_infoJSONObject.getString("cexiangban");
		String zhouju = basic_infoJSONObject.getString("zhouju");
		String remark = basic_infoJSONObject.getString("remark");
		String pur_name = "山东九州汽车制造有限公司";
		String audit_flag = basic_infoJSONObject.getString("audit_flag");
		System.out.println(contract_no);
		if ((contract_no != null) && !contract_no.equals("")) {
			String delete_contract_no = "delete from sal_contract where contract_no='"
					+ contract_no + "'";
			int rt = db.executeUpdate(delete_contract_no);
			System.out.println("rt:" + rt);
			String delete_model = " delete from sal_contract_model where contract_no='"
					+ contract_no + "'";
			int rt2 = db.executeUpdate(delete_model);
			System.out.println("rt2:" + rt2);

		} else {
			String temp2 = "00" + count;
			temp2 = temp2.substring(temp2.length() - 3, temp2.length());
			contract_no = date + temp2;
		}

		String sql_2 = "insert into sal_contract (product_name,product_code,contract_no,cusm_name,plan_date,contract_area,p_chetou,p_qyx,p_qyz,"
				+ "p_ldgd,y_xinghao,y_dunwei,y_gangban,y_zhoushu,y_yanse,y_luntai,p_num,p_price,"
				+ "totol_amt,first_amt,delivery_date,tihuofangshi,audit_flag,record_man,record_man_name,"
				+ "record_date,pur_name,wai_chang,wai_kuan,wai_gao,nei_chang,nei_kuan,nei_gao,cexiangban,zhouju,p_dongli,remark) values('"
				+ basic_item_name
				+ "','"
				+ basic_product_code
				+ "','"
				+ contract_no
				+ "','"
				+ buyer_name
				+ "','"
				+ sign_date
				+ "','"
				+ sign_address
				+ "','"
				+ tractor
				+ "','"
				+ traction_pin
				+ "','"
				+ traction_seat
				+ "','"
				+ heightfromground
				+ "','"
				+ model
				+ "','"
				+ tonnage
				+ "','"
				+ plate_num
				+ "','"
				+ axis_number
				+ "','"
				+ color
				+ "','"
				+ steel
				+ "','"
				+ sum
				+ "','"
				+ price
				+ "','"
				+ total_price
				+ "','"
				+ shoufu
				+ "','"
				+ extra_days
				+ "','"
				+ delivery_model
				+ "','"
				+ audit_flag
				+ "','"
				+ account
				+ "','"
				+ userman_name
				+ "','"
				+ record_date
				+ "','"
				+ pur_name
				+ "','"
				+ wai_chang
				+ "','"
				+ wai_kuan
				+ "','"
				+ wai_gao
				+ "','"
				+ nei_chang
				+ "','"
				+ nei_kuan
				+ "','"
				+ nei_gao
				+ "','"
				+ cexiangban
				+ "','"
				+ zhouju
				+ "','"
				+ power_type
				+ "','" + remark + "')";// 向sal_contract表中插入数据
		System.out.println("插入到sal_contract表之前");
		int rt = db.executeUpdate(sql_2);
		System.out.println("rt:" + rt);
		if (rt == 0) {
			System.out.println("插入表sal_contract时出现错误");
			code = 301;
			msg = "插入表sal_contract时出现错误";
		}
		int len = list_infoJSONArray.size();
		System.out.println("len:" + len);
		if (len > 0) {
			DBManager db_temp = DBManager.createInstance();
			db_temp.connectDB();
			for (int i = 0; i < len; i++) {
				System.out.println(i);
				JSONObject jsonObject_temp = list_infoJSONArray
						.getJSONObject(i);
				System.out.println(jsonObject_temp.toString());
				String process_code = jsonObject_temp.optString("process_code");
				if (process_code == null || process_code.equals("")) {
					continue;
				}
				String process_name = jsonObject_temp.optString("process_name");
				String class_code = jsonObject_temp.optString("class_code");
				String class_name = jsonObject_temp.optString("class_name");
				String model_name = jsonObject_temp.optString("model_name");
				String model_code = jsonObject_temp.optString("model_code");
				String insert_into_abc = "insert into sal_contract_model (contract_no,process_code,process_name,class_code"
						+ ",class_name,model_name,model_code) values('"
						+ contract_no
						+ "','"
						+ process_code
						+ "','"
						+ process_name
						+ "','"
						+ class_code
						+ "','"
						+ class_name
						+ "','"
						+ model_name
						+ "','"
						+ model_code
						+ "')";
				int rt2 = db_temp.executeUpdate(insert_into_abc);
				System.out.println("rt2:" + rt2);
				if (rt2 == 0) {
					code = 301;
					msg = "插入表sal_contract_model表时出现错误";
				}
			}
			db_temp.closeDB();
		}
		JSONObject jsonObject_return = new JSONObject();
		jsonObject_return.put("code", code);
		jsonObject_return.put("msg", msg);
		jsonObject_return.put("contract_no", contract_no);
		System.out.println(contract_no);
		db.closeDB();
		System.out.println("返回数据：" + jsonObject_return.toString());
		return jsonObject_return.toString();

	}

	public String getContractInfo2(String contract_no) {
		int code = 0;
		String msg = "OK";
		String date = "";
		String basic_info = "select * from sal_contract where contract_no='"
				+ contract_no + "'";
		String list_info = "select * from sal_contract_model where contract_no='"
				+ contract_no + "'";
		JSONObject basic_jsonObject = new JSONObject();
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs_basic = db.executeQuery(basic_info);
		try {
			if (rs_basic.next()) {
				String pingshen_txt = rs_basic.getString("pingshen_txt");// 生产评审
				basic_jsonObject.put("pingshen_txt", pingshen_txt);
				String audit_txt = rs_basic.getString("audit_txt");// 后勤评审
				basic_jsonObject.put("audit_txt", audit_txt);
				String buyer_name = rs_basic.getString("cusm_name");
				basic_jsonObject.put("buyer_name", buyer_name);
				String sign_date = rs_basic.getString("plan_date");
				basic_jsonObject.put("sign_date", sign_date);
				String sign_address = rs_basic.getString("contract_area");
				basic_jsonObject.put("sign_address", sign_address);
				String power_type = rs_basic.getString("p_dongli");
				basic_jsonObject.put("power_type", power_type);
				String tractor = rs_basic.getString("p_chetou");
				basic_jsonObject.put("tractor", tractor);
				String traction_pin = rs_basic.getString("p_qyx");
				basic_jsonObject.put("traction_pin", traction_pin);
				String traction_seat = rs_basic.getString("p_qyz");
				basic_jsonObject.put("traction_seat", traction_seat);
				String heightfromground = rs_basic.getString("p_ldgd");
				basic_jsonObject.put("heightfromground", heightfromground);
				String model = rs_basic.getString("y_xinghao");
				basic_jsonObject.put("model", model);
				String tonnage = rs_basic.getString("y_dunwei");
				basic_jsonObject.put("tonnage", tonnage);
				String plate_num = rs_basic.getString("y_gangban");
				basic_jsonObject.put("plate_num", plate_num);
				String axis_number = rs_basic.getString("y_zhoushu");
				basic_jsonObject.put("axis_number", axis_number);
				String color = rs_basic.getString("y_yanse");
				basic_jsonObject.put("color", color);
				String steel = rs_basic.getString("y_luntai");
				basic_jsonObject.put("steel", steel);
				String sum = rs_basic.getString("p_num");
				basic_jsonObject.put("sum", sum);
				String price = rs_basic.getString("p_price");
				basic_jsonObject.put("price", price);
				String total_price = rs_basic.getString("totol_amt");
				basic_jsonObject.put("total_price", total_price);
				String shoufu = rs_basic.getString("first_amt");
				basic_jsonObject.put("shoufu", shoufu);
				String extra_days = rs_basic.getString("delivery_date");
				basic_jsonObject.put("extra_days", extra_days);
				String delivery_model = rs_basic.getString("tihuofangshi");
				basic_jsonObject.put("delivery_model", delivery_model);
				String audit_flag = rs_basic.getString("audit_flag");
				basic_jsonObject.put("audit_flag", audit_flag);
				String record_man = rs_basic.getString("record_man");
				basic_jsonObject.put("record_man", record_man);
				String record_man_name = rs_basic.getString("record_man_name");
				basic_jsonObject.put("record_man_name", record_man_name);
				String record_date = rs_basic.getString("record_date");
				basic_jsonObject.put("record_date", record_date);

				String wai_chang = rs_basic.getString("wai_chang");
				basic_jsonObject.put("wai_chang", wai_chang);

				String wai_kuan = rs_basic.getString("wai_kuan");
				basic_jsonObject.put("wai_kuan", wai_kuan);

				String wai_gao = rs_basic.getString("wai_gao");
				basic_jsonObject.put("wai_gao", wai_gao);

				String nei_chang = rs_basic.getString("nei_chang");
				basic_jsonObject.put("nei_chang", nei_chang);

				String nei_kuan = rs_basic.getString("nei_kuan");
				basic_jsonObject.put("nei_kuan", nei_kuan);

				String nei_gao = rs_basic.getString("nei_gao");
				basic_jsonObject.put("nei_gao", nei_gao);

				String cexiangban = rs_basic.getString("cexiangban");
				basic_jsonObject.put("cexiangban", cexiangban);

				String zhouju = rs_basic.getString("zhouju");
				basic_jsonObject.put("zhouju", zhouju);

				String remark = rs_basic.getString("remark");
				basic_jsonObject.put("remark", remark);
				String basic_product_code = rs_basic.getString("product_code");
				basic_jsonObject.put("basic_product_code", basic_product_code);
				String basic_item_name = rs_basic.getString("product_name");
				basic_jsonObject.put("basic_item_name", basic_item_name);

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			code = 301;
			msg = e1.getMessage();
		}

		ResultSet rs_list = db.executeQuery(list_info);
		JSONArray list_jsonArray = new JSONArray();
		try {
			if (rs_list.next()) {
				rs_list.previous();
				while (rs_list.next()) {
					JSONObject jsonObject = new JSONObject();
					String process_code = rs_list.getString("process_code");
					String process_name = rs_list.getString("process_name");
					String class_code = rs_list.getString("class_code");
					String class_name = rs_list.getString("class_name");
					String model_code = rs_list.getString("model_code");
					String model_name = rs_list.getString("model_name");
					jsonObject.put("process_code", process_code);
					jsonObject.put("process_name", process_name);
					jsonObject.put("class_code", class_code);
					jsonObject.put("class_name", class_name);
					jsonObject.put("model_code", model_code);
					jsonObject.put("model_name", model_name);
					list_jsonArray.add(jsonObject);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code = 302;
			msg = e.getMessage();
		}
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("code", code);
		jsonObjectAll.put("msg", msg);
		if (code == 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("basic_info", basic_jsonObject);
			jsonObject.put("list_info", list_jsonArray);
			jsonObjectAll.put("data", jsonObject);
		}
		db.closeDB();
		return jsonObjectAll.toString();
	}

	public String changeContractFlag(String loan_code, String flag,
			String confirm_text) {
		// TODO Auto-generated method stub
		int code = 0;
		String msg = "OK";
		String paichan_flag = "";
		if (confirm_text == null) {
			confirm_text = "";
		}
		if (flag.equals("Y")) {
			paichan_flag = "A";
		}
		String sql = "update sal_contract set audit_flag ='" + flag
				+ "', chadan_txt='" + confirm_text + "',paichan_flag='"
				+ paichan_flag + "' where contract_no='" + loan_code + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		int ret = db.executeUpdate(sql);
		if (ret == 0) {
			code = 301;
			msg = "数据库插入数据失败！";

		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);

		db.closeDB();
		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}

	/**
	 * 获取合同列表，待确认事项的列表，需要排除掉已经审核成功后的合同
	 * 
	 * @param account
	 * @param count
	 * @return
	 */
	public String getContractList2(String account, String count) {
		int pages_num = Integer.parseInt(count);
		int pages_start = (pages_num - 1) * 10 + 1;
		int pages_end = pages_num * 10;
		JSONObject jsonObject;
		JSONArray jsonArray = new JSONArray();
		String newsSql;
		System.out.println(pages_num);
		String flag = "Y";// 排除审核成功后的数据
		if (pages_num == 1) {
			newsSql = "select top 10 * from sal_contract  where record_man='"
					+ account + "' and audit_flag <>'" + flag
					+ "' order by id desc";
		} else {
			newsSql = "select top 10 * from sal_contract where id<(select Min(id) id from"
					+ " (select top "
					+ pages_end
					+ " id from sal_contract where record_man='"
					+ account
					+ "'"
					+ "and audit_flag <>'"
					+ flag
					+ "'  order by id desc)AS "
					+ "T) and record_man='"
					+ account
					+ "'"
					+ "and audit_flag <>'"
					+ flag
					+ "' order by id desc";
		}
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(newsSql);
		String loan_code = "", loan_name = "", loan_date = "", bill_flag = "";
		int num = 0;
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					jsonObject = new JSONObject();
					loan_code = rs.getString("contract_no");
					loan_name = rs.getString("cusm_name");
					loan_date = rs.getString("record_date");
					bill_flag = rs.getString("audit_flag");
					if (loan_name == null) {
						loan_name = "";
					}
					if (loan_date == null) {
						loan_date = "";
					}
					if (bill_flag == null) {
						bill_flag = "";
					}
					jsonObject.put("loan_code", loan_code);
					jsonObject.put("loan_name", loan_name);
					jsonObject.put("loan_date", loan_date);
					jsonObject.put("bill_flag", bill_flag);
					jsonArray.add(jsonObject);
					num++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (num != 0) {
			db.closeDB();
			return jsonArray.toString();
		} else {
			return null;
		}
	}

	public String getPaiChanList(String account, String count) {
		// TODO Auto-generated method stub
		int pages_num = Integer.parseInt(count);
		int pages_start = (pages_num - 1) * 10 + 1;
		int pages_end = pages_num * 10;
		JSONObject jsonObject;
		JSONArray jsonArray = new JSONArray();
		String newsSql;
		System.out.println(pages_num);
		String flag = "B";// 排除审核成功后的数据
		if (pages_num == 1) {
			newsSql = "select top 10 * from sal_contract  where record_man='"
					+ account + "' and paichan_flag ='" + flag
					+ "' order by id desc";
		} else {
			newsSql = "select top 10 * from sal_contract where id<(select Min(id) id from"
					+ " (select top "
					+ pages_end
					+ " id from sal_contract where record_man='"
					+ account
					+ "'"
					+ "and paichan_flag ='"
					+ flag
					+ "'  order by id desc)AS "
					+ "T) and record_man='"
					+ account
					+ "'"
					+ "and paichan_flag ='"
					+ flag
					+ "' order by id desc";
		}
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(newsSql);
		String loan_code = "", loan_name = "", loan_date = "", bill_flag = "";
		int num = 0;
		try {
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					jsonObject = new JSONObject();
					loan_code = rs.getString("contract_no");
					loan_name = rs.getString("cusm_name");
					loan_date = rs.getString("record_date");
					bill_flag = rs.getString("audit_flag");
					if (loan_name == null) {
						loan_name = "";
					}
					if (loan_date == null) {
						loan_date = "";
					}
					if (bill_flag == null) {
						bill_flag = "";
					}
					jsonObject.put("loan_code", loan_code);
					jsonObject.put("loan_name", loan_name);
					jsonObject.put("loan_date", loan_date);
					jsonObject.put("bill_flag", bill_flag);
					jsonArray.add(jsonObject);
					num++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (num != 0) {
			db.closeDB();
			return jsonArray.toString();
		} else {
			return null;
		}
	}

	public String getPaichanInfo(String contract_no) {
		int code = 0;
		String msg = "OK";
		String date = "";
		String basic_info = "select * from sal_contract where contract_no='"
				+ contract_no + "'";
		String list_info = "select * from sal_contract_model_sc where contract_no='"
				+ contract_no + "'";
		JSONObject basic_jsonObject = new JSONObject();
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs_basic = db.executeQuery(basic_info);
		try {
			if (rs_basic.next()) {
				String pingshen_txt = rs_basic.getString("pingshen_txt");// 生产评审
				basic_jsonObject.put("pingshen_txt", pingshen_txt);
				String audit_txt = rs_basic.getString("audit_txt");// 后勤评审
				basic_jsonObject.put("audit_txt", audit_txt);
				String buyer_name = rs_basic.getString("cusm_name");
				basic_jsonObject.put("buyer_name", buyer_name);
				String sign_date = rs_basic.getString("plan_date");
				basic_jsonObject.put("sign_date", sign_date);
				String sign_address = rs_basic.getString("contract_area");
				basic_jsonObject.put("sign_address", sign_address);
				String power_type = rs_basic.getString("p_dongli");
				basic_jsonObject.put("power_type", power_type);
				String tractor = rs_basic.getString("p_chetou");
				basic_jsonObject.put("tractor", tractor);
				String traction_pin = rs_basic.getString("p_qyx");
				basic_jsonObject.put("traction_pin", traction_pin);
				String traction_seat = rs_basic.getString("p_qyz");
				basic_jsonObject.put("traction_seat", traction_seat);
				String heightfromground = rs_basic.getString("p_ldgd");
				basic_jsonObject.put("heightfromground", heightfromground);
				String model = rs_basic.getString("y_xinghao");
				basic_jsonObject.put("model", model);
				String tonnage = rs_basic.getString("y_dunwei");
				basic_jsonObject.put("tonnage", tonnage);
				String plate_num = rs_basic.getString("y_gangban");
				basic_jsonObject.put("plate_num", plate_num);
				String axis_number = rs_basic.getString("y_zhoushu");
				basic_jsonObject.put("axis_number", axis_number);
				String color = rs_basic.getString("y_yanse");
				basic_jsonObject.put("color", color);
				String steel = rs_basic.getString("y_luntai");
				basic_jsonObject.put("steel", steel);
				String sum = rs_basic.getString("p_num");
				basic_jsonObject.put("sum", sum);
				String price = rs_basic.getString("p_price");
				basic_jsonObject.put("price", price);
				String total_price = rs_basic.getString("totol_amt");
				basic_jsonObject.put("total_price", total_price);
				String shoufu = rs_basic.getString("first_amt");
				basic_jsonObject.put("shoufu", shoufu);
				String extra_days = rs_basic.getString("delivery_date");
				basic_jsonObject.put("extra_days", extra_days);
				String delivery_model = rs_basic.getString("tihuofangshi");
				basic_jsonObject.put("delivery_model", delivery_model);
				String audit_flag = rs_basic.getString("audit_flag");
				basic_jsonObject.put("audit_flag", audit_flag);
				String record_man = rs_basic.getString("record_man");
				basic_jsonObject.put("record_man", record_man);
				String record_man_name = rs_basic.getString("record_man_name");
				basic_jsonObject.put("record_man_name", record_man_name);
				String record_date = rs_basic.getString("record_date");
				basic_jsonObject.put("record_date", record_date);

				String wai_chang = rs_basic.getString("wai_chang");
				basic_jsonObject.put("wai_chang", wai_chang);

				String wai_kuan = rs_basic.getString("wai_kuan");
				basic_jsonObject.put("wai_kuan", wai_kuan);

				String wai_gao = rs_basic.getString("wai_gao");
				basic_jsonObject.put("wai_gao", wai_gao);

				String nei_chang = rs_basic.getString("nei_chang");
				basic_jsonObject.put("nei_chang", nei_chang);

				String nei_kuan = rs_basic.getString("nei_kuan");
				basic_jsonObject.put("nei_kuan", nei_kuan);

				String nei_gao = rs_basic.getString("nei_gao");
				basic_jsonObject.put("nei_gao", nei_gao);

				String cexiangban = rs_basic.getString("cexiangban");
				basic_jsonObject.put("cexiangban", cexiangban);

				String zhouju = rs_basic.getString("zhouju");
				basic_jsonObject.put("zhouju", zhouju);

				String remark = rs_basic.getString("remark");
				basic_jsonObject.put("remark", remark);
				String basic_product_code = rs_basic.getString("product_code");
				basic_jsonObject.put("basic_product_code", basic_product_code);
				String basic_item_name = rs_basic.getString("product_name");
				basic_jsonObject.put("basic_item_name", basic_item_name);

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			code = 301;
			msg = e1.getMessage();
		}

		ResultSet rs_list = db.executeQuery(list_info);
		JSONArray list_jsonArray = new JSONArray();
		try {
			if (rs_list.next()) {
				rs_list.previous();
				while (rs_list.next()) {
					JSONObject jsonObject = new JSONObject();
					String process_code = rs_list.getString("process_code");
					String process_name = rs_list.getString("process_name");
					String class_code = rs_list.getString("class_code");
					String class_name = rs_list.getString("class_name");
					String model_code = rs_list.getString("model_code");
					String model_name = rs_list.getString("model_name");
					jsonObject.put("process_code", process_code);
					jsonObject.put("process_name", process_name);
					jsonObject.put("class_code", class_code);
					jsonObject.put("class_name", class_name);
					jsonObject.put("model_code", model_code);
					jsonObject.put("model_name", model_name);
					list_jsonArray.add(jsonObject);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code = 302;
			msg = e.getMessage();
		}
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("code", code);
		jsonObjectAll.put("msg", msg);
		if (code == 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("basic_info", basic_jsonObject);
			jsonObject.put("list_info", list_jsonArray);
			jsonObjectAll.put("data", jsonObject);
		}
		db.closeDB();
		return jsonObjectAll.toString();
	}

	public String changePaichanFlag(String loan_code, String flag) {
		int code = 0;
		String msg = "OK";
		String paichan_flag = flag;
		String sql = "update sal_contract set paichan_flag='" + paichan_flag
				+ "' where contract_no='" + loan_code + "'";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		int ret = db.executeUpdate(sql);
		if (ret == 0) {
			code = 301;
			msg = "数据库插入数据失败！";

		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);

		db.closeDB();
		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}

	public String makePaichanInput(String jsonData) {
		int code = 0;
		String msg = "OK";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		long times = System.currentTimeMillis();
		String date = dateFormat.format(times);
		String record_date = date;
		String sql = "select * from sal_contract where contract_no like '"
				+ date + "%' order by contract_no desc ";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		ResultSet rs = db.executeQuery(sql);
		int count = 0;
		try {
			if (rs.next()) {
				String ts = rs.getString("contract_no");
				String t = ts.substring(ts.length() - 3, ts.length());
				int n = Integer.parseInt(t);
				count = n + 1;
			} else {
				count = 1;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		String anthorQuest = jsonObject.optString("remark");
		String account = jsonObject.getString("account");
		String userman_name = jsonObject.getString("userman_name");
		JSONObject model_typeJSONObject = jsonObject
				.getJSONObject("model_type");
		String basic_product_code = model_typeJSONObject
				.getString("product_code");
		String basic_item_name = model_typeJSONObject.getString("item_name");

		JSONObject basic_infoJSONObject = jsonObject
				.getJSONObject("basic_info");
		JSONArray list_infoJSONArray = jsonObject.getJSONArray("list_info");

		String contract_no = basic_infoJSONObject.getString("contract_no");
		String buyer_name = basic_infoJSONObject.getString("buyer_name");
		String sign_date = basic_infoJSONObject.getString("sign_date");
		String sign_address = basic_infoJSONObject.getString("sign_address");
		String tractor = basic_infoJSONObject.getString("tractor");
		String traction_pin = basic_infoJSONObject.getString("traction_pin");
		String traction_seat = basic_infoJSONObject.getString("traction_seat");
		String heightfromground = basic_infoJSONObject
				.getString("heightfromground");
		String power_type = basic_infoJSONObject.getString("power_type");
		String model = basic_infoJSONObject.getString("model");
		String tonnage = basic_infoJSONObject.getString("tonnage");
		String plate_num = basic_infoJSONObject.getString("plate_num");
		String axis_number = basic_infoJSONObject.getString("axis_number");
		String color = basic_infoJSONObject.getString("color");
		String steel = basic_infoJSONObject.getString("steel");
		String sum = basic_infoJSONObject.getString("sum");
		String price = basic_infoJSONObject.getString("price");
		String total_price = basic_infoJSONObject.getString("total_price");
		String shoufu = basic_infoJSONObject.getString("shoufu");
		String extra_days = basic_infoJSONObject.getString("extra_days");
		String delivery_model = basic_infoJSONObject
				.getString("delivery_model");
		String wai_chang = basic_infoJSONObject.getString("wai_chang");
		String wai_kuan = basic_infoJSONObject.getString("wai_kuan");
		String wai_gao = basic_infoJSONObject.getString("wai_gao");
		String nei_chang = basic_infoJSONObject.getString("nei_chang");
		String nei_kuan = basic_infoJSONObject.getString("nei_kuan");
		String nei_gao = basic_infoJSONObject.getString("nei_gao");
		String cexiangban = basic_infoJSONObject.getString("cexiangban");
		String zhouju = basic_infoJSONObject.getString("zhouju");
		String remark = basic_infoJSONObject.getString("remark");
		String pur_name = "山东九州汽车制造有限公司";
		String audit_flag = basic_infoJSONObject.getString("audit_flag");
		System.out.println(contract_no);

		if (!contract_no.equals("")) {
			String changeFlag = "update sal_contract set paichan_flag='" + "C"
					+ "',remark ='" + anthorQuest + "' where contract_no='"
					+ contract_no + "'";
			// String delete_contract_no =
			// "delete from sal_contract where contract_no='"
			// + contract_no + "'";
			int rt = db.executeUpdate(changeFlag);
			System.out.println("rt:" + rt);
			String delete_model = " delete from sal_contract_model_sc where contract_no='"
					+ contract_no + "'";
			int rt2 = db.executeUpdate(delete_model);
			System.out.println("rt2:" + rt2);
		}
		int len = list_infoJSONArray.size();
		System.out.println("len:" + len);
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				JSONObject jsonObject_temp = list_infoJSONArray
						.getJSONObject(i);
				String process_code = jsonObject_temp.optString("process_code");
				String process_name = jsonObject_temp.optString("process_name");
				String class_code = jsonObject_temp.optString("class_code");
				String class_name = jsonObject_temp.optString("class_name");
				String model_name = jsonObject_temp.optString("model_name");
				String model_code = jsonObject_temp.optString("model_code");
				if (process_code.equals("") || process_code == null) {
					continue;
				}
				String insert_into_abc = "insert into sal_contract_model_sc (contract_no,process_code,process_name,class_code"
						+ ",class_name,model_name,model_code) values('"
						+ contract_no
						+ "','"
						+ process_code
						+ "','"
						+ process_name
						+ "','"
						+ class_code
						+ "','"
						+ class_name
						+ "','"
						+ model_name
						+ "','"
						+ model_code
						+ "')";
				DBManager db_temp = DBManager.createInstance();
				db_temp.connectDB();
				int rt2 = db_temp.executeUpdate(insert_into_abc);
				db_temp.closeDB();
				if (rt2 == 0) {
					code = 301;
					msg = "插入表sal_contract_model时出现错误";
				}
			}
		}
		JSONObject jsonObject_return = new JSONObject();
		jsonObject_return.put("code", code);
		jsonObject_return.put("msg", msg);
		jsonObject_return.put("contract_no", contract_no);
		System.out.println(contract_no);
		db.closeDB();
		System.out.println("返回数据：" + jsonObject_return.toString());
		return jsonObject_return.toString();
	}

	public String getApkInfo() {
		DBManager db = DBManager.createInstance();
		db.connectDB();
		String sql = "select * from apkInfo order by version_code desc";
		ResultSet rs = db.executeQuery(sql);
		String updateMessage = "";
		String url = "";
		int versionCode = 0;
		try {
			if (rs.next()) {
				updateMessage = rs.getString("updateMessage");
				url = rs.getString("url");
				versionCode = rs.getInt("version_code");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("updateMessage", updateMessage);
		jsonObject.put("url", url);
		jsonObject.put("version_code", versionCode);
		db.closeDB();
		// TODO Auto-generated method stub
		return jsonObject.toString();
	}

	public String modifyPsw(String account, String original_psw, String new_psw) {
		// TODO Auto-generated method stub
		int code = 0;
		String msg = "success";
		DBManager db = DBManager.createInstance();
		db.connectDB();
		String sql_1 = "select * from sam_pro_users where user_name ='"
				+ account + "' and pass_word ='" + original_psw + "'";
		String sql_2 = "update sam_pro_users set pass_word='" + new_psw
				+ "' where user_name='" + account + "'";
		ResultSet rs = db.executeQuery(sql_1);
		try {
			if (rs.next()) {
				int ret = db.executeUpdate(sql_2);
				if (ret == 0) {
					code = 1;
					msg = "数据库处理出现异常";
				}
			} else {
				code = 1;
				msg = "原密码输入错误";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			code = 1;
			msg = e.getMessage();
		}
		db.closeDB();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		return jsonObject.toString();
	}

	public String makeQualityDetail(String jsonData) {
		// TODO Auto-generated method stub
		int code = 0;
		String msg = "success";
		int temp = 0;
		String audit_flag = "Y";
		String product_no = "";
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
		DBManager db = DBManager.createInstance();
		db.connectDB();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String item_result = jsonObject.getString("item_result");
			product_no = jsonObject.getString("product_no");
			String process_code = jsonObject.getString("process_code");
			String paixu = jsonObject.getString("paixu");
			String remark = jsonObject.getString("remark");
			String updateSql = "update TQM_product_no_project set item_result='"
					+ item_result
					+ "', item_remark='"
					+ remark
					+ "' where product_no='"
					+ product_no
					+ "'and process_code='"
					+ process_code
					+ "' and paixu='"
					+ paixu + "'";
			int ret = db.executeUpdate(updateSql);
			if (ret == 0) {
				temp = 1;
			}
		}
		if (temp == 1) {
			code = 1;
			msg = "数据处理出现异常";
		} else {
			String updateSql2 = "update TQM_product_no set audit_flag='"
					+ audit_flag + "' where product_no='" + product_no + "'";
			int ret = db.executeUpdate(updateSql2);
			if (ret == 0) {
				code = 1;
				msg = "数据处理出现异常";
			}
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		return jsonObject.toString();
	}

	public String getCompleteCarNum(String month) {
		// TODO Auto-generated method stub
		int code = 0;
		String msg = "success";
		JSONArray jsonArray = new JSONArray();
		String date = month + "-01";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		try {
			dt = dateFormat.parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			code = 1;
			msg = e1.getMessage();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int month_num = calendar.get(Calendar.MONTH);

		DBManager_old db = DBManager_old.createInstance();
		db.connectDB();
		while (calendar.get(Calendar.MONTH) == month_num) {
			int chuche_num = 0, penqi_num = 0, fache_num = 0, contract_car_num = 0;
			date = dateFormat.format(calendar.getTime());
			// 出车数量
			String chuche_Sql = "select count(*) as num_chuche from TQM_product_no where process_code='C0108' and Convert(varchar,check_date,120) like '%"
					+ date + "%'";
			// 喷漆数量
			String penqi_Sql = "select count(*) as num_penqi from TQM_product_no where process_code='C0110' and Convert(varchar,check_date,120)  like'%"
					+ date + "%'";
			// 发车数量
			String fache_Sql = "select count(*) as num_fache from sal_contract where fahuo_flag='Y' and Convert(varchar,fahuo_date,120)  like'%"
					+ date + "%'";
			// 合同数量
			String contract_Sql = "select count(*) as num_contract from sal_contract where Convert(varchar,record_date,120) like'%"
					+ date + "%' and audit_flag='Y'";
			try {
				ResultSet rs = db.executeQuery(chuche_Sql);
				if (rs.next()) {
					chuche_num = rs.getInt("num_chuche");
				}
				rs = db.executeQuery(penqi_Sql);
				if (rs.next()) {
					penqi_num = rs.getInt("num_penqi");
				}
				rs = db.executeQuery(fache_Sql);
				if (rs.next()) {
					fache_num = rs.getInt("num_fache");
				}
				rs = db.executeQuery(contract_Sql);
				if (rs.next()) {
					contract_car_num = rs.getInt("num_contract");
				}
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("chuche_num", chuche_num);
				jsonObject.put("penqi_num", penqi_num);
				jsonObject.put("fache_num", fache_num);
				jsonObject.put("contract_car_num", contract_car_num);
				jsonObject.put("date", date);
				jsonArray.add(jsonObject);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		JSONObject returnObject = new JSONObject();
		returnObject.put("code", code);
		returnObject.put("msg", msg);
		returnObject.put("completeCar", jsonArray.toString());
		db.closeDB();
		return returnObject.toString();
	}
}
