# Expense Tracker
A simple personal expense tracker

## Finished Features
- Add new transaction
    - input amount of transaction
    - choose currency type: NZD or USD
    - choose transaction type: expense or income
    - choose category
    - choose date of the transaction
    - show exchange rate to convert from USD to NZD
        - load exchange rate when user switched to USD transaction or user selects a date.
        - use historical currency exchange rate of the chosen transaction day. The exchange rate comes from api.currencylayer.com
        - use Repository pattern to provide exchange rate. If result is available in local database, use local data. Otherwise, call Web API service to fetch from currencylayer.com. The returned result is also saved in db for faster future requests.
- Show historical transactions
    - show list of historical transactions
    - each transaction shows these information: expense type, date, category, amount, transaction currency
    - transactions are saved in local database, so user data persists after launch the app again.
    - for USD transactions, show converted NZD amount using the exchange rate. Also show a USA flag icon at top right corner indicating this is a USD transaction.
- Edit existing transaction
    - open existing transaction and change amount, currency, expense type, category and date.
- Select Category
    - load hardcoded category into db when app first time launches
