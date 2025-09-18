INSERT INTO userstab(name, email, password, role, phoneno, created_date, modified_date)
VALUES (
  'Alice',
  'alice@example.com',
  '$2a$10$qn/gACx08kHPRqS9KxOlDeQ7sBy/XHfzGG79OE6qcrSCTUT4BiIJq',
  'ROLE_ADMIN',
  '6787879780',
  '2022-01-01T00:00:00',
  NOW()
);
INSERT INTO userstab(name, email, password, role, phoneno, created_date, modified_date)
VALUES (
  'renu',
  'renu@gmail.com',
  '$2a$10$FM3rfzZm7H6G9QjI7WVykOX20nV88xFpu/A00Pu5/v86eni.pPJ1a',
  'ROLE_USER',
  '9887879780',
  '2023-01-01T00:00:00',
  NOW()
);