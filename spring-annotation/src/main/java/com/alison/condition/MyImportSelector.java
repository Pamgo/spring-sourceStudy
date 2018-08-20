package com.alison.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by OKali on 2018/8/14.
 */
// 自定义逻辑返回需要导入的组件
public class MyImportSelector implements ImportSelector {

    /**
     * @param importingClassMetadata  当前标注@Import组件的类的所有注解信息
     * @return 导入到容器中的组件全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //importingClassMetadata.get
        return new String[]{"com.alison.bean.Blue","com.alison.bean.Yellow"};
    }
}
