import Image from "next/image";
import LoginForm from "@/app/components/LoginForm";
import SignupForm from "@/app/components/SignupForm";

export default function Home() {
    console.log("API URL:", process.env.NEXT_PUBLIC_API_URL);

    return (
       <div>
           <LoginForm/>
           <br/>
           <SignupForm/>
       </div>
    );
}
