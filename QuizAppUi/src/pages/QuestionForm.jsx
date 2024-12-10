import axios from "axios";
import { useEffect, useState } from "react";

// eslint-disable-next-line react/prop-types
export default function QuestionForm({ questionId = null, onSuccess }) {
	const [question, setQuestion] = useState({
		category: "",
		difficultylevel: "",
		questionTitle: "",
		option1: "",
		option2: "",
		option3: "",
		option4: "",
		rightAnswer: "",
	});

	const isEditMode = !!questionId;

	useEffect(() => {
		if (isEditMode) {
			// Fetch the question details for editing
			axios
				.get(`http://localhost:8087/question/getbyid/${questionId}`)
				.then((response) => setQuestion(response.data))
				.catch((error) => console.error("Error fetching question", error));
		}
	}, [questionId, isEditMode]);

	const handleChange = (e) => {
		const { name, value } = e.target;
		setQuestion({ ...question, [name]: value });
	};

	const handleSubmit = (e) => {
		e.preventDefault();

		const url = isEditMode
			? `http://localhost:8087/question/update/${questionId}`
			: `http://localhost:8087/question/add`;

		const method = isEditMode ? "put" : "post";

		axios[method](url, question)
			.then(() => {
				alert(
					isEditMode
						? "Question updated successfully"
						: "Question added successfully"
				);
				if (onSuccess) onSuccess();
			})
			.catch((error) => console.error("Error saving question", error));
	};

	return (
		<div>
			<h2>{isEditMode ? "Edit Question" : "Add New Question"}</h2>
			<form onSubmit={handleSubmit}>
				<div>
					<label>Category:</label>
					<input
						type="text"
						name="category"
						value={question.category}
						onChange={handleChange}
						required
					/>
				</div>
				<div>
					<label>Difficulty Level:</label>
					<input
						type="text"
						name="difficultylevel"
						value={question.difficultylevel}
						onChange={handleChange}
					/>
				</div>
				<div>
					<label>Question Title:</label>
					<textarea
						name="questionTitle"
						value={question.questionTitle}
						onChange={handleChange}
						required
					></textarea>
				</div>
				{[1, 2, 3, 4].map((num) => (
					<div key={num}>
						<label>{`Option ${num}:`}</label>
						<input
							type="text"
							name={`option${num}`}
							value={question[`option${num}`]}
							onChange={handleChange}
							required
						/>
					</div>
				))}
				<div>
					<label>Right Answer:</label>
					<input
						type="text"
						name="rightAnswer"
						value={question.rightAnswer}
						onChange={handleChange}
						required
					/>
				</div>
				<button type="submit">
					{isEditMode ? "Update Question" : "Add Question"}
				</button>
			</form>
		</div>
	);
}
