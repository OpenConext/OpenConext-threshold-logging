package filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.spi.FilterReply;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CustomThresholdFilterTest {

    @Test
    public void decide() {
        CustomThresholdFilter filter = new CustomThresholdFilter();

        filter.setLevel("INFO");
        filter.addClazz("java.lang.IllegalArgumentException");

        ILoggingEvent event = Mockito.mock(ILoggingEvent.class);
        when(event.getThrowableProxy())
                .thenReturn(new ThrowableProxy(new IllegalArgumentException("test")));

        FilterReply decision = filter.decide(event);
        assertEquals(FilterReply.DENY, decision);
    }
}