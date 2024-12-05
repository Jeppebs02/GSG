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

INSERT INTO dbo.Employee (CPR, SecurityClearance, AccountNr, Certification, RegistrationNr, Department)
VALUES
('010101-1234', 'Confidential', 'ACC001', 'First Aid', 'REG001', 'Human Resources'),
('020202-2345', 'Secret', 'ACC002', 'CPR Certification', 'REG002', 'IT Support'),
('030303-3456', 'Top Secret', 'ACC003', 'Project Management', 'REG003', 'Operations'),
('040404-4567', 'Confidential', 'ACC004', 'Safety Training', 'REG004', 'Logistics'),
('050505-5678', 'Secret', 'ACC005', 'Data Security', 'REG005', 'Cybersecurity'),
('060606-6789', 'Confidential', 'ACC006', 'Customer Relations', 'REG006', 'Customer Service'),
('070707-7890', 'Secret', 'ACC007', 'Leadership Development', 'REG007', 'Management'),
('080808-8901', 'Top Secret', 'ACC008', 'Software Development', 'REG008', 'Engineering'),
('090909-9012', 'Confidential', 'ACC009', 'Legal Compliance', 'REG009', 'Legal'),
('101010-0123', 'Secret', 'ACC010', 'Accounting Practices', 'REG010', 'Finance');


go


INSERT INTO dbo.EmployeeUser (User_ID, Employee_ID)
VALUES
(1, 1), -- User 1 linked to Employee 1
(2, 2), -- User 2 linked to Employee 2
(3, 3), -- User 3 linked to Employee 3
(4, 4), -- User 4 linked to Employee 4
(5, 5), -- User 5 linked to Employee 5
(6, 6), -- User 6 linked to Employee 6
(7, 7), -- User 7 linked to Employee 7
(8, 8), -- User 8 linked to Employee 8
(9, 9), -- User 9 linked to Employee 9
(10, 10); -- User 10 linked to Employee 10

go

INSERT INTO dbo.Task (Description, Location, Approval, Date, User_ID)
VALUES
('Supervise event security at concert', 'Copenhagen', 1, '2024-12-06', 11), -- Assigned to User_ID 11
('Escort VIP client to meeting', 'Aarhus', 0, '2024-12-07', 12),             -- Assigned to User_ID 12
('Monitor CCTV and secure premises', 'Odense', 1, '2024-12-08', 13),        -- Assigned to User_ID 13
('Conduct security risk assessment', 'Roskilde', 0, '2024-12-09', 14),      -- Assigned to User_ID 14
('Coordinate bouncer schedules for nightclub', 'Vejle', 1, '2024-12-10', 15); -- Assigned to User_ID 15

go

INSERT INTO dbo.Report (RejectionAge, RejectionAttitude, RejectionAlternative, AlternativeRemarks, EmployeeSignature, CustomerSignature, Task_ID)
VALUES
(5, 2, 1, 'Additional guards were arranged for underage attendees.', 'Signed', 'Signed', 1), -- Based on Task_ID 1
(0, 1, 3, 'VIP client delayed due to unforeseen traffic. Protocol adjusted.', 'Signed', 'Not Signed', 2), -- Based on Task_ID 2
(0, 0, 4, 'Camera blind spots resolved. Enhanced monitoring coverage.', 'Signed', 'Signed', 3), -- Based on Task_ID 3
(1, 0, 0, 'Risk assessment completed successfully. No issues identified.', 'Signed', 'Signed', 4), -- Based on Task_ID 4
(2, 3, 0, 'Unruly attendees managed. Additional bouncers deployed at entry.', 'Signed', 'Signed', 5); -- Based on Task_ID 5

go

INSERT INTO dbo.Shift (StartTime, EndTime, Employee_ID, Task_ID)
VALUES
('2024-12-06 08:00:00', '2024-12-06 16:00:00', 1, 1), -- Employee 1 for Task 1
('2024-12-07 10:00:00', '2024-12-07 18:00:00', 2, 2), -- Employee 2 for Task 2
('2024-12-08 09:00:00', '2024-12-08 17:00:00', 3, 3), -- Employee 3 for Task 3
('2024-12-09 12:00:00', '2024-12-09 20:00:00', 4, 4), -- Employee 4 for Task 4
('2024-12-10 15:00:00', '2024-12-10 23:00:00', 5, 5); -- Employee 5 for Task 5

go