import { FaLock } from "react-icons/fa";
import { CiMail } from "react-icons/ci";

interface Props {
  setShowLoginForm: (show: boolean) => void;
}

const RegistrationForm = ({ setShowLoginForm }: Props) => {
  return (
    <div className="content-container">
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
            Du hast schon einen Account ?{" "}
            <a onClick={() => setShowLoginForm(true)}>Login</a>
          </p>
        </div>
      </div>
    </div>
  );
};

export default RegistrationForm;
