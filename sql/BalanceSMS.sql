--select * from tblUserInfo
-- select * from ScholarDB1920.dbo.tblSMSPurchased

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

