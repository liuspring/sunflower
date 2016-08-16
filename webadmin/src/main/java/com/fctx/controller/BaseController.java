package com.fctx.controller;

import com.fctx.constant.Constants;
import com.fctx.constant.ErrorCode;
import com.fctx.mapper.BaseMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by liuJian on 2016/8/11.
 */
public abstract class BaseController {
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
    /**
     * 返回成功
     * @return
     */
    protected final String doSuccessResponse() {
        JSONObject json = new JSONObject();
        json.put(Constants.KEY_CODE, ErrorCode.SUCCESS);
        json.put(Constants.KEY_RESULT, "success");
        return json.toString();
    }

    /**
     * 返回成功，包含数据
     * @param key 不包括数组类型数据
     * @return
     */
    protected final String doSuccessResponse(String key, Object value) {
        JSONObject json = new JSONObject();
        json.put(Constants.KEY_CODE, ErrorCode.SUCCESS);
        json.put(key, value);

        return json.toString();
    }

    /**
     * 返回成功，包含数据
     * @param obj 不包括数组类型数据
     * @return
     */
    protected final String doSuccessResponse(Object obj) {
        JSONObject json = new JSONObject();
        json.put(Constants.KEY_CODE, ErrorCode.SUCCESS);
        json.put(Constants.KEY_RESULT, JSONObject.fromObject(obj));

        return json.toString();
    }

    /**
     * 返回成功，包含数据
     * @param list 不包括数组类型数据
     * @return
     */
    protected final String doSuccessResponse(List<? extends Object> list) {
        JSONObject json = new JSONObject();
        json.put(Constants.KEY_CODE, ErrorCode.SUCCESS);
        json.put(Constants.KEY_RESULT, JSONArray.fromObject(list));

        return json.toString();
    }

    /**
     * 返回成功，包含数据
     * @param list 不包括数组类型数据
     * @param count 总数
     * @return
     */
    protected final String doSuccessResponse(List<? extends Object> list,int count) {
        JSONObject json = new JSONObject();
        json.put(Constants.KEY_CODE, ErrorCode.SUCCESS);
        json.put(Constants.KEY_COUNT, count);
        json.put(Constants.KEY_RESULT, JSONArray.fromObject(list));
        return json.toString();
    }

    /**
     * 返回失败
     * @param list
     * @return
     */
    protected final String doFailedResponse(int code, List<? extends Object> list) {
        JSONObject json = new JSONObject();
        json.put(Constants.KEY_CODE, code);
        json.put(Constants.KEY_RESULT, JSONArray.fromObject(list));

        return json.toString();
    }

    /**
     * 返回失败
     * @param key
     * @return
     */
    protected final String doNullParamFailedResponse(String key) {
        JSONObject json = new JSONObject();
        json.put(Constants.KEY_CODE, ErrorCode.PARAM_NULL);
        json.put(Constants.KEY_RESULT, "param \"" + key + "\" requried");

        return json.toString();
    }

    /**
     * 返回失败
     * @param error
     * @return
     */
    protected final String doFailedResponse(int code, String error) {
        JSONObject json = new JSONObject();
        json.put(Constants.KEY_CODE, code);
        json.put(Constants.KEY_ERROR, error);

        return json.toString();
    }

    /**
     * 处理get请求是参数乱码问题
     * @param target
     * @param request
     * @return
     * @throws Exception
     */
    protected final String urlDecode(String target, HttpServletRequest request) throws Exception {
        ServletContext servletContext = request.getSession().getServletContext();
        String serverInfo = servletContext.getServerInfo();
        if (!serverInfo.contains("Tomcat/8")) {
            byte[] bytes = target.getBytes("iso8859-1");
            return URLDecoder.decode(new String(bytes, "utf-8"), "utf-8");
        }
        return target;
    }

    /**
     * 解析post请求参数内容
     * @param request
     * @return
     * @throws Exception
     */
    protected String parseRequest(HttpServletRequest request) throws Exception {
        return new String(requestData(request), "UTF-8");
    }

    /**
     * 解析post请求参数内容
     * @param request
     * @return
     * @throws Exception
     */
    protected byte[] requestData(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        InputStream inputStream = request.getInputStream();
        int len = request.getContentLength();
        if (len < 0) {
            throw new Exception("request param is empty!");
        }

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream(len);
        byte[] buf = new byte[1024];
        while (len > 0) {
            int i = inputStream.read(buf);
            len -= i;
            byteOut.write(buf, 0, i);
        }

        return byteOut.toByteArray();
    }

    protected final JSONObject parseJsonObject(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return new JSONObject();
        }

        return JSONObject.fromObject(jsonString);
    }

    protected final Map<String, Object> parseMap(String jsonString) {
        Map<String, Object> ret = new HashMap<String, Object>();

        if (jsonString == null || jsonString.trim().isEmpty()) {
            return ret;
        }
        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        Iterator<?> it = jsonObject.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            ret.put(key, jsonObject.get(key));
        }
        return ret;
    }
    /**
     * 将url参数转换成map
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, Object> getUrlParams(String param) {
        Map<String, Object> map = new HashMap<String, Object>(0);
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成url
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }
}
