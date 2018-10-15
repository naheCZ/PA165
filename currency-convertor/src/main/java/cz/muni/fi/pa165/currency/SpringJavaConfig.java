package cz.muni.fi.pa165.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.inject.Inject;

@Configuration
@EnableAspectJAutoProxy
public class SpringJavaConfig
{
    @Bean
    public ExchangeRateTable exchangeRateTable()
    {
        return new ExchangeRateTableImpl();
    }

    @Bean
    public CurrencyConvertor currencyConvertor(ExchangeRateTable table)
    {
        return new CurrencyConvertorImpl(table);
    }
}
