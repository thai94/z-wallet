# z-wallet
Install guide

## Production Install
### 1. Install docker and docker compose
- https://docs.docker.com/install/
- https://docs.docker.com/compose/install/

### 2. Build service
- Install maven: http://maven.apache.org/install.html
- Open folder: \z-wallet\src\z-wallet\
- Open command-line
- Run command: mvn package

- Open folder: \z-wallet\src\bank-msb\
- Open command-line
- Run command: mvn package
### 2. Run service
- Open folder: \z-wallet\src
- Open command-line
- Run command: sudo docker-compose build
- Run command: sudo docker-compose up

### Service informatin
- Database: your-ip:3307
- Z-Wallet service: your-ip:8080
_________________________________________________________________
## Develop Install
### 1. Import databse vao mysql
- Download mySQL link: https://dev.mysql.com/downloads/installer/
- File name: Windows (x86, 32-bit), MSI Installer
- Cai dat: MySQL server and Mysql workbench
- Import cac databse sau: /db/bank_msb.sq & /db/zwallet.sql

### 2. Install redis cache
- Link: https://redis.io/

### 3. Cai dat project: z-wallet & bank-msb
- Install java: https://www.oracle.com/java/technologies/javase-jdk8-downloads.html
- Donwload intellij IDE (https://www.jetbrains.com/idea/download/#section=windows)
- Import into intellij IDE
- Run: Right click on file: BankMsbApplication then choose RUN
- Run: Right click on file: ZWalletApplication then choose RUN

### 4. Test API
- Using: PostMan (https://www.postman.com/downloads/)
- Import into postman: /api/zwallet.postman_collection.json