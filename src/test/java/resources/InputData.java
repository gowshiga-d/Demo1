package resources;

import pojo.AddTransaction;

public class InputData {
	
	public AddTransaction addTransactionPayload(String transaction, String amount)
	{
		AddTransaction obj = new AddTransaction();
		obj.setText(transaction);
		obj.setAmount(amount);
		return obj;
	}

}
