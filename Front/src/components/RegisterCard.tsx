import { Link } from "react-router-dom";
import "@/styles/AuthCard.css";

interface AuthCardProps {
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

const AuthCard = ({ onSubmit }: AuthCardProps) => {
  return (
    <section className="auth-card-container">
      <h2 className="auth-card-container-title">Registrarse</h2>
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
          <label htmlFor="rolesOptions" style={{ display: "none" }}>
            Rol
          </label>
          <select
            className="auth-card-container-form-inputs-input"
            defaultValue="Client"
            id="rolesOptions"
          >
            <option value="Client">Cliente</option>
            <option value="Admin">Admin</option>
          </select>
        </div>
        <button type="submit" className="auth-card-container-form-button">
          Registrarse
        </button>
      </form>
      <div className="auth-card-container-footer">
        ¿Ya tienes una cuenta?
        <Link to="/login" className="auth-card-container-footer-link">
          Ingresar
        </Link>
      </div>
    </section>
  );
};

export default AuthCard;
