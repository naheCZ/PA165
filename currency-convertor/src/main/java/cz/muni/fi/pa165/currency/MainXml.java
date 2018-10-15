package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml
{

    public static void main(String[] args)
    {
        Currency EUR = Currency.getInstance("EUR");
        Currency CZK = Currency.getInstance("CZK");
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");

        CurrencyConvertor convertor = (CurrencyConvertorImpl) context.getBean("Convertor");
        System.out.println(convertor.convert(EUR, CZK, new BigDecimal("1")));
    }
}
