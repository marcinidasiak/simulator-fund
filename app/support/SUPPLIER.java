package support;

import java.math.BigDecimal;

/**
 * 
 * Class stores necessary to change the static configuration.
 * @author Marcin
 * 
 */
public class SUPPLIER {
	public static BigDecimal CASH = new BigDecimal("1000.00");
	public static String CURRENCY = "PLN";
	public static BigDecimal PRICE = new BigDecimal("100.00");
	public static String USERNAME = "client";
	public static String SYMULATOR = "symulator";
	public static String LOCATION = "member.xml";

	public static String SERRIOUS_ERROR = "SERRIOUS ERROR:";
	public static String PARAMETR_AMOUNT_ERROR = "Zły parametr ilości";
	public static String PARAMETR_FUND_ERROR = "Zły parametr fundusz";
	public static String PARAMETR_TYPE_ERROR = "Zły parametr typu funduszu";
	public static String PARAMETR_KIND_ERROR = "Zły parametr rodzaju transakcji";
	public static String PARAMETR_NUMBERUNIT_ERROR_BUY = "Nie posiadasz wystarczającej liczby jednostek aby móc je kupić";
	public static String PARAMETR_NUMBERUNIT_ERROR_SELL = "Nie posiadasz wystarczającej liczby jednostek aby móc je sprzedać";

	public static String PARAMETR_NOTUSER = "Użytkownik nie jest zarejestrowany";

	public static String NUMBERUNIT_SET = "Liczba jednostek nie może być mniejsza od zera lub być nullem";
	public static String NUMBERUNIT_GET = "Liczba jednostek nie może być mniejsza od zera lub być nullem, został utworzony FundInWallet domyślnym konstruktorem i nie został zmodyfikowany";
	public static String UNITTYPE_SET = "Typ funduszu nie możebyć typ unull";
	public static String UNITTYPE_GET = "Typ funduszu nie możebyć typ unull, został utworzony FundInWallet domyślnym konstruktorem i nie został zmodyfikowany";
	public static String INVESTMENTFUND_SET = "Fundusz nie może być typu null";
	public static String INVESTMENTFUND_GET = "Fundusz nie może być typu null, został utworzony FundInWallet domyślnym konstruktorem i nie został zmodyfikowany";

	public static String CASH_SET = "Ilość gotówki w portfelu nie może być nullem";
	public static String CASH_GET = "Ilość gotówki w portfelu nie może być nullem, został utworzony Wallet z domyślnym konstruktorem i nie został zmodyfikowany";
	public static String LISTFUND_SET = "Lista funduszy w portfelu nie może być typu null";
	public static String TOTALVALUEFUNDS_SET = "Wycena funduszy nie może być nullem";
	public static String TOTALVALUEFUNDS_GET = "Wycena funduszy nie może być nullem, został utworzony Wallet z domyślnym konstruktorem i nie został zmodyfikowany";
	public static String CURRENCY_SET = "Rodzaj waluty nie może być nullem";
	public static String CURRENCY_GET = "Rodzaj waluty nie może być nullem, został utworzony Wallet z domyślnym konstruktorem i nie został zmodyfikowany";
	public static String CURRENCYFORMAT_SET = "Został wprowadzony zły format waluty";

	public static String LOCATION_NULL = "Lokalizacja jest nullem";

	public static String SUCCESS = "The operation was successful";
	public static String ERROR = "The operation resulted in an error";

}