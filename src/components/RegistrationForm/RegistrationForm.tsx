import "./RegistrationForm.css";
import { FaLock } from "react-icons/fa";
import { CiMail } from "react-icons/ci";
const LoginForm = () => {
  return (
    <div className="login-container">
      <div>
        <h1>Registrierung</h1>
        <div className="input-box">
          <input type="text" placeholder="Email" />
          <CiMail className="icon" />
        </div>
        <div className="input-box">
          <input type="password" placeholder="Passwort" />
          <FaLock className="icon" />
        </div>

        <button className="login-button" type="submit">
          Registrieren
        </button>

        <div className="register-link">
          <p>
            Du hast schon einen Account ? <a href="#">Login</a>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
