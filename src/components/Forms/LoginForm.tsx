import { useState } from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Row from "react-bootstrap/Row";

const LoginForm = () => {
  const [validated, setValidated] = useState(false);

  const handleSubmit = (event) => {
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }

    setValidated(true);
  };
  return (
    <Form noValidate validated={validated} onSubmit={handleSubmit}>
      <Form.Group as={Row} className="mb-3" controlId="validationEmail">
        <Form.Label column sm="1">
          Email
        </Form.Label>
        <Col sm="10">
          <Form.Control
            required
            type="email"
            placeholder="example@example.com"
          />
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="validationPassword">
        <Form.Label column sm="1">
          Passwort
        </Form.Label>
        <Col sm="10">
          <Form.Control required type="password" />{" "}
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="validationNickname">
        <Form.Label column sm="1">
          Nickname
        </Form.Label>
        <Col sm="10">
          <Form.Control type="text" placeholder="Plötze69" required />
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="validationHornlength">
        <Form.Label column sm="1">
          Hornlänge
        </Form.Label>{" "}
        <Col sm="10">
          <Form.Control type="number" placeholder="15" required />{" "}
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="validationGender">
        <Form.Label column sm="1">
          Geschlecht
        </Form.Label>{" "}
        <Col sm="10">
          <Form.Select>
            <option value="1">Männlich</option>
            <option value="2">Weiblich</option>
          </Form.Select>
        </Col>
      </Form.Group>

      <Form.Group
        as={Row}
        className="mb-3"
        controlId="validationAttractedToGender"
      >
        <Form.Label column sm="1">
          Was suchst du?
        </Form.Label>
        <Col sm="10">
          <Form.Select>
            <option value="2">Frauen</option>
            <option value="1">Männer</option>
            <option value="0">Beides</option>
          </Form.Select>
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="validationPhoto">
        <Form.Label column sm="1">
          Lade ein Profilbild hoch{" "}
        </Form.Label>
        <Col sm="10">
          <Form.Control required type="file" />{" "}
        </Col>
      </Form.Group>

      <Form.Group as={Row} className="mb-3" controlId="validationDescription">
        <Form.Label column sm="1">
          Beschreibung
        </Form.Label>
        <Col sm="10">
          <Form.Control
            required
            as="textarea"
            placeholder="Beschreibe dich mit ein paar Worten."
            style={{ height: "100px" }}
          />
        </Col>
      </Form.Group>

      <Button>Abbrechen</Button>
      <Button type="submit">Registrieren</Button>
    </Form>
  );
};

export default LoginForm;
