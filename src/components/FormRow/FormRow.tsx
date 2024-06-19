import React from "react";
import { Col, Form, Row } from "react-bootstrap";

interface Props {
  label: typeof Form.Label;
  control: typeof Form.Control;
  controlId: string;
}

const FormRow = ({ label, control, controlId }: Props) => {
  return (
    <>
      <Form.Group as={Row} className="mb-3" controlId={controlId}>
        {label}
        <Col sm="10">{control}</Col>
      </Form.Group>
    </>
  );
};

export default FormRow;
