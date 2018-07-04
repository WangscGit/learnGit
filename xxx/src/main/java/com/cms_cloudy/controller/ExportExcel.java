package com.cms_cloudy.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.cms_cloudy.component.service.IPartDataService;
import com.cms_cloudy.pojo.PartDataExcel;
import com.cms_cloudy.pojo.SysExcel;
import com.cms_cloudy.user.pojo.HrUser;
import com.cms_cloudy.user.service.UserService;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL
 * 
 * @author WANGSC
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
@Controller
public class ExportExcel<T> extends BaseController {
	public void exportExcel(Collection<T> dataset, OutputStream out) {
		exportExcel("测试POI导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out) {
		exportExcel("测试POI导出EXCEL文档", headers, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
		exportExcel("测试POI导出EXCEL文档", headers, dataset, out, pattern);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// if (value instanceof Integer) {
					// int intValue = (Integer) value;
					// cell.setCellValue(intValue);
					// } else if (value instanceof Float) {
					// float fValue = (Float) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(fValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Double) {
					// double dValue = (Double) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(dValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Long) {
					// long longValue = (Long) value;
					// cell.setCellValue(longValue);
					// }
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,
								index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						System.out.println("+++" + textValue);
						if (null == textValue) {
							textValue = "";
						}
						if (null == value || "".equals(value)) {
							value = "";
						}
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exportProductBom(String productId, HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		IPartDataService partDataService = (IPartDataService) context.getBean("IPartDataService");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productID", productId);
		// ExportExcel<PartDataExcel> exs = new ExportExcel<PartDataExcel>();
		String[] headers = { "物料编码", "细目", "规格型号", "编号前缀", "value", "国产/进口", "数量" };
		List<Map<String, Object>> dataset = partDataService.selectProductBomByPn(map);
		List<PartDataExcel> excel = new ArrayList<PartDataExcel>();
		if (dataset.size() > 0) {
			for (Map<String, Object> data : dataset) {
				excel.add(new PartDataExcel(null == data.get("PartNumber") ? "" : data.get("PartNumber").toString(),
						null == data.get("Part_Type") ? "" : data.get("Part_Type").toString(),
						null == data.get("ITEM") ? "" : data.get("ITEM").toString(),
						null == data.get("Part_Reference") ? "" : data.get("Part_Reference").toString(),
						null == data.get("Value") ? "" : data.get("Value").toString(),
						null == data.get("Country") ? "" : data.get("Country").toString(),
						null == data.get("Numbers") ? "" : data.get("Numbers").toString()));
			}
		}
		try {
			String[][] values = new String[excel.size()][];
			for (int i = 0; i < dataset.size(); i++) {
				values[i] = new String[7];
				// 将对象内容转换成string
				PartDataExcel obj = excel.get(i);
				values[i][0] = obj.getPartNumber();
				values[i][1] = obj.getPart_Type();
				values[i][2] = obj.getITEM();
				values[i][3] = obj.getPart_Reference();
				values[i][4] = obj.getValue();
				values[i][5] = obj.getCountry();
				values[i][6] = obj.getNumbers();
			}
			HSSFWorkbook wb = getHSSFWorkbook("器件信息.xls", headers, values, null);
			this.setResponseHeader(response, "器件信息.xls");
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void test() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		UserService userService = (UserService) context.getBean("UserService");
		// 测试学生
		ExportExcel<HrUser> ex = new ExportExcel<HrUser>();
		ExportExcel<SysExcel> exs = new ExportExcel<SysExcel>();
		List<HrUser> dataset = new ArrayList<HrUser>();
		String[] headers = { "员工编号", "用户编号", "中文名", "英文名", "任职职位", "邮箱", "电话", "手机", "所在部门", "创建者", "创建时间", "是否离职" };
		Map<String,Object> map  =  new HashMap<String,Object>();
		dataset = userService.selectUserToExport(map);
		List<SysExcel> excel = new ArrayList<SysExcel>();
		if (dataset.size() > 0) {
			for (HrUser user : dataset) {
				excel.add(new SysExcel(user.getEmployeeNumber(), user.getUserNumber(), user.getUserName(),
						user.getLoginName(), user.getPosition(), user.getEmail(), user.getTelephone(),
						user.getMobilePhone(), user.getDepartment(), user.getCreateuser(), user.getCreatetime(),
						user.getIsOrNot()));
			}
		}
		try {
			// BufferedInputStream bis = new BufferedInputStream(
			// new
			// FileInputStream("C:\\Users\\Administrator\\Desktop\\qqq.png"));
			// byte[] buf = new byte[bis.available()];
			// while ((bis.read(buf)) != -1) {
			// //
			// }
			// dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",
			// "清华出版社", buf));
			// dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
			// "阳光出版社", buf));
			// dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",
			// "清华出版社", buf));
			// dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567",
			// "清华出版社", buf));
			// dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567",
			// "汤春秀出版社", buf));
			OutputStream out = new FileOutputStream("D://a.xls");
			// OutputStream out2 = new FileOutputStream("D://b.xls");
			// ex.exportExcel(headers, excel, out);
			exs.exportExcel(headers, excel, out);
			// ex2.exportExcel(headers2, dataset2, out2);
			out.close();
			// JOptionPane.showMessageDialog(null, "导出成功!");
			// System.out.println("excel导出成功！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {
		// 第一步，创建一个webbook，对应一个Excel文件
		if (wb == null) {
			wb = new HSSFWorkbook();
		}
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = null;
		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				row.createCell(j).setCellValue(values[i][j]);
			}
		}

		return wb;
	}

	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// user导出
	public HSSFWorkbook exportUserExcel(List<HrUser> list, String[] showName) {
		int count = list.size();
		int num = count % 5000;
		int num1;
		if (num == 0) {
			num1 = count / 5000;
		} else {
			num1 = count / 5000 + 1;
		}
		String[] excelHeader = showName;
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int j = 1; j <= num1; j++) {
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short) 800);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellValue("员工信息表");
			// 设置字体
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 15);// 字体高度
			font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font.setFontName("隶书"); // 字体
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			// 设置单元格类型
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setWrapText(true);
			cell1.setCellStyle(cellStyle); // 设置单元格样式
			HSSFRow row = sheet.createRow(1);
			row.setHeight((short) 400); // 设置行高
			for (int s = 0; s < showName.length; s++) {
				sheet.setColumnWidth(s, 5000); // 设置没列的宽度
			}
			// sheet.setColumnWidth(0, 7500); //设置没列的宽度
			// sheet.setColumnWidth(1, 8500);
			// sheet.setColumnWidth(2, 7500);
			// sheet.setColumnWidth(3, 11500);
			// sheet.setColumnWidth(4, 14500);
			// 设置字体
			HSSFFont fontlast = wb.createFont();
			fontlast.setFontHeightInPoints((short) 12);// 字体高度
			fontlast.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			fontlast.setFontName("宋体"); // 字体
			HSSFCellStyle style = wb.createCellStyle();
			style.setFont(fontlast);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setWrapText(true);
			for (int i = 0; i < excelHeader.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(cellStyle);
			}
			// 当没一个sheet满50000条的时候就新建一个sheet。每个sheet最多放65535条数据
			for (int i = 5000 * j - 5000; i < 5000 * j && i < count; i++) {
				row = sheet.createRow(i - 5000 * j + 5002);
				HrUser operateLog = list.get(i);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(operateLog.getEmployeeNumber());
				cell.setCellStyle(style);
				cell = row.createCell(1);
				cell.setCellValue(operateLog.getUserNumber());
				cell.setCellStyle(style);
				cell = row.createCell(2);
				cell.setCellValue(operateLog.getUserName());
				cell.setCellStyle(style);
				cell = row.createCell(3);
				cell.setCellValue(operateLog.getLoginName());
				cell.setCellStyle(style);
				cell = row.createCell(4);
				cell.setCellValue(operateLog.getPosition());
				cell.setCellStyle(style);
				cell = row.createCell(5);
				cell.setCellValue(operateLog.getEmail());
				cell.setCellStyle(style);
				cell = row.createCell(6);
				cell.setCellValue(operateLog.getTelephone());
				cell.setCellStyle(style);
				cell = row.createCell(7);
				cell.setCellValue(operateLog.getMobilePhone());
				cell.setCellStyle(style);
				cell = row.createCell(8);
				cell.setCellValue(operateLog.getDepartment());
				cell.setCellStyle(style);
				cell = row.createCell(9);
				cell.setCellValue(operateLog.getCreateuser());
				cell.setCellStyle(style);
				cell = row.createCell(10);
				cell.setCellValue(operateLog.getCreatetime());
				cell.setCellStyle(style);
				cell = row.createCell(11);
				if (0 == operateLog.getIsOrNot()) {
					cell.setCellValue("在职");
				} else {
					cell.setCellValue("离职");
				}
				cell.setCellStyle(style);
				cell = row.createCell(12);
			}
			// Region region = new Region(0, (short) 0, 0, (short)
			// (showName.length-1));
			// sheet.addMergedRegion(region);
			HSSFRow rowasss = sheet.getRow(0);
			sheet.removeRow(rowasss);
			sheet.shiftRows(1, sheet.getLastRowNum(), -1);
		}
		return wb;
	}

	// partData导出
	public HSSFWorkbook exportExcel(Map<String, Object> listMap, String[] showName, String[] fieldName) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) listMap.get("partDataList");
		int count = list.size();
		int num = count % 200;
		int num1;
		if (num == 0) {
			num1 = count / 200;
		} else {
			num1 = count / 200 + 1;
		}
		String[] excelHeader = showName;
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int j = 1; j <= num1; j++) {
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short) 800);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellValue("元器件主数据");
			// 设置字体
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 15);// 字体高度
			font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font.setFontName("隶书"); // 字体
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			// 设置单元格类型
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setWrapText(true);
			cell1.setCellStyle(cellStyle); // 设置单元格样式
			HSSFRow row = sheet.createRow(1);
			row.setHeight((short) 400); // 设置行高
			for (int s = 0; s < showName.length; s++) {
				sheet.setColumnWidth(s, 5000); // 设置没列的宽度
			}
			// sheet.setColumnWidth(0, 7500); //设置没列的宽度
			// sheet.setColumnWidth(1, 8500);
			// sheet.setColumnWidth(2, 7500);
			// sheet.setColumnWidth(3, 11500);
			// sheet.setColumnWidth(4, 14500);
			// 设置字体
			HSSFFont fontlast = wb.createFont();
			fontlast.setFontHeightInPoints((short) 12);// 字体高度
			fontlast.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			fontlast.setFontName("宋体"); // 字体
			HSSFCellStyle style = wb.createCellStyle();
			style.setFont(fontlast);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setWrapText(true);
			for (int i = 0; i < excelHeader.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(cellStyle);
			}
			// 当没一个sheet满50000条的时候就新建一个sheet。每个sheet最多放65535条数据
			for (int i = 200 * j - 200; i < 200 * j && i < count; i++) {
				row = sheet.createRow(i - 200 * j + 202);
				Map<String, Object> operateLog = list.get(i);
				HSSFCell cell = row.createCell(0);
				for (int x = 0; x < fieldName.length; x++) {
					if (null == fieldName[x] || null == operateLog || null == operateLog.get(fieldName[x])
							|| StringUtils.isEmpty(operateLog.get(fieldName[x]).toString())) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(operateLog.get(fieldName[x]).toString());
					}
					cell.setCellStyle(style);
					cell = row.createCell(x + 1);
				}
				// cell = row.createCell(fieldName.length);
			}
			// Region region = new Region(0, (short) 0, 0, (short)
			// (fieldName.length-1));
			// sheet.addMergedRegion(region);
			HSSFRow rowasss = sheet.getRow(0);
			sheet.removeRow(rowasss);
			sheet.shiftRows(1, sheet.getLastRowNum(), -1);
		}
		return wb;
	}

	// 流程报表导出
	public HSSFWorkbook exportExcel(Map<String, Object> listMap) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) listMap.get("dataList");
		int count = list.size();
		int num = count % 5000;
		int num1;
		if (num == 0) {
			num1 = count / 5000;
		} else {
			num1 = count / 5000 + 1;
		}
		String[] excelHeader = listMap.get("showName").toString().split(",");
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int j = 1; j <= num1; j++) {
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short) 800);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellValue("元器件主数据");
			// 设置字体
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 15);// 字体高度
			font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font.setFontName("隶书"); // 字体
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			// 设置单元格类型
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setWrapText(true);
			cell1.setCellStyle(cellStyle); // 设置单元格样式
			HSSFRow row = sheet.createRow(1);
			row.setHeight((short) 400); // 设置行高
			for (int s = 0; s < listMap.get("showName").toString().split(",").length; s++) {
				sheet.setColumnWidth(s, 5000); // 设置没列的宽度
			}
			// sheet.setColumnWidth(0, 7500); //设置没列的宽度
			// sheet.setColumnWidth(1, 8500);
			// sheet.setColumnWidth(2, 7500);
			// sheet.setColumnWidth(3, 11500);
			// sheet.setColumnWidth(4, 14500);
			// 设置字体
			HSSFFont fontlast = wb.createFont();
			fontlast.setFontHeightInPoints((short) 12);// 字体高度
			fontlast.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			fontlast.setFontName("宋体"); // 字体
			HSSFCellStyle style = wb.createCellStyle();
			style.setFont(fontlast);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setWrapText(true);
			for (int i = 0; i < excelHeader.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(cellStyle);
			}
			// 当没一个sheet满50000条的时候就新建一个sheet。每个sheet最多放65535条数据
			for (int i = 5000 * j - 5000; i < 5000 * j && i < count; i++) {
				row = sheet.createRow(i - 5000 * j + 5002);
				Map<String, Object> operateLog = list.get(i);
				HSSFCell cell = row.createCell(0);
				String[] fieldName = listMap.get("fieldName").toString().split(",");
				for (int x = 0; x < fieldName.length; x++) {
					if (null == fieldName[x] || null == operateLog || null == operateLog.get(fieldName[x])
							|| StringUtils.isEmpty(operateLog.get(fieldName[x]).toString())) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(operateLog.get(fieldName[x]).toString());
					}
					cell.setCellStyle(style);
					cell = row.createCell(x + 1);
				}
				// cell = row.createCell(fieldName.length);
			}
			// Region region = new Region(0, (short) 0, 0, (short)
			// (fieldName.length-1));
			// sheet.addMergedRegion(region);
			HSSFRow rowasss = sheet.getRow(0);
			sheet.removeRow(rowasss);
			sheet.shiftRows(1, sheet.getLastRowNum(), -1);
		}
		return wb;
	}

	// bom导出
	public HSSFWorkbook exportBomExcel(List<Map<String, Object>> listMap, String[] showName, String[] fieldName) {
		List<Map<String, Object>> list = listMap;
		int count = list.size();
		int num = count % 200;
		int num1;
		if (num == 0) {
			num1 = count / 200;
		} else {
			num1 = count / 200 + 1;
		}
		String[] excelHeader = showName;
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int j = 1; j <= num1; j++) {
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short) 800);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellValue("元器件主数据");
			// 设置字体
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 15);// 字体高度
			font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font.setFontName("隶书"); // 字体
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			// 设置单元格类型
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setWrapText(true);
			cell1.setCellStyle(cellStyle); // 设置单元格样式
			HSSFRow row = sheet.createRow(1);
			row.setHeight((short) 200); // 设置行高
			for (int s = 0; s < showName.length; s++) {
				sheet.setColumnWidth(s, 500); // 设置没列的宽度
			}
			// sheet.setColumnWidth(0, 7500); //设置没列的宽度
			// sheet.setColumnWidth(1, 8500);
			// sheet.setColumnWidth(2, 7500);
			// sheet.setColumnWidth(3, 11500);
			// sheet.setColumnWidth(4, 14500);
			// 设置字体
			HSSFFont fontlast = wb.createFont();
			fontlast.setFontHeightInPoints((short) 12);// 字体高度
			fontlast.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			fontlast.setFontName("宋体"); // 字体
			HSSFCellStyle style = wb.createCellStyle();
			style.setFont(fontlast);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setWrapText(true);
			for (int i = 0; i < excelHeader.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(cellStyle);
			}
			// 当没一个sheet满50000条的时候就新建一个sheet。每个sheet最多放65535条数据
			for (int i = 200 * j - 200; i < 200 * j && i < count; i++) {
				row = sheet.createRow(i - 200 * j + 202);
				Map<String, Object> operateLog = list.get(i);
				HSSFCell cell = row.createCell(0);
				for (int x = 0; x < fieldName.length; x++) {
					if (null == fieldName[x] || null == operateLog || null == operateLog.get(fieldName[x])
							|| StringUtils.isEmpty(operateLog.get(fieldName[x]).toString())) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(operateLog.get(fieldName[x]).toString());
					}
					cell.setCellStyle(style);
					cell = row.createCell(x + 1);
				}
				// cell = row.createCell(fieldName.length);
			}
			// Region region = new Region(0, (short) 0, 0, (short)
			// (fieldName.length-1));
			// sheet.addMergedRegion(region);
			HSSFRow rowasss = sheet.getRow(0);
			sheet.removeRow(rowasss);
			sheet.shiftRows(1, sheet.getLastRowNum(), -1);
		}
		return wb;
	}
}