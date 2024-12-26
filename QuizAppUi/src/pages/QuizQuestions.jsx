import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { getQuizQuestions, submitQuiz } from "../api";

export default function QuizQuestions() {
	const { quizId } = useParams();
	const [questions, setQuestions] = useState([]);
	const [responses, setResponses] = useState({});
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		getQuizQuestions(quizId)
			.then((response) => {
				setQuestions(response.data);
				console.log("API Response: " + response);
				console.log("Hello World");
			})
			.catch((error) => {
				console.error("Failed to fetch quiz questions:", error);
			})
			.finally(() => {
				setLoading(false);
			});
	}, [quizId]);

	if (loading) {
		return <p>Loading...</p>;
	}
	const handleChange = (questionId, answer) => {
		setResponses({ ...responses, [questionId]: answer });
	};

	const handleSubmit = () => {
		const formattedResponses = Object.entries(responses).map(
			([id, response]) => ({ id, response })
		);
		submitQuiz(quizId, formattedResponses)
			.then((response) => {
				const score = Number(response.data); // Server response contains the score
				console.log("RESPONSE: ", response);

				const total = questions.length; // Total number of questions
				console.log(`/result/${score}/${total}`); // Log the URL
				window.location.href = `/result/${score}/${total}`;
			})
			.catch((error) => {
				console.error("Error submitting quiz:", error);
			});
	};

	return (
		<div className="flex flex-col max-w-[800px] mx-auto my-2">
			<h2 className="flex text-4xl my-4 ml-5 pl-4 justify-center items-center">
				Quiz Questions
			</h2>
			{questions.map((q) => (
				<div key={q.id} className="border-2 ml-5 pl-4 py-2">
					<p className="text-xl pb-2">{q.questionTitle}</p>
					{[q.option1, q.option2, q.option3, q.option4].map(
						(option, index) => (
							<label key={index}>
								<div className="flex w-fit gap-1">
									<input
										type="radio"
										name={`question-${q.id}`}
										value={option}
										//className="ml-5 mr-1"
										onChange={() => handleChange(q.id, option)}
									/>
									{option}
								</div>
							</label>
						)
					)}
				</div>
			))}
			<div className="h-[30px] flex items-center justify-center py-8 mt-2">
				<button
					className="text-3xl font-bold
				bg-blue-700 text-white p-3
				rounded-md"
					onClick={handleSubmit}
				>
					Submit
				</button>
			</div>
		</div>
	);
}
