import { Link } from "react-router-dom";
import "@/styles/AuthCard.css";

interface AuthCardProps {
  footerLinkPath: string;
  footerLinkText: string;
  footerText: string;
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
  fields: {
    id: string;
    type: string;
    placeholder: string;
  }[];
  title: string;
}

const AuthCard = ({
  footerLinkPath,
  footerLinkText,
  footerText,
  onSubmit,
  fields,
  title,
}: AuthCardProps) => {
  return (
    <section className="login-card-container">
      <h2 className="login-card-container-title">{title}</h2>
      <form onSubmit={onSubmit} className="login-card-container-form">
        <div className="login-card-container-form-inputs">
          {fields.map(field => (
            <>
              <label htmlFor="username" style={{ display: "none" }}>
                {field.placeholder}
              </label>
              <input
                className="login-card-container-form-inputs-input"
                placeholder={field.placeholder}
                autoComplete="off"
                type={field.type}
                id={field.id}
              />
            </>
          ))}
        </div>
        <button type="submit" className="login-card-container-form-button">
          {title}
        </button>
      </form>
      <div className="login-card-container-footer">
        {footerText}{" "}
        <Link to={footerLinkPath} className="login-card-container-footer-link">
          {footerLinkText}
        </Link>
      </div>
    </section>
  );
};

export default AuthCard;
