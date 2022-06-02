# Client-server multithread Java application

Refine the program from laboratory work 6 according to the recommendation:

1. Organize the storage of the collection in a relational DB (PostgresQL). Delete a collection in a file.
2. To create a data usage identifier use database tools (the sequence tool).
3. Update the state of the collection in memory only when the object is successfully added to the database.
4. All data manipulations must work with the collection in memory, not in the database.
5. Organize the possibility of registration and authorization of users. The user has the option to specify a password.
6. Hash passwords at storage using the SHA-256 algorithm.
7. Prohibit execution of commands by unauthorized users.
8. When saving object's information, also save the user who created it.
9. Users should be able to view all the objects in the collection, but only modify their component parts.
10. To identify the user, a username and password are sent with each request.

It is necessary to implement multi-threaded processing of requests.

1. Use Fixed thread pool for multi-threaded reading.
2. For multi-threaded processing of the received request, create a new thread (java.lang.Thread).
3. To multi-thread message sending create a new thread (java.lang.Thread).
4. Use java.util.concurrent.locks.ReentrantLock to synchronize collection access (read/write).
