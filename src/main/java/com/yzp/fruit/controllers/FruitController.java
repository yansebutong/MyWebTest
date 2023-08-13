package com.yzp.fruit.controllers;

import com.yzp.fruit.pojo.Fruit;
import com.yzp.fruit.service.FruitService;
import com.yzp.myssm.myspringmvc.ViewBaseServlet;
import com.yzp.myssm.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FruitController extends ViewBaseServlet {

    private FruitService fruitService = null;

    protected String index(String oper, String keyword, Integer pageNo, HttpServletRequest req) {
        HttpSession session = req.getSession();

        if(pageNo == null) {
            pageNo = 1;
        }

        if(StringUtil.isNotEmptyOrNull(oper) && "search".equals(oper)) {
            pageNo = 1;
            if(StringUtil.isEmptyOrNull(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if(keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        session.setAttribute("pageNo", pageNo);

        List<Fruit> fruitList = fruitService.getFruitList(keyword, pageNo);
        session.setAttribute("fruitList", fruitList);

        int pageCount = fruitService.getPageCount(keyword);
        session.setAttribute("pageCount", pageCount);

        return "index";
    }

    protected String jumpToAdd(HttpServletRequest req) throws IOException {
        return "add";
    }

    protected String add(String fname, Integer price, Integer fcount, String remark) {
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitService.addFruit(fruit);
        return "redirect:fruit.do";
    }

    protected String del(Integer fid) {
        if(fid != null){
            fruitService.delFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    protected String update(Integer fid, String fname, Integer price, Integer fcount, String remark) {
        fruitService.updateFruit(new Fruit(fid, fname, price, fcount, remark));
        return "redirect:fruit.do";
    }

    protected String edit(Integer fid, HttpServletRequest req) {
        if(fid != null) {
            Fruit fruit = fruitService.getFruitByFid(fid);
            req.setAttribute("fruit", fruit);
//            processTemplate("edit", req, resp);
            return "edit";
        }
        return "error";
    }

//    protected void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        index(req, resp);
//    }
}
