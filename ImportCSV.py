import csv;
import mysql.connector
import os.path
from mysql.connector import Error
from nltk.tokenize import word_tokenize
from datetime import datetime

"""============================================================================"""    
"""Establishes connection to MySQL - LIBRARY Database"""
"""============================================================================"""
def getConnection():
    """ Connecting to MySQL Database"""
    try: 
        connection = mysql.connector.connect(host = 'localhost',
                                       database = 'LIBRARY',
                                       user = 'root',
                                       password = 'welcome'      
                                       );
        return connection
    except Error as e:
        print (e);
#End of getConnection    

"""============================================================================"""    
"""Parses Author field to Title|Firstname|MiddleName|LastName|Suffix"""
"""============================================================================"""
def getAuthorDetails(name):
    prefix = ('Rev', 'Miss', 'Ms', 'Mr', 'Sir', 'Mrs.', 'Dr.', 'Lady.', 'Lord.')   
    sfx = ('A.B', 'B.A', 'B.S', 'B.E', 'B.F.A', 'B.Tech', 'L.L.B', 'B.Sc', 'M.A', \
           'M.S', 'M.F.A', 'LL.M', 'M.L.A', 'M.B.A', 'M.Sc', 'M.Eng', 'J.D', 'M.D', \
           'D.O', 'Pharm.D.','Ph.D', 'Ed.D', 'D.Phil', 'LL.D', 'Eng.D', 'Atty')

    #Author Name values 
    title = ""
    firstName = ""
    middleName = ""
    lastName = ""
    suffix = ""
        
    tokenizedText = word_tokenize(name)
    filteredText = [tokens for tokens in tokenizedText if not tokens == "."]
    
    #Checking for prefixes 
    if filteredText[0] in prefix:
        title = filteredText[0]
        filteredText.remove(filteredText[0])
               
    #Checking for suffixes
    if filteredText[len(filteredText) - 1] in sfx:
        suffix = filteredText[len(filteredText) - 1]
        filteredText.remove(filteredText[len(filteredText) - 1])
        
    #Checking for first,middle and last name
    if (len(filteredText) > 3):
        firstName = filteredText[0]
        for i in range (1, len(filteredText)-1):
            middleName += filteredText[i]
        lastName = filteredText[len(filteredText) - 1]
    elif (len(filteredText) == 3):
        firstName = filteredText[0]
        middleName = filteredText[1]
        lastName = filteredText[2]
    elif (len(filteredText) == 2):    
        firstName = filteredText[0]
        lastName = filteredText[1]
    elif (len(filteredText) == 1):
        firstName = filteredText[0]
    
    if (title == ""):
        title = None
    if (firstName == ""):
        firstName = None
    if (middleName == ""):
        middleName = None
    if (lastName == ""):
        lastName = None
    if (suffix == ""):
        suffix = None
         
    resultList = [title,firstName,middleName,lastName,suffix]    
    
    return resultList
#End of getAuthorDetails

"""============================================================================"""    
""" Loads values into BOOK table from books.csv file"""
"""============================================================================"""
def loadBooks():
    try:    
        filePath = os.path.join(os.path.dirname(__file__),'SQL_Library_Data/books.csv')
        booksData = csv.reader(file(filePath),delimiter='\t');
        
        #List to store author values
        authorDetails = []
        
        #Auto Increment variable
        auto = 0
        
        #To skip the header
        booksData.next()
        
        for nonDecodedRow in booksData:
            
            row = [x.decode('utf8') for x in nonDecodedRow]
            
            #Book Id and Title
            connection  = getConnection() 
            bookCursor = connection.cursor()
            
            if row[3] == "": 
                print row
                isAuthorNotPresent = True 
                query = "INSERT INTO BOOK(Isbn,Title,Type,Publisher) VALUES(%s,%s,%s,%s)"
                bookCursor.execute(query,(row[0],row[2],isAuthorNotPresent,row[5]));
                bookCursor.close()
                connection.commit() 
                connection.close()
                continue
            else:
                isAuthorNotPresent = False
                query = "INSERT INTO BOOK(Isbn,Title,Type,Publisher) VALUES(%s,%s,%s,%s)"
                bookCursor.execute(query,(row[0],row[2],isAuthorNotPresent,row[5]));
                bookCursor.close()
                connection.commit()
                connection.close()

            moreThanOneAuthor = row[3].split(',');
            if(len(moreThanOneAuthor) > 1):
                for authors in moreThanOneAuthor:
                    auto = auto + 1
                    
                    #Author        
                    authorDetails = getAuthorDetails(authors)
                    connection  = getConnection()
                    authorCursor = connection.cursor()
                    query = "INSERT INTO AUTHORS(Author_id,Fullname,Title,Fname,Mname,Lname,Suffix) VALUES(%s,%s,%s,%s,%s,%s,%s)"
                    authorCursor.execute(query,(auto,authors,authorDetails[0],authorDetails[1],authorDetails[2],authorDetails[3],authorDetails[4]))
                    authorCursor.close()
                    connection.commit()
                    connection.close()
                    
                    ##Book Authors table
                    connection  = getConnection() 
                    bookAuthorCursor = connection.cursor()
                    bookAuthorCursor.execute("INSERT INTO BOOK_AUTHORS(Isbn,Author_id) VALUES(%s,%s)",(row[0],auto))
                    bookAuthorCursor.close()
                    connection.commit()
                    connection.close()
            else:
                auto = auto + 1
            
                authorDetails = getAuthorDetails(row[3])
                connection  = getConnection()
                authorCursor = connection.cursor()
                query = "INSERT INTO AUTHORS(Author_id,Fullname,Title,Fname,Mname,Lname,Suffix) VALUES(%s,%s,%s,%s,%s,%s,%s)"
                authorCursor.execute(query,(auto,row[3],authorDetails[0],authorDetails[1],authorDetails[2],authorDetails[3],authorDetails[4]))
                authorCursor.close()
                connection.commit()
                connection.close()
                
                connection  = getConnection() 
                bookAuthorCursor = connection.cursor()    
                bookAuthorCursor.execute("INSERT INTO BOOK_AUTHORS(Isbn,Author_id) VALUES(%s,%s)",(row[0],auto));
                bookAuthorCursor.close()
                connection.commit()
                connection.close()    
        return True
    except Error as e:
        print(e)

"""============================================================================"""
""" Loads values into LIBRARY_BRANCH table from library_branch.csv file"""
"""============================================================================"""
def loadLibraryBranch():
    try:
        connection = getConnection()
        cursor = connection.cursor();
        
        filePath = os.path.join(os.path.dirname(__file__),'SQL_Library_Data/library_branch.csv')
        librarybranchData = csv.reader(file(filePath),delimiter='\t');
            
        #To skip the header
        librarybranchData.next()
            
        for row in librarybranchData:
            cursor.execute('INSERT INTO LIBRARY_BRANCH(Branch_id,Branch_name,Address) VALUES(%s,%s,%s)',row);
            
        #Committing the changes to the database         
        connection.commit();
        return True;
    except Error as e:
        print(e)
    finally:
        cursor.close()   
#End of loadLibraryBranch

"""============================================================================"""    
""" Loads values into BOOK_COPIES table from book_copies.csv file"""    
"""============================================================================"""
def loadBookCopies():
    try:
        connection = getConnection()
        cursor = connection.cursor();
        
        filePath = os.path.join(os.path.dirname(__file__),'SQL_Library_Data/book_copies.csv')
        bookCopiesData = csv.reader(file(filePath),delimiter='\t');
            
        #To skip the header
        bookCopiesData.next()
            
        for row in bookCopiesData:
            cursor.execute('INSERT INTO BOOK_COPIES(Book_id,Branch_id,No_of_copies) VALUES(%s,%s,%s)',row);
        
        #Committing the changes to the database         
        connection.commit();
        return True;
    except Error as e:
        print(e)
    finally:
        cursor.close();        
#End of loadBookCopies

"""============================================================================"""
""" Loads values into BORROWER table from borrowers.csv file"""
"""============================================================================"""
def loadBorrowers():
    try:
        connection = getConnection()
        cursor = connection.cursor();
        
        filePath = os.path.join(os.path.dirname(__file__),'SQL_Library_Data/book_copies.csv')
        borrowersData = csv.reader(file(filePath),delimiter=',');
            
        #To skip the header
        borrowersData.next()
            
        for row in borrowersData:
            address = row[5] + " " + row[6] + " " +row[7]
            query = "INSERT INTO BORROWER(Card_no,Ssn,Fname,Lname,Address,Phone) VALUES(%s,%s,%s,%s,%s,%s)"
            cursor.execute(query,(row[0],row[1],row[2],row[3],address,row[8]))
            
        #Committing the changes to the database         
        connection.commit();
        return True;
    except Error as e:
        print(e)
    finally:
        cursor.close();    
#End of loadBorrowers

"""============================================================================"""        
""" Loads values into BOOK_LOANS table from book_loans.csv file"""    
"""============================================================================"""
def loadBookLoans():
    try:
        connection = getConnection()
        cursor = connection.cursor()
        
        filePath = os.path.join(os.path.dirname(__file__),'SQL_Library_Data/book_loans.csv')
        bookloansData = csv.reader(file(filePath),delimiter='\t');
                
        for row in bookloansData:
            query = "INSERT INTO BOOK_LOANS(Loan_id,Isbn,Branch_id,Card_no,Date_out,Due_date,Date_in) VALUES(%s,%s,%s,%s,%s,%s,%s)"
            cursor.execute(query,row)
           
            dueDate = datetime.strptime(row[5],"%Y-%m-%d")
            checkInDate = datetime.strptime(row[6],"%Y-%m-%d")
            dateDiff = abs(dueDate - checkInDate).days
        
            query = "INSERT INTO FINES(Loan_id,fine_amt,fine_amt_paid,paid) VALUES(%s,%s,%s,%s)"
            cursor.execute(query,(row[0],(dateDiff * 0.25),0,False))
            
        #Committing the changes to the database         
        connection.commit();
        return True;
    except Error as e:
        print(e)
    finally:
        cursor.close();
#End of loadBookLoans

    
"""============================================================================"""    
"""Checks if the mysql connector is open and closes it """
"""============================================================================"""
def checkConnected():
    if getConnection().is_connected():
        print ("Connection is connected")
        getConnection().close();
    else:
        print ("Connection is closed")
#End of chekConnected

"""============================================================================"""
""" Program Starts here..."""
"""============================================================================"""
if __name__ == "__main__":
    print ("Importing.....")
    
    #Loading books.csv
    result = loadBooks()
    if(result):
        print ('books.csv Loaded successfully...')
    else : 
        print ('Loading books.csv failed...')
    
    #Loading library_branch.csv
    result = loadLibraryBranch()
    if(result):
        print ('library_branch.csv Loaded successfully...')
    else : 
        print ('Loading library_branch.csv failed...')
    
    #Loading book_copies.csv
    result = loadBookCopies()
    if(result):
        print ('book_copies.csv Loaded successfully...')
    else : 
        print ('Loading book_copies.csv failed...')
     
    #Loading borrowers.csv
    result = loadBorrowers()
    if(result):
        print ('borrowers.csv Loaded successfully...')
    else : 
        print ('Loading borrowers.csv failed...')
      
    #Loading book_loans.csv    
    result = loadBookLoans()
    if(result):
        print ('book_loans.csv Loaded successfully...')
    else : 
        print ('Loading book_loans.csv failed...')
    
    #Closing the connection
    checkConnected()
#End of main