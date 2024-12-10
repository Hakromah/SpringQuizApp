import axios from "axios";

const API_BASE_URL = "http://localhost:8087"; // Adjust based on your Spring Boot server

export const getQuizzes = () =>axios.get(`${API_BASE_URL}/quiz/getQuizs`);
export const getQuizQuestions = (quizId) =>
	axios.get(`${API_BASE_URL}/quiz/getQuiz/${quizId}`);
export const submitQuiz = (quizId, responses) =>
	axios.post(`${API_BASE_URL}/quiz/submitQz/${quizId}`, responses);

