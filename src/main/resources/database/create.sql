insert into books(book_name, author, isbn, publication_year, total_copies, available_copies)
values('Преступление и наказание', 'Достоевский', '1234567890', '1866', 5, 3),
('Повесть о настоящем человеке', 'Полевой', '0123456789', '1948', 6, 5),
('Судьба человека', 'Шолохов', '1234567890123', '1956', 9, 9);

insert into readers(first_name, last_name, email, phone_number, registration_date)
values('Artem', 'Ivanov', 'test@yandex.ru', '89106541241', CURRENT_DATE),
('Ivan', 'Petrov', 'sobaka@sobaka.com', '89158901254', '2026-01-01'),
('Petr', 'Smirnov', 'petr.smirnov@gmail.com', '89956124213', '2026-02-01');

insert into book_loans(book_id, reader_id, loan_date, due_date, status)
values(1, 1, '2026-02-09', '2026-02-16', 'ISSUED'),
(1, 2, '2026-01-01', '2026-01-08', 'ISSUED'),
(2, 2, '2026-01-01', '2026-01-23', 'ISSUED');

insert into book_loans(book_id, reader_id, loan_date, due_date, return_date, status)
values(3, 3, '2026-01-20', '2026-01-27', '2026-01-05', 'RETURNED');