package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    //private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) throws ExternalServiceFailureException {
        if(sourceCurrency == null || targetCurrency == null || sourceAmount == null)
            throw new IllegalArgumentException("Null value!");

        BigDecimal result;
        BigDecimal rate;

        rate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);

        if(rate == null)
            throw new UnknownExchangeRateException("Unkown exchange");



        result = rate.multiply(sourceAmount);
        result = result.setScale(2, RoundingMode.HALF_EVEN);

        return result;
    }

}
