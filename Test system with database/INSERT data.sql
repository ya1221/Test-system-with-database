-- Insert into Repository Table
INSERT INTO Repository (Profession) VALUES ('Math');
INSERT INTO Repository (Profession) VALUES ('History');

-- Insert into Answer Table
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Blue', 1);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Black', 1);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('white', 1);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Yellow', 1);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Earth', 1);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Good', 1);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Bad', 1);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Great', 1);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Terrible', 1);

INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('USA', 2);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Israel', 2);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('France', 2);
INSERT INTO Answer (Answer_Content, Repository_ID) VALUES ('Spain', 2);

-- Insert into Question Table
INSERT INTO Question (Question_Content, Difficulty, Question_Type, Repository_ID) 
VALUES ('What is the color of the sky?', 'Easy', 'Close_Question', 1);
INSERT INTO Question (Question_Content, Difficulty, Question_Type, Repository_ID) 
VALUES ('Who is bigger, the earth or the moon?', 'Medium', 'Open_Question', 1);
INSERT INTO Question (Question_Content, Difficulty, Question_Type, Repository_ID) 
VALUES ('How are you?', 'Hard', 'Close_Question', 1);

INSERT INTO Question (Question_Content, Difficulty, Question_Type, Repository_ID) 
VALUES ('Which country was established first?', 'Easy', 'Close_Question', 2);

-- Insert into Open_Question Table
INSERT INTO Open_Question (Question_ID, Answer_ID) VALUES (2, 5);

-- Insert into Close_Question Table
INSERT INTO Close_Question (Question_ID) VALUES (1);
INSERT INTO Close_Question (Question_ID) VALUES (3);

INSERT INTO Close_Question (Question_ID) VALUES (4);

-- Insert into Close_Question_with_answer Table
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (1, 1, TRUE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (1, 2, FALSE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (1, 3, FALSE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (1, 4, FALSE);

INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (3, 6, TRUE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (3, 7, FALSE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (3, 8, TRUE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (3, 9, FALSE);

INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (4, 10, FALSE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (4, 11, FALSE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (4, 12, TRUE);
INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) 
VALUES (4, 13, FALSE);

-- Insert into Exam Table
INSERT INTO Exam (Exam_Type, Repository_ID) VALUES ('Manual', 1);
INSERT INTO Exam (Exam_Type, Repository_ID) VALUES ('Automatic', 1);

-- Insert into Question_Of_Exam Table
INSERT INTO Question_Of_Exam (Exam_ID, Question_ID) VALUES (1, 1);
INSERT INTO Question_Of_Exam (Exam_ID, Question_ID) VALUES (1, 2);
INSERT INTO Question_Of_Exam (Exam_ID, Question_ID) VALUES (1, 3);

INSERT INTO Question_Of_Exam (Exam_ID, Question_ID) VALUES (2, 2);

-- Insert into Question_Of_Automatic_Exam Table
INSERT INTO Question_Of_Automatic_Exam (Exam_ID, Question_ID, Answer_ID, Correct) 
VALUES (2, 2, 3, FALSE);
