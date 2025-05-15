import { Link } from "react-router-dom";
import "@/styles/NotFound.css";

const NotFound = () => {
  return (
    <div className="not-found-container">
      <h1 className="not-found-container-title">404</h1>
      <h3 className="not-found-container-subtitle">Not Found</h3>
      <p className="not-found-container-text">
        La página que estas buscando no existe.
      </p>
      <Link className="not-found-container-link" to="/">
        Volver al inicio
      </Link>
    </div>
  );
};

export default NotFound;
