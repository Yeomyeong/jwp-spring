package hello;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by wymstar on 8/11/16.
 */
public class UppercaseMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String ret = (String)methodProxy.invokeSuper(o, objects);
        return ret.toUpperCase();
    }
}
