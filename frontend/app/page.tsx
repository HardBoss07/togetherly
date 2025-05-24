import Image from "next/image";
import LoginForm from "@/app/components/LoginForm";
import SignupForm from "@/app/components/SignupForm";

export default function Home() {
    return (
       <div>
           <LoginForm/>
           <br/>
           <SignupForm/>
       </div>
    );
}
