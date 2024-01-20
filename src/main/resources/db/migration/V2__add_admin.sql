INSERT INTO users(id, archive, email, name, password, role)
VALUES (1, false, 'mail@mail.ru', 'admin', '$2a$10$pmzFw02ag75aexLRfwdvie41XWzkNjZ01kVrpp.3OqWBh3Ma9wsye', 'ROLE_ADMIN');

ALTER SEQUENCE users_seq RESTART with 2;