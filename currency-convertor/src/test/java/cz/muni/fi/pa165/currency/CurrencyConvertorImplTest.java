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
    private final ExchangeRateTable rateTable = mock(ExchangeRateTable.class);
    private final CurrencyConvertor convertor = new CurrencyConvertorImpl(rateTable);

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.
        when(rateTable.getExchangeRate(USD, INR)).thenReturn(new BigDecimal("73.152"));

        assertThat(convertor.convert(USD, INR, new BigDecimal("1.50"))).isEqualTo(new BigDecimal("109.73"));
        assertThat(convertor.convert(USD, INR, new BigDecimal("1"))).isEqualTo(new BigDecimal("73.15"));

    }

    @Test
    public void testConvertWithNullSourceCurrency() throws IllegalArgumentException, ExternalServiceFailureException {

        IllegalArgumentException ex = new IllegalArgumentException("Null value!");
        when(rateTable.getExchangeRate(null, USD)).thenThrow(ex);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> convertor.convert(null, USD, BigDecimal.ONE))
                .withMessage("Null value!")
                .withCause(ex);
    }

    @Test
    public void testConvertWithNullTargetCurrency() throws IllegalArgumentException, ExternalServiceFailureException {

        IllegalArgumentException ex = new IllegalArgumentException("Null value!");
        when(rateTable.getExchangeRate(USD, null)).thenThrow(ex);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> convertor.convert(USD, null, BigDecimal.ONE))
                .withMessage("Null value!")
                .withCause(ex);
    }

    @Test
    public void testConvertWithNullSourceAmount() throws IllegalArgumentException, ExternalServiceFailureException {
        IllegalArgumentException ex = new IllegalArgumentException("Null value!");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> convertor.convert(INR, USD, null))
                .withMessage("Null value!")
                .withCause(ex);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws UnknownExchangeRateException  {
        UnknownExchangeRateException ex = new UnknownExchangeRateException("Unkown exchange");

        assertThatExceptionOfType(UnknownExchangeRateException.class)
                .isThrownBy(() -> convertor.convert(INR, USD, BigDecimal.ONE))
                .withMessage("Unkown exchange")
                .withCause(ex);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        ExternalServiceFailureException ex = new ExternalServiceFailureException("External error");

        when(rateTable.getExchangeRate(USD, INR)).thenThrow(ex);

        assertThatExceptionOfType(ExternalServiceFailureException.class)
                .isThrownBy(() -> convertor.convert(USD, INR, BigDecimal.ONE))
                .withMessage("External error")
                .withCause(ex);
    }

}
