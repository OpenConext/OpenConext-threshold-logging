# OpenConext-threshold-logger

Not all exceptions should result in an email. The `CustomThresholdFilter` can be configured with Exception classes to ignore.

Example snippet configuration of logback.xml

```
<appender name="EMAIL" class="ch.qos.logback.classic.net.SMTPAppender">
    <smtpHost>{{ smtp_server }}</smtpHost>
    <from>{{ noreply_email }}</from>
    <to>{{ error_mail_to }}</to>
    <subject>{{ error_subject_prefix }}Unexpected error myconext</subject>
    <filter class="oidc.config.CustomThresholdFilter">
        <level>WARN</level>
        <clazz>com.nimbusds.oauth2.sdk.ParseException</clazz>
        <clazz>org.springframework.security.authentication.BadCredentialsException</clazz>
        <clazz>oidc.exceptions.RedirectMismatchException</clazz>
        <clazz>org.springframework.dao.EmptyResultDataAccessException</clazz>
        <clazz>java.lang.IllegalArgumentException</clazz>
    </filter>
</appender>
```
