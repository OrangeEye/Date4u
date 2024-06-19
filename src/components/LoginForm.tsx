import "./LoginForm.css";
import { FaLock } from "react-icons/fa";
import { CiMail } from "react-icons/ci";

const LoginForm = () => {
  return (
    <div className="login-container">
      <div>
        <h1>Login</h1>
        <div className="input-box">
          <input type="text" placeholder="Email" />
          <CiMail className="icon" />
        </div>
        <div className="input-box">
          <input type="password" placeholder="Passwort" />
          <FaLock className="icon" />
        </div>

        <button className="login-button" type="submit">
          Login
        </button>

        <div className="register-link">
          <p>
            Du hast keinen Account? <a href="#">Registrierung</a>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
