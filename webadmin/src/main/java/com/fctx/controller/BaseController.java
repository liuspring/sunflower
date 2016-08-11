package com.fctx.controller;

import com.fctx.mapper.BaseMapper;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by liuJian on 2016/8/11.
 */
public class BaseController {
    /**
     * 列表页分页获取的数据
     *
     * 页面自定义的参数
     * @param pageCount
     * @param currentPage
     * @param pageSize
     * @param service
     * 数据库操作接口
     * @param paramMap
     *
     * * @param res

     * @return
     * @throws IOException
     */
    public void getAll(int pageCount, int currentPage, int pageSize,
                       BaseMapper service, Map<String, Object> paramMap,
                       HttpServletResponse res) throws IOException {
        if (pageSize == 0)
            pageSize = 10;
        if (currentPage == 0)
            currentPage = 1;
        Map<String, Object> maps = new HashMap<String, Object>();
        // 自定义参数整合
        if (paramMap != null) {
            maps.putAll(paramMap);
        }
        if (pageCount == 0) {
            int count = service.selectCount(maps);
            if (count / pageSize == 0) {
                pageCount = 1;
            } else if (count % pageSize == 0) {
                pageCount = count / pageSize;
            } else {
                pageCount = count / pageSize + 1;
            }
        }
        maps.put("start", (currentPage - 1) * pageSize);
        maps.put("rows", pageSize);

        List<Object> list = service.selectAll(maps);
        writeJson(pageCount, currentPage, pageSize, list, res);
    }

    public void writeJson(int pageCount, int currentPage, int pageSize,
                          List<Object> list, HttpServletResponse res) throws IOException {
        int size = list.size();
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"pageCount\":" + pageCount + ",");
        sb.append("\"currentPage\":" + currentPage + ",");
        sb.append("\"pageSize\":" + pageSize + ",");
        sb.append("\"count\":" + size + ",");
        sb.append("\"resultList\":");
        JSONArray jsonArray = JSONArray.fromObject(list);
        sb.append(jsonArray);
        sb.append("}");
        StringBuffer temp = new StringBuffer();
        StringTokenizer token = new StringTokenizer(sb.toString(), "\n\r\t");
        while (token.hasMoreTokens())
            temp.append(token.nextToken());
        res.setContentType("text/json; charset=utf-8");
        System.out.println(temp.toString());
        res.getWriter().print(temp.toString());
    }
}
