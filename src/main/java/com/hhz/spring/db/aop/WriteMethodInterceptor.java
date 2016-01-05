package com.hhz.spring.db.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhz.spring.db.annotation.Write;
import com.hhz.spring.db.datasource.ConnectionType;
import com.hhz.spring.db.datasource.ConnectionTypeHolder;

public class WriteMethodInterceptor implements MethodInterceptor {
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (invocation.getMethod().isAnnotationPresent(Write.class)) {
			ConnectionType origType = ConnectionTypeHolder.get();
			try {
				ConnectionType newType = new ConnectionType(
						ConnectionType.READ_WRITE, null);
				ConnectionTypeHolder.set(newType);
				return invocation.proceed();
			} catch (Throwable throwable) {
				LOGGER.warn("error while processing write method", throwable);
				throw throwable;
			} finally {
				if (origType != null) {
					ConnectionTypeHolder.set(origType);
				} else {
					ConnectionTypeHolder.release();
				}
			}
		}
		return invocation.proceed();
	}
}
