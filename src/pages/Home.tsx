import { Button, Nav, Image } from "react-bootstrap";
import logo from "../assets/images/logo.png";
import "./Home.css";
import LoginForm from "../components/LoginForm";
import { useState } from "react";
import RegistrationForm from "../components/RegistrationForm/RegistrationForm";

const Home = () => {
  const [showLoginForm, setShowLoginForm] = useState(false);

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
        {showLoginForm ? <LoginForm /> : <RegistrationForm />}
      </div>
    </>
  );
};

export default Home;
