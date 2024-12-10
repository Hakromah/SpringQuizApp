import { useEffect, useState } from "react";
import { getQuizzes } from "../api";
import { Link } from "react-router";

export default function QuizList() {
	const [quizzes, setQuizzes] = useState([]);

	useEffect(() => {
		getQuizzes().then((response) => {
			setQuizzes(response.data);
			console.log(response);

		});
	}, []);
	console.log(quizzes);
	return (
		<div>
			<h1>Available Quizzes:</h1>
			<ul>
				{quizzes.map((quiz) => (
					<li key={quiz.id}>
						<Link to={`/quiz/${quiz.id}`}>{quiz.title}</Link>
					</li>
				))}
			</ul>
		</div>
	);
}
