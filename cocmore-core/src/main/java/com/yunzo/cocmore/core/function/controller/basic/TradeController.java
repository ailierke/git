package com.yunzo.cocmore.core.function.controller.basic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yunzo.cocmore.core.function.aop.aspect.SystemControllerLog;
import com.yunzo.cocmore.core.function.model.mysql.YBasicCity;
import com.yunzo.cocmore.core.function.model.mysql.YBasicCounty;
import com.yunzo.cocmore.core.function.model.mysql.YBasicMember;
import com.yunzo.cocmore.core.function.model.mysql.YBasicProvince;
import com.yunzo.cocmore.core.function.model.mysql.YBasicSocialgroups;
import com.yunzo.cocmore.core.function.model.mysql.YBasicTrade;
import com.yunzo.cocmore.core.function.model.mysql.YSystemUsers;
import com.yunzo.cocmore.core.function.service.GetNumberService;
import com.yunzo.cocmore.core.function.service.TradeServiceI;
import com.yunzo.cocmore.core.function.util.PagingList;
import com.yunzo.cocmore.utils.base.MD5Util;

/** 
 *Description: <行业控制层>. <br>
 * @date:2014年11月25日 下午5:21:52
 * @author beck
 * @version V1.0                             
 */
@Controller
@RequestMapping("/trade")
public class TradeController {
	private static final Logger logger = Logger.getLogger(TradeController.class);

	@Resource(name = "tradeService")
	private TradeServiceI tradeService;
	
	@Resource(name = "getNumberService")
	private GetNumberService getNumberService;
	
	//此处的参数也可以是ServletRequestDataBinder类型 
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {  
	    //注册自定义的属性编辑器 (日期)  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    //表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换  
	    binder.registerCustomEditor(Date.class, dateEditor);     
	}

	@RequestMapping("/returnURL")
	@ResponseBody
	@SystemControllerLog(description="returnURL")
	public String login(String error,Model model) {
		logger.info("hygl");
		model.addAttribute("error", error);
		return "hygl.jsp";
	}
	
	@RequestMapping("/findTradeById")
	@ResponseBody
	@SystemControllerLog(description="YBasicTrade  findTradeById")
	public Map<String, Object> doNotNeedSessionAndSecurity_findTradeById(@RequestParam("fid")String fid){
		logger.info("YBasicTrade  findTradeById");
		Map<String, Object> map = new HashMap<String,Object>();
		YBasicTrade obj = null;
		try {
			obj = tradeService.getById(fid);
			map.put("msg", "查询成功");
			map.put("obj", obj);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "查询失败");
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 分页查询（默认查询全部，在有参数的情况下，根据搜索名称模糊查询）
	 * @param searchName
	 * @return
	 */
	@RequestMapping("/findAllTrade")
	@ResponseBody
	@SystemControllerLog(description="分页查询（默认查询全部，在有参数的情况下，根据搜索名称模糊查询）")
	public Map<String, Object> doNotNeedSessionAndSecurity_findAllTrade(@RequestParam(value="searchName", required=false)String searchName,
			@RequestParam(value="start", required=false)Integer start,@RequestParam(value="limit", required=false)Integer limit){
		logger.info("YBasicTrade  handleList");
		Map<String, Object> map = new HashMap<String,Object>();
		PagingList<YBasicTrade> list = null;
		try {
			list = tradeService.findAll(searchName,start,limit);
			map.put("msg", "查询成功");
			map.put("obj", list);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "查询失败");
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping("/saveTrade")
	@ResponseBody
	@SystemControllerLog(description="save YBasicTrade")
	public Map<String, Object> doNotNeedSessionAndSecurity_saveTrade(@ModelAttribute("form")YBasicTrade form){
		logger.info("save YBasicTrade");
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			tradeService.save(form);
			map.put("msg", "保存成功");
			map.put("success", true);
			getNumberService.addSerialNumber("JC-HYGL");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "保存失败");
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping("/updateTrade")
	@ResponseBody
	@SystemControllerLog(description="update YBasicTrade")
	public Map<String, Object> doNotNeedSessionAndSecurity_updateTrade(@ModelAttribute("form")YBasicTrade form){
		logger.info("update YBasicTrade");
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			tradeService.update(form);
			map.put("msg", "修改成功");
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "修改失败");
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 审核、反审核、生效和失效操作
	 * @param fid
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateTradeStatus")
	@ResponseBody
	@SystemControllerLog(description="updateTradeStatus YBasicTrade")
	public Map<String, Object> doNotNeedSessionAndSecurity_updateTradeStatus(@RequestParam("fid")String fid,@RequestParam("stateId")int stateId){
		logger.info("updateTradeStatus YBasicTrade");
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			YBasicTrade obj = tradeService.getById(fid);
			obj.setFbillState(stateId);
			tradeService.update(obj);
			map.put("msg", "状态修改成功");
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "状态修改失败");
			map.put("success", false);
		}
		return map;
	}
	/**
	 * 下载模板
	 * 
	 * @return
	 */
	@RequestMapping("/download")
	public void doNotNeedSessionAndSecurity_Download(
			HttpServletRequest request, HttpServletResponse response) {
		String fileName = "行业信息.xls";
		try {

			// 处理不同浏览器对文件名的解析问题
			String agent = (String) request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) { // FF
				// fileName = new String(fileName.getBytes("ISO-8859-1"),
				// "UTF-8");
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
				// fileName = "=?UTF-8?B?" + (new
				// String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) +
				// "?=";
			} else { // IE
				fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
			}
			response.setContentType("application/x-msdownload; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HSSFWorkbook excel = new HSSFWorkbook();

		HSSFCellStyle style = excel.createCellStyle(); // 样式对象

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平

		HSSFCellStyle style2 = excel.createCellStyle(); // 样式对象
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 垂直
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平
		style2.setWrapText(true); // 自动换行
		HSSFFont font = excel.createFont();// 设置字体
		font.setColor(HSSFFont.COLOR_RED);// 设置字体颜色
		style2.setFont(font);
		HSSFSheet sheet = excel.createSheet();
		excel.setSheetName(0, "组织信息");
		HSSFRow firstRow = sheet.createRow(0);

		HSSFCell cells[] = new HSSFCell[3];

		String[] titles = new String[] { "名称", "上级行业", "备注"};

		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 7000);


		int rowNumber = 0;
		HSSFRow infoRow = sheet.createRow(rowNumber);
		rowNumber++;
		infoRow.setHeight((short) 0x280);// 设置行高
		HSSFFont infoFont = excel.createFont();// 设置字体
		infoFont.setBoldweight((short) 1000);// 设置字体类型
		infoFont.setFontHeightInPoints((short) 20);// 设置字体大小
		HSSFCellStyle infoStyle = excel.createCellStyle();// 添加字体样式
		infoStyle.setFont(infoFont);
		infoStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置对齐方式

		for (int i = 0; i < 3; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
			cells[0].setCellStyle(style);
		}

		HSSFRow titleRow = sheet.createRow(rowNumber);
		rowNumber++;
		titleRow.setHeight((short) 0x180);// 设置行高
		HSSFFont titlefont = excel.createFont();// 设置字体
		titlefont.setColor(HSSFFont.COLOR_NORMAL);// 设置字体颜色
		titlefont.setBoldweight((short) 1000);// 设置字体类型

		HSSFCellStyle titleStyle = excel.createCellStyle();
		titleStyle.setFont(titlefont);// 添加字体样式
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 设置对齐方式

		HSSFCellStyle cellStyle = excel.createCellStyle();
		cellStyle.setWrapText(true);

		// 设置单元格边框
		titleStyle.setBorderBottom((short) 1);
		titleStyle.setBorderLeft((short) 1);
		titleStyle.setBorderRight((short) 1);
		titleStyle.setBorderTop((short) 1);

		for (int i = 0; i < 1; i++) {
			HSSFRow row = sheet.createRow(i + 1);

			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("");
			cell = row.createCell(1);
			cell.setCellValue("");
			cell = row.createCell(2);
			cell.setCellValue("");
		}
		HSSFRow row = sheet.createRow(2);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("名称为必填项，");
		cell.setCellStyle(style2);
		sheet.addMergedRegion(new Region(2, (short) 0, 2, (short) 2));
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.write(out);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导入
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/import")
	public Map<String,Object> doNotNeedSessionAndSecurity_import(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			//MultipartFile file,MultipartHttpServletRequest request,HttpServletRequest request,HttpServletResponse response
			
			MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;  
		    MultipartFile excelfile = mulRequest.getFile("file");  
			String filename = excelfile.getOriginalFilename();  
			InputStream input = excelfile.getInputStream();  
			
			HSSFWorkbook wookbook = new HSSFWorkbook(input);
			
			// 在Excel文档中，第一张工作表的缺省索引是0
			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFSheet sheet = wookbook.getSheetAt(0);
			// 获取到Excel文件中的所有行数
			int rows = sheet.getPhysicalNumberOfRows();
			
			

			if (rows >= 3) {
				// 遍历行
				for (int i = 1; i < rows-1; i++) {
					// 读取左上端单元格
					HSSFRow row = sheet.getRow(i);
					
					if (row != null) {
						// 获取到Excel文件中的所有的列
						int cells = row.getPhysicalNumberOfCells();
						String value = "";
						// 遍历列
						for (int j = 0; j <= cells; j++) {
							// 获取到列的值
							HSSFCell cell = row.getCell(j);							
							
							if (cell != null && !cell.equals("")) {
								switch (cell.getCellType()) {
									case HSSFCell.CELL_TYPE_FORMULA:
										value += cell.getCellFormula()+",";
										break;
									case HSSFCell.CELL_TYPE_NUMERIC:
										if (HSSFDateUtil.isCellDateFormatted(cell)){ 
						                    Date date = cell.getDateCellValue();
						                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						                    String cellvalue = sdf.format(date);
											value += cellvalue+",";
										}else{
											DecimalFormat df = new DecimalFormat("0");  
										    String strCell = df.format(cell.getNumericCellValue()); 
											
											value += strCell + ",";
										}
										break;
									case HSSFCell.CELL_TYPE_STRING:
										value += cell.getStringCellValue().trim()
												+ ",";
										break;
									case HSSFCell.CELL_TYPE_BLANK:
										value += "null" + ",";
										break;
									default:
										value += "输入格式不对";
										break;
								}
								//value +=cell.toString()+",";
							} else {
								value += "null" + ",";
							}
							
						}
						// 将数据插入到mysql数据库中
						String[] val = value.split(",");
						//System.out.println("vvvvvvvvvvvv     "+value);
						
						//
						
						if (val.length < 1 || val[0].equals("") ) {
							map.put("msg","您上传的文件第" + (i + 1) + "行输入的数据不对，上传终止");
							return map;
						} else {
							if (val[0].equals("输入格式不对")
									|| val[1].equals("输入格式不对")
									) {
								map.put("msg","您上传的文件第" + (i + 1) + "行输入的数据不对，上传终止");
								return map;
							} else {
								YBasicTrade trade=new YBasicTrade();
								
								try {
									trade.setFid(UUID.randomUUID().toString());
									
									trade.setFbillState(0);
									Map<String, Object> num = getNumberService.checkExist("JC-HYGL");
									trade.setFnumber((String)num.get("serialNumber"));
									YSystemUsers user=(YSystemUsers)request.getSession().getAttribute("user");
									trade.setFcreaterId(user.getFid());

									trade.setFcreateTime(new Date());
									trade.setFname(val[0]);
									if(null!=val[1]&&!"".equals(val[1])){
										List<YBasicTrade> trades=new ArrayList<YBasicTrade>();
										trades=tradeService.findByHql("from YBasicTrade as y where y.fname='"+val[1]+"'");
										if(trades.size()>0){
											trade.setFsuperTradeId(trades.get(0).getFid());
										}
									}
									
									if(!val[2].equals("null")){
										trade.setFcomment(val[2]);
									}
									
									try {
										tradeService.save(trade);
										getNumberService.addSerialNumber("JC-HYGL");
										map.put("msg", "成功");
									} catch (Exception e) {
										e.printStackTrace();
										map.put("msg","您上传的文件第" + (i + 1)+"行输入的数据不对，上传终止");
										return map;
									}
								} catch (Exception e) {
									e.printStackTrace();
									map.put("msg","您上传的文件第" + (i + 1)+"行输入的数据不对，上传终止");
									return map;
								}

							}

						}
						
						//
						
						}
				
				}
			} else {
				map.put("msg", "请按照模板填写数据！");
				return map;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			map.put("msg", "异常");
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			map.put("msg", "出错");
			return map;
		}
		return map;
	}
	
}
