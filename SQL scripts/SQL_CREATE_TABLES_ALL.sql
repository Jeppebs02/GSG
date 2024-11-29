-- Table: Address
CREATE TABLE Address (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    Zipcode NVARCHAR(10) NOT NULL,
    City NVARCHAR(100) NOT NULL,
    Street NVARCHAR(100) NOT NULL,
    Number NVARCHAR(20) NOT NULL,
    CONSTRAINT UC_Address UNIQUE (Zipcode, City, Street, Number)
);

-- Table: User
CREATE TABLE [User] (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    UserName NVARCHAR(20) NOT NULL UNIQUE,
    [Password] NVARCHAR(20) NOT NULL,
    FirstName NVARCHAR(20) NOT NULL,
    LastName NVARCHAR(20) NOT NULL,
    Email NVARCHAR(30) NOT NULL UNIQUE,
    PhoneNr NVARCHAR(20) NOT NULL,
    AccountPrivileges NVARCHAR(20) NOT NULL, --ENUM
    [Type] NVARCHAR(20) NOT NULL,
    Address_ID INT NOT NULL,
    CONSTRAINT FK_User_Address FOREIGN KEY (Address_ID) REFERENCES Address(ID),
	CONSTRAINT CK_AccountPrivileges CHECK (AccountPrivileges IN ('CEO', 'MANAGER', 'EMPLOYEE', 'CUSTOMER', 'GUEST')
);

-- Table: Business
CREATE TABLE Business (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    CVR NVARCHAR(50) NOT NULL UNIQUE,
    CompanyName NVARCHAR(100) NOT NULL
);

-- Table: Employee
CREATE TABLE Employee (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    CPR NVARCHAR(20) NOT NULL UNIQUE,
    SecurityClearance NVARCHAR(50),
    AccountNr NVARCHAR(50),
    Certification NVARCHAR(100),
    RegistrationNr NVARCHAR(50),
    Department NVARCHAR(100)
);

-- Table: EmployeeUser
CREATE TABLE EmployeeUser (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    User_ID INT NOT NULL,
    Employee_ID INT NOT NULL,
    CONSTRAINT FK_EmployeeUser_User FOREIGN KEY (User_ID) REFERENCES [User](ID),
    CONSTRAINT FK_EmployeeUser_Employee FOREIGN KEY (Employee_ID) REFERENCES Employee(ID)
);

-- Table: BusinessUser
CREATE TABLE BusinessUser (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    User_ID INT NOT NULL,
    Business_ID INT NOT NULL,
    CONSTRAINT FK_BusinessUser_User FOREIGN KEY (User_ID) REFERENCES [User](ID),
    CONSTRAINT FK_BusinessUser_Business FOREIGN KEY (Business_ID) REFERENCES Business(ID)
);

-- Table: Task
CREATE TABLE Task (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    "Description" NVARCHAR(500),
    "Location" NVARCHAR(100) NOT NULL,
    Approval NVARCHAR(50),
    [Date] DATE NOT NULL,
    User_ID INT NOT NULL,
    CONSTRAINT FK_Task_User FOREIGN KEY (User_ID) REFERENCES [User](ID)
);

-- Table: Shift
CREATE TABLE Shift (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    StartTime DATETIME NOT NULL,
    EndTime DATETIME NOT NULL,
    Employee_ID INT NOT NULL,
    Task_ID INT NOT NULL,
    CONSTRAINT FK_Shift_Employee FOREIGN KEY (Employee_ID) REFERENCES Employee(ID),
    CONSTRAINT FK_Shift_Task FOREIGN KEY (Task_ID) REFERENCES Task(ID)
);

-- Table: Report
CREATE TABLE Report (
    ReportNr INT IDENTITY(1,1) PRIMARY KEY,
    RejectionAge INT,
    RejectionAttitude NVARCHAR(100),
    RejectionAlternative NVARCHAR(100),
    AlternativeRemarks NVARCHAR(500),
    EmployeeSignature NVARCHAR(100),
    CustomerSignature NVARCHAR(100),
    Task_ID INT NOT NULL UNIQUE,
    CONSTRAINT FK_Report_Task FOREIGN KEY (Task_ID) REFERENCES Task(ID)
);

-- Table: Rating
CREATE TABLE Rating (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    SecurityScore INT,
    SecurityComment NVARCHAR(500),
    ServiceScore INT,
    ServiceComment NVARCHAR(500),
    ReportNr INT NOT NULL,
    Employee_ID INT NOT NULL,
    CONSTRAINT FK_Rating_Report FOREIGN KEY (ReportNr) REFERENCES Report(ReportNr),
    CONSTRAINT FK_Rating_Employee FOREIGN KEY (Employee_ID) REFERENCES Employee(ID)
);

-- Table: Alarm
CREATE TABLE Alarm (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    [Time] DATETIME NOT NULL,
    Description NVARCHAR(500),
    Notify NVARCHAR(100),
    Classification NVARCHAR(20) NOT NULL,
    Report_ID INT NOT NULL,
    CONSTRAINT FK_Alarm_Report FOREIGN KEY (Report_ID) REFERENCES Report(ReportNr),
    CONSTRAINT CK_Alarm_Classification CHECK (Classification IN ('GREEN', 'RED'))
);


-- Table: Availability
CREATE TABLE Availability (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    [Date] DATE NOT NULL,
    Unavailable NVARCHAR(20) NOT NULL,
    Employee_ID INT NOT NULL,
    CONSTRAINT FK_Availability_Employee FOREIGN KEY (Employee_ID) REFERENCES Employee(ID),
    CONSTRAINT CK_Availability_Unavailable CHECK (Unavailable IN ('SICK', 'HOLIDAY', 'UNAVAILABLE', 'INJURED'))
);

