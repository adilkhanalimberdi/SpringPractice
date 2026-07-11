import {Navbar} from "../components/Navbar.tsx";

export function HomePage() {
    return (
        <div className="flex flex-col gap-5 items-center justify-center">
            <Navbar />
            <div className="">
                <p>Home page</p>
            </div>
        </div>
    )
}