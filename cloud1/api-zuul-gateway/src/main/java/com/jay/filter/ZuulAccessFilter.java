package com.jay.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Zuul允许开发者在API网关上通过定义过滤器来实现对请求的拦截与过滤
 * <p>
 * filterType：过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。这里定义为pre，代表会在请求被路由之前执行。
 * filterOrder：过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行。
 * shouldFilter：判断该过滤器是否需要被执行。这里我们直接返回了true，因此该过滤器对所有请求都会生效。实际运用中我们可以利用该函数来指定过滤器的有效范围。
 * run：过滤器的具体逻辑。这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由，然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，当然我们也可以进一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。
 */
@Slf4j
public class ZuulAccessFilter extends ZuulFilter {

    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * zuul的过滤器类型
     * PRE：这种过滤器在请求到达Origin Server之前调用。比如身份验证，在集群中选择请求的Origin Server，记log等，workshop中用的就是这种过滤器。
     * ROUTING：在这种过滤器中把用户请求发送给Origin Server。发送给Origin Server的用户请求在这类过滤器中build。并使用Apache HttpClient或者Netfilx Ribbon发送给Origin Server。
     * POST：这种过滤器在用户请求从Origin Server返回以后执行。比如在返回的response上面加response header，做各种统计等。并在该过滤器中把response返回给客户。
     * ERROR：在其他阶段发生错误时执行该过滤器。
     * 客户定制：比如我们可以定制一种STATIC类型的过滤器，用来模拟生成返回给客户的response。
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        // 获取请求uri
//        String uri = request.getRequestURI();
//        // 为了不影响其他路由，uri中含有 rest-tpl-sale 才执行本路由器
//        if(uri.indexOf("rest-tpl-sale") != -1) {
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }

    /**
     * 限制条件：
     *      1.必须有  accessToken  参数
     *      2.仅允许本地IP访问
     *
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        //打印请求地址的处理方法
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }else{
            //配置本地IP白名单，生产环境可放入数据库或者redis中
            List<String> ips=new ArrayList<String>();

            ips.add("127.0.0.1");
            ips.add("0:0:0:0:0:0:0:1");

            if (!ips.contains(getIpAddr(request))){
                log.warn("IP地址校验不通过！！！");
                ctx.setResponseStatusCode(401);
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody("IpAddr is forbidden!");
                return null;
            }
        }
        log.info("access token ok");
        return null;
    }


    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public  String getIpAddr(HttpServletRequest request){

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
