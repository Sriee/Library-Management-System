# Library Management System

A Database host application that interfaces with a backend SQL database implementing a Library Management System.

![alt Title](https://cloud.githubusercontent.com/assets/8402606/15100480/b4ebeed6-1538-11e6-9216-0aed23f1499d.jpg)

##Functional Requirements

### Users 

The users of the system are Library Administrators who use the application to perform library management tasks. This system is not intended for the use of general public who visit the library. 

### Graphical User Interface (GUI) and Overall Design

All interfaces with the Library Management System(queries, updates, deletes, etc.) should be done from a graphical user interface. GUI application should interface with the Library database via an appropriate MySQL connector to perform user actions. 

### Book Search and Availability

Using the GUI, users should be able to search for a book, given any combination of _ISBN_, _title_, and/or _Author(s)_.Application should support substring matching. 

The following should be displayed in the search results:
 * ISBN
 * Book title
 * Book author(s) 
 * Branch info
 * branch_id
 * branch_name
 * How many copies are owned/inventoried by a specified branch
 * Book availability at each branch

 **Each book at a given branch should display on a single line.**
 
### Book Loans 

#### Checking Out Books

 * Using the GUI,users should be able to check out a book, given the combination of **BOOK_COPIES**(_Isbn_, _branch_id_) and **BORROWER**(_Card_no_)
 * A new tuple should be created in **BOOK_LOANS**. Generate a new unique primary key for _loan_id_. The _date_out_ should be today’s date. The _due_date_ should be 14 days after the _date_out_.
 * Each **BORROWER** is permitted a maximum of 3 **BOOK_LOANS**. If a **BORROWER** already has 3 **BOOK_LOANS**, then the checkout (i.e. create new **BOOK_LOANS** tuple)
 should fail and return a useful error message.
 * If the number of **BOOK_LOANS** for a given book at a branch already equals the _No_of_copies_ (i.e. There are no more book copies available at a _library_branch_), then the checkout should fail and return a useful error message.

#### Checking In Books

 * Using the GUI,users should be able to check in a book. Be able to locate **BOOK_LOANS** tuples by searching on any of _book_id_, _Card_no_, and/or any part of **BORROWER** name. Once located, provide a way of selecting one of potentially multiple results and a button (or menu item) to check in (i.e. enter a value for _date_in_ in corresponding **BOOK_LOANS** tuple).

### Borrower Management

 * Using the GUI,users should be able to create new borrowers in the system.
 * All name, SSN, and address attributes are required to create a new account (i.e. value must be not null).
 * You must devise a way to automatically generate new _card_no_ primary keys for each new tuple that uses a compatible format with the existing borrower IDs.
 * Borrowers are allowed to possess exactly one library card. If a new borrower is attempted withe same SSN, then your system should reject and return a useful error message.

### Fines

 * _fine_amt_ attribute is a dollar amount that should have two decimal places.
 * _paid_ attribute is a boolean value (or integer 0/1) that idicates whether a fine has been paid.
 * Fines are assessed at a rate of $0.25/day (twenty-five cents per day).
 * You should provide a button, menu item, etc. that updates/refreshes entries in the **FINES** table.
 * There are two scenarios for late books
	 - Late books that have been returned — the fine will be [(the difference in days between the _due_date_ and _date_in_) * $0.25].
	 - Late book that are still out — the estimated fine will be [(the difference between the _due_date_ and _TODAY_) * $0.25].
 * If a row already exists in **FINES** for a particular late **BOOK_LOANS** record, then
	 - If paid == FALSE, do not create a new row, only update the _fine_amt_ if different than current value.
	 - If paid == TRUE, do nothing.
 * Provide a mechanism for librarians to enter payment of fines (i.e. to update a **FINES** record where paid == TRUE)
	 - Do not allow payment of a fine for books that are not yet returned.
	 - Display of Fines should be grouped by _card_no_. i.e. SUM the _fine_amt_ for each Borrower.
	 - Display of Fines should provide a mechanism to filter out previously paid fines (either by default or choice)

## Library Schema 

The Backend database for the Library Management System. Uses MySQL database for storing data. The Library Schema is shown below

![alt Title](https://cloud.githubusercontent.com/assets/8402606/15100483/da926782-1538-11e6-8370-f5eabef92f85.jpg)

## Data 

 * Baseline data to initialize your database is provided in the [Data](../master/Data) folder.
 * All data is provided in plain text CSV files. Part of your system design task is to map (i.e. normalize) these data onto your schema and tables.
 * All book id's are 10-character ISBN numbers (i.e. some contain alpha characters). 
 
 **Note:** _some may contain leading zeroes. These are part of ISBN and should not be truncated_

## Parser 

The parser is responsible to import the data from **csv** files to the backend database. [ImportCSV.py](../master/ImportCSV.py) is responsible for normalizing the data and importing to appropriate relations in the Database. 

## Documents
 
[Design document](../master/Design.pdf) that describes your system architecture including design decisions and assumptions. 
 A [Quick Start](../master/Quick Start Guide.pdf) user guide for librarian system users 

## Licence 

This project is licensed under the MIT License - see the [LICENCE](../master/LICENSE) file for details
 
