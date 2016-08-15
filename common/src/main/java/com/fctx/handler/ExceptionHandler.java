package com.fctx.handler;

import com.fctx.constant.Constants;
import com.fctx.constant.ErrorCode;
import com.fctx.constant.ModuleType;
import com.fctx.ex.SunException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuJian on 2016/8/15.
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o,
                                         Exception e) {
        if (!response.isCommitted()) {
            PrintWriter writer = null;
            try {
                response.setCharacterEncoding("UTF-8");
                writer = response.getWriter();

                Map<String, Object> result = new HashMap<String, Object>();

                result.put(Constants.KEY_ERROR, e.getMessage());
                result.put(Constants.KEY_CODE, ErrorCode.UK_ERROR);
                result.put(Constants.KEY_MODULE, ModuleType.UNKNOWN);

                if (e instanceof SunException) {
                    SunException te = (SunException) e;
                    result.put(Constants.KEY_CODE, te.code());
                    result.put(Constants.KEY_MODULE, te.module());
                }

                JSONObject jsonObj = JSONObject.fromObject(result);
                writer.print(jsonObj.toString());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                writer.close();
            }
        }
        return null;
    }
}
