# Rock Paper Scissors

- First you have to register into the app to be able to play a game against the machine
- The admin user is "alfonso" with password "123456" by default
- Depending on the role of the user, the user can perform different actions:
  * ADMIN: 
    * Can list all the games of all users even if they are not finished
    * Can play any game
    * Configure the roles of the other users, enable/disable users
  * USER:
    * Can list all finish games of all users and only the games not finished that belongs to the user
    * Only can play its games

## How to run



### Known issues
The database was created with PostgreSQL. Please if any error related to authentication, follow the next instructions:
1. Change password_encryption to md5 in postgresql.conf
2. Change scram-sha-256 to md5 in pg_hba.conf (host all all 0.0.0.0/0 md5)
3. Change password to restore in md5 format: ALTER ROLE [USERNAME] WITH PASSWORD '[PASSWORD]';
4. Check that listen_addresses = '*' is set in postgresql.conf