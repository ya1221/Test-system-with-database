CREATE TABLE Repository(
	Repository_ID SERIAL PRIMARY KEY,
	Profession VARCHAR(50) NOT NULL,
	UNIQUE (Profession));

CREATE TABLE Answer(
	Answer_ID SERIAL PRIMARY KEY,
	Answer_Content TEXT NOT NULL,
	Repository_ID INT NOT NULL,
	FOREIGN KEY (Repository_ID) REFERENCES Repository(Repository_ID) ON DELETE CASCADE,
	UNIQUE (Answer_Content, Repository_ID));

CREATE TYPE Difficulty_ENUM AS ENUM ('Hard', 'Medium', 'Easy');

CREATE TYPE Question_Type_ENUM AS ENUM ('Close_Question', 'Open_Question');

CREATE TABLE Question(
	Question_ID SERIAL PRIMARY KEY,
	Question_Content TEXT NOT NULL,
    Difficulty Difficulty_ENUM NOT NULL,
	Question_Type Question_Type_ENUM NOT NULL,
	Repository_ID INT NOT NULL,
	FOREIGN KEY (Repository_ID) REFERENCES Repository(Repository_ID) ON DELETE CASCADE,
	UNIQUE (Question_Content, Repository_ID));

CREATE TABLE Open_Question(
	Question_ID INT PRIMARY KEY,
	Answer_ID INT,
	FOREIGN KEY (Question_ID) REFERENCES Question(Question_ID) ON DELETE CASCADE,
	FOREIGN KEY (Answer_ID) REFERENCES Answer(Answer_ID) ON DELETE CASCADE);

CREATE TABLE Close_Question(
	Question_ID INT PRIMARY KEY,
	FOREIGN KEY (Question_ID) REFERENCES Question(Question_ID) ON DELETE CASCADE);


CREATE TABLE Close_Question_with_answer(
	Question_ID INT NOT NULL,
	Answer_ID INT NOT NULL,
	Correct BOOLEAN NOT NULL,
	FOREIGN KEY (Question_ID) REFERENCES Close_Question(Question_ID) ON DELETE CASCADE,
	FOREIGN KEY (Answer_ID) REFERENCES Answer(Answer_ID) ON DELETE CASCADE,
	PRIMARY KEY(Question_ID, Answer_ID));

CREATE TYPE Exam_Type_ENUM AS ENUM ('Manual', 'Automatic');

CREATE TABLE Exam(
	Exam_ID SERIAL PRIMARY KEY,
	Time_Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	Exam_Type Exam_Type_ENUM NOT NULL,
	Repository_ID INT NOT NULL,
	FOREIGN KEY (Repository_ID) REFERENCES Repository(Repository_ID) ON DELETE CASCADE);

CREATE TABLE Question_Of_Exam(
	Exam_ID INT NOT NULL,
	Question_ID INT NOT NULL,
	FOREIGN KEY (Exam_ID) REFERENCES Exam(Exam_ID) ON DELETE CASCADE,
	FOREIGN KEY (Question_ID) REFERENCES Question(Question_ID) ON DELETE CASCADE,
	PRIMARY KEY(Exam_ID, Question_ID));

CREATE OR REPLACE FUNCTION check_before_insert_question_to_exam()
RETURNS TRIGGER AS $$
BEGIN
    IF ((SELECT COUNT(*) FROM Close_Question_with_answer WHERE Question_ID = NEW.Question_ID) < 4) AND (SELECT (Question_Type = 'Close_Question') AS question_status FROM Question WHERE Question_ID = NEW.Question_ID) THEN
        RAISE EXCEPTION 'Can not add to the exam, because this question does not have at least 4 answers.';
    END IF;

	IF EXISTS (SELECT 1 FROM Question_Of_Exam WHERE Exam_ID = NEW.Exam_ID AND Question_ID = NEW.Question_ID) THEN
        RAISE EXCEPTION 'This question already exists in the exam.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_question_to_exam
BEFORE INSERT ON Question_Of_Exam
FOR EACH ROW
EXECUTE FUNCTION check_before_insert_question_to_exam();

CREATE TABLE Question_Of_Automatic_Exam(
	Exam_ID INT NOT NULL,
	Question_ID INT NOT NULL,
	Answer_ID INT NOT NULL,
	Correct BOOLEAN NOT NULL,
	FOREIGN KEY (Exam_ID) REFERENCES Exam(Exam_ID) ON DELETE CASCADE,
	FOREIGN KEY (Question_ID) REFERENCES Question(Question_ID) ON DELETE CASCADE,
	PRIMARY KEY(Exam_ID, Question_ID, Answer_ID));