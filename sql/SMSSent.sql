-- select top 1000 * from tblSMSSent order by smsid desc
-- select * from tblUserINfo

DECLARE @FDT DATETIME
DECLARE @TDT DATETIME

SET @FDT = '2020-05-10'
SET @TDT = '2020-06-16'

SELECT s.UserID, sum(MSGcount) smsCount
, UserName
--,Count(StudentMasterID) Students
, (select count(*) from AccountsDB.dbo.tblStudentMaster st where st.userID=s.UserId)Students
, sum(MSGcount)/(select count(*) from AccountsDB.dbo.tblStudentMaster st where st.userID=s.UserId) sms
, 'AccountsDB' DB
from AccountsDB.dbo.tblSMSSent s
LEFT OUTER JOIN AccountsDB.dbo.tblUserInfo u ON u.UserInfoId=s.userID
--LEFT OUTER JOIN tblStudentMaster st ON st.UserId=s.userID
where
s.CreatedAt Between @FDT
and @TDT
--'2019-01-01'
--and USERID=15
GROUP BY s.USERID, u.UserName
--ORDER BY SMSCount DESC

UNION


SELECT s.UserID, sum(MSGcount) smsCount
, UserName
--,Count(StudentMasterID) Students
, (select count(*) from BPSDB1920.dbo.tblStudentMaster st where st.userID=s.UserId)Students
, sum(MSGcount)/(select count(*) from BPSDB1920.dbo.tblStudentMaster st where st.userID=s.UserId) sms
, 'BPSDB1920' DB
from BPSDB1920.dbo.tblSMSSent s
LEFT OUTER JOIN BPSDB1920.dbo.tblUserInfo u ON u.UserInfoId=s.userID
--LEFT OUTER JOIN tblStudentMaster st ON st.UserId=s.userID
where
s.CreatedAt Between @FDT
and @TDT
--'2019-01-01'
--and USERID=15
GROUP BY s.USERID, u.UserName



UNION


SELECT s.UserID, sum(MSGcount) smsCount
, UserName
--,Count(StudentMasterID) Students
, (select count(*) from AccountsDB_Krist.dbo.tblStudentMaster st where st.userID=s.UserId)Students
, sum(MSGcount)/(select count(*) from AccountsDB_Krist.dbo.tblStudentMaster st where st.userID=s.UserId) sms
, 'AccountsDB_Krist' DB
from AccountsDB_Krist.dbo.tblSMSSent s
LEFT OUTER JOIN AccountsDB_Krist.dbo.tblUserInfo u ON u.UserInfoId=s.userID
--LEFT OUTER JOIN tblStudentMaster st ON st.UserId=s.userID
where
s.CreatedAt Between @FDT
and @TDT
--'2019-01-01'
--and USERID=15
GROUP BY s.USERID, u.UserName



UNION


SELECT s.UserID, sum(MSGcount) smsCount
, UserName
--,Count(StudentMasterID) Students
, (select count(*) from ScholarDB1920.dbo.tblStudentMaster st where st.userID=s.UserId)Students
, sum(MSGcount)/(select count(*) from ScholarDB1920.dbo.tblStudentMaster st where st.userID=s.UserId) sms
, 'ScholarDB1920' DB
from ScholarDB1920.dbo.tblSMSSent s
LEFT OUTER JOIN ScholarDB1920.dbo.tblUserInfo u ON u.UserInfoId=s.userID
--LEFT OUTER JOIN tblStudentMaster st ON st.UserId=s.userID
where
s.CreatedAt Between @FDT
and @TDT
--'2019-01-01'
--and USERID=15
GROUP BY s.USERID, u.UserName



UNION


SELECT s.UserID, sum(MSGcount) smsCount
, UserName
--,Count(StudentMasterID) Students
, (select count(*) from KrishnaDB1920.dbo.tblStudentMaster st where st.userID=s.UserId)Students
, sum(MSGcount)/(select count(*) from KrishnaDB1920.dbo.tblStudentMaster st where st.userID=s.UserId) sms
, 'KrishnaDB1920' DB
from KrishnaDB1920.dbo.tblSMSSent s
LEFT OUTER JOIN KrishnaDB1920.dbo.tblUserInfo u ON u.UserInfoId=s.userID
--LEFT OUTER JOIN tblStudentMaster st ON st.UserId=s.userID
where
s.CreatedAt Between @FDT
and @TDT
--'2019-01-01'
--and USERID=15
GROUP BY s.USERID, u.UserName


UNION


SELECT s.UserID, sum(MSGcount) smsCount
, UserName
--,Count(StudentMasterID) Students
, (select count(*) from OakWoodDB1920.dbo.tblStudentMaster st where st.userID=s.UserId)Students
, sum(MSGcount)/(select count(*) from OakWoodDB1920.dbo.tblStudentMaster st where st.userID=s.UserId) sms
, 'OakWoodDB1920' DB
from OakWoodDB1920.dbo.tblSMSSent s
LEFT OUTER JOIN OakWoodDB1920.dbo.tblUserInfo u ON u.UserInfoId=s.userID
--LEFT OUTER JOIN tblStudentMaster st ON st.UserId=s.userID
where
s.CreatedAt Between @FDT
and @TDT
--'2019-01-01'
--and USERID=15
GROUP BY s.USERID, u.UserName

UNION


SELECT s.UserID, sum(MSGcount) smsCount
, UserName
--,Count(StudentMasterID) Students
, (select count(*) from HSMDB1819.dbo.tblStudentMaster st where st.userID=s.UserId)Students
, sum(MSGcount)/(select count(*) from HSMDB1819.dbo.tblStudentMaster st where st.userID=s.UserId) sms
, 'HSMDB1819' DB
from HSMDB1819.dbo.tblSMSSent s
LEFT OUTER JOIN HSMDB1819.dbo.tblUserInfo u ON u.UserInfoId=s.userID
--LEFT OUTER JOIN tblStudentMaster st ON st.UserId=s.userID
where
s.CreatedAt Between @FDT
and @TDT
--'2019-01-01'
--and USERID=15
GROUP BY s.USERID, u.UserName

UNION

SELECT s.UserID, sum(MSGcount) smsCount
, UserName
--,Count(StudentMasterID) Students
, (select count(*) from AccountsDB_Hashmi_1819.dbo.tblStudentMaster st where st.userID=s.UserId)Students
, sum(MSGcount)/(select count(*) from AccountsDB_Hashmi_1819.dbo.tblStudentMaster st where st.userID=s.UserId) sms
, 'AccountsDB_Hashmi_1819' DB
from AccountsDB_Hashmi_1819.dbo.tblSMSSent s
LEFT OUTER JOIN AccountsDB_Hashmi_1819.dbo.tblUserInfo u ON u.UserInfoId=s.userID
--LEFT OUTER JOIN tblStudentMaster st ON st.UserId=s.userID
where
s.CreatedAt Between @FDT
and @TDT
--'2019-01-01'
--and USERID=15
GROUP BY s.USERID, u.UserName












