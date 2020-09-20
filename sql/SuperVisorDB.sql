use Master


--DROP DATABASE SuperVisorDB
--go

CREATE DATABASE [SuperVisorDB] ON  PRIMARY 
( NAME = N'SuperVisorDB'
, FILENAME = N'C:\app\DATA\SuperVisorDB.mdf' 
, SIZE = 2240KB 
, MAXSIZE = UNLIMITED
, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'SchoolDB1920_log'
, FILENAME = N'C:\app\DATA\SuperVisorDB_log.LDF' 
, SIZE = 504KB 
, MAXSIZE = 2048GB 
, FILEGROWTH = 10%)
 COLLATE SQL_Latin1_General_CP1_CI_AS
go



USE SuperVisorDB
go




CREATE TABLE tblCompany(
	[CompanyId] [int] IDENTITY(1,1) NOT NULL,
	CompanyName VARCHAR(100) NOT NULL,
	MobileNo VARCHAR(20) NULL,
	CompanyPassword VARCHAR(50) NULL,
	DeviceToken VARCHAR(500) NULL,
    ImageName VARCHAR(25) Null,
    CEmail VARCHAR(100) NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)


-- DROp TABLE tblScan
CREATE TABLE tblScan(
	ScanId [int] IDENTITY(1,1) NOT NULL,
	QRID INT NULL,
	GuardId INT NOT NULL,
	Latitude VARCHAR(20) NULL,
	Longitude VARCHAR(20) NULL,
	IsScan INT NOT NULL DEFAULT 1,
    PostType INT NOT NULL DEFAULT 1,
	CompanyID [int] NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)

-- 1 staff type is Guard
--2 staff type is Manager

-- ALTER TABLE tblStaff DROP column ImageURL
-- DROp TABLE tblStaff
CREATE TABLE tblStaff(
	StaffId [int] IDENTITY(1,1) NOT NULL,
	StaffName VARCHAR(50) NOT NULL,
	DesignationId INT NOT NULL DEFAULT 1,
	MobileNo VARCHAR(15) NOT NULL,
	StaffPassword VARCHAR(20) NOT NULL,

	IsActive INT NOT NULL DEFAULT 1, -- 1 -- Staff is Active, 0 --- Staff not active
	CompanyID [int] NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)

-- white theme = 2
ALTER TABLE tblStaff ADD ThemeNo INT DEFAULT 2;
-- OUT = 2
ALTER TABLE tblStaff ADD PostType INT DEFAULT 2;




--use GardenValleyDB
-- DROp TABLE tblProfile
CREATE TABLE tblProfile(
	ProfileId [int] IDENTITY(1,1) NOT NULL,
	MobileNo VARCHAR(15) NOT NULL,
	ImageURL VARCHAR(300) NULL,
	StaffEmail VARCHAR(100) NULL,
    Country VARCHAR(50) NULL,
  	CreatedAt DATETIME DEFAULT getdate()
	)



--DROp TABLE tblLocationQR
CREATE TABLE tblLocationQR(
	LocationId [int] IDENTITY(1,1) NOT NULL,
	LocationName VARCHAR(15) NOT NULL,
	Latitude VARCHAR(20) NULL,
	Longitude VARCHAR(20) NULL,
	CompanyID [int] NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)


-- DROP TABLE tblStaffLocation
CREATE TABLE tblStaffLocation(
	slId [int] IDENTITY(1,1) NOT NULL,
    LocationID INT NOT NULL,
    WorkerID INT NOT NULL,
	ManagerID INT NULL,
	CompanyID INT NOT NULL,
  	CreatedAt DATETIME DEFAULT getdate()
	)
	

-- DROP TABLE tblDesignationQR
CREATE TABLE tblDesignationQR(
	DId [int] IDENTITY(1,1) NOT NULL,
    DName VARCHAR(50) NOT NULL,
    HNo  INT NOT NULL,
	Dept VARCHAR(50) NULL,
    CompanyID INT NOT NULL,
  	CreatedAt DATETIME DEFAULT getdate()
	)


-- Select * from tblDesignationQR

INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Guard', 1, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Supervisor', 2, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Incharge', 3, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Manager', 4, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Nurse', 1, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Shopkeeper', 2, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Resident', 2, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Co-Ordinator', 2, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Principal', 3, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Admin', 9, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Buissness Owner', 10, 1);


