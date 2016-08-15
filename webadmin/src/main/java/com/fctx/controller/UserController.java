package com.fctx.controller;

import com.fctx.model.User;
import com.fctx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuJian on 2016/8/11.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/getAllList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllList(HttpServletRequest req, HttpServletResponse res,
                             User model,ModelMap map) {
        Integer a=userService.getMaxId();
        map.addAttribute("list", userService.getAll());
        return "demo/user_list";
    }
    @RequestMapping(value = {"/getList_{pageCount}_{currentPage}_{pageSize}.do"}, method = {
            RequestMethod.GET, RequestMethod.POST})
    public void getList(HttpServletRequest req, HttpServletResponse res,
                        User model, @PathVariable("pageCount")
                        int pageCount, @PathVariable("currentPage")
                        int currentPage, @PathVariable("pageSize")
                        int pageSize) throws Exception {

        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("loginname", model.getLoginname());
        getAll(pageCount, currentPage, pageSize, userService, maps, res);

    }

    @RequestMapping(value = {"/getDetail_{id}.do"}, method = {RequestMethod.GET})
    public String getDetail(HttpServletRequest req, @PathVariable
    int id, ModelMap modelMap) {
        // 详情
        if (id > 0) {
            Object model = userService.getEntityById(id);
            modelMap.addAttribute("model", model);
        }
        return "demo/user_detail";
    }

    @RequestMapping(value = {"/saveUser.do"}, method = {RequestMethod.POST})
    @ResponseBody
    public String saveUser(User user, HttpServletRequest request) {
        // 判断是否重复
        // 新增时，密码加密
        // 新增用户
        userService.insert(user);
        return "success";
    }

    @RequestMapping("/delUser")
    @ResponseBody
    public String delUser(int id, HttpServletRequest request) {
        userService.delById(id);
        return "success";
    }
}
