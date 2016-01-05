package com.hhz.spring.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 只读事务，不涉及回滚
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public @interface ReadOnlyTransactional {
}
