import { Link } from "react-router-dom";
import "@/styles/AuthCard.css";

interface LoginCardProps {
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

const LoginCard = ({ onSubmit }: LoginCardProps) => {
  return (
    <section className="login-card-container">
      <h2 className="login-card-container-title">Ingresar</h2>
      <form onSubmit={onSubmit} className="login-card-container-form">
        <div className="login-card-container-form-inputs">
          <label htmlFor="username" style={{ display: "none" }}>
            Nombre
          </label>
          <input
            className="login-card-container-form-inputs-input"
            placeholder="Nombre"
            autoComplete="off"
            type="text"
            id="username"
          />
          <label htmlFor="password" style={{ display: "none" }}>
            Contraseña
          </label>
          <input
            className="login-card-container-form-inputs-input"
            placeholder="Contraseña"
            autoComplete="off"
            type="password"
            id="password"
          />
        </div>
        <button type="submit" className="login-card-container-form-button">
          Ingresar
        </button>
      </form>
      <div className="login-card-container-footer">
        ¿No tienes una cuenta?
        <Link to="/register" className="login-card-container-footer-link">
          "Registrarse"
        </Link>
      </div>
    </section>
  );
};

export default LoginCard;
