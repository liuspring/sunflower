package com.fctx.controller.account;

import com.fctx.constant.ErrorCode;
import com.fctx.controller.BaseController;
import com.fctx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by liuJian on 2016/8/15.
 */
@Controller
@RequestMapping(value = "account")
public class AccountController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/WEB-INF/views/account/login");
        return mav;
    }

    /*
    * @param
    *
    * */
    @RequestMapping(value = "/loginSubmit.do",method = RequestMethod.POST)
    public String loginSubmit(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws Exception {
        String jsonString = this.parseRequest(request);
        Map<String, Object> params = this.parseMap(jsonString);
        params.put("checktype","select");
        String vcode=((String)params.get("vcode")).trim();
        String code=session.getAttribute("code").toString();
        if (vcode.equalsIgnoreCase(code)) {  //忽略验证码大小写
            int count= userService.checkExist(params);
            if(count==1){
                return this.doSuccessResponse();
            }
            else {
                return this.doFailedResponse(ErrorCode.FAILED,"账号或密码有误，请重新输入");
            }
        }else {
            return this.doFailedResponse(ErrorCode.FAILED,"输入验证码错误");
        }
    }
}
