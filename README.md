# voll-med-api
An API built with JAVA, to a Medical Bussiness Model. <br />
Using [swagger](https://swagger.io/) to doc. <br />
There are exemples to test controller and repository.

## These endpoints have already been added:
### MEDICOS - /medicos
- **GET** - List all doctors;
  - **GET /id** - List detail of one doctor;
- **DELETE** - Inactivate one doctor (soft delete);
- **POST** - Create one doctor;
- **PUT** - Update one doctor;
### PACIENTES - /pacientes
- **GET** - List all patients;
  - **GET /id** - List detail of one patient;
- **DELETE** - Inactivate one patient (soft delete);
- **POST** - Create one patient;
- **PUT** - Update one patient;
### CONSULTAS - /consultas
- **POST** - Scheduling using a available doctor and one patient;

