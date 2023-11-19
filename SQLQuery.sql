CREATE TABLE [Course] (
  [Course_Id] int,
  [Name] varchar(50),
  [Credits] int,
  PRIMARY KEY ([Course_Id])
);
 
CREATE TABLE [User] (
  [User_Id] int,
  [Username] varchar(25),
  [Password] Type,
  PRIMARY KEY ([User_Id])
);
 
CREATE TABLE [Department] (
  [Department_Id] int,
  [Name] varchar(50),
  PRIMARY KEY ([Department_Id])
);
 
CREATE TABLE [Faculty] (
  [Faculty_Id] int,
  [Name] varchar(50),
  [Department_Id] int,
  PRIMARY KEY ([Faculty_Id]),
  CONSTRAINT [FK_Faculty.Department_Id]
    FOREIGN KEY ([Department_Id])
      REFERENCES [Department]([Department_Id])
);
 
CREATE TABLE [CourseSchedule] (
  [Schedule_Id] int,
  [Day] varchar(9),
  [StartTime] time,
  [EndTime] time,
  [Course_Id] int,
  PRIMARY KEY ([Course_Id]),
  CONSTRAINT [FK_CourseSchedule.Course_Id]
    FOREIGN KEY ([Course_Id])
      REFERENCES [Course]([Course_Id])
);
 
CREATE INDEX [Pk] ON  [CourseSchedule] ([Schedule_Id]);
 
CREATE TABLE [Role] (
  [Role_Id] int,
  [Name] varchar(25),
  PRIMARY KEY ([Role_Id])
);
 
CREATE TABLE [UserRole] (
  [UserRole_Id] int,
  [Role_Id] int,
  [User_Id] int,
  PRIMARY KEY ([UserRole_Id]),
  CONSTRAINT [FK_UserRole.Role_Id]
    FOREIGN KEY ([Role_Id])
      REFERENCES [Role]([Role_Id]),
  CONSTRAINT [FK_UserRole.User_Id]
    FOREIGN KEY ([User_Id])
      REFERENCES [User]([User_Id])
);
 
CREATE TABLE [CourseMaterial] (
  [Material_Id] int,
  [Material] varchar(500),
  [Course_Id] int,
  PRIMARY KEY ([Material_Id]),
  CONSTRAINT [FK_CourseMaterial.Course_Id]
    FOREIGN KEY ([Course_Id])
      REFERENCES [Course]([Course_Id])
);
 
CREATE TABLE [Student] (
  [Student_Id] int,
  [FirstName] varchar(50),
  [LastName] varchar(50),
  [Email] nvarchar(100),
  [PhoneNumber] varchar(12),
  [User_Id] int,
  PRIMARY KEY ([Student_Id]),
  CONSTRAINT [FK_Student.User_Id]
    FOREIGN KEY ([User_Id])
      REFERENCES [User]([User_Id])
);
 
CREATE TABLE [Enrolment] (
  [Enrolment_Id] int,
  [Student_Id] int,
  [Course_Id] int,
  [Enrolment_Date] date,
  [Grade] int,
  CONSTRAINT [FK_Enrolment.Student_Id]
    FOREIGN KEY ([Student_Id])
      REFERENCES [Student]([Student_Id])
);
 
CREATE INDEX [Pk] ON  [Enrolment] ([Enrolment_Id]);
 
CREATE TABLE [DepartmentHead] (
  [Head_Id] int,
  [FirstName] varchar(50),
  [LastName] varchar(50),
  [Email] nvarchar(50),
  [PhoneNumber] varchar(12),
  [Department_Id] int,
  [User_Id] int,
  PRIMARY KEY ([Head_Id]),
  CONSTRAINT [FK_DepartmentHead.Department_Id]
    FOREIGN KEY ([Department_Id])
      REFERENCES [Department]([Department_Id]),
  CONSTRAINT [FK_DepartmentHead.User_Id]
    FOREIGN KEY ([User_Id])
      REFERENCES [User]([User_Id])
);
 
CREATE TABLE [DepartmentResource] (
  [Resource_Id] int,
  [Name] varchar(50),
  [UploadDate] date,
  [Department_Id] int,
  [DepartmentHead_Id] int,
  PRIMARY KEY ([Resource_Id]),
  CONSTRAINT [FK_DepartmentResource.Department_Id]
    FOREIGN KEY ([Department_Id])
      REFERENCES [Department]([Department_Id]),
  CONSTRAINT [FK_DepartmentResource.DepartmentHead_Id]
    FOREIGN KEY ([DepartmentHead_Id])
      REFERENCES [DepartmentHead]([Head_Id])
);
 
CREATE TABLE [Adminstrator] (
  [Admin_Id] int,
  [FirstName] varchar(50),
  [LastName] varchar(50),
  [Email] nvarchar(50),
  [PhoneNumber] varchar(12),
  [User_Id] int,
  PRIMARY KEY ([Admin_Id]),
  CONSTRAINT [FK_Adminstrator.User_Id]
    FOREIGN KEY ([User_Id])
      REFERENCES [User]([User_Id])
);
 
CREATE TABLE [FacultyMember] (
  [Member_Id] int,
  [FirstName] varchar(50),
  [LastName] varchar(50),
  [Email] nvarchar(50),
  [PhoneNumber] varchar(12),
  [Faculty_Id] int,
  [User_Id] int,
  PRIMARY KEY ([Member_Id]),
  CONSTRAINT [FK_FacultyMember.User_Id]
    FOREIGN KEY ([User_Id])
      REFERENCES [User]([User_Id]),
  CONSTRAINT [FK_FacultyMember.Faculty_Id]
    FOREIGN KEY ([Faculty_Id])
      REFERENCES [Faculty]([Faculty_Id])
);