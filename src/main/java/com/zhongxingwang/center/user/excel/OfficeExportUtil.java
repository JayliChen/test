package com.zhongxingwang.center.user.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhongxingwang.center.user.util.HttpServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class OfficeExportUtil {

    /** 允许导出的最大条数 */
    private static final Integer EXPORT_EXCEL_MAX_NUM = 10000;

    /**
     * 获取导出的 Workbook对象
     *
     * @param title     大标题
     * @param sheetName 页签名
     * @param object    导出实体
     * @param list      数据集合
     * @return Workbook
     */
    public static Workbook getWorkbook(String title, String sheetName, Class object, List list) {
        //判断导出数据是否为空
        if (list == null) {
            list = new ArrayList<>();
        }
        //判断导出数据数量是否超过限定值
        if (list.size() > EXPORT_EXCEL_MAX_NUM) {
            title = "导出数据行数超过:" + EXPORT_EXCEL_MAX_NUM + "条,无法导出、请添加导出条件!";
            list = new ArrayList<>();
        }
        //获取导出参数
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        //设置导出样式
        exportParams.setStyle(ExcelStyleUtil.class);
        //输出Workbook流
        return ExcelExportUtil.exportExcel(exportParams, object, list);
    }

    /**
     * 导出Excel
     *
     * @param workbook workbook流
     * @param fileName 文件名
     */
    public static void exportExcel(Workbook workbook, String fileName) {
        //给文件名拼接上日期
        fileName = fileName + StrUtil.UNDERLINE + DateUtil.today();
        //获取输出流
        HttpServletResponse response = HttpServletUtil.getResponse();
        //输出文件
        try (OutputStream out = response.getOutputStream()) {
            //获取文件名并转码
            String name = URLEncoder.encode(fileName, "UTF-8");
            //编码
            response.setCharacterEncoding("UTF-8");
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xlsx");
            //输出表格
            workbook.write(out);
        } catch (IOException e) {
            log.error("文件导出异常,详情如下:", e);
        } finally {
            try {
                if (workbook != null) {
                    //关闭输出流
                    workbook.close();
                }
            } catch (IOException e) {
                log.error("文件导出异常,详情如下:", e);
            }
        }
    }
}
