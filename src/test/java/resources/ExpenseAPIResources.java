package resources;

public enum ExpenseAPIResources {

	AddExpenseAPI("/api/v1/transactions/"),
	GetExpenseAPI("/api/v1/transactions/"),
	DeleteExpenseAPI("/api/v1/transactions/");
	
	private String resource;
	
	ExpenseAPIResources(String resource) {
		
		this.resource = resource;
	
	}
	
	public String getResource() {
		
		return resource;
	}
	
}
