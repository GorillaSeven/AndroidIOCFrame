package com.mengqi.dong.baseioclibrary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dong on 2017/5/7.
 *
 * Description: Viewz注解的Annotation
 */

//@Target(ElementType.METHOD) 代表Annotation注解的位置  FIELD属性  METHOD方法上  TYPE类上 CONSTRUCTOR构造函数上
@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.SOURCE) 表示什么时候生效  CLASS编译时  RUNTIME运行时 SOURCE源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {

}
