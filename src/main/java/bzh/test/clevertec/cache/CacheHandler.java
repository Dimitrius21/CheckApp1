package bzh.test.clevertec.cache;

import bzh.test.clevertec.dao.SimpleDao;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class CacheHandler implements InvocationHandler {
    private SimpleDao obj;
    private Cacheable cache;

    public CacheHandler(SimpleDao obj, Cacheable cache) {
        this.obj = obj;
        this.cache = cache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return switch (method.getName()) {
            case "getById" -> getByIdHandle(proxy, method, args);
            case "deleteById" -> deleteHandle(proxy, method, args);
            case "create" -> createHandle(proxy, method, args);
            case "update" -> updateHandle(proxy, method, args);
            default -> method.invoke(obj, args);
        };
    }

    private Object getByIdHandle(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object value = cache.get(args[0]);
        if (value != null) {
            return value;
        } else {
            value = method.invoke(obj, args);
            cache.put(args[0], value);
            return value;
        }
    }

    private Object deleteHandle(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object res = method.invoke(obj, args);
        cache.remove(args[0]);
        return res;
    }

    private Object updateHandle(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object res = method.invoke(obj, args);
        cache.put(args[0], args[1]);
        return res;
    }

    private Object createHandle(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object res = method.invoke(obj, args);
        cache.put(res, args[1]);
        return res;
    }

    public static SimpleDao checkCaching(SimpleDao dao, Class<? extends SimpleDao> interfaceClass){
        if (dao.getClass().getDeclaredAnnotation(CachedEntity.class)!=null){
            Cacheable cache = CacheFabric.getCacheInstance();
            CacheHandler handler = new CacheHandler(dao, cache);
            dao = (SimpleDao) Proxy
                    .newProxyInstance(interfaceClass.getClassLoader(),
                            new Class[]{interfaceClass}, handler);
        }
        return dao;
    }
}
