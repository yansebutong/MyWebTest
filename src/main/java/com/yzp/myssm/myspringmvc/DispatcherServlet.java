package com.yzp.myssm.myspringmvc;

import com.yzp.myssm.ioc.BeanFactory;
import com.yzp.myssm.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        // 之前在此处主动创建IOC容器
        // 现在改为通过上下文获取
//        beanFactory = new ClassPathXmlApplicationContext();


        ServletContext application = getServletContext();
        Object beanFactoryObj = application.getAttribute("beanFactory");
        if(beanFactoryObj != null) {
            beanFactory = (BeanFactory) beanFactoryObj;
        } else {
            throw new RuntimeException("IOC容器获取失败！");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取url
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1, servletPath.length()-3);

        Object controllerBeanObj = beanFactory.getBean(servletPath);

        String operate = req.getParameter("operate");
        if(StringUtil.isEmptyOrNull(operate)) {
            operate = "index";
        }

        // 获取需要调用的方法，并调用
        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for(Method method : methods) {
                if(operate.equals(method.getName())) {
                    // 1. 统一获取请求参数
                    // 获取当前方法的参数，返回参数数组
                    Parameter[] parameters = method.getParameters();
                    // parameterValues用来承载参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for(int i = 0; i < parameterValues.length; i++) {
                        // 如果参数名是req, resp, session, 那么不是从req中获取参数
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if("req".equals(parameterName)) {
                            parameterValues[i] = req;
                        }else if("resp".equals(parameterName)) {
                            parameterValues[i] = resp;
                        }else if("session".equals(parameterName)) {
                            parameterValues[i] = req.getSession();
                        }else {
                            String parameterValue = req.getParameter(parameterName);
                            String typeName = parameter.getType().getName();

                            Object parameterObj = parameterValue;

                            if(parameterObj != null) {
                                if("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }

                            parameterValues[i] = parameterObj;
                        }
                    }

                    // 2. controller组件方法调用
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj, parameterValues);

                    // 3. 视图处理
                    String methodReturnStr = (String) returnObj;
                    if(methodReturnStr.startsWith("redirect:")) {
                        // 需要重定向
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    } else {
                        // 跳转到编辑页面或添加页面
                        super.processTemplate(methodReturnStr, req, resp);
                    }
                }
            }

//            } else {
//                throw new RuntimeException("operate值非法");
//            }
        } catch ( Exception e) {
            e.printStackTrace();
            throw new DispatcherServletException("DispatcherServlet出错了....");
        }
    }
}
