package com.hhz.spring.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 读写事务，默认隔离级别为 ReadCommitted，Exception回滚
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public @interface ReadWriteTransactional {
}
