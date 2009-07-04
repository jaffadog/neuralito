This is the standard bundle for National Oceanographic Data Center
(NODC) accession data. Each accession maintained by NODC is given a
unique integer identifier known as an 'accession id'. All information
related to that accession is contained within a directory using the
accession id as the directory name. The accession directory has the
following standard structure:

<accession id>: This directory

  NODC-Readme.txt: This file.

  about: Directory. Contains all accession related metadata including but
         not limited to the following two standard files.

    journal.txt: Text file. Contains any notes, correspondence etc. relating
         to this accession.

    <accession id>.md5: Text file: contains MD5 checksums for all files in this
         accession except for the <accession id>.md5 file itself.
         other metadata: ...

  data: Directory. All accession data is located in the 'data' directory.

    0-data: Directory. Contains the originator's data unmodified from its
         intial digital format as submitted to NODC. The initial source for
         this data should be documented in the header of the:

         <accession id>/about/journal.txt

         file after the keyword, 'Source'.

    1-data: Optional directory. May contain processed version of originator's
         data from '0-data' directory. E.g. unzipped, uncompressed, untarred,
         or otherwise extracted or modified data. A note should be found
         in <accession id>/about/journal.txt explaining how files in
         1-data were derived from the files in 0-data.

    <n>-data: Optional directories. Additional processed forms of originators
         data. Similar to 1-data above.

For further information about this accession see:

./about/journal.txt
