import { Link } from "react-router-dom";
import "@/styles/AuthCard.css";

interface LoginCardProps {
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

const LoginCard = ({ onSubmit }: LoginCardProps) => {
  return (
    <section className="auth-card-container">
      <h2 className="auth-card-container-title">Ingresar</h2>
      <form onSubmit={onSubmit} className="auth-card-container-form">
        <div className="auth-card-container-form-inputs">
          <label htmlFor="username" style={{ display: "none" }}>
            Nombre
          </label>
          <input
            className="auth-card-container-form-inputs-input"
            placeholder="Nombre"
            autoComplete="off"
            type="text"
            id="username"
          />
          <label htmlFor="password" style={{ display: "none" }}>
            Contraseña
          </label>
          <input
            className="auth-card-container-form-inputs-input"
            placeholder="Contraseña"
            autoComplete="off"
            type="password"
            id="password"
          />
        </div>
        <button type="submit" className="auth-card-container-form-button">
          Ingresar
        </button>
      </form>
      <div className="auth-card-container-footer">
        ¿No tienes una cuenta?
        <Link to="/register" className="auth-card-container-footer-link">
          Registrarse
        </Link>
      </div>
    </section>
  );
};

export default LoginCard;
