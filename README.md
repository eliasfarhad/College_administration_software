# College Administration Software

A program that works similar to CUNYFirst! CUNYFirst is a fully integrated resources and services tool for the City University 
of New York.

This program allows the college admission office to enroll or delete a student from the system if the admission period is not 
over. When the program is opened, an administrator has the option to open either StudentData.txt (student admission period is
not over) or StudentDataSorted.txt (student admission period is over). If the admission period is over but the drop period is
not over, a student can add or drop a course from his/her schedule. At last, if the drop period is over, no changes can be
made in the system and user will only be able to search for students and display data.

A Red Black Tree is used to store student data in sorted order.

#Searching for a Student

An administrator can search for a student by his/her Student ID# or by last name. If there exist multiple students with same 
last name, an advanced search can be performed which includes student's first name, last name and Student ID#. 
