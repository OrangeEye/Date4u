import { FaLock } from "react-icons/fa";
import { CiMail } from "react-icons/ci";
import { useState } from "react";

interface Props {
  jwt: string;
  setJwt: (jwt: string) => void;
  setShowLoginForm: (show: boolean) => void;
}

const LoginForm = ({ jwt, setJwt, setShowLoginForm }: Props) => {
  const [email, setEmail] = useState("fillmore.fat@wyman.co");
  const [password, setPassword] = useState("u87szdzwr6j");

  const handleLogin = () => {
    const xhr = new XMLHttpRequest();

    xhr.addEventListener("readystatechange", function () {
      if (this.readyState === this.DONE) {
        setJwt(this.responseText);
      }
    });

    xhr.open("POST", "http://localhost:8080/token");
    xhr.setRequestHeader(
      "Authorization",
      "Basic " + window.btoa(email + ":" + password)
    );
    xhr.send();
  };

  const getStats = () => {
    const xhr = new XMLHttpRequest();

    xhr.addEventListener("readystatechange", function () {
      if (this.readyState === this.DONE) {
        console.log(this.responseText);
      }
    });

    xhr.open("GET", "http://localhost:8080/stats");
    xhr.setRequestHeader("Authorization", "Bearer " + jwt);
    xhr.send();
  };

  function handleChangeEmail(e) {
    setEmail(e.target.value);
  }

  function handleChangePassword(e) {
    setPassword(e.target.value);
  }

  return (
    <div className="content-container">
      <div>
        <h1>Login</h1>
        <div className="input-box">
          <input type="text" placeholder="Email" onChange={handleChangeEmail} value={email} />
          <CiMail className="icon" />
        </div>
        <div className="input-box">
          <input type="password" placeholder="Passwort" onChange={handleChangePassword} value={password} />
          <FaLock className="icon" />
        </div>

        <button className="login-button" type="submit" onClick={handleLogin}>
          Login
        </button>

        <div className="register-link">
          <p>
            Du hast keinen Account?{" "}
            <a onClick={() => setShowLoginForm(false)}>Registrierung</a>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
