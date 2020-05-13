package com.zhongxingwang.center.user.web.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongxingwang.center.user.common.Enumeration;
import com.zhongxingwang.center.user.web.dao.BaseCalendarDao;
import com.zhongxingwang.center.user.web.entity.BaseCalendar;
import com.zhongxingwang.center.user.web.service.BaseCalendarService;
import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 用户订单表(BaseCalendar)表服务实现类
 *
 * @author 陈杰攀、
 * @since 2020-04-29 17:45:17
 */


@Service
public class BaseCalendarServiceImpl implements BaseCalendarService {
    @Resource
    private BaseCalendarDao baseCalendarDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public BaseCalendar queryById(Long id) {
        return this.baseCalendarDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<BaseCalendar> queryAllByLimit(int offset, int limit) {
        return this.baseCalendarDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param baseCalendar 实例对象
     * @return 实例对象
     */
    @Override
    public BaseCalendar insert(BaseCalendar baseCalendar) {
        this.baseCalendarDao.insert(baseCalendar);
        return baseCalendar;
    }

    /**
     * 修改数据
     *
     * @param baseCalendar 实例对象
     * @return 实例对象
     */
    @Override
    public BaseCalendar update(BaseCalendar baseCalendar) {
        this.baseCalendarDao.update(baseCalendar);
        return this.queryById(baseCalendar.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.baseCalendarDao.deleteById(id) > 0;
    }

    /**
     * 数据初始化
     */
//    @Scheduled(fixedRate = 5)
//    @Test
    public void init() {
        System.out.println("开始初始化数据");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.parseDate("1970-01-01"));
        List<BaseCalendar> baseCalendarList = new ArrayList<>();
        while (calendar.getTime().getTime() <= DateUtil.parse("9999-12-31").getTime()) {
            BaseCalendar baseCalendar = new BaseCalendar();
            baseCalendar.setYearToDay(Integer.valueOf(DateUtil.format(calendar.getTime(), "yyyyMMdd")));
            baseCalendar.setYearMonthDay(DateUtil.format(calendar.getTime(), "yyyy-MM-dd"));
            baseCalendar.setBaseYear(calendar.get(Calendar.YEAR));
            baseCalendar.setBaseMonth(calendar.get(Calendar.MONTH) + 1);
            baseCalendar.setBaseDay(calendar.get(Calendar.DAY_OF_MONTH));
            baseCalendar.setBaseWeekday(calendar.get(Calendar.DAY_OF_WEEK));
            if (2 <= calendar.get(Calendar.DAY_OF_WEEK) && calendar.get(Calendar.DAY_OF_WEEK) <= 6) {
                baseCalendar.setBaseType(0);
            } else {
                baseCalendar.setBaseType(1);
            }
            baseCalendarList.add(baseCalendar);
            calendar.add(Calendar.DATE, 1);
        }
        int num = (baseCalendarList.size() + 1000 - 1) / 1000; // TODO
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * 1000;
            // 结束位置
            int toIndex = Math.min((i + 1) * 1000, baseCalendarList.size());
            List<BaseCalendar> baseCalendars = baseCalendarList.subList(fromIndex, toIndex);
            baseCalendarDao.insertBatch(baseCalendars);
        }
    }

    /**
     * 测试批量更新接口
     */
//    @Scheduled(cron = "0 36 14 7 5 ?")

    /**
     * 从第三方网站获取节假日数据，填到数据库
     */
    @Scheduled(cron = "0 22 9 8 5 ?")
    public void initCalendar() {
        Map<String,String> map = new HashMap<>();
        //从2011年01月开始获取数据
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.parse("2011-02","yyyy-MM"));
        while (calendar.getTime().getTime() < DateUtil.parse("2021-01","yyyy-MM").getTime()){
            //请求第三方网站
            String result = request(DateUtil.format(calendar.getTime(),"yyyy-M"));
            //解析数据
            JSONObject jsonObject = JSONObject.parseObject(result);
            String reason = jsonObject.getString("reason");
            if ("Success".equals(reason)){
                JSONObject respon = jsonObject.getJSONObject("result");
                JSONObject data = respon.getJSONObject("data");
                JSONArray holidays = data.getJSONArray("holiday_array");
                for (int i = 0;i < holidays.size(); i++) {
                    JSONObject holiday = holidays.getJSONObject(i);
                    JSONArray list = holiday.getJSONArray("list");
                    for (int j = 0; j < list.size() ; j++) {
                        JSONObject object = list.getJSONObject(j);
                        String day = object.getString("date");
                        String status = object.getString("status");
                        map.put(DateUtil.format(DateUtil.parse(day,"yyyy-M-d"),"yyyy-MM-dd"),status);
                    }
                }
            }
            calendar.add(Calendar.MONTH,3);
        }
        //把节假日数据更新到数据库
        List<BaseCalendar> baseCalendarList = new ArrayList<>();
        if (map.size() > 0){
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String key = (String) iterator.next();
                BaseCalendar baseCalendar = new BaseCalendar();
                baseCalendar.setYearToDay(Integer.valueOf(DateUtil.format(DateUtil.parse(key),"yyyyMMdd")));
                baseCalendar.setBaseType("1".equals(map.get(key))?2:0);
                baseCalendarList.add(baseCalendar);
            }
        }
        if (!CollectionUtils.isEmpty(baseCalendarList)){
            baseCalendarDao.updateBatchByYearToDay(baseCalendarList);
        }
        System.out.println(JSONObject.toJSONString(map));
    }

    /**
     * 通过第三方网站获取节假日数据
     */
    public static String request(String param) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(Enumeration.CALENDAR_URL + "?year-month=" + param + "&key=" + Enumeration.CALENDAR_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String str = null;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
                stringBuffer.append("\r\n");
            }
            reader.close();
            result = stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}