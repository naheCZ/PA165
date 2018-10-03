package cz.muni.fi.pa165.currency;

import org.junit.Test;

import javax.xml.transform.Source;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.*;
import java.util.Currency;

public class CurrencyConvertorImplTest
{
    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency INR = Currency.getInstance("INR");
    private static final Currency CZK = Currency.getInstance("CZK");
    private static final Currency EUR = Currency.getInstance("EUR");
    private final ExchangeRateTable rateTable = mock(ExchangeRateTable.class);
    private final CurrencyConvertor convertor = new CurrencyConvertorImpl(rateTable);

    @Test
    public void testConvert() throws ExternalServiceFailureException
    {
        // Don't forget to test border values and proper rounding.
        when(rateTable.getExchangeRate(USD, INR)).thenReturn(new BigDecimal("73.152"));
        when(rateTable.getExchangeRate(EUR, CZK)).thenReturn(new BigDecimal("25.00"));
        when(rateTable.getExchangeRate(CZK, CZK)).thenReturn(new BigDecimal("1.00"));

        assertThat(convertor.convert(USD, INR, new BigDecimal("1.50"))).isEqualTo(new BigDecimal("109.73"));
        assertThat(convertor.convert(USD, INR, new BigDecimal("1"))).isEqualTo(new BigDecimal("73.15"));
        assertThat(convertor.convert(EUR, CZK, new BigDecimal("0.5"))).isEqualTo(new BigDecimal("12.50"));
        assertThat(convertor.convert(CZK, CZK, new BigDecimal("5.2591"))).isEqualTo(new BigDecimal("5.26"));
        assertThat(convertor.convert(CZK, CZK, new BigDecimal("5.2549"))).isEqualTo(new BigDecimal("5.25"));

    }

    @Test
    public void testConvertWithNullSourceCurrency() throws IllegalArgumentException
    {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> convertor.convert(null, USD, BigDecimal.ONE))
                .withMessage("Null value!");
    }

    @Test
    public void testConvertWithNullTargetCurrency() throws IllegalArgumentException
    {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> convertor.convert(USD, null, BigDecimal.ONE))
                .withMessage("Null value!");
    }

    @Test
    public void testConvertWithNullSourceAmount() throws IllegalArgumentException
    {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> convertor.convert(INR, USD, null))
                .withMessage("Null value!");
    }

    @Test
    public void testConvertWithUnknownCurrency() throws UnknownExchangeRateException, ExternalServiceFailureException
    {
        when(rateTable.getExchangeRate(INR, USD)).thenReturn(null);

        assertThatExceptionOfType(UnknownExchangeRateException.class)
                .isThrownBy(() -> convertor.convert(INR, USD, BigDecimal.ONE))
                .withMessage("Unkown exchange");
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        ExternalServiceFailureException ex = new ExternalServiceFailureException("External error");

        when(rateTable.getExchangeRate(USD, INR)).thenThrow(ex);

        assertThatExceptionOfType(ExternalServiceFailureException.class)
                .isThrownBy(() -> convertor.convert(USD, INR, BigDecimal.ONE))
                .withMessage("External error");
    }

}
