import { Button, Nav, Image } from "react-bootstrap";
import logo from "../assets/images/logo.png";
import "./Home.css";
import LoginForm from "../components/LoginForm/LoginForm";
import { useState } from "react";
import RegistrationForm from "../components/RegistrationForm/RegistrationForm";

interface Props {
  jwt: string;
  setJwt: (jwt: string) => void;
}

const Home = ({ jwt, setJwt }: Props) => {
  const [showLoginForm, setShowLoginForm] = useState(true);

  return (
    <>
      {/* <Nav className="bg-body-tertiary justify-content-between nav-bar">
        <Image className="img-logo" src={logo} roundedCircle alt="Logo" />
        <div>
          <Button variant="primary">Registrieren</Button>
          <Button variant="primary">Anmelden</Button>
        </div>
      </Nav> */}
      <div className="home-container">
        {showLoginForm ? (
          <LoginForm
            jwt={jwt}
            setJwt={setJwt}
            setShowLoginForm={setShowLoginForm}
          />
        ) : (
          <RegistrationForm setShowLoginForm={setShowLoginForm} />
        )}
      </div>
    </>
  );
};

export default Home;
