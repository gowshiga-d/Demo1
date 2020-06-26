Feature: Validating Expense APIs

Scenario Outline: Verify if expense/Income is successfully added using AddExpenseAPI
    Given Add expense or Income with "<transactionName>" and "<amount>"
    When user calls "AddExpenseAPI" with "POST" http request
    Then it returns status code 201
    And verify transaction created maps to "<transactionName>" using "GetExpenseAPI"
    
Examples:
	|amount	|transactionName|
	|+50000 |Salary			|
	|+10000	|Interest 		|
	|-10000	|Rent			|
	|-8000	|Food			|
	|-5000	|Misc			|
    
Scenario Outline: Verify is expense/Income is successfully deleted using DeleteExpenseAPI
	Given Get transactions using "GetExpenseAPI" and delete expense or Income with "<transactionName>"
	When user calls "DeleteExpenseAPI" with "DELETE" http request
	Then it returns status code 200
	And verify transaction is deleted with "Transaction deleted" message
	
Examples:
	|transactionName|
	|Rent			|
	
Scenario: Verify if expense/Income is not adding without transaction name
	Given Add expense or Income with "" and "-5000"
	When user calls "AddExpenseAPI" with "POST" http request
	Then it returns status code 400
	And verify the error message "Please enter a text for transaction"
	
Scenario: Verify if expense/Income is not deleting without proper transaction Id
	Given Delete expense or Income with invalid transaction Id "5ef1dd57e1f0017ba3d5b"
	When user calls "DeleteExpenseAPI" with "DELETE" http request
	Then it returns status code 404
	And verify the error message "Transaction not found"
	

	