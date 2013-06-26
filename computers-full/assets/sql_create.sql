CREATE TABLE "quiz_level" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL UNIQUE,
    "level" integer NULL UNIQUE,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "quiz_question" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "text" varchar(200) NULL,
    "category" varchar(80) NULL,
    "level_id" integer NULL REFERENCES "quiz_level" ("_id"),
    "hint" text NULL,
    "explanation" text NULL,
    "is_active" bool NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("text", "level_id")
)
;
CREATE TABLE "quiz_answer" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "question_id" integer NULL REFERENCES "quiz_question" ("_id"),
    "text" varchar(200) NULL,
    "is_correct" bool NULL,
    "is_active" bool NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("question_id", "text")
)
;

INSERT INTO "quiz_level" VALUES(1,'Hobbyist/Professional',2,'2013-01-27 00:42:27','2013-01-27 00:42:27');
INSERT INTO "quiz_level" VALUES(2,'Expert',3,'2013-01-27 00:42:43','2013-01-27 00:42:43');
INSERT INTO "quiz_level" VALUES(3,'Nightmare!',4,'2013-01-27 00:42:51','2013-01-27 00:42:51');
INSERT INTO "quiz_level" VALUES(4,'Level 1',1,'2013-03-16 02:33:03.667000','2013-03-16 02:33:03.667000');
INSERT INTO "quiz_question" VALUES(1,'Which of these is NOT a web server software?','tools',3,'','X server is the server counterpart of the X Window System in UNIX, and has nothing to do with web servers.',1,'2013-06-21 06:22:12.259614','2013-06-21 06:22:12.259626');
INSERT INTO "quiz_question" VALUES(2,'Which of these is NOT a network analysis tool?','tools',3,'','tcpdump dumps traffic on a network; wireshark is a network traffic analyzer; nmap is a network security auditing tool; that leaves us with screen, which is a terminal multiplexer tool.',1,'2013-06-21 06:22:12.277573','2013-06-21 06:22:12.277585');
INSERT INTO "quiz_question" VALUES(3,'What is the purpose of the -h flag of the ls command in UNIX?','tools',3,'','When used together with the -l flag for long format, the -h makes the ls command print file sizes in human readable format like 1K, 234M, 2G.',1,'2013-06-21 06:22:12.296035','2013-06-21 06:22:12.296047');
INSERT INTO "quiz_question" VALUES(4,'Which of the following languages is NOT in the same group as LISP?','programming',3,'','LISP, R, Scheme, and Scala are all functional languages. C++ is an imperative language.',1,'2013-06-21 06:22:12.314196','2013-06-21 06:22:12.314208');
INSERT INTO "quiz_question" VALUES(5,'Which of the following languages is NOT in the same group as C++?','programming',3,'','C++, C, Java, and MATLAB, are all imperative languages. Haskell is a functional language.',1,'2013-06-21 06:22:12.332682','2013-06-21 06:22:12.332694');
INSERT INTO "quiz_question" VALUES(6,'Which of the following is NOT a programming language?','programming',3,'','CGI stands for Common Gateway Interface, a standard for dynamic generation of web pages by a web server. It is NOT a programming language.',1,'2013-06-21 06:22:12.349103','2013-06-21 06:22:12.349115');
INSERT INTO "quiz_question" VALUES(7,'Can you parse an HTML document using regular expressions?','programming',3,'','Regular expressions can only match regular languages but HTML is a context-free language. It is always possible to present a HTML file that will be matched wrongly by any regular expression.',1,'2013-06-21 06:22:12.367028','2013-06-21 06:22:12.367041');
INSERT INTO "quiz_question" VALUES(8,'Which is a good reason to use a LinkedList instead of an ArrayList?','programming',3,'','In a linked list, elements are chained together: each element has a pointer to the next. Insert and remove operations are more memory-efficient compared to an ArrayList, at the cost that random access of elements is less efficient.',1,'2013-06-21 06:22:12.410352','2013-06-21 06:22:12.410365');
INSERT INTO "quiz_question" VALUES(9,'What is the advantage of using an ArrayList instead of a LinkedList?','programming',3,'','Elements in an ArrayList can be accessed directly by a numeric index, but in a LinkedList you must travers the elements from the start or the end until the desired element.',1,'2013-06-21 06:22:12.429961','2013-06-21 06:22:12.429975');
INSERT INTO "quiz_question" VALUES(10,'In web programming, what is the QUERY_STRING?','web-programming',3,'','The QUERY_STRING is the part of a URL after the question mark, not including the question mark itself, for example "search=hello&page=2".',1,'2013-06-21 06:22:12.448182','2013-06-21 06:22:12.448195');
INSERT INTO "quiz_question" VALUES(11,'Which one of these is NOT a common CGI environment variable?','web-programming',3,'','HTTP_USER_AGENT is the browser type of the visitor, HTTP_REFERER is the URL of the page that called the current one, and DOCUMENT_ROOT is the root directory of the server.',1,'2013-06-21 06:22:12.467753','2013-06-21 06:22:12.467765');
INSERT INTO "quiz_question" VALUES(12,'Which one of these is a common CGI environment variable?','web-programming',3,'','REMOTE_ADDR is the IP address of the visitor.',1,'2013-06-21 06:22:12.485413','2013-06-21 06:22:12.485427');
INSERT INTO "quiz_question" VALUES(13,'Which HTTP method was not part of the HTTP/1.0 specification?','web-programming',3,'','The OPTIONS method was added in the HTTP/1.1 specification.',1,'2013-06-21 06:22:12.552519','2013-06-21 06:22:12.552531');
INSERT INTO "quiz_question" VALUES(14,'What is the difference between the HTTP GET and HEAD methods?','web-programming',3,'','The response of a HEAD requests is a header identical to that of a GET request, but without a response body.',1,'2013-06-21 06:22:12.572808','2013-06-21 06:22:12.572821');
INSERT INTO "quiz_question" VALUES(15,'What is the status code of a successful HTTP request?','web-programming',3,'','"200 OK" is the standard response for successful HTTP requests.',1,'2013-06-21 06:22:12.591508','2013-06-21 06:22:12.591521');
INSERT INTO "quiz_question" VALUES(16,'Which one of these server responses is NOT part of the HTTP standard?','web-programming',3,'','404 is part of the standard but it means something else: "Not Found"',1,'2013-06-21 06:22:12.610396','2013-06-21 06:22:12.610410');
INSERT INTO "quiz_question" VALUES(17,'The 4xx class of HTTP status code indicates which type of error?','web-programming',3,'','Status codes in the class 4xx all indicate some kind of client error, such as requesting a non-existent page, or a syntax error in the request, just to name a few.',1,'2013-06-21 06:22:12.629869','2013-06-21 06:22:12.629882');
INSERT INTO "quiz_question" VALUES(18,'What was the limit of the header size in the HTTP/1.0 standard?','web-programming',3,'','The standard itself does not set a limit on the header size, but most implementations impose some limits for practical and security reasons.',1,'2013-06-21 06:22:12.683947','2013-06-21 06:22:12.683959');
INSERT INTO "quiz_question" VALUES(19,'What can you do with HTTP POST that you cannot do with HTTP GET?','web-programming',3,'','Uploading a file requires enclosing an entity in the request, which is supported by a POST request but not by a GET request.',1,'2013-06-21 06:22:12.701565','2013-06-21 06:22:12.701578');
INSERT INTO "quiz_question" VALUES(20,'Which of these build tools is the oldest?','build-automation',3,'','Make appeared in 1977, Ant in 2000, Maven in 2002, and CMake in 1999.',1,'2013-06-21 06:22:12.719422','2013-06-21 06:22:12.719433');
INSERT INTO "quiz_question" VALUES(21,'What is NOT a main responsibility of an automated build system?','build-automation',3,'','Debugging is an activity that involves interacting with a running program, not really part of the build process.',1,'2013-06-21 06:22:12.736678','2013-06-21 06:22:12.736691');
INSERT INTO "quiz_question" VALUES(22,'In an automated build system, which of these is the most logical next step after running unit tests?','build-automation',3,'','The software must already be compiled to run unit tests. Without a package, what is there to install or deploy?',1,'2013-06-21 06:22:12.753228','2013-06-21 06:22:12.753241');
INSERT INTO "quiz_question" VALUES(23,'What is the traditional step between "./configure" and "make install" when building a software from a source tarball?','build-automation',3,'','"make" without any arguments runs the default build task, which is typically building the entire software. This step is usually after the configuration step. After this the software can be installed with "make install".',1,'2013-06-21 06:22:12.770784','2013-06-21 06:22:12.770798');
INSERT INTO "quiz_question" VALUES(24,'What is the purpose of the "clean" step in a typical automated build system?','build-automation',3,'','The "clean" step typically removes build products, with the intention to start over as if the "compile" or other build steps were never executed.',1,'2013-06-21 06:22:12.787619','2013-06-21 06:22:12.787633');
INSERT INTO "quiz_question" VALUES(25,'What is the weakest point in the computer security of a typical large firm?','security',3,'','The typical weakest point is the employees, as social engineering and human gallibility can often circumvent otherwise state of the art computer security system.',1,'2013-06-21 06:22:12.805279','2013-06-21 06:22:12.805291');
INSERT INTO "quiz_question" VALUES(26,'Which of these is the most dangerous security probing tool?','security',3,'','nmap is a sophisticated port scanner, and can reveal much more about a network than all the other tools combined.',1,'2013-06-21 06:22:12.825061','2013-06-21 06:22:12.825073');
INSERT INTO "quiz_question" VALUES(27,'What''s the worst thing that can happen if you configure nmap with suid root?','security',3,'','Privilege escalation could be used to create all the other damages. Except maybe releasing the kraken. Not sure.',1,'2013-06-21 06:22:12.843817','2013-06-21 06:22:12.843830');
INSERT INTO "quiz_question" VALUES(28,'In Linux, what is the best way to give read permission to everybody on a file?','security',3,'','The +r flag gives read permission to everybody on the system. 444 is not a good idea because it removes all other permissions that there might have been. Anybody taking the other options should be fired on the spot.',1,'2013-06-21 06:22:12.861748','2013-06-21 06:22:12.861761');
INSERT INTO "quiz_question" VALUES(29,'Which is the worst method for encrypting data?','security',3,'','sha1 is not an encryption method but a message digest method. The next worst method here is crypt, if you wanted to know... :)',1,'2013-06-21 06:22:12.881180','2013-06-21 06:22:12.881193');
INSERT INTO "quiz_question" VALUES(30,'What''s the scariest defect that can happen in a web application?','security',3,'','A buffer overflow can potentially lead to privilege escalation, which could be used to produce all the others and much worse.',1,'2013-06-21 06:22:12.898800','2013-06-21 06:22:12.898813');
INSERT INTO "quiz_question" VALUES(31,'Which C library function is the least unsafe?','security',3,'','Only the strncpy function checks bounds, the others don''t. Not checking bounds is very likely to lead to buffer overflow defects.',1,'2013-06-21 06:22:12.918017','2013-06-21 06:22:12.918029');
INSERT INTO "quiz_question" VALUES(32,'Which tool cannot be used to encrypt files?','security',3,'','encfs and TrueCrypt can be used to create encrypted directories, and openssl can be used to encrypt a stream of data. nmap is a network scanning tool, not for encryption.',1,'2013-06-21 06:22:12.937770','2013-06-21 06:22:12.937782');
INSERT INTO "quiz_question" VALUES(33,'Which tool cannot be used to find open ports in the local system?','security',3,'','ping can only send ICMP ECHO requests to hosts, it cannot be used to find open ports.',1,'2013-06-21 06:22:12.956320','2013-06-21 06:22:12.956332');
INSERT INTO "quiz_question" VALUES(34,'Which tool cannot be used to find open ports in a remote system?','security',3,'','Unlike the others, netstat cannot be used to scan arbitrary remote ports.',1,'2013-06-21 06:22:12.973577','2013-06-21 06:22:12.973591');
INSERT INTO "quiz_question" VALUES(35,'Which one of these is NOT a Version Control System?','version-control',3,'','CSV stands for Comma Separated Values, it is a common file format, not a Version Control System.',1,'2013-06-21 06:22:12.990715','2013-06-21 06:22:12.990728');
INSERT INTO "quiz_question" VALUES(36,'Which one of these is a Distributed Version Control System?','version-control',3,'','These are all version control systems, but only Mercurial has a distributed model, the others use a client-server model.',1,'2013-06-21 06:22:13.009777','2013-06-21 06:22:13.009790');
INSERT INTO "quiz_question" VALUES(37,'What can be a disadvantage of a distributed VCS in comparison to a centralized VCS?','version-control',3,'','In a distributed VCS, initial cloning is typically slower, as the tool must download the entire project history. The other choices are disadvantages of centralized VCS.',1,'2013-06-21 06:22:13.029009','2013-06-21 06:22:13.029022');
INSERT INTO "quiz_question" VALUES(38,'In a version control system, which type of conflict can have an automatic resolution when merging branchA into branchB?','version-control',3,'','If branchA and branchB edited completely different parts of the file, that should be possible to resolve automatically. The other types of conflict require an explicit decision and action by a human.',1,'2013-06-21 06:22:13.046445','2013-06-21 06:22:13.046457');
INSERT INTO "quiz_question" VALUES(39,'What version control operation requires access to the server in a centralized VCS?','version-control',3,'','In a centralized VCS all operations that involve the history need to access the repository on the central server. The status, add, and mv commands can be performed without access to the server.',1,'2013-06-21 06:22:13.065553','2013-06-21 06:22:13.065565');
INSERT INTO "quiz_question" VALUES(40,'What is the most likely cause of a stack overflow?','programming',3,'','A common cause of a stack overflow is very deep or infinite recursion, exhausting the address space of the call stack. A common solution is to improve the algorithm to reduce the depth of recursions.',1,'2013-06-21 06:22:13.085272','2013-06-21 06:22:13.085285');
INSERT INTO "quiz_question" VALUES(41,'Which Linux distribution appeared first?','linux',3,'','Knoppix, Ubuntu, Mint are all derivatives of Debian.',1,'2013-06-21 06:22:13.104170','2013-06-21 06:22:13.104182');
INSERT INTO "quiz_question" VALUES(42,'Which command can be used to configure a firewall in Linux?','linux',2,'','iptables is an administration tool for IPv4 packet filtering and NAT, a common firewall software in Linux.',1,'2013-06-21 06:22:13.123695','2013-06-21 06:22:13.123707');
INSERT INTO "quiz_question" VALUES(43,'In Linux, which command cannot be used to list the files in a directory?','linux',3,'','The dirs command shows the list of remembered directories (stored by the pushd command), it cannot be used to list the files in a directory.',1,'2013-06-21 06:22:13.142094','2013-06-21 06:22:13.142107');
INSERT INTO "quiz_question" VALUES(44,'Which is an example of input redirection in common shells?','linux',2,'','"<" is the input redirection operator in common shells, even in DOS.',1,'2013-06-21 06:22:13.161742','2013-06-21 06:22:13.161754');
INSERT INTO "quiz_question" VALUES(45,'Which environment variable in Linux is typically not an absolute path?','linux',3,'','LOGNAME is usually the username of the current user, not a path.',1,'2013-06-21 06:22:13.180788','2013-06-21 06:22:13.180802');
INSERT INTO "quiz_question" VALUES(46,'Which command cannot be used to send an email in Linux?','linux',3,'','groff is a document formatting system (for example rendering man pages) and cannot be used to send emails. Yes you can send an email with telnet, though it won''t be pretty.',1,'2013-06-21 06:22:13.199627','2013-06-21 06:22:13.199639');
INSERT INTO "quiz_question" VALUES(47,'In Linux, which file controls the number of available virtual terminals?','linux',3,'','The virtual terminals are configured in the /etc/inittab file.',1,'2013-06-21 06:22:13.219505','2013-06-21 06:22:13.219518');
INSERT INTO "quiz_question" VALUES(48,'In Linux, what information is usually NOT stored in /etc/passwd file?','linux',3,'','The password is usually stored in /etc/shadow, with more strict access permissions.',1,'2013-06-21 06:22:13.238603','2013-06-21 06:22:13.238615');
INSERT INTO "quiz_question" VALUES(49,'In Linux, which runlevel is a multi-user runlevel?','linux',3,'','Runlevels 2 to 5 are multi-user runlevels.',1,'2013-06-21 06:22:13.258864','2013-06-21 06:22:13.258877');
INSERT INTO "quiz_question" VALUES(50,'In Linux, what is the name of the process with PID 1?','linux',3,'','In Linux the "init" process is the parent of all processes, it gets the first PID which is 1.',1,'2013-06-21 06:22:13.280716','2013-06-21 06:22:13.280729');
INSERT INTO "quiz_question" VALUES(51,'In Linux, which signal cannot be trapped?','linux',3,'','The KILL signal is non-catchable, non-ignorable, immediately kills a process without possibility of cleaning up.',1,'2013-06-21 06:22:13.299826','2013-06-21 06:22:13.299838');
INSERT INTO "quiz_question" VALUES(52,'In Linux, which command can be used to check if a given PID is currently running or not?','linux',3,'','The output of kill -0 PID includes the text "No such process" if the process doesn''t exist. The command exits with 1 if the process does not exist or the user does not own it.',1,'2013-06-21 06:22:13.317526','2013-06-21 06:22:13.317538');
INSERT INTO "quiz_answer" VALUES(1,1,'X server',1,1,'2013-06-21 06:22:12.262460','2013-06-21 06:22:12.262475');
INSERT INTO "quiz_answer" VALUES(2,1,'nginx',0,1,'2013-06-21 06:22:12.265125','2013-06-21 06:22:12.265135');
INSERT INTO "quiz_answer" VALUES(3,1,'Apache',0,1,'2013-06-21 06:22:12.267593','2013-06-21 06:22:12.267607');
INSERT INTO "quiz_answer" VALUES(4,1,'Lighttpd',0,1,'2013-06-21 06:22:12.270256','2013-06-21 06:22:12.270269');
INSERT INTO "quiz_answer" VALUES(5,2,'screen',1,1,'2013-06-21 06:22:12.280017','2013-06-21 06:22:12.280030');
INSERT INTO "quiz_answer" VALUES(6,2,'nmap',0,1,'2013-06-21 06:22:12.283152','2013-06-21 06:22:12.283166');
INSERT INTO "quiz_answer" VALUES(7,2,'tcpdump',0,1,'2013-06-21 06:22:12.286194','2013-06-21 06:22:12.286209');
INSERT INTO "quiz_answer" VALUES(8,2,'wireshark',0,1,'2013-06-21 06:22:12.288583','2013-06-21 06:22:12.288594');
INSERT INTO "quiz_answer" VALUES(9,3,'Print file sizes in human readable format',1,1,'2013-06-21 06:22:12.299012','2013-06-21 06:22:12.299024');
INSERT INTO "quiz_answer" VALUES(10,3,'Print short help message',0,1,'2013-06-21 06:22:12.301468','2013-06-21 06:22:12.301482');
INSERT INTO "quiz_answer" VALUES(11,3,'Print long help message',0,1,'2013-06-21 06:22:12.304503','2013-06-21 06:22:12.304518');
INSERT INTO "quiz_answer" VALUES(12,3,'Print hidden files',0,1,'2013-06-21 06:22:12.306858','2013-06-21 06:22:12.306870');
INSERT INTO "quiz_answer" VALUES(13,4,'C++',1,1,'2013-06-21 06:22:12.316691','2013-06-21 06:22:12.316702');
INSERT INTO "quiz_answer" VALUES(14,4,'R',0,1,'2013-06-21 06:22:12.319447','2013-06-21 06:22:12.319463');
INSERT INTO "quiz_answer" VALUES(15,4,'Scheme',0,1,'2013-06-21 06:22:12.321825','2013-06-21 06:22:12.321842');
INSERT INTO "quiz_answer" VALUES(16,4,'Scala',0,1,'2013-06-21 06:22:12.325335','2013-06-21 06:22:12.325350');
INSERT INTO "quiz_answer" VALUES(17,5,'Haskell',1,1,'2013-06-21 06:22:12.335119','2013-06-21 06:22:12.335130');
INSERT INTO "quiz_answer" VALUES(18,5,'C',0,1,'2013-06-21 06:22:12.337316','2013-06-21 06:22:12.337327');
INSERT INTO "quiz_answer" VALUES(19,5,'Java',0,1,'2013-06-21 06:22:12.339463','2013-06-21 06:22:12.339477');
INSERT INTO "quiz_answer" VALUES(20,5,'MATLAB',0,1,'2013-06-21 06:22:12.342124','2013-06-21 06:22:12.342136');
INSERT INTO "quiz_answer" VALUES(21,6,'CGI',1,1,'2013-06-21 06:22:12.351844','2013-06-21 06:22:12.351857');
INSERT INTO "quiz_answer" VALUES(22,6,'Bash',0,1,'2013-06-21 06:22:12.354584','2013-06-21 06:22:12.354602');
INSERT INTO "quiz_answer" VALUES(23,6,'R',0,1,'2013-06-21 06:22:12.357406','2013-06-21 06:22:12.357421');
INSERT INTO "quiz_answer" VALUES(24,6,'Python',0,1,'2013-06-21 06:22:12.360009','2013-06-21 06:22:12.360021');
INSERT INTO "quiz_answer" VALUES(25,7,'No, because HTML is not a regular language',1,1,'2013-06-21 06:22:12.369754','2013-06-21 06:22:12.369772');
INSERT INTO "quiz_answer" VALUES(26,7,'Yes, you can parse anything using regular expressions',0,1,'2013-06-21 06:22:12.372349','2013-06-21 06:22:12.372362');
INSERT INTO "quiz_answer" VALUES(27,7,'Yes, but only if it contains only ASCII characters',0,1,'2013-06-21 06:22:12.400218','2013-06-21 06:22:12.400236');
INSERT INTO "quiz_answer" VALUES(28,7,'Yes, because HTML is a context-free language',0,1,'2013-06-21 06:22:12.403145','2013-06-21 06:22:12.403157');
INSERT INTO "quiz_answer" VALUES(29,8,'I will only access elements by traversing from the beginning',1,1,'2013-06-21 06:22:12.414323','2013-06-21 06:22:12.414340');
INSERT INTO "quiz_answer" VALUES(30,8,'LinkedList is more efficient than ArrayList in most practical cases',0,1,'2013-06-21 06:22:12.417848','2013-06-21 06:22:12.417861');
INSERT INTO "quiz_answer" VALUES(31,8,'LinkedList uses a hashmap internally',0,1,'2013-06-21 06:22:12.420468','2013-06-21 06:22:12.420478');
INSERT INTO "quiz_answer" VALUES(32,8,'I can access any element in a LinkedList very fast',0,1,'2013-06-21 06:22:12.422986','2013-06-21 06:22:12.422996');
INSERT INTO "quiz_answer" VALUES(33,9,'Accessing elements by index is faster',1,1,'2013-06-21 06:22:12.432928','2013-06-21 06:22:12.432940');
INSERT INTO "quiz_answer" VALUES(34,9,'Traversal of elements from start to end is faster',0,1,'2013-06-21 06:22:12.435484','2013-06-21 06:22:12.435494');
INSERT INTO "quiz_answer" VALUES(35,9,'ArrayList uses less memory than a LinkedList',0,1,'2013-06-21 06:22:12.438014','2013-06-21 06:22:12.438029');
INSERT INTO "quiz_answer" VALUES(36,9,'Removing an element in the middle of the list is faster',0,1,'2013-06-21 06:22:12.440569','2013-06-21 06:22:12.440627');
INSERT INTO "quiz_answer" VALUES(37,10,'The part of a URL after the "?" mark',1,1,'2013-06-21 06:22:12.451271','2013-06-21 06:22:12.451286');
INSERT INTO "quiz_answer" VALUES(38,10,'The part of a URL after the "#" mark',0,1,'2013-06-21 06:22:12.454017','2013-06-21 06:22:12.454035');
INSERT INTO "quiz_answer" VALUES(39,10,'A variable in a HTTP GET request',0,1,'2013-06-21 06:22:12.457867','2013-06-21 06:22:12.457882');
INSERT INTO "quiz_answer" VALUES(40,10,'A variable in a HTTP POST request',0,1,'2013-06-21 06:22:12.460735','2013-06-21 06:22:12.460749');
INSERT INTO "quiz_answer" VALUES(41,11,'IOS_VERSION',1,1,'2013-06-21 06:22:12.470451','2013-06-21 06:22:12.470468');
INSERT INTO "quiz_answer" VALUES(42,11,'HTTP_REFERER',0,1,'2013-06-21 06:22:12.473017','2013-06-21 06:22:12.473031');
INSERT INTO "quiz_answer" VALUES(43,11,'HTTP_USER_AGENT',0,1,'2013-06-21 06:22:12.476113','2013-06-21 06:22:12.476131');
INSERT INTO "quiz_answer" VALUES(44,11,'DOCUMENT_ROOT',0,1,'2013-06-21 06:22:12.478403','2013-06-21 06:22:12.478416');
INSERT INTO "quiz_answer" VALUES(45,12,'REMOTE_ADDR',1,1,'2013-06-21 06:22:12.536782','2013-06-21 06:22:12.536799');
INSERT INTO "quiz_answer" VALUES(46,12,'MOBILE_PHONE_TYPE',0,1,'2013-06-21 06:22:12.539590','2013-06-21 06:22:12.539606');
INSERT INTO "quiz_answer" VALUES(47,12,'IOS_VERSION',0,1,'2013-06-21 06:22:12.541919','2013-06-21 06:22:12.541932');
INSERT INTO "quiz_answer" VALUES(48,12,'ANDROID_VERSION',0,1,'2013-06-21 06:22:12.544683','2013-06-21 06:22:12.544700');
INSERT INTO "quiz_answer" VALUES(49,13,'OPTIONS',1,1,'2013-06-21 06:22:12.555406','2013-06-21 06:22:12.555417');
INSERT INTO "quiz_answer" VALUES(50,13,'GET',0,1,'2013-06-21 06:22:12.558130','2013-06-21 06:22:12.558140');
INSERT INTO "quiz_answer" VALUES(51,13,'POST',0,1,'2013-06-21 06:22:12.561193','2013-06-21 06:22:12.561206');
INSERT INTO "quiz_answer" VALUES(52,13,'HEAD',0,1,'2013-06-21 06:22:12.564081','2013-06-21 06:22:12.564093');
INSERT INTO "quiz_answer" VALUES(53,14,'HEAD doesn''t include a response body',1,1,'2013-06-21 06:22:12.575689','2013-06-21 06:22:12.575704');
INSERT INTO "quiz_answer" VALUES(54,14,'HEAD returns only the <head> tag of HTML',0,1,'2013-06-21 06:22:12.578227','2013-06-21 06:22:12.578238');
INSERT INTO "quiz_answer" VALUES(55,14,'GET returns a response without a header',0,1,'2013-06-21 06:22:12.580905','2013-06-21 06:22:12.580919');
INSERT INTO "quiz_answer" VALUES(56,14,'GET can support SSL',0,1,'2013-06-21 06:22:12.583456','2013-06-21 06:22:12.583471');
INSERT INTO "quiz_answer" VALUES(57,15,'200',1,1,'2013-06-21 06:22:12.594739','2013-06-21 06:22:12.594761');
INSERT INTO "quiz_answer" VALUES(58,15,'100',0,1,'2013-06-21 06:22:12.597991','2013-06-21 06:22:12.598007');
INSERT INTO "quiz_answer" VALUES(59,15,'0',0,1,'2013-06-21 06:22:12.600850','2013-06-21 06:22:12.600860');
INSERT INTO "quiz_answer" VALUES(60,15,'1',0,1,'2013-06-21 06:22:12.603208','2013-06-21 06:22:12.603221');
INSERT INTO "quiz_answer" VALUES(61,16,'404 Internal Server Error',1,1,'2013-06-21 06:22:12.613213','2013-06-21 06:22:12.613224');
INSERT INTO "quiz_answer" VALUES(62,16,'418 I''m a teapot',0,1,'2013-06-21 06:22:12.615450','2013-06-21 06:22:12.615461');
INSERT INTO "quiz_answer" VALUES(63,16,'400 Bad Request',0,1,'2013-06-21 06:22:12.618407','2013-06-21 06:22:12.618422');
INSERT INTO "quiz_answer" VALUES(64,16,'401 Unauthorized',0,1,'2013-06-21 06:22:12.621403','2013-06-21 06:22:12.621415');
INSERT INTO "quiz_answer" VALUES(65,17,'Client error',1,1,'2013-06-21 06:22:12.667637','2013-06-21 06:22:12.667652');
INSERT INTO "quiz_answer" VALUES(66,17,'Server error',0,1,'2013-06-21 06:22:12.670620','2013-06-21 06:22:12.670631');
INSERT INTO "quiz_answer" VALUES(67,17,'Application error',0,1,'2013-06-21 06:22:12.673425','2013-06-21 06:22:12.673439');
INSERT INTO "quiz_answer" VALUES(68,17,'Connection error',0,1,'2013-06-21 06:22:12.676130','2013-06-21 06:22:12.676143');
INSERT INTO "quiz_answer" VALUES(69,18,'Unspecified (no limit)',1,1,'2013-06-21 06:22:12.686727','2013-06-21 06:22:12.686738');
INSERT INTO "quiz_answer" VALUES(70,18,'8190 bytes',0,1,'2013-06-21 06:22:12.689199','2013-06-21 06:22:12.689209');
INSERT INTO "quiz_answer" VALUES(71,18,'1024 bytes',0,1,'2013-06-21 06:22:12.692205','2013-06-21 06:22:12.692219');
INSERT INTO "quiz_answer" VALUES(72,18,'512 bytes',0,1,'2013-06-21 06:22:12.694841','2013-06-21 06:22:12.694853');
INSERT INTO "quiz_answer" VALUES(73,19,'Upload files',1,1,'2013-06-21 06:22:12.704088','2013-06-21 06:22:12.704104');
INSERT INTO "quiz_answer" VALUES(74,19,'Implement session state',0,1,'2013-06-21 06:22:12.707214','2013-06-21 06:22:12.707229');
INSERT INTO "quiz_answer" VALUES(75,19,'Create cookies',0,1,'2013-06-21 06:22:12.710603','2013-06-21 06:22:12.710617');
INSERT INTO "quiz_answer" VALUES(76,19,'Pass multiple variables to a form',0,1,'2013-06-21 06:22:12.713109','2013-06-21 06:22:12.713122');
INSERT INTO "quiz_answer" VALUES(77,20,'Make',1,1,'2013-06-21 06:22:12.721646','2013-06-21 06:22:12.721661');
INSERT INTO "quiz_answer" VALUES(78,20,'Apache Ant',0,1,'2013-06-21 06:22:12.724674','2013-06-21 06:22:12.724689');
INSERT INTO "quiz_answer" VALUES(79,20,'Apache Maven',0,1,'2013-06-21 06:22:12.727279','2013-06-21 06:22:12.727289');
INSERT INTO "quiz_answer" VALUES(80,20,'CMake',0,1,'2013-06-21 06:22:12.729831','2013-06-21 06:22:12.729844');
INSERT INTO "quiz_answer" VALUES(81,21,'Debug the source code',1,1,'2013-06-21 06:22:12.738990','2013-06-21 06:22:12.739003');
INSERT INTO "quiz_answer" VALUES(82,21,'Compile the source code',0,1,'2013-06-21 06:22:12.741658','2013-06-21 06:22:12.741671');
INSERT INTO "quiz_answer" VALUES(83,21,'Eliminate redundant tasks',0,1,'2013-06-21 06:22:12.744086','2013-06-21 06:22:12.744096');
INSERT INTO "quiz_answer" VALUES(84,21,'Execute unit tests',0,1,'2013-06-21 06:22:12.746295','2013-06-21 06:22:12.746305');
INSERT INTO "quiz_answer" VALUES(85,22,'package',1,1,'2013-06-21 06:22:12.755706','2013-06-21 06:22:12.755718');
INSERT INTO "quiz_answer" VALUES(86,22,'compile',0,1,'2013-06-21 06:22:12.758065','2013-06-21 06:22:12.758077');
INSERT INTO "quiz_answer" VALUES(87,22,'deploy',0,1,'2013-06-21 06:22:12.760673','2013-06-21 06:22:12.760685');
INSERT INTO "quiz_answer" VALUES(88,22,'install',0,1,'2013-06-21 06:22:12.762703','2013-06-21 06:22:12.762715');
INSERT INTO "quiz_answer" VALUES(89,23,'"make"',1,1,'2013-06-21 06:22:12.773442','2013-06-21 06:22:12.773458');
INSERT INTO "quiz_answer" VALUES(90,23,'"make universe"',0,1,'2013-06-21 06:22:12.775620','2013-06-21 06:22:12.775631');
INSERT INTO "quiz_answer" VALUES(91,23,'"make clean"',0,1,'2013-06-21 06:22:12.778150','2013-06-21 06:22:12.778160');
INSERT INTO "quiz_answer" VALUES(92,23,'"./run.sh"',0,1,'2013-06-21 06:22:12.780589','2013-06-21 06:22:12.780605');
INSERT INTO "quiz_answer" VALUES(93,24,'Remove generated files',1,1,'2013-06-21 06:22:12.789958','2013-06-21 06:22:12.789970');
INSERT INTO "quiz_answer" VALUES(94,24,'Detect and report bugs in the software',0,1,'2013-06-21 06:22:12.792198','2013-06-21 06:22:12.792211');
INSERT INTO "quiz_answer" VALUES(95,24,'Detect and eliminate bugs in the software',0,1,'2013-06-21 06:22:12.794544','2013-06-21 06:22:12.794560');
INSERT INTO "quiz_answer" VALUES(96,24,'Remove unknown files that don''t belong to the project',0,1,'2013-06-21 06:22:12.797674','2013-06-21 06:22:12.797687');
INSERT INTO "quiz_answer" VALUES(97,25,'The employees',1,1,'2013-06-21 06:22:12.810108','2013-06-21 06:22:12.810123');
INSERT INTO "quiz_answer" VALUES(98,25,'The firewall software used',0,1,'2013-06-21 06:22:12.812459','2013-06-21 06:22:12.812472');
INSERT INTO "quiz_answer" VALUES(99,25,'The antivirus software used',0,1,'2013-06-21 06:22:12.815098','2013-06-21 06:22:12.815111');
INSERT INTO "quiz_answer" VALUES(100,25,'The intranet software used',0,1,'2013-06-21 06:22:12.818253','2013-06-21 06:22:12.818267');
INSERT INTO "quiz_answer" VALUES(101,26,'nmap',1,1,'2013-06-21 06:22:12.827811','2013-06-21 06:22:12.827824');
INSERT INTO "quiz_answer" VALUES(102,26,'ping',0,1,'2013-06-21 06:22:12.830801','2013-06-21 06:22:12.830815');
INSERT INTO "quiz_answer" VALUES(103,26,'ifconfig',0,1,'2013-06-21 06:22:12.833527','2013-06-21 06:22:12.833538');
INSERT INTO "quiz_answer" VALUES(104,26,'telnet',0,1,'2013-06-21 06:22:12.835711','2013-06-21 06:22:12.835725');
INSERT INTO "quiz_answer" VALUES(105,27,'It could be used for privilege escalation',1,1,'2013-06-21 06:22:12.846555','2013-06-21 06:22:12.846579');
INSERT INTO "quiz_answer" VALUES(106,27,'It could be used to completely jam a network with DOS attacks',0,1,'2013-06-21 06:22:12.849154','2013-06-21 06:22:12.849169');
INSERT INTO "quiz_answer" VALUES(107,27,'It could be used to delete all files in the system',0,1,'2013-06-21 06:22:12.852228','2013-06-21 06:22:12.852256');
INSERT INTO "quiz_answer" VALUES(108,27,'It could be used to unleash the kraken',0,1,'2013-06-21 06:22:12.854833','2013-06-21 06:22:12.854846');
INSERT INTO "quiz_answer" VALUES(109,28,'chmod +r file.txt',1,1,'2013-06-21 06:22:12.864866','2013-06-21 06:22:12.864878');
INSERT INTO "quiz_answer" VALUES(110,28,'chmod 777 file.txt',0,1,'2013-06-21 06:22:12.867790','2013-06-21 06:22:12.867803');
INSERT INTO "quiz_answer" VALUES(111,28,'chmod 4777 file.txt',0,1,'2013-06-21 06:22:12.870336','2013-06-21 06:22:12.870346');
INSERT INTO "quiz_answer" VALUES(112,28,'chmod 444 file.txt',0,1,'2013-06-21 06:22:12.872694','2013-06-21 06:22:12.872708');
INSERT INTO "quiz_answer" VALUES(113,29,'sha1',1,1,'2013-06-21 06:22:12.883667','2013-06-21 06:22:12.883679');
INSERT INTO "quiz_answer" VALUES(114,29,'des3',0,1,'2013-06-21 06:22:12.886014','2013-06-21 06:22:12.886023');
INSERT INTO "quiz_answer" VALUES(115,29,'aes-128-cbc',0,1,'2013-06-21 06:22:12.888604','2013-06-21 06:22:12.888616');
INSERT INTO "quiz_answer" VALUES(116,29,'crypt',0,1,'2013-06-21 06:22:12.890995','2013-06-21 06:22:12.891007');
INSERT INTO "quiz_answer" VALUES(117,30,'Buffer overflow',1,1,'2013-06-21 06:22:12.901492','2013-06-21 06:22:12.901503');
INSERT INTO "quiz_answer" VALUES(118,30,'Blue screen of death',0,1,'2013-06-21 06:22:12.904896','2013-06-21 06:22:12.904908');
INSERT INTO "quiz_answer" VALUES(119,30,'Denial of service',0,1,'2013-06-21 06:22:12.907629','2013-06-21 06:22:12.907645');
INSERT INTO "quiz_answer" VALUES(120,30,'NullPointerException',0,1,'2013-06-21 06:22:12.911001','2013-06-21 06:22:12.911015');
INSERT INTO "quiz_answer" VALUES(121,31,'strncpy',1,1,'2013-06-21 06:22:12.921781','2013-06-21 06:22:12.921796');
INSERT INTO "quiz_answer" VALUES(122,31,'strcpy',0,1,'2013-06-21 06:22:12.924526','2013-06-21 06:22:12.924542');
INSERT INTO "quiz_answer" VALUES(123,31,'gets',0,1,'2013-06-21 06:22:12.927410','2013-06-21 06:22:12.927422');
INSERT INTO "quiz_answer" VALUES(124,31,'scanf',0,1,'2013-06-21 06:22:12.930128','2013-06-21 06:22:12.930143');
INSERT INTO "quiz_answer" VALUES(125,32,'nmap',1,1,'2013-06-21 06:22:12.940599','2013-06-21 06:22:12.940610');
INSERT INTO "quiz_answer" VALUES(126,32,'encfs',0,1,'2013-06-21 06:22:12.943381','2013-06-21 06:22:12.943395');
INSERT INTO "quiz_answer" VALUES(127,32,'openssl',0,1,'2013-06-21 06:22:12.946227','2013-06-21 06:22:12.946243');
INSERT INTO "quiz_answer" VALUES(128,32,'TrueCrypt',0,1,'2013-06-21 06:22:12.949338','2013-06-21 06:22:12.949354');
INSERT INTO "quiz_answer" VALUES(129,33,'ping',1,1,'2013-06-21 06:22:12.958527','2013-06-21 06:22:12.958539');
INSERT INTO "quiz_answer" VALUES(130,33,'nmap',0,1,'2013-06-21 06:22:12.961302','2013-06-21 06:22:12.961314');
INSERT INTO "quiz_answer" VALUES(131,33,'telnet',0,1,'2013-06-21 06:22:12.963569','2013-06-21 06:22:12.963578');
INSERT INTO "quiz_answer" VALUES(132,33,'netstat',0,1,'2013-06-21 06:22:12.966244','2013-06-21 06:22:12.966259');
INSERT INTO "quiz_answer" VALUES(133,34,'netstat',1,1,'2013-06-21 06:22:12.975972','2013-06-21 06:22:12.975985');
INSERT INTO "quiz_answer" VALUES(134,34,'nmap',0,1,'2013-06-21 06:22:12.978474','2013-06-21 06:22:12.978485');
INSERT INTO "quiz_answer" VALUES(135,34,'telnet',0,1,'2013-06-21 06:22:12.981191','2013-06-21 06:22:12.981201');
INSERT INTO "quiz_answer" VALUES(136,34,'curl',0,1,'2013-06-21 06:22:12.983583','2013-06-21 06:22:12.983597');
INSERT INTO "quiz_answer" VALUES(137,35,'CSV',1,1,'2013-06-21 06:22:12.993992','2013-06-21 06:22:12.994008');
INSERT INTO "quiz_answer" VALUES(138,35,'Bazaar',0,1,'2013-06-21 06:22:12.996738','2013-06-21 06:22:12.996748');
INSERT INTO "quiz_answer" VALUES(139,35,'Subversion',0,1,'2013-06-21 06:22:12.999748','2013-06-21 06:22:12.999763');
INSERT INTO "quiz_answer" VALUES(140,35,'CVS',0,1,'2013-06-21 06:22:13.002525','2013-06-21 06:22:13.002667');
INSERT INTO "quiz_answer" VALUES(141,36,'Mercurial',1,1,'2013-06-21 06:22:13.012611','2013-06-21 06:22:13.012712');
INSERT INTO "quiz_answer" VALUES(142,36,'Subversion',0,1,'2013-06-21 06:22:13.015213','2013-06-21 06:22:13.015228');
INSERT INTO "quiz_answer" VALUES(143,36,'ClearCase',0,1,'2013-06-21 06:22:13.017968','2013-06-21 06:22:13.017981');
INSERT INTO "quiz_answer" VALUES(144,36,'Visual SourceSafe',0,1,'2013-06-21 06:22:13.021330','2013-06-21 06:22:13.021344');
INSERT INTO "quiz_answer" VALUES(145,37,'The initial cloning is slower',1,1,'2013-06-21 06:22:13.031697','2013-06-21 06:22:13.031710');
INSERT INTO "quiz_answer" VALUES(146,37,'Users cannot commit revisions without a server',0,1,'2013-06-21 06:22:13.034355','2013-06-21 06:22:13.034370');
INSERT INTO "quiz_answer" VALUES(147,37,'Most operations are slower without a server',0,1,'2013-06-21 06:22:13.036762','2013-06-21 06:22:13.036773');
INSERT INTO "quiz_answer" VALUES(148,37,'Users cannot create forks without a server',0,1,'2013-06-21 06:22:13.039519','2013-06-21 06:22:13.039533');
INSERT INTO "quiz_answer" VALUES(149,38,'The same file was edited in both BranchA and BranchB',1,1,'2013-06-21 06:22:13.048957','2013-06-21 06:22:13.048969');
INSERT INTO "quiz_answer" VALUES(150,38,'The same file was edited in BranchA but deleted in BranchB',0,1,'2013-06-21 06:22:13.051882','2013-06-21 06:22:13.051895');
INSERT INTO "quiz_answer" VALUES(151,38,'The same file was edited in BranchA but renamed in BranchB',0,1,'2013-06-21 06:22:13.055235','2013-06-21 06:22:13.055253');
INSERT INTO "quiz_answer" VALUES(152,38,'A file with the same name was added in both BranchA and BranchB',0,1,'2013-06-21 06:22:13.058476','2013-06-21 06:22:13.058491');
INSERT INTO "quiz_answer" VALUES(153,39,'log',1,1,'2013-06-21 06:22:13.068646','2013-06-21 06:22:13.068672');
INSERT INTO "quiz_answer" VALUES(154,39,'status',0,1,'2013-06-21 06:22:13.071545','2013-06-21 06:22:13.071558');
INSERT INTO "quiz_answer" VALUES(155,39,'add',0,1,'2013-06-21 06:22:13.074624','2013-06-21 06:22:13.074639');
INSERT INTO "quiz_answer" VALUES(156,39,'mv',0,1,'2013-06-21 06:22:13.077759','2013-06-21 06:22:13.077771');
INSERT INTO "quiz_answer" VALUES(157,40,'Too deep recursion when using a recursive function',1,1,'2013-06-21 06:22:13.089182','2013-06-21 06:22:13.089195');
INSERT INTO "quiz_answer" VALUES(158,40,'Trying to access an array element by an index beyond the end of the array',0,1,'2013-06-21 06:22:13.091726','2013-06-21 06:22:13.091740');
INSERT INTO "quiz_answer" VALUES(159,40,'Trying to access an array element by a negative index',0,1,'2013-06-21 06:22:13.094368','2013-06-21 06:22:13.094383');
INSERT INTO "quiz_answer" VALUES(160,40,'The stack size limit is not set to unlimited in your system',0,1,'2013-06-21 06:22:13.097327','2013-06-21 06:22:13.097343');
INSERT INTO "quiz_answer" VALUES(161,41,'Debian',1,1,'2013-06-21 06:22:13.107921','2013-06-21 06:22:13.107935');
INSERT INTO "quiz_answer" VALUES(162,41,'Mint',0,1,'2013-06-21 06:22:13.110697','2013-06-21 06:22:13.110709');
INSERT INTO "quiz_answer" VALUES(163,41,'Ubuntu',0,1,'2013-06-21 06:22:13.113454','2013-06-21 06:22:13.113470');
INSERT INTO "quiz_answer" VALUES(164,41,'Knoppix',0,1,'2013-06-21 06:22:13.115926','2013-06-21 06:22:13.115939');
INSERT INTO "quiz_answer" VALUES(165,42,'iptables',1,1,'2013-06-21 06:22:13.126537','2013-06-21 06:22:13.126551');
INSERT INTO "quiz_answer" VALUES(166,42,'ipconfig',0,1,'2013-06-21 06:22:13.130022','2013-06-21 06:22:13.130038');
INSERT INTO "quiz_answer" VALUES(167,42,'ifconfig',0,1,'2013-06-21 06:22:13.132402','2013-06-21 06:22:13.132414');
INSERT INTO "quiz_answer" VALUES(168,42,'netstat',0,1,'2013-06-21 06:22:13.135181','2013-06-21 06:22:13.135191');
INSERT INTO "quiz_answer" VALUES(169,43,'dirs',1,1,'2013-06-21 06:22:13.144620','2013-06-21 06:22:13.144633');
INSERT INTO "quiz_answer" VALUES(170,43,'ls',0,1,'2013-06-21 06:22:13.147111','2013-06-21 06:22:13.147127');
INSERT INTO "quiz_answer" VALUES(171,43,'tree',0,1,'2013-06-21 06:22:13.150971','2013-06-21 06:22:13.150989');
INSERT INTO "quiz_answer" VALUES(172,43,'echo',0,1,'2013-06-21 06:22:13.153821','2013-06-21 06:22:13.153832');
INSERT INTO "quiz_answer" VALUES(173,44,'sort < data.txt',1,1,'2013-06-21 06:22:13.164416','2013-06-21 06:22:13.164432');
INSERT INTO "quiz_answer" VALUES(174,44,'cat data.txt | sort',0,1,'2013-06-21 06:22:13.167127','2013-06-21 06:22:13.167139');
INSERT INTO "quiz_answer" VALUES(175,44,'sort data.txt',0,1,'2013-06-21 06:22:13.169811','2013-06-21 06:22:13.169825');
INSERT INTO "quiz_answer" VALUES(176,44,'sort data.txt &',0,1,'2013-06-21 06:22:13.172561','2013-06-21 06:22:13.172571');
INSERT INTO "quiz_answer" VALUES(177,45,'LOGNAME',1,1,'2013-06-21 06:22:13.183142','2013-06-21 06:22:13.183155');
INSERT INTO "quiz_answer" VALUES(178,45,'PWD',0,1,'2013-06-21 06:22:13.186097','2013-06-21 06:22:13.186115');
INSERT INTO "quiz_answer" VALUES(179,45,'HOME',0,1,'2013-06-21 06:22:13.189596','2013-06-21 06:22:13.189609');
INSERT INTO "quiz_answer" VALUES(180,45,'SHELL',0,1,'2013-06-21 06:22:13.191909','2013-06-21 06:22:13.191923');
INSERT INTO "quiz_answer" VALUES(181,46,'groff',1,1,'2013-06-21 06:22:13.202146','2013-06-21 06:22:13.202157');
INSERT INTO "quiz_answer" VALUES(182,46,'telnet',0,1,'2013-06-21 06:22:13.204490','2013-06-21 06:22:13.204502');
INSERT INTO "quiz_answer" VALUES(183,46,'mailx',0,1,'2013-06-21 06:22:13.207325','2013-06-21 06:22:13.207340');
INSERT INTO "quiz_answer" VALUES(184,46,'mutt',0,1,'2013-06-21 06:22:13.210961','2013-06-21 06:22:13.210973');
INSERT INTO "quiz_answer" VALUES(185,47,'/etc/inittab',1,1,'2013-06-21 06:22:13.222856','2013-06-21 06:22:13.222869');
INSERT INTO "quiz_answer" VALUES(186,47,'/etc/virttab',0,1,'2013-06-21 06:22:13.226293','2013-06-21 06:22:13.226311');
INSERT INTO "quiz_answer" VALUES(187,47,'/etc/fstab',0,1,'2013-06-21 06:22:13.229242','2013-06-21 06:22:13.229259');
INSERT INTO "quiz_answer" VALUES(188,47,'/etc/mtab',0,1,'2013-06-21 06:22:13.231764','2013-06-21 06:22:13.231778');
INSERT INTO "quiz_answer" VALUES(189,48,'Password',1,1,'2013-06-21 06:22:13.241229','2013-06-21 06:22:13.241243');
INSERT INTO "quiz_answer" VALUES(190,48,'Username',0,1,'2013-06-21 06:22:13.245487','2013-06-21 06:22:13.245504');
INSERT INTO "quiz_answer" VALUES(191,48,'Shell',0,1,'2013-06-21 06:22:13.248650','2013-06-21 06:22:13.248665');
INSERT INTO "quiz_answer" VALUES(192,48,'Home directory',0,1,'2013-06-21 06:22:13.251315','2013-06-21 06:22:13.251327');
INSERT INTO "quiz_answer" VALUES(193,49,'2',1,1,'2013-06-21 06:22:13.262510','2013-06-21 06:22:13.262528');
INSERT INTO "quiz_answer" VALUES(194,49,'S',0,1,'2013-06-21 06:22:13.265609','2013-06-21 06:22:13.265625');
INSERT INTO "quiz_answer" VALUES(195,49,'1',0,1,'2013-06-21 06:22:13.268223','2013-06-21 06:22:13.268236');
INSERT INTO "quiz_answer" VALUES(196,49,'6',0,1,'2013-06-21 06:22:13.270738','2013-06-21 06:22:13.270752');
INSERT INTO "quiz_answer" VALUES(197,50,'init',1,1,'2013-06-21 06:22:13.283439','2013-06-21 06:22:13.283451');
INSERT INTO "quiz_answer" VALUES(198,50,'kernel',0,1,'2013-06-21 06:22:13.286696','2013-06-21 06:22:13.286709');
INSERT INTO "quiz_answer" VALUES(199,50,'root',0,1,'2013-06-21 06:22:13.289429','2013-06-21 06:22:13.289441');
INSERT INTO "quiz_answer" VALUES(200,50,'launchd',0,1,'2013-06-21 06:22:13.292298','2013-06-21 06:22:13.292312');
INSERT INTO "quiz_answer" VALUES(201,51,'9 KILL',1,1,'2013-06-21 06:22:13.302701','2013-06-21 06:22:13.302717');
INSERT INTO "quiz_answer" VALUES(202,51,'3 QUIT',0,1,'2013-06-21 06:22:13.305453','2013-06-21 06:22:13.305464');
INSERT INTO "quiz_answer" VALUES(203,51,'6 ABRT',0,1,'2013-06-21 06:22:13.307949','2013-06-21 06:22:13.307962');
INSERT INTO "quiz_answer" VALUES(204,51,'15 TERM',0,1,'2013-06-21 06:22:13.310824','2013-06-21 06:22:13.310835');
INSERT INTO "quiz_answer" VALUES(205,52,'kill',1,1,'2013-06-21 06:22:13.320909','2013-06-21 06:22:13.320920');
INSERT INTO "quiz_answer" VALUES(206,52,'netstat',0,1,'2013-06-21 06:22:13.323434','2013-06-21 06:22:13.323449');
INSERT INTO "quiz_answer" VALUES(207,52,'top',0,1,'2013-06-21 06:22:13.325798','2013-06-21 06:22:13.325811');
INSERT INTO "quiz_answer" VALUES(208,52,'lsof',0,1,'2013-06-21 06:22:13.328494','2013-06-21 06:22:13.328507');
