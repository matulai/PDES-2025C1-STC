import { Link } from "react-router-dom";
import "@/styles/AuthCard.css";

interface AuthCardProps {
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

const AuthCard = ({ onSubmit }: AuthCardProps) => {
  return (
    <section className="login-card-container">
      <h2 className="login-card-container-title">Registrarse</h2>
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
          <label htmlFor="rolesOptions" style={{ display: "none" }}>
            Rol
          </label>
          <select
            className="login-card-container-form-inputs-input"
            defaultValue="Client"
            id="rolesOptions"
          >
            <option value="Client">Cliente</option>
            <option value="Admin">Admin</option>
          </select>
        </div>
        <button type="submit" className="login-card-container-form-button">
          Registrarse
        </button>
      </form>
      <div className="login-card-container-footer">
        ¿Ya tienes una cuenta?
        <Link to="/login" className="login-card-container-footer-link">
          Ingresar
        </Link>
      </div>
    </section>
  );
};

export default AuthCard;
