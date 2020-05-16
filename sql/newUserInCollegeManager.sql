
/*

select * from vwCls

select * from tblSubUser where userID=16

select * from tblSmsSent where userID=16


select * from tblClassMaster where userID=21
select * from tblSectionMaster where userID=21

select * from tblUserInfo


delete from tblClassMaster where classOrder = 63

delete from tblClassMaster where userID = 20

select * from tblClassMaster where userID=3
select * from tblStudentMaster where userID=3

select * from tblStudentMaster where classid = 24


select StudentName, classId, MobileF from tblStudentMaster
where userId = 16
and len(MobileF) > 10



select * from tblClassMAster where userId = 2

update tblStudentMaster set ClassId =  where classId = 17 -- I

select * from tblClassMaster where userId = 20

Update tblStudentMaster s set classId = select classOrder from tblClassMaster c
WHERE

drop view vwCls

create view vwCls as
select s.classMasterId
, s.ClassName
, s2.ClassOrder
from tblClassMaster s
JOIN tblClassMaster s2 ON s.ClassName=s2.className
where s.userID IN (3)
and s2.userId=20


UPDATE table1
SET table1.name = table2.name
FROM table1, table2
WHERE table1.id = table2.id
AND table2.foobar ='stuff'

update tblStudentMaster
set tblStudentMaster.ClassId = vwCls.classMasterId
FROM tblStudentMaster , vwCls
WHERE tblStudentMaster.classid = vwCls.classOrder
AND tblStudentMaster.userId = 3



INSERT INTO tblSubjectGroupMaster(SubjectGroupName
, SubjectGroupType, UserID, Fyear)
VALUES('Scholastic', 'Marks', 19, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('CoScholastic', 'Grade', 19, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Discipline', 'Grade', 19, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Physical', 'Grade', 19, 2014)



INSERT INTO tblExamMaster(ExamName, ExamCode, TermID, MaxMarks, Passmarks, IsFormula, Formula, ExamOrder, UserID, FYear)
VALUES('UnitTest I', 'UN1', 1, 25, 10, 0, 1,101,1,2014)

INSERT INTO tblExamMaster(ExamName, ExamCode, TermID, MaxMarks, Passmarks, IsFormula, Formula, ExamOrder, UserID, FYear)
VALUES('UnitTest II', 'UN2', 1, 25, 10, 0, 1,102,1,2014)

INSERT INTO tblExamMaster(ExamName, ExamCode, TermID, MaxMarks, Passmarks, IsFormula, Formula, ExamOrder, UserID, FYear)
VALUES('Annual Exam', 'Annual', 1, 100, 35, 0, 1,103,1,2014)
----BPS------
INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Scholastic', 'Marks', 19, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('CoScholastic', 'Grade', 19, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Discipline', 'Grade', 19, 2014)





select * from tblUserInfo
select * from tblSubUser

Update tblUserInfo set SenderId = 'SKMHLD' where userInfoId = 2

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('SKM Sr Sc School, Haldwani', 'skeswsociety@gmail.com', '123'
, getdate(), 'Y'
, 5000, '2014-11-2 00:00:00.000', '2015-11-21 00:00:00.000'
, 1, 3, 2014, 'SKMHLD')



INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('UC Sunta', '123'
, '2014-11-2 00:00:00.000'
, 'skeswsociety@gmail.com', 1, 1, 2)

----------------OakWood School ------------------------------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Oakwood School', 'manojsharma1710@gmail.com', '123'
, getdate(), 'Y'
, 5000, '2014-11-2 00:00:00.000', '2015-11-21 00:00:00.000'
, 1, 3, 2014, 'OWSBZP')



INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Manoj Sharma', '123'
, '2014-11-2 00:00:00.000'
, 'manojsharma1710@gmail.com', 1, 1, 3)




-----HSM School------------

Update tblUserInfo set SenderId = 'HSMPSK' where userInfoId = 2

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('HSM Public School, Dhakia', 'hsmpsk@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-03-17 00:00:00.000', '2016-03-17 00:00:00.000'
, 1, 3, 2014, 'HSMPSK')



INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Nitin Vatsalya', '123'
, '2015-03-17 00:00:00.000'
, 'hsmpsk@gmail.com', 1, 1, 5)


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 5, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 5, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 5, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 5, 2015)
GO


select * from tblUserInfo

-----Garden Valley School------------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Garden Valley Public School, Gaujani', 'gvpsmehra@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-03-17 00:00:00.000', '2016-03-17 00:00:00.000'
, 1, 3, 2014, 'GVPSGR')



INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('KS Mehra', '123'
, '2015-03-17 00:00:00.000'
, 'gvpsmehra@gmail.com', 1, 1, 6)


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 6, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 6, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 6, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 6, 2015)
GO

--------------Guru Nanak Model School----------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Guru Nanak Model School, Kashipur', 'sgnms.ksp@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-03-23 00:00:00.000', '2016-03-23 00:00:00.000'
, 1, 3, 2014, 'GNMSKU')




INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-03-23 00:00:00.000'
, 'sgnms.ksp@gmail.com', 1, 1, 7)


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 7, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 7, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 7, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 7, 2015)
GO



--------------Guru Nanak Senior Secondary School, Kundeshwari----------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Guru Nanak Senior Sec. School, Kashipur', 'sgns.ksp@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-03-23 00:00:00.000', '2016-03-23 00:00:00.000'
, 1, 3, 2014, 'SGNSSS')




INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-03-23 00:00:00.000'
, 'sgns.ksp@gmail.com', 1, 1, 8)


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 8, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 8, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 8, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 8, 2015)
GO




--------------Fatherson Sr. Sec. School, Chandpur----------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Fatherson Sen. Sec. Public School, Chandpur', 'vtyagi333@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-04-05 00:00:00.000', '2016-04-05 00:00:00.000'
, 1, 3, 2014, 'FSSPSC')




INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-04-05 00:00:00.000'
, 'vtyagi333@gmail.com', 1, 1, 9)


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 9, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 9, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 9, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 9, 2015)
GO


--------------Binor Public School, Bijnor----------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Bijnor Public School, Bijnor', 'bijnorPublicSchool@yahoo.in', '123'
, getdate(), 'Y'
, 0, '2015-04-05 00:00:00.000', '2016-04-05 00:00:00.000'
, 1, 3, 2014, 'BPSBZR')


INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-04-05 00:00:00.000'
, 'bijnorPublicSchool@yahoo.in', 1, 1, 10)


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 10, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 10, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 10, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 10, 2015)
GO


-------------5 CS, Lalpur, Rudrapur----------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('5th Centenary School, Lalpur(US Nagar)', '5cs@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-07-28 00:00:00.000', '2016-07-29 00:00:00.000'
, 1, 3, 2014, 'FCSLPR')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-07-28 00:00:00.000'
, '5cs@gmail.com', 1, 1, 11)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 11, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 11, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 11, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 11, 2015)
GO

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Academic', 'Marks', 11, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Personal', 'Grade', 11, 2014)


INSERT INTO tblExamMaster(ExamName, ExamCode, TermID, MaxMarks, Passmarks, IsFormula, Formula, ExamOrder, UserID, FYear)
VALUES('UnitTest I', 'UN1', 1, 25, 10, 0, 1,101,11,2014)


-----------Little Angels Sr. Sec.School, Kichha(US Nagar)-------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Little Angels Sr. Sec.School, Kichha(US Nagar)', 'info@littleangelschoolkichha.com', '123'
, getdate(), 'Y'
, 0, '2015-08-01 00:00:00.000', '2016-07-30 00:00:00.000'
, 1, 3, 2014, 'LAngel')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-08-01 00:00:00.000'
, 'info@littleangelschoolkichha.com', 1, 1, 12)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 12, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 12, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 12, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 12, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 12, 2015)
GO



INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Academic', 'Marks', 12, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Personal', 'Grade', 12, 2014)


INSERT INTO tblExamMaster(ExamName, ExamCode, TermID, MaxMarks, Passmarks, IsFormula, Formula, ExamOrder, UserID, FYear)
VALUES('UnitTest I', 'UN1', 1, 25, 10, 0, 1,101,12,2014)






----------- Christ Academy, Gopeshwar -------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Christ Academy, Gopeshwar', 'christacademygp@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-08-10 00:00:00.000', '2016-07-30 00:00:00.000'
, 1, 3, 2014, 'GSCRDP')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-08-10 00:00:00.000'
, 'christacademygp@gmail.com', 1, 1, 13)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 13, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 13, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 13, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 13, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 13, 2015)
GO



INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Academic', 'Marks', 13, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Personal', 'Grade', 13, 2014)


INSERT INTO tblExamMaster(ExamName, ExamCode, TermID, MaxMarks, Passmarks, IsFormula, Formula, ExamOrder, UserID, FYear)
VALUES('UnitTest I', 'UN1', 1, 25, 10, 0, 1,101,13,2014)





----------- Dashmesh Public School, Matkhera-------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Dashmesh Public School, Matkhera', 'dpsMatkhera.2012@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-10-05 00:00:00.000', '2016-10-05 00:00:00.000'
, 1, 3, 2014, 'DPSMTK')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-10-05 00:00:00.000'
, 'dpsMatkhera.2012@gmail.com', 1, 1, 14)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 14, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 14, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 14, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 14, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 14, 2015)
GO




----------- Hashmi Educational Academy, Chandpur -------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Hashmi Educational Academy, Chandpur', 'HashmiEducationalAcademy@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-11-07 00:00:00.000', '2016-11-07 00:00:00.000'
, 1, 3, 2014, 'HASHMI')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-11-07 00:00:00.000'
, 'HashmiEducationalAcademy@gmail.com', 1, 1, 15)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 15, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 15, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 15, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 15, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 15, 2015)
GO





----------- St Mary School, Bijnor -------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('StMary School, Bijnor', 'schoolstmary29@gmail.com', '123'
, getdate(), 'Y'
, 0, '2015-12-28 00:00:00.000', '2016-12-28 00:00:00.000'
, 1, 3, 2014, 'StMary')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-11-28 00:00:00.000'
, 'schoolstmary29@gmail.com', 1, 1, 16)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 16, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 16, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 16, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 16, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 16, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('E', 6, 16, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('F', 7, 16, 2015)
GO




----------- Navoday Inter College, Chakkimod -------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Navoday Inter College, Chakkimod'
, 'NavodayInterCollege@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2015-12-30 00:00:00.000', '2016-12-30 00:00:00.000'
, 1, 3, 2014, 'NICCHK')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-12-30 00:00:00.000'
, 'NavodayInterCollege@gmail.com', 1, 1, 17)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 17, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 17, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 17, 2015)
GO





----------- Sri Guru Hargovind, Khempur -------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Sri Hargovind School, Khempur'
, 'SriHargovind@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2015-12-30 00:00:00.000', '2016-12-30 00:00:00.000'
, 1, 3, 2014, 'SGHGVK')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-12-30 00:00:00.000'
, 'SriHargovind@gmail.com', 1, 1, 18)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 18, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 18, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 18, 2015)
GO




----------- BPS School, Rudrapur -------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('BPS School, Sharda Nagar, Rudrapur'
, 'SriHargovind@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2016-04-01 00:00:00.000', '2017-03-30 00:00:00.000'
, 1, 3, 2014, 'BPMPSR')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-12-30 00:00:00.000'
, 'bpmpschool@rediffmail.com', 1, 1, 19)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 19, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 19, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 19, 2015)
GO





----------- Sarv Siksha Abhiyan-------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Sarv Siksha Abhiyan, Rudrapur'
, 'dpossausn@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2016-04-01 00:00:00.000', '2017-03-30 00:00:00.000'
, 1, 3, 2014, 'SSAUSN')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('DPO', '123'
, '2016-04-01 00:00:00.000'
, 'dpossausn@gmail.com', 1, 1, 20)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 20, 2015)
GO


INSERT INTO tblSMSPurchased(SMSPurchasedCount, amount, USERID)
VALUES(25010, 8000, 20)
GO

INSERT INTO tblSMSPurchased(SMSPurchasedCount, amount, USERID)
VALUES(100000, 15000, 9)
GO

INSERT INTO tblSMSPurchased(SMSPurchasedCount, amount, USERID)
VALUES(25000, 4500, 22)
GO


----------- LS School, Kashipur-------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Little Scholar School, Kashipur'
, 'singhsubodh282@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2016-04-04 00:00:00.000', '2017-03-30 00:00:00.000'
, 1, 3, 2014, 'LSSKSP')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2015-12-30 00:00:00.000'
, 'singhsubodh282@gmail.com', 1, 1, 21)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 21, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 21, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 21, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 21, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 21, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('E', 6, 21, 2015)
GO

----------- Miri Piri Khalsa Acadey, Gadarpur-------

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Miri Piri Khalsa Academy, Ratanpura'
, 'principal.miripiri@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2016-04-20 00:00:00.000', '2017-03-30 00:00:00.000'
, 1, 3, 2014, 'MPKAUK')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2016-04-20 00:00:00.000'
, 'principal.miripiri@gmail.com', 1, 1, 22)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 22, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 22, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 22, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 22, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 22, 2015)
GO


INSERT INTO tblSMSPurchased(SMSPurchasedCount, amount, USERID)
VALUES(25010, 5000, 22)
GO


-------------------St MAry-Najibabad-----------------------------------------------


INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('St. Marys School, Najibabad'
, 'smsnajibabad@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2016-08-01 00:00:00.000', '2017-08-01 00:00:00.000'
, 1, 3, 2014, 'StMary')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2016-08-01 00:00:00.000'
, 'smsnajibabad@gmail.com', 1, 1, 27)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 27, 2015)
GO



-------------------AN International, Bijnor-----------------------------------------------


INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('AN International, Bijnor,'
, 'an_international_school@yahoo.com'
, '123'
, getdate(), 'Y'
, 0, '2016-08-01 00:00:00.000', '2017-08-01 00:00:00.000'
, 1, 3, 2014, 'ANIntr')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2016-08-01 00:00:00.000'
, 'an_international_school@yahoo.com', 1, 1, 28)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 28, 2015)
GO




-------------------MMc Coaching, Bijnor-----------------------------------------------


INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('MMC Institute, Bijnor,'
, 'mmcinstitute.bijnor@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2016-10-01 00:00:00.000', '2017-08-01 00:00:00.000'
, 1, 3, 2014, 'MmcBij')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2016-10-01 00:00:00.000'
, 'mmcinstitute.bijnor@gmail.com', 1, 1, 29)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 29, 2015)
GO





-------------------StMary Ghansali, Bijnor-----------------------------------------------


INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('StMary, Ghansali,'
, 'stmaryschoolgh@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2017-06-01 00:00:00.000', '2018-06-17 00:00:00.000'
, 1, 3, 2014, 'StMary')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2017-06-01 00:00:00.000'
, 'stmaryschoolgh@gmail.com', 1, 1, 30)


go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 30, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 30, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 30, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 30, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 30, 2015)
GO

-------------------MEPSMW, Bijnor-----------------------------------------------


INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('MEPS, Mandawar,'
, 'mepsmandawar@yahoo.com'
, '123'
, getdate(), 'Y'
, 0, '2017-08-01 00:00:00.000', '2018-08-17 00:00:00.000'
, 1, 3, 2014, 'MEPS')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID )
VALUES('Principal', '123'
, '2017-06-01 00:00:00.000'
, 'mepsmandawar@yahoo.com', 1, 1, 31)

go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 31, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 31, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 31, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 31, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 31, 2015)
GO




--------------------------------------------------St John School Bijnor----

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('St. John, Bijnor,'
, 'stjohnsschoolbijnor@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2017-10-21 00:00:00.000', '2018-10-12 00:00:00.000'
, 1, 3, 2014, 'SJSBJN')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID)
VALUES('Principal', '123'
, '2017-06-01 00:00:00.000'
, 'stjohnsschoolbijnor@gmail.com', 1, 1, 32)

go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 32, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 32, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 32, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 32, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 32, 2015)
GO



--------------------------------------------------Rainbow School Bijnor----

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Rainbow Public School, Bijnor,'
, 'rainbowphss326@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2017-11-17 00:00:00.000', '2018-12-12 00:00:00.000'
, 1, 3, 2014, 'RPHSBJ')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID)
VALUES('Principal', '123'
, '2017-11-17 00:00:00.000'
, 'rainbowphss326@gmail.com', 1, 1, 3)

go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 33, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 33, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 33, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 33, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 33, 2015)
GO



--------------------------------------------------RAN VIJAY BHAN SINGH JUNIOR HIGH SCHOOL, Bijnor----

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('RAN VIJAY BHAN SINGH JUNIOR HIGH SCHOOL, Bijnor,'
, 'RANVIJAYBHANSINGH234@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2017-12-14 00:00:00.000', '2018-12-12 00:00:00.000'
, 1, 3, 2014, 'RPHSBJ')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID)
VALUES('Principal', '123'
, '2017-12-14 00:00:00.000'
, 'RANVIJAYBHANSINGH234@gmail.com', 1, 1, 34)

go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 34, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 34, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 34, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 34, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 34, 2015)
GO




-----Avinash, Gurgoan----

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Demo School, Gurgoan,'
, 'digitalAviSingh@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2018-02-01 00:00:00.000', '2019-02-12 00:00:00.000'
, 1, 3, 2014, 'GSCRDP')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID)
VALUES('Avinash', '123'
, '2018-02-02 00:00:00.000'
, 'digitalAviSingh@gmail.com', 1, 1, 35)

go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 35, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 35, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 35, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 35, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 35, 2015)
GO


INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Scholastic', 'Marks', 6, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('CoScholastic', 'Grade', 6, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Discipline', 'Grade', 6, 2014)




INSERT INTO tblSubjectGroupMaster(SubjectGroupName
, SubjectGroupType, UserID, Fyear)
VALUES('Scholastic', 'Marks', 8, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('CoScholastic', 'Grade', 8, 2014)

INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Discipline', 'Grade', 8, 2014)



-- for height weight 15 Hashmi
-- 19 for BPS
INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Physical', 'Grade', 19, 2014)

-- for Remarks
INSERT INTO tblSubjectGroupMaster(SubjectGroupName, SubjectGroupType, UserID, Fyear)
VALUES('Remarks', 'Grade', 19, 2014)



-----------------Krist Jayanti School Najibabad----

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Krist Jayanti School, Najibabad,'
, 'KristJayantiSchool@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2018-10-15 00:00:00.000', '2019-03-31 00:00:00.000'
, 1, 10, 2014, 'KJSBMM')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID)
VALUES('Principal', '123'
, '2018-10-15 00:00:00.000'
, 'KristJayantiSchool@gmail.com', 1, 1, 36)

go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 36, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('A', 2, 36, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('B', 3, 36, 2015)
GO

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('C', 4, 36, 2015)
GO


INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('D', 5, 36, 2015)
GO









--sp_Help tblSMSSent

--select * from tblUserInfo
--select * from tblSubUser
--select * from tblUserInfo

--select top 10 * from tblSMSSent

---ALTER TABLE tblSMSSent alter Column SMSText VARCHAR(2000)


--ALTER TABLE tblExamMarks alter Column MarksObtained VARCHAR(200)

---SMS Provider Table
-- DROP TABLE tblSMSProvider
CREATE TABLE tblSMSProvider
(
	ProviderID INT IDENTITY(1,1) PRIMARY KEY,
    PURL VARCHAR(500) NOT NULL,
	IsUnicode INT NOT NULL,
	Active INT NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
)
GO

INSERT INTO tblSMSProvider(PURL, Active)
	VALUES('http://sms.hspsms.com/sendSMS?username=" + sUser + "&password=2314&message=" + sMessage + "&sendername=" + sSenderID + "&smstype=TRANS&numbers=" + sNumber + "&apikey=d6a74a66-e6b8-430a-9121-52aa758c34fa', 0, 1)

INSERT INTO tblSMSProvider(PURL, Active)
	VALUES('http://sms.hspsms.com/sendSMS?username=" + sUser + "&password=2314&message=" + sMessage + "&sendername=" + sSenderID + "&smstype=TRANS&numbers=" + sNumber + "&apikey=d6a74a66-e6b8-430a-9121-52aa758c34fa',1, 1)


INSERT INTO tblSMSProvider(PURL, Active)
	VALUES('http://cloud.smsindiahub.in/vendorsms/pushsms.aspx?user=" + sUser + "&password=" + spwd + "&msisdn=" + sNumber + "&sid=" + sSenderID + "&msg=" + msg + "&fl=0&dc=8&gwid=2',1, 1)

INSERT INTO tblSMSProvider(PURL, Active)
	VALUES('http://cloud.smsindiahub.in/vendorsms/pushsms.aspx?user=" + sUser + "&password=" + spwd + "&msisdn=" + sNumber + "&sid=" + sSenderID + "&msg=" + msg + "&fl=0&dc=8&gwid=2',1, 1)

---Upload TC Table
-- DROP TABLE tblTC
CREATE TABLE tblTC
(
	TcId INT IDENTITY(1,1) PRIMARY KEY,
	SchoolNo VARCHAR(20) NOT NULL,
    AdmNo VARCHAR(20) NOT NULL,
	UploadedFileName VARCHAR(500) NOT NULL,
	SubUserId INT NOT NULL,
	UserId INT NOT NULL,
	FYear INT NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
)
GO


--DROP TABLE tblFee

CREATE TABLE dbo.tblFee
(
	FeeID [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    PID           VARCHAR(20) NOT NULL,
    StudentName   VARCHAR(100),
    FeeHead 	  VARCHAR(100),
	FeeHead 	  VARCHAR(40),
	FeeAmount     float,
	Discount      float,
	FeeAfterDiscount float,
	PaymentRcd    float,
    BalanceAmount float,
    MobileNo      VARCHAR(50),
    ClassName     VARCHAR(50),
    [CreatedAt] [datetime] NULL DEFAULT (getdate())

)
GO


---- New User in Stock---


INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('Tirupati Wheat Products Pvt Ltd, Rudrapur,'
, 'Pulkit.Mittal@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2018-10-15 00:00:00.000', '2019-03-31 00:00:00.000'
, 1, 10, 2014, 'GSCRDP')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID)
VALUES('Pulkit', '123'
, '2018-10-15 00:00:00.000'
, 'Pulkit.Mittal@gmail.com', 1, 1, 20)

go

-- AccountsDB,
--ALTER TABLE tblUserInfo ADD SMSUser VARCHAR(50)
--ALTER TABLE tblUserInfo ADD SMSPassword VARCHAR(50)
--ALTER TABLE tblUserInfo ADD SMSDelivery int default 90


select * from tblUserInfo

Update tblUserInfo set SMSUser='fatherson'
, SMSPassword='Pantnagar@123', SMSDelivery=80
where userInfoID=9

-- Garden Valley
Update tblUserInfo set SMSUser='sandeep99'
, SMSPassword='pantnagar', SMSDelivery=80
where userInfoID=6


---------------WhiteHall--------------

-----------------Krist Jayanti School Najibabad----

-- select * from tblUserINfo

INSERT INTO tblUserInfo(UserName, Email, UserPassword
, CreationDate,isActive
, AmountPaid, startDate, EndDate
, UserType, NumberOFSubUsers, FYear, senderID)
VALUES('White Hall School, Haldwani'
, 'sandeepTFT@gmail.com'
, '123'
, getdate(), 'Y'
, 0, '2019-03-04 00:00:00.000', '2020-03-31 00:00:00.000'
, 1, 50, 2014, 'HSMPSK')

go

INSERT INTO tblSubUser(SubUserName,subUserPassword
, subuserCreationDate
, EmailId
, IsAdmin
, Active, userID)
VALUES('Principal', '123'
, '2019-03-04 00:00:00.000'
, 'sandeepTFT@gmail.com', 1, 1, 36)

go

INSERT INTO tblSectionMaster(SectionName, SectionOrder,USERID, FYear)
VALUES('', 1, 36, 2014)
GO


-----Table to Store Device IDs for Android APP---
-- DROP TABLE tblDeviceID
CREATE TABLE tblDeviceID
(
	DID INT IDENTITY(1,1) PRIMARY KEY,
    DeviceID VARCHAR(1000) NOT NULL,
	MobileNo VARCHAR(20) NOT NULL,
	Active INT NOT NULL DEFAULT 1,
	CreatedAt DATETIME DEFAULT getdate()
)
GO

-- INSERT INTO tblDeviceID(DeviceID, MobileNo, Active) VALUES('assdsseee', '9219484030', 1)


-- select * from tbltrackgps

--DROP TABLE tbltrackgps
CREATE TABLE tbltrackgps (
  trackId int IDENTITY(1,1) PRIMARY KEY,
  IMEI  bigint NOT NULL,
  Date varchar(16) NOT NULL,
  Time varchar(16) NOT NULL,
  Latitude decimal(9,6) NOT NULL,
  Longitude decimal(9,6) NOT NULL,
  Speed decimal NOT NULL,
Course varchar(16) DEFAULT NULL,
Accstatus varchar(16) DEFAULT NULL,
bats int,
AIN1 varchar(16) DEFAULT NULL,
Rfid  varchar(16) DEFAULT NULL,
Odo  varchar(16) DEFAULT NULL,
SOS  varchar(16) DEFAULT NULL,
Fuel1  varchar(16) DEFAULT NULL,
DIN1  varchar(11) DEFAULT NULL,
  DIN2  varchar(11) DEFAULT NULL,
  Accstus varchar(11) DEFAULT NULL,
  Do1  varchar(11) DEFAULT NULL,
  Do2 varchar(11) DEFAULT NULL,
vehiclenum varchar(45) DEFAULT NULL,
GpsStatus varchar(2) DEFAULT NULL,
CreatedAt DATETIME DEFAULT getdate()
  )



-- DROP TABLE tblBusA
CREATE TABLE tblBusA(
	[AId] [int] IDENTITY(1,1) NOT NULL,
	[YearNo] [int] NOT NULL,
	[MonthNo] [int] NOT NULL,
	[DayNo] [int] NOT NULL,
	[StudentMasterId] [int] NOT NULL,
	[InTime] [datetime] NULL,
	[OutTime] [datetime] NULL,
	Latitude decimal(9,6)  NULL,
	Longitude decimal(9,6) NULL,
	[Status] [int] NULL,
	[IsPosted] [int] NULL,
	[IsSMSSent] [int] NULL,
	[SubUserId] [int] NOT NULL,
	[UserID] [int] NOT NULL,
	[FYear] [int] NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)

--DROp TABLE tblRouteMaster
CREATE TABLE tblRouteMaster(
	[RId] [int] IDENTITY(1,1) NOT NULL,
	RouteName VARCHAR(100) NOT NULL,
	BusNo VARCHAR(20) NULL,
	Driver VARCHAR(50) NULL,
	DriverMobile INT NULL,
	DeviceID VARCHAR(20) NULL,
	MorningStartTime [datetime] NOT NULL,
	MorningEndTime [datetime] NOT NULL,
	NoonStartTime [datetime] NOT NULL,
	NoonEndTime [datetime] NOT NULL,
	UserID [int] NOT NULL,
	[FYear] [int] NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)

ALTER TABLE tblStudentMaster ADD RouteID INT DEFAULT 0

--DROp TABLE tblRouteMaster
CREATE TABLE tblRouteMaster(
	[RId] [int] IDENTITY(1,1) NOT NULL,
	RouteName VARCHAR(100) NOT NULL,
	BusNo VARCHAR(20) NULL,
	Driver VARCHAR(50) NULL,
	DriverMobile INT NULL,
	DeviceID VARCHAR(20) NULL,
	MorningStartTime [datetime] NOT NULL,
	MorningEndTime [datetime] NOT NULL,
	NoonStartTime [datetime] NOT NULL,
	NoonEndTime [datetime] NOT NULL,
	UserID [int] NOT NULL,
	[FYear] [int] NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)


--DROp TABLE tblSMSHUB
CREATE TABLE tblSMSHUB(
	SMSHUBId [int] IDENTITY(1,1) NOT NULL,
	PurchaseDate DATETIME NOT NULL,
	SMSPurchased INT NOT NULL,
	AMOUNT INT NOT NULL,
	SMSAccount VARCHAR(20) NOT NULL,
	PresentSMSCount INT NOT NULL,
  	CreatedAt DATETIME DEFAULT getdate()
	)

--DROp TABLE tblSMSBill
CREATE TABLE tblSMSBill(
	BillId [int] IDENTITY(1,1) NOT NULL,
    BillMonth VARCHAR(50) NOT NULL,
    TotalSMSSent INT NOT NULL,
   	BillNo VARCHAR(10) NOT NULL,
    BillDate DATETIME NOT NULL,
    BillSentDate DATETIME NULL,
	BillAMOUNT INT NOT NULL,
    BillRemarks VARCHAR(100) Null,
	PaymentDate DATETIME NOT NULL,
	PaymentRcd INT NOT NULL,
    PaymentRcdFrom Varchar(50) NOT NULL,

	SMSAccount VARCHAR(20) NOT NULL,
	PresentSMSCount INT NOT NULL,
  	CreatedAt DATETIME DEFAULT getdate()
	)



----------  Begin SuperVisor -------

--DROp TABLE tblRegisterCompany
CREATE TABLE tblRegisterCompany(
	[CompanyId] [int] IDENTITY(1,1) NOT NULL,
	CompanyName VARCHAR(100) NOT NULL,
	MobileNo VARCHAR(20) NULL,
	CompanyPassword VARCHAR(50) NULL,
	DeviceToken VARCHAR(500) NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)


-- DROp TABLE tblScan
CREATE TABLE tblScan(
	ScanId [int] IDENTITY(1,1) NOT NULL,
	QRID INT NULL,
	GuardId INT NOT NULL,
	Latitude VARCHAR(20) NULL,
	Longitude VARCHAR(20) NULL,
	CompanyID [int] NOT NULL,
	CreatedAt DATETIME DEFAULT getdate()
	)

-- 1 staff type is Guard
--2 staff type is Manager

--DROp TABLE tblStaff
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
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Resident', 1, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Co-Ordinator', 2, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Principal', 3, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Admin', 9, 1);
INSERT INTO tblDesignationQR(DName, HNo, CompanyID) VALUES('Buissness Owner', 10, 1);



select  StaffId, StaffName
, DesignationID, DName
, s.MobileNo, s.CompanyId, CompanyName
from tblStaffLocation sl
LEFT OUTER JOIN tblStaff s ON s.staffID = sl.workerID
LEFT OUTER JOIN tblDesignationQR d ON d.DiD = s.DesignationID
		-- AND did < 6
LEFT OUTER JOIN tblRegisterCompany c on c.companyID = s.companyID
WHERE sl.companyID = 1


-- sql server
WITH managers AS
(
SELECT  workerID
FROM tblStaffLocation 
WHERE ManagerID = 4
AND companyID=1

UNION ALL

SELECT s.workerid
FROM tblStaffLocation s 
INNER JOIN Managers m ON s.managerID = m.workerID
AND s.companyID=1
)
SELECT workerID, staffName WorkerName 
, Did, DName, s.MobileNo, s.CompanyId, companyName 
FROM managers m
LEFT OUTER JOIN tblStaff s on s.staffid = m.workerid
LEFT OUTER JOIN tblDesignationQR d ON d.Did = s.designationID
LEFT OUTER JOIN tblRegisterCompany c ON c.companyID = s.companyID
ORDER BY did desc




-- mysql 

WITH RECURSIVE cte_name AS (
    initial_query  -- anchor member
    UNION ALL
    recursive_query -- recursive member that references to the CTE name
)
SELECT * FROM cte_name;


*/


