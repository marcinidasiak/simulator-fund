package model;

import java.math.BigDecimal;
import java.util.Set;

public class WalletSaving {
	BigDecimal cash;
	Set<FundInWallet> funds;
	
	public void changeCash(BigDecimal money){
		if(!((money.compareTo(BigDecimal.ZERO)<0)&&(money.abs().compareTo(cash)>0))){
			cash=cash.add(money);
		}
	}
	
	public BigDecimal valuationWallet(){
		return null;
	}
}
