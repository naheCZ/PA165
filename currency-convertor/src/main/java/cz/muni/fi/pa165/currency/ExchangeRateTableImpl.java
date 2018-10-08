package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;

public class ExchangeRateTableImpl implements ExchangeRateTable
{

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException
    {
        if (sourceCurrency.equals(Currency.getInstance("EUR")) && targetCurrency.equals(Currency.getInstance("CZK")))
            return new BigDecimal("27");
        else
            return null;
    }
}
