import { NavLink, Outlet } from "react-router";

export default function RootLayout() {
	return (
		<div className="root-layout w-full">
			<header className="flex bg-blue-500 gap-5 h-16 items-center
			w-full justify-center text-2xl">
				<NavLink to="/" className="hover:text-red-700">Home</NavLink>
				<NavLink className="hover:text-red-700" to="/about">About</NavLink>
			</header>
			<main>
				<Outlet />
			</main>
		</div>
	);
}
