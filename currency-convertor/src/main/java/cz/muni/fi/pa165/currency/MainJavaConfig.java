package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainJavaConfig
{

    public static void main(String[] args)
    {
        Currency EUR = Currency.getInstance("EUR");
        Currency CZK = Currency.getInstance("CZK");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringJavaConfig.class, LoggingAspect.class);

        CurrencyConvertor convertor = context.getBean(CurrencyConvertor.class);
        System.out.println(convertor.convert(EUR, CZK, new BigDecimal("1")));
    }
}
