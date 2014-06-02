CODE LOCATION
====================================================================

The code for the main system that processes the article data and builds the machine learning model can be found at the following path:
NewsArticleAnalysis\src\uk\ac\aber\dst1\newsarticleanalysis

The code of the application that helps with manually labelling the articles can be found at the following path:
LabellingHelper\src

The sql dump for the database can be found in the folder db.
------------------------

LIBRARY LOCATION
=====================================================================

The Libraries used can be found in the \lib folder of each of the application folders:
- NewsArticleAnalysis\lib
- LabellingHelper\lib

For the Stanford Libraries there are models in the NewsArticleAnalysis\lib\models folder
due to the limit of GitHub one of the main libraries has been removed (stanford-corenlp-3.3.1-models), this is part of "A Suite of Core NLP Tools"
and can be downloaded at: http://nlp.stanford.edu/software/corenlp.shtml


------------------------

TOOLS REQUIRED
=====================================================================

To run the application you need MySQL server and MySQL database on the computer.
The sql dump that is provided in the folder db_sql_dump is the database that contains the download articles and all the data used for the machine learning model.

Eclipse will be needed to run the code with a new MySQL server and database.


------------------------

EXECUTING THE APPLICATION
=====================================================================

Using the Jar file in the command line - this can be achieved if the database has the same structure, name and password as the one in the db_sql_dump folder.
The Database username and password are:
USER = "Diana";
PASS = "ppiytirK";

For labelling helper run: java -jar LabellingHelperApp.jar
For the system run: java -jar NewsArticleAnalysisApp.jar (this has been removed due to GitHub limit of less than 100 MB)


From Eclipse - in case the database is not built and set-up from the dump file and with the same name "articledatabase" the code will need to be edited for the new database name.
The name of the database is used and needs to be changed in the following classes:
- Labelling helper: DatabaseAccess.java
- NewsArticleAnalysis: DatabaseSetup.java and MLTable.java
at the following line:
static final String DB_URL = "jdbc:mysql://localhost:3306/articledatabase";


Also the password that connects to the MySQL Server where the database is needs to be changed in the same .java files.

The News Article Analysis starts with 3 menu options that will go through the entire system:
1 - Search and download more articles
This option will search with a given search term and download from the National Library Server the number of articles that you input.


2 - Build the ML tables
This option updates the machine learning tables with the verb/noun occurrences and all the data that is necessary for the machine learning model. I don't advice running this part of the system as this takes several hours to finish. This needs to be done only in case of new labelled articles.


3 - Get the ML results
This option gives a few different options that will show the model and evaluation of the tables with either noun or verbs attributes in two of the machine learning classifiers: Naive Bayes and Decision Trees






