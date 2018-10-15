package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.util.resources.cldr.ee.CurrencyNames_ee;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations
{
    public static void main(String[] args)
    {
        Currency EUR = Currency.getInstance("EUR");
        Currency CZK = Currency.getInstance("CZK");
        ApplicationContext context = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");

        CurrencyConvertor convertor = context.getBean(CurrencyConvertor.class);
        System.out.println(convertor.convert(EUR, CZK, new BigDecimal("1")));
    }
}
