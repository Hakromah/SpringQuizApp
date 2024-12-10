import { useParams } from "react-router-dom";

export default function QuizResult() {
	const { score, total } = useParams();
	console.log(`Score: ${score}, Total: ${total}`); // Debug log
	const percentage = ((score / total) * 100).toFixed(2);

	return (
		<div className="flex flex-col items-center text-2xl justify-center w-full h-[calc(100vh-80px)]">
			<div className="flex flex-col w-[70%] h-[50%] border-2 bg-slate-400 items-center justify-center">
				<h2>Quiz Result</h2>
				<p className="text-4xl ">
					Score: {score}/{total} ({percentage}%)
				</p>
			</div>
		</div>
	);
}
