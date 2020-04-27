--select * from tblUserInfo
-- select * from tblSMSPurchased

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
              FROM tblSMSPurchased p
			  WHERE p.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY p.UserID 
              ) T1  
              LEFT OUTER JOIN  
              ( 
              select userID, SUM(msgCount) SMSSent 
              FROM tblSMSSent s
			  WHERE s.CreatedAt BETWEEN @FDT AND @TDT	
              GROUP BY s.UserID 
              ) T2 ON t2.userId = t1.UserID 
              WHERE t1.USerID = 9;

