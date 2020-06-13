package filter;

import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.spi.FilterReply;

import java.util.ArrayList;
import java.util.List;

public class CustomThresholdFilter extends ThresholdFilter {

    @SuppressWarnings("unchecked")
    private ArrayList<Class> ignoreClasses = new ArrayList();

    public void addClazz(String clazz) {
        try {
            ignoreClasses.add(Class.forName(clazz));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        FilterReply decide = super.decide(event);
        if (decide.equals(FilterReply.NEUTRAL)) {
            IThrowableProxy throwableProxy = event.getThrowableProxy();
            if (throwableProxy == null) {
                return FilterReply.NEUTRAL;
            }

            if (!(throwableProxy instanceof ThrowableProxy)) {
                return FilterReply.NEUTRAL;
            }

            ThrowableProxy throwableProxyImpl = (ThrowableProxy) throwableProxy;
            Throwable throwable = throwableProxyImpl.getThrowable();
            if (ignoreClasses.contains(throwable.getClass())) {
                return FilterReply.DENY;
            }
        }
        return decide;
    }
}
