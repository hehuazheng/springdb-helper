package com.hhz.spring.db.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhz.spring.db.annotation.Read;
import com.hhz.spring.db.datasource.ConnectionType;
import com.hhz.spring.db.datasource.ConnectionTypeHolder;

public class ReadMethodInterceptor implements MethodInterceptor {
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Read read = invocation.getMethod().getAnnotation(Read.class);
		if (read != null) {
			ConnectionType origType = ConnectionTypeHolder.get();
			try {
				ConnectionType newType = new ConnectionType(
						ConnectionType.READ, read.value());
				ConnectionTypeHolder.set(newType);
				return invocation.proceed();
			} catch (Throwable throwable) {
				LOGGER.warn("error while processing read method", throwable);
				throw throwable;
			} finally {
				if (origType != null) {
					ConnectionTypeHolder.set(origType);
				} else {
					ConnectionTypeHolder.release();
				}
			}
		} else {
			return invocation.proceed();
		}
	}
}
