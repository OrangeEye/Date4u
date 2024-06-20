import { useState } from "react";

import "./App.css";
import Home from "./pages/Home";
import Profile from "./components/Profile/Profile";

function App() {
  const [jwt, setJwt] = useState("");

  return <div>
    {jwt ? 
    <Profile />
     : <Home jwt={jwt} setJwt={setJwt} />}</div>;
}

export default App;
