# Pet Management Application

## Task

Create a Spring Boot application where users can manage a list of pets. 

Users can:
- View a list of treir pets.
- Add new pets with the following attributes:
  - Name
  - Identification code
  - Type (e.g., CAT, DOG)
  - Fur color
  - Country of origin
- Edit existing pets.
- Delete existing pets.
- Log in using pre-created user accounts.
- Users don't have access to other users' pets).

All input data is validated, and mandatory fields are checked when saving a new pet. 

Data is stored in an embedded H2 database, and values for select lists (e.g., pet type) are populated dynamically from the database.

## Technical Requirements

- Use Spring Boot.
- Embed an H2 database.
- Use Liquibase as a database migration tool
- Use Angular for the front end.
- Use Bootstrap for design and styling (avoid multiple CSS libraries).
- Ensure the API is RESTful.

