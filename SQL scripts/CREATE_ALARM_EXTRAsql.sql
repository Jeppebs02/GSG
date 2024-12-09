CREATE TABLE AlarmExtra (ID INT IDENTITY(1,1) PRIMARY KEY, -- Auto-incrementing primary key
    
	[Alarm_ID] INT FOREIGN KEY REFERENCES Alarm(ID), -- Foreign key constraint

	[Description] NVARCHAR(100) NOT NULL
	
	);