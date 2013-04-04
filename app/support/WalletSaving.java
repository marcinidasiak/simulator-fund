package support;

import java.math.BigDecimal;
import java.util.List;

import enums.InvestmentFund;

import model.FundInWallet;
import model.Wallet;

public class WalletSaving {
	 private Wallet wallet;
	
	public void changeCash(BigDecimal money){
		if(!((money.compareTo(BigDecimal.ZERO)<0)&&(money.abs().compareTo(wallet.getCash())>0))){
			wallet.setCash(wallet.getCash().add(money));
		}
	}
	
	public WalletSaving(Wallet wallet) {
		super();
		this.wallet = wallet;
	}

	public BigDecimal valuationWallet(InvestmentFund[] data){
		BigDecimal sum = BigDecimal.ZERO;
		for(FundInWallet fund: wallet.getFunds()){
			sum=sum.add(new BigDecimal(fund.getNumberUnit()).multiply(InvestmentFund.valueOf(fund.getCode()).getPrice()));
		}
		wallet.setTotalValueFunds(sum);
		return sum;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
}
