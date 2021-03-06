-- select * from AccountsDB.dbo.tblUserInfo
-- select * from AccountsDB_Hashmi_1819.dbo.tblUserInfo
-- select * from AccountsDB.dbo.tblSMSPurchased

--INSERT INTO AccountsDB.dbo.tblSMSPurchased(SMSPurchasedCount, userID)
--VALUES (-1300, 9)


-- Disable SMS Send Button
-- Abdul Aahad Memorial Public School, Kiratpur
--UPDATE AccountsDB.dbo.tblUserInfo SET SMSDelivery=10 WHERE UserInfoID=38

-- Rainbow Public School, Bijnor,
--UPDATE AccountsDB.dbo.tblUserInfo SET SMSDelivery=10 WHERE UserInfoID=33

-- Hashmi Academy, Chandpur,
--UPDATE AccountsDB_Hashmi_1819.dbo.tblUserInfo SET SMSDelivery=10 WHERE UserInfoID=15


DECLARE @FDT DATETIME
DECLARE @TDT DATETIME

SET @FDT = '2019-02-01'
SET @TDT = '2020-10-01'


SELECT t1.userID, 'Fatherson' School
, purchased
, IsNull(smsSent, 0) SMSSent 
, T1.Purchased - IsNull(smsSent, 0) as BalanceOnHand 
              FROM 
             ( 
              select userID, SUM(SmsPurchasedCount) Purchased 
              FROM AccountsDB.DBO.tblSMSPurchased p
			  WHERE p.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY p.UserID 
              ) T1  
              LEFT OUTER JOIN  
              ( 
              select userID, SUM(msgCount) SMSSent 
              FROM AccountsDB.DBO.tblSMSSent s
			  WHERE s.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY s.UserID 
              ) T2 ON t2.userId = t1.UserID 
              WHERE t1.USerID = 9

UNION

-- select * from KrishnaDB1920.dbo.tblUserInfo

SELECT t1.userID, 'KrishnaDB1920' School
, purchased
, IsNull(smsSent, 0) SMSSent 
, T1.Purchased - IsNull(smsSent, 0) as BalanceOnHand 
              FROM 
             ( 
              select userID, SUM(SmsPurchasedCount) Purchased 
              FROM KrishnaDB1920.DBO.tblSMSPurchased p
			  WHERE p.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY p.UserID 
              ) T1  
              LEFT OUTER JOIN  
              ( 
              select userID, SUM(msgCount) SMSSent 
              FROM KrishnaDB1920.DBO.tblSMSSent s
			  WHERE s.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY s.UserID 
              ) T2 ON t2.userId = t1.UserID 
              WHERE t1.USerID = 37

UNION

-- select * from ScholarDB1920.dbo.tblUserInfo

SELECT t1.userID, 'ScholarDB1920' School
, purchased
, IsNull(smsSent, 0) SMSSent 
, T1.Purchased - IsNull(smsSent, 0) as BalanceOnHand 
              FROM 
             ( 
              select userID, SUM(SmsPurchasedCount) Purchased 
              FROM ScholarDB1920.DBO.tblSMSPurchased p
			  WHERE p.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY p.UserID 
              ) T1  
              LEFT OUTER JOIN  
              ( 
              select userID, SUM(msgCount) SMSSent 
              FROM ScholarDB1920.DBO.tblSMSSent s
			  WHERE s.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY s.UserID 
              ) T2 ON t2.userId = t1.UserID 
              WHERE t1.USerID = 36

UNION

-- select * from HSMDB1819.dbo.tblUserInfo

SELECT t1.userID, 'HSMDB1819' School
, purchased
, IsNull(smsSent, 0) SMSSent 
, T1.Purchased - IsNull(smsSent, 0) as BalanceOnHand 
              FROM 
             ( 
              select userID, SUM(SmsPurchasedCount) Purchased 
              FROM HSMDB1819.DBO.tblSMSPurchased p
			  WHERE p.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY p.UserID 
              ) T1  
              LEFT OUTER JOIN  
              ( 
              select userID, SUM(msgCount) SMSSent 
              FROM HSMDB1819.DBO.tblSMSSent s
			  WHERE s.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY s.UserID 
              ) T2 ON t2.userId = t1.UserID 
              WHERE t1.USerID = 5

