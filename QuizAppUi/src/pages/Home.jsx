import { Link } from "react-router";


export default function Home() {
	return (
		<div className=" h-screen items-center flex justify-center flex-col">
			<h1 className="text-4xl text-red-700 font-bold">
				WELCOME TO OUR HOME PAGE
			</h1>
			<div className="bg-blue-600 px-2 my-5 rounded-md text-yellow-50 font-bold text-2xl">
				<Link to="/quizlist">Start Quiz</Link>
			</div>
		</div>
	);
}
