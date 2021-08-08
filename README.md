# Expense Tracker
A simple personal expense tracker

## Finished requirements
- Add & Edit new transaction details
    - User can input amount of transaction
    - User can choose currency: NZD or USD
    - User can choose transaction type: expense or income
    - User can choose category
    - User can choose date of transaction
    - Show exchange rate:  from USD to NZD
        - Load exchange rate when user switches currency to USD or user selects a date.
        - Show exchange rate of the chosen transaction day. The exchange rate originally comes from api.currencylayer.com
        - Use Repository pattern to provide exchange rate. If result is available in local database, use local data. Otherwise, call Web API service to fetch from currencylayer.com. The returned result is also saved in db for faster response to future requests.
- Show historical transactions
    - Show list of historical transactions
    - Each transaction shows these information: expense type, date, category, amount, transaction currency
    - Transactions are saved in local database, so user data persists across app launches.
    - For USD transactions, show converted NZD amount using the exchange rate. Also show a USA flag icon at top right corner indicating this is a USD transaction.
- Edit existing transaction
    - open existing transaction and change amount, currency, expense type, category and date.
- Delete a transaction
    - User can open a historical transaction and delete it.
- Select Category
    - User can load hardcoded category into db when app first time launches
- User can select a category for a transaction

## About Code
- Technology and framework being used
    - Kotlin, MVVM, Data binding, Navigation, Room, Hilt, Retrofit
- UI module
    - MainActivity - to load the navigation graph
    - TransactionListFragment + TransactionAdapter
    - TransactionDetailFragment
    - CategoryListFragment + CategoryAdapter
    - MyDatePickerDialog: a picker dialog supports both year-month picker and year-month-day picker
- View Models
    - TransactionListViewModel
    - TransanctionDetailViewModel
    -  CategoryListViewModel
- Repository Module
    - Repository - interface to all data services, both remote network data and local database
    - RepositoryImpl - an implementation of Repository interface. This class is instantiated by Hilt and used by Dependency Injection.
    - Transaction + TransactionDao - data and method to access ’transaction’ table
    - Category + CategoryDao - data and method to access  ‘category’ table
    - ExchangeRate + ExchangeDao - data and method to access ‘exchange_rate” table
- Network Service Module
    - ExchangeRateService - Retrofit service for api.currencylayer.com Web API
    - ExchangeRateResponse - Web API result
- Dependency Injection Module
    - DatabaseModule - provide RoomDatabase and DAOs
    - NetworkModule - Provide network service
    - RepositoryModule - provide Repository by binding to RepositoryImpl instance.
- Other
    - Util - utility methods

