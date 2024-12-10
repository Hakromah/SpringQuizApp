import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RootLayout from "./layouts/RootLayout";
import About from "./pages/About";
import Home from "./pages/Home";
import QuizList from "./pages/QuizList";
import QuizQuestions from "./pages/QuizQuestions";
import QuizResult from "./pages/QuizResult";

const router = createBrowserRouter([
	{
		path: "/",
		element: <RootLayout />,
		children: [
			{ path: "/", element: <Home /> },
			{ path: "/about", element: <About /> },
			{ path: "/quizlist", element: <QuizList /> },
			{ path: "/quiz/:quizId", element: <QuizQuestions /> }, // Ensure param name matches backend
			{ path: "/result/:score/:total", element: <QuizResult /> },
		],
	},
]);

function App() {
	return <RouterProvider router={router} />;
}

export default App;
