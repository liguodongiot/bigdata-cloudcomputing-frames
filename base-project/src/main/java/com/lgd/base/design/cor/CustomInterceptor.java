package com.lgd.base.design.cor;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.base.design.cor</p>
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/2/8 14:16 星期四
 */
public class CustomInterceptor implements Ratify {

    @Override
    public Result deal(Chain chain) {
        Request request = chain.request();
        System.out.println("CustomInterceptor=>" + request.toString());
        String reason = request.reason();
        if (reason != null && reason.equals("事假")) {
            Request newRequest = new Request.Builder().newRequest(request)
                    .setCustomInfo(request.name() + "请的是事假，而且很着急，请领导重视一下")
                    .build();
            System.out.println("CustomInterceptor=>转发请求");
            return chain.proceed(newRequest);
        }
        return new Result(true, "同意请假");
    }

}
