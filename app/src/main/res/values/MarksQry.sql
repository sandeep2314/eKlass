--select * from tblUserInfo

-- select * from tblExamMaster

-- select * from tblSubUser
--delete from tblExamMarks where classID=456 and SubjectID=84 and examID=33

--Update tblExamMarks set ExamId=37 where ExamId=35 and classID IN (456, 475,1705 )
---select * from tblSubUser
--select * from tblExamMarks
--- select * from tblTerm
-- select * from tblClassMaster

--select * from tblStudentMaster where studentMasterID=47203

--class III
--PT2 ke number - PT3 mai dikh rahe hai

--select * from tblExamMarks
--where 
--classID=458 and SubjectID=73 and examID=25
--and createdAT < '2018-03-22'
--
--Delete from tblExamMarks
--where 
--classID=458 and SubjectID=73 and examID=25
--and createdAT < '2018-03-22'

-- Sivam Sini 
-- 41864, 41865, 41416, 41392, 41636, 41625, 41646
--, 41577, 41357, 41364, 41955- 41354
-- 41591, 41907, 41247, 41327, 41305, 41310,
--select * from tblSubJectMaster 

-- select * from tblSubUser


--select * from tblExamMarks where subuserID=48
--delete from tblExamMarks where subuserID=48


--Delete from tblExamMarks
--where 
--1=1
--and SubjectID=102 
--and StudentID IN (41864, 41865, 41416, 41392, 41636, 41625, 41646
--, 41577, 41357, 41364, 41955- 41354
--,41591, 41907, 41247, 41327, 41305, 41310, 41991, 42006, 41183, 41241)


--select * from tblExamMarks
--where 
--1=1
--and SubjectID=102 
--and StudentID IN (41864, 41865, 41416, 41392, 41636, 41625, 41646
--, 41577, 41357, 41364, 41955- 41354
--,41591, 41907, 41247, 41327, 41305, 41310)

--Scholar
-- yearly 30, 50, 70, 100
-- Practical 30, 70, 50

--47211
--select * from tblExamMarks
--where classID=2930
--and StudentID = 47203

--update tblExamMarks SET StudentID = 47211
--where classID=2930
--and StudentID = 47203




select 
StudentID
, StudentName
, em.ClassID
, ClassName
, ExamId
, ExamName
, ExamCode
, SubjectID
, SubjectName
, MarksObtained
, MaxMarks
, m.TermId
, TermName
, em.CreatedAt
, em.subuserID
, SubUserName
, su.EmailID
, su.subuserPassword

 from tblExamMarks em
LEFT OUTER JOIN tblSubjectMaster sm ON sm.SubjectMasterID = em.SubjectId
LEFT OUTER JOIN tblClassMaster cm ON cm.classMasterID = em.classID
Left Outer  JOIN tblExamMaster m ON m.ExamMasterID= em.examId
LEFT OUTER JOIN tblStudentMaster st ON st.StudentMAsterId=em.studentID
LEFT OUTER JOIN tblTerm tm ON tm.TermId=m.termId
LEFT OUTER JOIN tblSubUser su ON su.subuserID=em.subuserID
where 1=1
AND className='VIII'
--and ExamName='EVS'
--and SubjectName='Hindi'
--AND ExamID = 42
and StudentID IN (54883)
--AND SubJectID in (155)
--,34985, 20304)
--AND em.USERID=36
--AND em.CreatedAT > '2019-11-30 20:50:58.903'

--order by examOrder
--PT 57 - annual 80
--
--update tblExamMarks set ExamID=81 
--where ExamID=102 and classID=2972
--AND SUBJECTID=110

	
-- HAlf Yearly maeks not coming in Report Card


