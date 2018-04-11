package io.github.efrem.springsecurityrest.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class AclMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    private final MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler;

    @Autowired
    public AclMethodSecurityConfiguration(MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler) {
        this.defaultMethodSecurityExpressionHandler = defaultMethodSecurityExpressionHandler;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return defaultMethodSecurityExpressionHandler;
    }
}
