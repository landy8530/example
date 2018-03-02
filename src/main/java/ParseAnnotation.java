import com.test.ann.Description;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author landyl
 * @create 2018-01-12:35 PM
 * 注解解析类
 */
public class ParseAnnotation {

    public static void main(String[] args){
        //使用类加载器加载类
        try {
            Class c = Class.forName("com.test.ann.Person");//加载使用了定义注解的类
            //找到类上的注解
            boolean isExist = c.isAnnotationPresent(Description.class);
            if(isExist){
                //拿到注解示例
                Description d = (Description)c.getAnnotation(Description.class);
                System.out.println(d.value());
            }
            //找到方法上的注解
            Method[] ms = c.getMethods();
            for(Method m : ms){
                boolean isMExist = m.isAnnotationPresent(Description.class);
                if(isMExist){
                    Description d = m.getAnnotation(Description.class);
                    System.out.println(d.value());
                }
            }
            //另外一种注解方式
            for(Method m:ms){
                Annotation[] as = m.getAnnotations();
                for(Annotation a:as){
                    if(a instanceof Description){
                        Description d = (Description)a;
                        System.out.println(d.value());
                    }
                }

            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
