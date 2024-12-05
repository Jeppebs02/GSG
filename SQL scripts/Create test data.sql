INSERT INTO dbo.Address (Zipcode, City, Street, Number)
VALUES
('1000', 'Copenhagen', 'Nørrebrogade', '1'),
('2100', 'Copenhagen Ø', 'Østerbrogade', '5'),
('5000', 'Odense', 'Kongensgade', '10'),
('8000', 'Aarhus', 'M.P. Bruuns Gade', '15'),
('9000', 'Aalborg', 'Bispensgade', '20'),
('6000', 'Kolding', 'Jernbanegade', '25'),
('7000', 'Fredericia', 'Vesterbrogade', '30'),
('7100', 'Vejle', 'Horsensvej', '35'),
('4000', 'Roskilde', 'Algade', '40'),
('3700', 'Rønne', 'Store Torvegade', '45'),
('2200', 'Copenhagen N', 'Griffenfeldsgade', '50'),
('6700', 'Esbjerg', 'Strandbygade', '55'),
('9800', 'Hjørring', 'Hjørringgade', '60'),
('6400', 'Sønderborg', 'Perlegade', '65'),
('8300', 'Odder', 'Rådhusgade', '70'),
('8600', 'Silkeborg', 'Vestergade', '75'),
('5500', 'Middelfart', 'Algade', '80'),
('9000', 'Aalborg', 'Sønderbro', '85'),
('8800', 'Viborg', 'Sankt Mathias Gade', '90'),
('9400', 'Nørresundby', 'Havnevej', '95');

go

INSERT INTO dbo.[User] (UserName, Password, FirstName, LastName, Email, PhoneNr, AccountPrivileges, Type, Address_ID)
VALUES
('user1', 'password1', 'Mohammad', 'Yasin', 'Mo@example.com', '1234567890', 'CEO', 'EMPLOYEE', 1),
('user2', 'password2', 'Jane', 'Smith', 'jane.smith@example.com', '2345678901', 'MANAGER', 'EMPLOYEE', 2),
('user3', 'password3', 'Alice', 'Johnson', 'alice.johnson@example.com', '3456789012', 'MANAGER', 'EMPLOYEE', 3),
('user4', 'password4', 'Bob', 'Brown', 'bob.brown@example.com', '4567890123', 'EMPLOYEE', 'EMPLOYEE', 4),
('user5', 'password5', 'Charlie', 'Davis', 'charlie.davis@example.com', '5678901234', 'EMPLOYEE', 'EMPLOYEE', 5),
('user6', 'password6', 'Emily', 'Clark', 'emily.clark@example.com', '6789012345', 'EMPLOYEE', 'EMPLOYEE', 6),
('user7', 'password7', 'David', 'Lewis', 'david.lewis@example.com', '7890123456', 'EMPLOYEE', 'EMPLOYEE', 7),
('user8', 'password8', 'Sophia', 'Walker', 'sophia.walker@example.com', '8901234567', 'EMPLOYEE', 'EMPLOYEE', 8),
('user9', 'password9', 'Liam', 'Young', 'liam.young@example.com', '9012345678', 'EMPLOYEE', 'EMPLOYEE', 9),
('user10', 'password10', 'Olivia', 'King', 'olivia.king@example.com', '1230984567', 'EMPLOYEE', 'EMPLOYEE', 10),



('user11', 'password11', 'Noah', 'Scott', 'noah.scott@heidisbeerbar.com', '4561237890', 'CUSTOMER', 'BUSINESS', 11),
('user12', 'password12', 'Emma', 'Harris', 'emma.harris@example.com', '5672348901', 'CUSTOMER', 'BUSINESS', 12),
('user13', 'password13', 'Mason', 'Adams', 'mason.adams@example.com', '6783459012', 'CUSTOMER', 'BUSINESS', 13),
('user14', 'password14', 'Ava', 'Baker', 'ava.baker@example.com', '7894560123', 'CUSTOMER', 'BUSINESS', 14),
('user15', 'password15', 'James', 'Carter', 'james.carter@example.com', '8905671234', 'CUSTOMER', 'BUSINESS', 15),
('user16', 'password16', 'Isabella', 'Mitchell', 'isabella.mitchell@example.com', '9016782345', 'CUSTOMER', 'BUSINESS', 16),
('user17', 'password17', 'Ethan', 'Perez', 'ethan.perez@example.com', '1237893456', 'CUSTOMER', 'BUSINESS', 17),
('user18', 'password18', 'Mia', 'Roberts', 'mia.roberts@example.com', '2348904567', 'CUSTOMER', 'BUSINESS', 18),
('user19', 'password19', 'Alexander', 'Turner', 'alexander.turner@example.com', '3459015678', 'CUSTOMER', 'BUSINESS', 19),
('user20', 'password20', 'Charlotte', 'Phillips', 'charlotte.phillips@example.com', '4560126789', 'CUSTOMER', 'BUSINESS', 20);

go

INSERT INTO dbo.Business (CVR, CompanyName)
VALUES
('12345678', 'Danish Tech Solutions'),
('87654321', 'Copenhagen Consulting Group'),
('23456789', 'Odense Construction'),
('98765432', 'Aarhus IT Services'),
('34567890', 'Bornholm Bakery'),
('76543210', 'Vejle Logistics'),
('45678901', 'Silkeborg Textiles'),
('65432109', 'Roskilde Renewable Energy'),
('56789012', 'Esbjerg Shipping Co.'),
('21098765', 'Fredericia Foods');


go


INSERT INTO dbo.BusinessUser (User_ID, Business_ID)
VALUES
(11, 1), -- Noah Scott is linked to Danish Tech Solutions
(12, 2), -- Emma Harris is linked to Copenhagen Consulting Group
(13, 3), -- Mason Adams is linked to Odense Construction
(14, 4), -- Ava Baker is linked to Aarhus IT Services
(15, 5), -- James Carter is linked to Bornholm Bakery
(16, 6), -- Isabella Mitchell is linked to Vejle Logistics
(17, 7), -- Ethan Perez is linked to Silkeborg Textiles
(18, 8), -- Mia Roberts is linked to Roskilde Renewable Energy
(19, 9), -- Alexander Turner is linked to Esbjerg Shipping Co.
(20, 10); -- Charlotte Phillips is linked to Fredericia Foods


go

