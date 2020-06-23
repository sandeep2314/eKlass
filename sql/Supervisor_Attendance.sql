use gardenvalleyDB
-- select * from tblStaff
-- select * from tblscan

-- update tblScan set createdAt = '2020-06-18 20:50:47.433'where scanID=24

SELECT staffName
, MAX(day1)day1
, MAX(day18) day18
, MIN(day18_min) day18_min
, DATEDIFF(mi, MIN(day18_min), MAX(day18))/60 duration
, MAX(day19) day19
, MAX(day20) day20
, MAX(day23) day23

FROM
(
select staffName, d.dayNo, dayNo2
, case when dayNo = 1 
   THEN  MAX(a.createdAT) END day1 	
, case when dayNo = 18 
   THEN  MAX(a.createdAT) END day18 	
, case when dayNo = 19 
   THEN  MAX(a.createdAT) END day19 	

, case when dayNo = 20 
   THEN  MAX(a.createdAT) END day20 	

, case when dayNo = 23 
   THEN  MAX(a.createdAT) END day23 	


, case when dayNo = 18 
   THEN  MIN(a.createdAT) END day18_min 	

from tblDT d
LEFT OUTER JOIN
(
select staffid
, StaffName
, sc.createdAT, Day(sc.createdAT) dayNo2
from tblStaff s
left outer join tblScan sc ON sc.GuardId = s.staffid
WHERE s.companyId=1
and designationId <> 124
) as a
  ON a.dayNo2 = d.DayNO
WHERE staffName is not null
GROUP BY StaffName, DayNO, DayNo2
) as b
GROUP BY StaffName