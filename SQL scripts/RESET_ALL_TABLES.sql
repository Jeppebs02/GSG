-- Step 1: Disable all foreign key constraints
EXEC sp_MSForEachTable "ALTER TABLE ? NOCHECK CONSTRAINT ALL";

-- Step 2: Delete data from tables in dependency order
DELETE FROM dbo.[Availability];      -- No dependencies
DELETE FROM dbo.Alarm;             -- Depends on Report_ID
DELETE FROM dbo.Rating;            -- Depends on Report_ID and Employee_ID
DELETE FROM dbo.Report;            -- Depends on Task_ID
DELETE FROM dbo.[Shift];             -- Depends on Employee_ID and Task_ID
DELETE FROM dbo.Task;              -- Depends on User_ID
DELETE FROM dbo.EmployeeUser;      -- Depends on Employee_ID and User_ID
DELETE FROM dbo.BusinessUser;      -- Depends on Business_ID and User_ID
DELETE FROM dbo.Employee;          -- No dependencies
DELETE FROM dbo.Business;          -- No dependencies
DELETE FROM dbo.[User];              -- Depends on Address_ID
DELETE FROM dbo.[Address];           -- No dependencies

-- Step 3: Reset identity columns
DBCC CHECKIDENT ('dbo.Availability', RESEED, 0);
DBCC CHECKIDENT ('dbo.Alarm', RESEED, 0);
DBCC CHECKIDENT ('dbo.Rating', RESEED, 0);
DBCC CHECKIDENT ('dbo.Report', RESEED, 0);
DBCC CHECKIDENT ('dbo.Shift', RESEED, 0);
DBCC CHECKIDENT ('dbo.Task', RESEED, 0);
DBCC CHECKIDENT ('dbo.EmployeeUser', RESEED, 0);
DBCC CHECKIDENT ('dbo.BusinessUser', RESEED, 0);
DBCC CHECKIDENT ('dbo.Employee', RESEED, 0);
DBCC CHECKIDENT ('dbo.Business', RESEED, 0);
DBCC CHECKIDENT ('dbo.User', RESEED, 0);
DBCC CHECKIDENT ('dbo.Address', RESEED, 0);

-- Step 4: Re-enable all foreign key constraints
EXEC sp_MSForEachTable "ALTER TABLE ? CHECK CONSTRAINT ALL";
