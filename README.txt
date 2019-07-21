In few words, it analyze the daily backups faileds three times and (no daily) backups that faileds at least one time.

The initial objective to have this proyect here is only to have my proyects in the cloud,
 if anyone want to use this proyect, it can ask any doubt contacting me by mail: anonymousheimer@gmail.com

The proyect is thinked to work with the information extracted from a version old of Networker Management through of files of type cs, 
but working in the proyect , easily could be changed to use it with others aplications of backup. 


I think that the utility of this aplication would be to analyze backups of clients very big with old backup environments. 
In other cases, i don`t think that anyone needs to analyze backups faileds more of One or two days.



To run the application, please, read the next instructions: 

Inside of the database directory, there is a file "fileRute.bbdd" that contains the path and the name of the file 
where the application save the information of the ten last days. If the file with the information is not exists, 
then the application will create the file by first time only if you has created first the directory with name "data". 

Inside of the main directory, you need to have the files type cs with all the information, 
the name of file should contain the name of the backup server. Besides you need change in the code, the name of the backups servers. 
The file of type cs should be like the next example or you also could change the code of read:
 
Save Set Details by Client: Save Set Details by Client

Server Name: #type here the name of backup server
Group Name: all
Client Name: all
Save Set Name: all
Status: all
Backup Type: all
Level: all
Group Start Time: from 7/12/17 23:09:17 (10 days) to 17/12/17 23:09:17  #change here the date time

Client Name,Save Set Name,Save Set ID,Group Start Time,Save Type,Level,Status

#type here all the information like for example:
#NameclientServer,/,1630291398,9/12/17 21:05:00,save,incr,succeeded,
#NameclientServer,/,,13/12/17 21:05:00,save,full,failed,
#NameclientServer,/export/home,1848912625,15/12/17 21:05:00,save,incr,succeeded,
